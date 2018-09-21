package com.andi.DungeonExplorer.model.world.SpecialTiles;

import com.andi.DungeonExplorer.model.TERRAIN;
import com.andi.DungeonExplorer.model.Tile;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.actor.PlayerActor;

/**
 * Created by Andi Li on 11/9/2017.
 */

public class Teleporter extends Tile{
    private int newX;
    private int newY;
    public Teleporter(TERRAIN terrain,int newX, int newY) {
        super(terrain);
        this.newX = newX;
        this.newY = newY;
    }

    @Override
    public void actorStep(Actor a) {
        if (a instanceof PlayerActor) {
            a.teleport(newX,newY);
            a.getWorld().getMap().getTile(newX,newY).setActor(a);
            this.setActor(null);
        }
    }

}
