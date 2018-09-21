package com.andi.DungeonExplorer.model.world.cutscene;

import com.andi.DungeonExplorer.model.DIRECTION;
import com.andi.DungeonExplorer.model.world.World;
import com.badlogic.gdx.graphics.Color;

/**
 * @author hydrozoa
 */
public interface CutscenePlayer {
	
	/**
	 * Smooth transition to another world.
	 * @param newWorld
	 * @param x
	 * @param y
	 * @param facing
	 * @param color
	 */
	public void changeLocation(World newWorld, int x, int y, DIRECTION facing, Color color);
	
	/**
	 * Get a loaded World from name
	 * @param worldName
	 * @return
	 */
	public World getWorld(String worldName);
}
