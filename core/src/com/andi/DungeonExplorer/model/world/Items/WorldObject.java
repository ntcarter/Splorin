package com.andi.DungeonExplorer.model.world.Items;

import java.util.ArrayList;
import java.util.List;

import com.andi.DungeonExplorer.controller.DialogueController;
import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.model.YSortable;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.world.cutscene.CutsceneEventQueuer;
import com.andi.DungeonExplorer.model.world.cutscene.CutscenePlayer;
import com.andi.DungeonExplorer.model.world.editor.Index;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;



public class WorldObject implements YSortable {

	private float sizeX, sizeY;
	private int x, y;
	private List<GridPoint2> tiles;
	private boolean walkable;
	private boolean grabbable;
	private boolean dialogueStarter;
	private TextureRegion texture;
	private Dialogue dialogue;
	private Animation animation;
	private float animationTimer;
	private boolean activatable;
	private int activateId;
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}



	/**
	 *
	 * This is a object that can be activated by a switch ie. Doors, spikes, traps, etc..
	 */
	public WorldObject(int x, int y, boolean walkable, TextureRegion texture,
					   float sizeX, float sizeY, GridPoint2 tile, boolean activatable, int actId) {
		this.x = x;
		this.y = y;
		this.texture = texture;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.tiles = new ArrayList<GridPoint2>();
		this.tiles.add(tile);
		this.walkable = walkable;
		this.activatable = activatable;
		this.activateId = actId;

	}



	/**
	 *	This object is an object that is grabbable
	 */
	public WorldObject(int x, int y, boolean walkable, TextureRegion texture, float sizeX, float sizeY, GridPoint2 tile, boolean grabbable) {
		this.x = x;
		this.y = y;
		this.texture = texture;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.tiles = new ArrayList<GridPoint2>();
		this.tiles.add(tile);
		this.walkable = walkable;
		this.grabbable = grabbable;
	}

	/**
	 * This object is a visual object with no interaction
	 */
	public WorldObject(int x, int y, boolean walkable, TextureRegion texture, float sizeX, float sizeY, GridPoint2[] tiles,boolean grabbable) {
		this.x = x;
		this.y = y;
		this.texture = texture;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.tiles = new ArrayList<GridPoint2>();
		for (GridPoint2 p : tiles) {
			this.tiles.add(p);
		}
		this.walkable = walkable;
		this.grabbable = grabbable;
	}

	/**
	 * Animated object with no interaction
	 */
	public WorldObject(int x, int y, boolean walkable, Animation animation, float sizeX, float sizeY, GridPoint2[] tiles,boolean grabbable) {
		this.x = x;
		this.y = y;
		this.animation = animation;
		this.animationTimer = 0f;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.tiles = new ArrayList<GridPoint2>();
		for (GridPoint2 p : tiles) {
			this.tiles.add(p);
		}
		this.walkable = walkable;
		this.grabbable = grabbable;
	}

	public void update(float delta) {
		if (animation != null) {
			animationTimer += delta;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public float getSizeX() {
		return sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public boolean isWalkable() {
		return walkable;
	}

	/**
	 * Tests if this object occupies a specific tile.
	 * @param x		world coords
	 * @param y
	 * @return		true if the object occupies tile
	 */
	public boolean containsTile(int x, int y) {
		for (GridPoint2 point : tiles) {
			if (point.x+this.x == x && point.y+this.y == y) {
				return true;
			}
		}
		return false;
	}
	public boolean isGrabbable(){
		return grabbable;
	}
	public TextureRegion getSprite() {
		if (texture != null) {
			return texture;
		} else {
			return (TextureRegion) animation.getKeyFrame(animationTimer);
		}
	}

	public List<GridPoint2> getTiles() {
		return tiles;
	}

	@Override
	public float getWorldX() {
		return x;
	}

	@Override
	public float getWorldY() {
		return y;
	}


	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}
	public boolean isActivatable() {
		return activatable;
	}

	public void setActivatable(boolean activatable) {
		this.activatable = activatable;
	}

	public int getActivateId() {
		return activateId;
	}

	public void setActivateId(int activateId) {
		this.activateId = activateId;
	}

	public void actorInteract(Actor a){

	}
	public void indexInteract(Index i){

	}
	public void setDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
	}

	public Dialogue getDialogue() {
		return dialogue;
	}
	public void setDialogueStarter(boolean dialogueStarter) {
		this.dialogueStarter = dialogueStarter;
	}

	public boolean isDialogueStarter() {
		return dialogueStarter;
	}
	public void setTexture(TextureRegion texture) {
		this.texture = texture;
	}
	public void activate(){

	}
	public void boulderActivate(){

	}
	public void boulderDeactivate(){

	}
}
