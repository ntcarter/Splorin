package com.andi.DungeonExplorer.model.world.InteractTiles;

import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.model.DIRECTION;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.actor.PlayerActor;
import com.andi.DungeonExplorer.model.world.Items.Key;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.andi.DungeonExplorer.model.world.cutscene.ActorVisibilityEvent;
import com.andi.DungeonExplorer.model.world.cutscene.ActorWalkEvent;
import com.andi.DungeonExplorer.model.world.cutscene.ChangeWorldEvent;
import com.andi.DungeonExplorer.model.world.cutscene.CutsceneEventQueuer;
import com.andi.DungeonExplorer.model.world.cutscene.DoorEvent;
import com.andi.DungeonExplorer.model.world.editor.Index;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Timer;


public class Door extends WorldObject {

	private int doorID;
	private int oldDoorId;
	private Animation openAnimation;
	private Animation closeAnimation;
	private boolean doorChecker;
	private float animationTimer = 0f;
	private float animationTime = 0.5f;

	private STATE state = STATE.CLOSED;
	private Dialogue dialogue;
	private CutsceneEventQueuer broadcaster;
	private boolean activable;
	private int actId;


	public enum STATE {
		OPEN,
		CLOSED,
		OPENING,
		CLOSING,
		;
	}



	public Door(int x, int y, Animation openAnimation, Animation closeAnimation, int doorId, boolean activable, int actId, CutsceneEventQueuer broadcaster) {
		super(x, y, false, (TextureRegion) openAnimation.getKeyFrames()[0], 1f, 1.5f, new GridPoint2(0,0),activable,actId);
		this.activable = activable;
		this.actId=actId;
		this.openAnimation = openAnimation;
		this.closeAnimation = closeAnimation;
		this.doorID = doorId;
		oldDoorId = doorID;
		this.broadcaster = broadcaster;

	}

	public void open() {
		state = STATE.OPENING;
	}

	public void close() {
		state = STATE.CLOSING;
	}

	@Override
	public void update(float delta) {
		super.update(delta);

		if (state == STATE.CLOSING || state == STATE.OPENING) {
			animationTimer += delta;
			if (animationTimer >= animationTime) {
				if (state == STATE.CLOSING) {
					state = STATE.CLOSED;
				} else if (state == STATE.OPENING) {
					state = STATE.OPEN;
				}
				animationTimer = 0f;
			}
		}
	}

	public STATE getState() {
		return state;
	}
	public void setState(STATE state) {
		this.state = state;
	}
	public TextureRegion getSprite() {
		if (state == STATE.OPEN) {
			return (TextureRegion) closeAnimation.getKeyFrames()[3];
		} else if (state == STATE.CLOSED) {
			return (TextureRegion)  closeAnimation.getKeyFrames()[0];
		} else if (state == STATE.OPENING) {
			return (TextureRegion)  openAnimation.getKeyFrame(animationTimer);
		} else if (state == STATE.CLOSING) {
			return (TextureRegion)  closeAnimation.getKeyFrame(animationTimer);
		}
		return null;
	}
	public void setDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
	}

	public Dialogue getDialogue() {
		return dialogue;
	}

	public void unlockDoor(){
		this.doorID = 0;
	}
	public void lockDoor(){
		this.doorID = oldDoorId;
	}
	public boolean isActivable() {
		return activable;
	}

	public void setActivable(boolean activable) {
		this.activable = activable;
	}

	public int getActId() {
		return actId;
	}

	public void setActId(int actId) {
		this.actId = actId;
	}
	@Override
	public void actorInteract(Actor a) {
		if(a instanceof PlayerActor){
			interactDoor(this ,a);
			if(doorChecker == true ){
				doorChecker= false;
				broadcaster.queueEvent(new DoorEvent(this, true));
				this.setWalkable(true);
				broadcaster.queueEvent(new ActorWalkEvent(a, DIRECTION.NORTH));
				broadcaster.queueEvent(new ActorVisibilityEvent(a, true));
				broadcaster.queueEvent(new DoorEvent(this, false));
				broadcaster.queueEvent(new ChangeWorldEvent("test_indoors", 5, 0, DIRECTION.NORTH, Color.BLACK));//add world
				broadcaster.queueEvent(new ActorVisibilityEvent(a, false));
				broadcaster.queueEvent(new ActorWalkEvent(a, DIRECTION.NORTH));
			}


		}

	}

	public void indexInteract(Index i) {
		interactDoor(this ,i);
		this.setWalkable(true);
		broadcaster.queueEvent(new ChangeWorldEvent("test_indoors", 5, 0, DIRECTION.NORTH, Color.BLACK));//add world}

	}



	private Door interactDoor(Door door, Actor a) {
		int checkId = door.getDoorID();
		Key key = a.getInventory().getKey(checkId);
		if(checkId == 0){
			setDialogueStarter(false);
			doorChecker = true;
			return this;
		}
		else if(key == null){
			setDialogueStarter(true);
			doorChecker = false;
			Dialogue closed = new Dialogue();
			LinearDialogueNode node1 = new LinearDialogueNode("You need a key to open this door\n or you have the wrong key.", 0,false);
			closed.addNode(node1);
			this.setDialogue(closed);
			return this;

		}
		else if(key.getKeyId() == checkId){
			setDialogueStarter(false);
			doorChecker = true;
			return this;
		}
		return this;
	}

	private Door interactDoor(Door door, Index i) {

			doorChecker = true;
			return this;
	}

	public  int getDoorID() {
		return doorID;
	}


	@Override
	public void activate() {
		final Door door = this;
		door.unlockDoor();
		broadcaster.queueEvent(new DoorEvent(door, true));
		float delay = 5; //In seconds this time
		Timer.schedule(new Timer.Task(){
			@Override
			public void run() {
				door.lockDoor();
				broadcaster.queueEvent(new DoorEvent(door, false));
			}
		}, delay);

	}
	@Override
	public void boulderActivate(){
		final Door door = this;
		if(door.getState() == STATE.CLOSED){
			door.unlockDoor();
			broadcaster.queueEvent(new DoorEvent(door, true));
		}

	}
	@Override
	public void boulderDeactivate(){
		final Door door = this;
		if(door.getState() == STATE.OPEN){
			door.lockDoor();
			broadcaster.queueEvent(new DoorEvent(door, false));
		}
	}

}
