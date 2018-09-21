package com.andi.DungeonExplorer.model.world.InteractTiles;

import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.world.SpecialTiles.SwitchTile;
import com.andi.DungeonExplorer.model.world.World;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Andi Li on 11/20/2017.
 */

public class Boulder extends Actor{
    public Boulder(World world, int x, int y, TextureRegion sprite) {
        super(world, x, y, sprite);
    }
    @Override
    public void finishMove(){
         this.state = ACTOR_STATE.STANDING;
        this.worldX = destX;
        this.worldY = destY;
        this.srcX = 0;
        this.srcY = 0;
        this.destX = 0;
        this.destY = 0;

        if (!noMoveNotifications) {
            this.world.getMap().getTile(x, y).actorStep(this);
        } else {
            noMoveNotifications = false;
        }
        if(this.world.getMap().getTile(x,y) instanceof SwitchTile){
             this.world.getMap().getTile(x,y).boulderActivate(this);
        }

        else if(this.world.getMap().getTile(x,y+1) instanceof SwitchTile){
            this.world.getMap().getTile(x,y+1).boulderDeactivate(this);
        }
        else if(this.world.getMap().getTile(x,y-1) instanceof SwitchTile){
            this.world.getMap().getTile(x,y-1).boulderDeactivate(this);
        }
        else if(this.world.getMap().getTile(x+1,y) instanceof SwitchTile){
            this.world.getMap().getTile(x+1,y).boulderDeactivate(this);
        }
        else if(this.world.getMap().getTile(x-1,y) instanceof SwitchTile){
            this.world.getMap().getTile(x-1,y).boulderDeactivate(this);
        }


    }
}
