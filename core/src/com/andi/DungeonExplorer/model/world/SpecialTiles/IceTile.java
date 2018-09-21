package com.andi.DungeonExplorer.model.world.SpecialTiles;

import com.andi.DungeonExplorer.model.DIRECTION;
import com.andi.DungeonExplorer.model.TERRAIN;
import com.andi.DungeonExplorer.model.Tile;
import com.andi.DungeonExplorer.model.actor.Actor;

/**
 * Created by Andi Li on 11/13/2017.
 */

public class IceTile extends Tile {


    public IceTile(TERRAIN terrain) {
        super(terrain);
    }

    @Override
    public void actorStep(Actor a) {
        DIRECTION iceD = a.getFacing();
        if(iceD == DIRECTION.NORTH){
            a.move(DIRECTION.NORTH);
        }
        else if(iceD == DIRECTION.EAST){
            a.move(DIRECTION.EAST);
        }
        else if(iceD == DIRECTION.SOUTH){
            a.move(DIRECTION.SOUTH);
        }
        else if(iceD == DIRECTION.WEST){
            a.move(DIRECTION.WEST);
        }
    }

}
