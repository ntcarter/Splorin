package com.andi.DungeonExplorer.model.actor;

import com.andi.DungeonExplorer.model.actor.Inventory.Inventory;
import com.andi.DungeonExplorer.model.world.World;
import com.andi.DungeonExplorer.util.AnimationSet;


public class PlayerActor extends Actor {
	private Inventory inv;
	public PlayerActor(World world, int x, int y, AnimationSet animations) {
		super(world, x, y, animations);
		isPlayer = true;
	}

}
