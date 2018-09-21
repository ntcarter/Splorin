package com.andi.DungeonExplorer.screen;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.battle.moves.Attack;
import com.andi.DungeonExplorer.controller.ActorMovementController;
import com.andi.DungeonExplorer.controller.DialogueController;
import com.andi.DungeonExplorer.controller.InteractionController;
import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.homeUI.Account.Account;
import com.andi.DungeonExplorer.model.Camera;
import com.andi.DungeonExplorer.model.DIRECTION;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.actor.Character.Character;
import com.andi.DungeonExplorer.model.actor.Character.Monster;
import com.andi.DungeonExplorer.model.actor.PlayerActor;
import com.andi.DungeonExplorer.model.world.Items.Sword;
import com.andi.DungeonExplorer.model.world.Items.Weapon;
import com.andi.DungeonExplorer.model.world.WorldLoader;
import com.andi.DungeonExplorer.model.world.World;
import com.andi.DungeonExplorer.model.world.cutscene.ActorWalkEvent;
import com.andi.DungeonExplorer.model.world.cutscene.CutsceneEvent;
import com.andi.DungeonExplorer.model.world.cutscene.CutsceneEventQueuer;
import com.andi.DungeonExplorer.model.world.cutscene.CutscenePlayer;
import com.andi.DungeonExplorer.screen.renderer.EventQueueRenderer;
import com.andi.DungeonExplorer.screen.renderer.WorldRenderer;
import com.andi.DungeonExplorer.screen.transition.Action;
import com.andi.DungeonExplorer.screen.transition.FadeInTransition;
import com.andi.DungeonExplorer.screen.transition.FadeOutTransition;
import com.andi.DungeonExplorer.ui.DialogueBox;
import com.andi.DungeonExplorer.ui.OptionBox;
import com.andi.DungeonExplorer.util.AnimationSet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.IMPACT;
import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.SLASH;


public class GameScreen extends AbstractScreen implements CutscenePlayer, CutsceneEventQueuer {

	private InputMultiplexer multiplexer;
	private DialogueController dialogueController;
	private ActorMovementController playerController;
	private InteractionController interactionController;

	private HashMap<String, World> worlds = new HashMap<String, World>();
	private World world;
	private PlayerActor player;
	private Camera camera;

	private Queue<CutsceneEvent> eventQueue = new ArrayDeque<CutsceneEvent>();
	private CutsceneEvent currentEvent;

	private SpriteBatch batch;

	private Viewport gameViewport;

	private WorldRenderer worldRenderer;
	private EventQueueRenderer queueRenderer;

	private int uiScale = 2;

	private Stage uiStage;
	private Table root;
	private DialogueBox dialogueBox;
	private OptionBox optionsBox;

	private Account account;

	public GameScreen(DungeonExplorer app, Account account) {
		super(app);
		this.account = account;
		gameViewport = new ScreenViewport();
		batch = new SpriteBatch();

		TextureAtlas atlas = app.getAssetManager().get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);

