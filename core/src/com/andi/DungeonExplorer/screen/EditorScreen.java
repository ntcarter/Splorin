package com.andi.DungeonExplorer.screen;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;

import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.controller.ActorMovementController;
import com.andi.DungeonExplorer.controller.DialogueController;
import com.andi.DungeonExplorer.controller.IndexInteractionController;
import com.andi.DungeonExplorer.controller.IndexMovementController;
import com.andi.DungeonExplorer.controller.InteractionController;
import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.homeUI.Account.Account;
import com.andi.DungeonExplorer.model.Camera;
import com.andi.DungeonExplorer.model.DIRECTION;
import com.andi.DungeonExplorer.model.actor.PlayerActor;
import com.andi.DungeonExplorer.model.world.WorldLoader;
import com.andi.DungeonExplorer.model.world.World;
import com.andi.DungeonExplorer.model.world.cutscene.ActorWalkEvent;
import com.andi.DungeonExplorer.model.world.cutscene.CutsceneEvent;
import com.andi.DungeonExplorer.model.world.cutscene.CutsceneEventQueuer;
import com.andi.DungeonExplorer.model.world.cutscene.CutscenePlayer;
import com.andi.DungeonExplorer.model.world.editor.Index;
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
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class EditorScreen extends AbstractScreen implements CutscenePlayer, CutsceneEventQueuer {

    private InputMultiplexer multiplexer;
    private DialogueController dialogueController;
    private IndexMovementController indexController;
    private IndexInteractionController interactionController;

    private HashMap<String, World> worlds = new HashMap<String, World>();
    private World world;
    private Index index;
    private Camera camera;
    private Dialogue dialogue;
    private WorldLoader worldLoader;
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

    public EditorScreen(DungeonExplorer app, Account account) {
        super(app);
        this.account=account;
        gameViewport = new ScreenViewport();
        batch = new SpriteBatch();

        TextureAtlas atlas = app.getAssetManager().get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureRegion cursor = atlas.findRegion("redSquare");

        worlds.put("test_level", worldLoader.outdoorLevel(app.getAssetManager(), this, this, null));
        worlds.put("test_indoors", worldLoader.startRoom(app.getAssetManager(), this, this, null));
        worlds.put("upperStar", worldLoader.upperStar(app.getAssetManager(), this, this, null));
        worlds.put("mainRoom", worldLoader.mainRoom(app.getAssetManager(), this, this, null));
        worlds.put("topRightStar", worldLoader.topRightStar(app.getAssetManager(), this, this, null));
        worlds.put("bottomRightStar", worldLoader.bottomRightStar(app.getAssetManager(), this, this, null));
        world = worlds.get("test_level");

        camera = new Camera();
        index = new Index(world, 10, 8, cursor);
        world.addIndex(index);

        initUI();

        multiplexer = new InputMultiplexer();

        indexController = new IndexMovementController(index);
        dialogueController = new DialogueController(dialogueBox, optionsBox);
        interactionController = new IndexInteractionController(index);
        multiplexer.addProcessor(0, dialogueController);
        multiplexer.addProcessor(1, indexController);
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
            indexController.update(delta);
        }

        dialogueController.update(delta);

        if (!dialogueBox.isVisible()) {
            camera.update(index.getWorldX()+0.5f, index.getWorldY()+0.5f);
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
        index.changeWorld(newWorld, x, y);
        this.world = newWorld;
        this.worldRenderer.setWorld(newWorld);
        this.camera.update(index.getWorldX()+0.5f, index.getWorldY()+0.5f);
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

}