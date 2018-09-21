package com.andi.DungeonExplorer.model;

import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.world.InteractTiles.Boulder;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.andi.DungeonExplorer.model.world.World;
import com.andi.DungeonExplorer.model.world.editor.Index;

public class Tile {
	
	private TERRAIN terrain;
	private WorldObject object;
	private Actor actor;
	private Index index;	//index used to edit this tile
	public int x;
	public int y;
	
	private boolean walkable = true;

	@Override
	public String toString() {
		return "Tile{" +
				"terrain=" + terrain +
				", object=" + object +
				", actor=" + actor +
				", walkable=" + walkable +
				'}';
	}

	public Tile(){}
	public Tile(TERRAIN terrain) {
		this.terrain = terrain;
	}
	
	public Tile(TERRAIN terrain, boolean walkable) {
		this.terrain = terrain;
		this.walkable = walkable;
	}

	public TERRAIN getTerrain() {
		return terrain;
	}

	public Actor getActor() {
		return actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

	public Index getIndex() {
		return index;
	}

	public void setIndex(Index index) {
		this.index = index;
	}

	public WorldObject getObject() {
		return object;
	}

	public void setObject(WorldObject object) {
		this.object = object;
	}


	public boolean walkable() {
		return walkable;
	}
	
	/**
	 * Fires when an Actor steps on the Tile. Called when the Actor is just about finished with his/her step.
	 */
	public void actorStep(Actor a) {
		
	}


	public boolean actorBeforeStep(Actor a) {
		return true;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public void boulderActivate(Boulder a){

	}
	public void boulderDeactivate(Boulder a){
		World targetWorld = a.getWorld();
		for(int i = 0;i<targetWorld.getMap().getWidth();i++){
			for(int j = 0;j<targetWorld.getMap().getHeight();j++){
				WorldObject obj = targetWorld.getMap().getTile(i,j).getObject();

				obj.boulderDeactivate();

			}
		}
	}

	public void setTerrain(TERRAIN terrain){
		this.terrain = terrain;
	}

}