		AnimationSet rickAnimations = new AnimationSet(
				new Animation(0.3f/2f, atlas.findRegions("Rick_Walk_North"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("Rick_Walk_South"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("Rick_Walk_East"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("Rick_Walk_West"), PlayMode.LOOP_PINGPONG),
				atlas.findRegion("Rick_Stand_North"),
				atlas.findRegion("Rick_Stand_South"),
				atlas.findRegion("Rick_Stand_East"),
				atlas.findRegion("Rick_Stand_West")
		);
		AnimationSet mortyAnimations = new AnimationSet(
				new Animation(0.3f/2f, atlas.findRegions("Morty_Walk_North"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("Morty_Walk_South"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("Morty_Walk_East"), PlayMode.LOOP_PINGPONG),
				new Animation(0.3f/2f, atlas.findRegions("Morty_Walk_West"), PlayMode.LOOP_PINGPONG),
				atlas.findRegion("Morty_Stand_North"),
				atlas.findRegion("Morty_Stand_South"),
				atlas.findRegion("Morty_Stand_East"),
				atlas.findRegion("Morty_Stand_West")
		);

		worlds.put("test_level", WorldLoader.outdoorLevel(app.getAssetManager(), this, this, rickAnimations));
		worlds.put("test_indoors", WorldLoader.startRoom(app.getAssetManager(), this, this, rickAnimations));
		worlds.put("upperStar", WorldLoader.upperStar(app.getAssetManager(), this, this, rickAnimations));
		worlds.put("mainRoom", WorldLoader.mainRoom(app.getAssetManager(), this, this, rickAnimations));
		worlds.put("topRightStar", WorldLoader.topRightStar(app.getAssetManager(), this, this, rickAnimations));
		worlds.put("bottomRightStar", WorldLoader.bottomRightStar(app.getAssetManager(), this, this, rickAnimations));
		worlds.put("topLeftStar", WorldLoader.topLeftStar(app.getAssetManager(), this, this, rickAnimations));
		worlds.put("bottomLeftStar", WorldLoader.bottomLeftStar(app.getAssetManager(), this, this, rickAnimations));
		world = worlds.get("test_level");

		camera = new Camera();
		player = new PlayerActor(world, 10, 8, mortyAnimations);
		player.character = new Character();
        player.character.owner = player;
		player.character.name = "Player";
        player.name = "Player";
		player.character.levelUp();
        Sword sword = new Sword(0, 0, null, 1, 1, null, true, new int[]{10,0,0,0,0,0,0,0,0,0}, "Magic Sword");
        player.character.equip(sword);
		//give the player a bunch of health so they don't die - reduce or remove this for testing
		player.character.maxHealth += 1500;
		player.character.heal(1500);
		world.addActor(player);



		initUI();

		multiplexer = new InputMultiplexer();

		playerController = new ActorMovementController(player);
		dialogueController = new DialogueController(dialogueBox, optionsBox);
		interactionController = new InteractionController(player, dialogueController);
		multiplexer.addProcessor(0, dialogueController);
		multiplexer.addProcessor(1, playerController);
		multiplexer.addProcessor(2, interactionController);

		worldRenderer = new WorldRenderer(getApp().getAssetManager(), world);
		queueRenderer = new EventQueueRenderer(app.getSkin(), eventQueue);



	}

	@Override
	public void dispose() {

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void update(float delta) {

		if (Gdx.input.isKeyJustPressed(Keys.K)) {
			queueEvent(new ActorWalkEvent(player, DIRECTION.NORTH));
		}
		if (Gdx.input.isKeyJustPressed(Keys.X)) {
			//attack the character you face
			//System.out.println("trying attack");
            Weapon weapon = (Weapon)player.character.equipment.get("MainHand");
            if(weapon==null){
                return;
            }
            int maxRange = 1;
            for(Map.Entry entry : weapon.attacks.entrySet()){
                Attack attack = (Attack)entry.getValue();
                if(attack.range > maxRange){
                    maxRange = attack.range;
                }
            }
            Actor facingActor = null;
            int dist;
            for(dist=1; dist<=maxRange; dist++){
                facingActor = player.getFacingActor(dist);
                if(facingActor != null){break;}
            }
			if(facingActor != null && facingActor.character != null){
				System.out.println("hp before: " + facingActor.character.health);
				Character.attack(player.character, facingActor.character, weapon.chooseAttack(dist));
                //counterattack
                maxRange=1;
                Monster monster = (Monster)facingActor.character;
                for(Map.Entry entry : monster.innateAttacks.entrySet()){
                    Attack attack = (Attack)entry.getValue();
                    if(attack.range > maxRange){
                        maxRange = attack.range;
                    }
                }
                if(maxRange >= dist){//if in range for a counterattack
                    Attack attack = monster.innateAttacks.get(monster.combatAI.chooseAttack(dist));
                    System.out.println(facingActor.name + " counterattacks!");
                    Character.attack(monster, player.character, attack);
                }
                else{
                    System.out.println(facingActor.name + " can't counterattack!");
                }
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.C)) {
			//Simulate a combat until death
			//make sure the enemy is a monster or this will not work
			//System.out.println("trying attack");
			Actor facingActor = player.getFacingActor();
			while(facingActor != null && facingActor.character != null && facingActor.character.health > 0){
				System.out.println("Enemy hp before: " + facingActor.character.health);
				System.out.println("Player hp before: " + player.character.health);
				Character.attack(player.character, facingActor.character, new Attack("Punch", 12, 75, 5, IMPACT));
				//don't want to get attacked if it's already dead
				if(facingActor.character.health <= 0){
					break;
				}
				Monster monster = (Monster)facingActor.character;
				Character.attack(facingActor.character, player.character, monster.innateAttacks.get(monster.combatAI.chooseAttack(1)));
			}
		}
		if (Gdx.input.isKeyJustPressed(Keys.U)){
			player.multiplayerHandler.getPlayerPosition();
			player.teleport(player.multiplayerHandler.getxPos(), player.multiplayerHandler.getyPos());
		}
		while (currentEvent == null || currentEvent.isFinished()) { // no active event
			if (eventQueue.peek() == null) { // no event queued up
				currentEvent = null;
				break;
			} else {					// event queued up
				currentEvent = eventQueue.poll();
				currentEvent.begin(this);
			}
		}

		if (currentEvent != null) {
			currentEvent.update(delta);
		}

		if (currentEvent == null) {
			playerController.update(delta);
		}

		dialogueController.update(delta);

		if (!dialogueBox.isVisible()) {
			camera.update(player.getWorldX()+0.5f, player.getWorldY()+0.5f);
			world.update(delta);
		}
		uiStage.act(delta);
	}

	@Override
	public void render(float delta) {
		gameViewport.apply();
		batch.begin();
		worldRenderer.render(batch, camera);
		queueRenderer.render(batch, currentEvent);
		batch.end();

		uiStage.draw();
	}

	@Override
	public void resize(int width, int height) {
		batch.getProjectionMatrix().setToOrtho2D(0, 0, width, height);
		uiStage.getViewport().update(width/uiScale, height/uiScale, true);
		gameViewport.update(width, height);
	}

	@Override
	public void resume() {

	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(multiplexer);
		if (currentEvent != null) {
			currentEvent.screenShow();
		}
	}

	private void initUI() {
		uiStage = new Stage(new ScreenViewport());
		uiStage.getViewport().update(Gdx.graphics.getWidth()/uiScale, Gdx.graphics.getHeight()/uiScale, true);
		//uiStage.setDebugAll(true);

		root = new Table();
		root.setFillParent(true);
		uiStage.addActor(root);

		dialogueBox = new DialogueBox(getApp().getSkin());
		dialogueBox.setVisible(false);

		optionsBox = new OptionBox(getApp().getSkin());
		optionsBox.setVisible(false);

		Table dialogTable = new Table();
		dialogTable.add(optionsBox)
				.expand()
				.align(Align.right)
				.space(8f)
				.row();
		dialogTable.add(dialogueBox)
				.expand()
				.align(Align.bottom)
				.space(8f)
				.row();


		root.add(dialogTable).expand().align(Align.bottom);
	}

	public void changeWorld(World newWorld, int x, int y, DIRECTION face) {
		player.changeWorld(newWorld, x, y);
		this.world = newWorld;
		player.refaceWithoutAnimation(face);
		this.worldRenderer.setWorld(newWorld);
		this.camera.update(player.getWorldX()+0.5f, player.getWorldY()+0.5f);
	}

	@Override
	public void changeLocation(final World newWorld, final int x, final int y, final DIRECTION facing, Color color) {
		getApp().startTransition(
				this,
				this,
				new FadeOutTransition(0.8f, color, getApp().getTweenManager(), getApp().getAssetManager()),
				new FadeInTransition(0.8f, color, getApp().getTweenManager(), getApp().getAssetManager()),
				new Action() {
					@Override
					public void action() {
						changeWorld(newWorld, x, y, facing);
					}
				});
	}

	@Override
	public World getWorld(String worldName) {
		return worlds.get(worldName);
	}

	@Override
	public void queueEvent(CutsceneEvent event) {
		eventQueue.add(event);
	}
	public DialogueController getDialogueController() {
		return dialogueController;
	}
}
