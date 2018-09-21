package com.andi.DungeonExplorer.model.world.SpecialTiles;


import com.andi.DungeonExplorer.model.DIRECTION;
import com.andi.DungeonExplorer.model.TERRAIN;
import com.andi.DungeonExplorer.model.Tile;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.actor.PlayerActor;
import com.andi.DungeonExplorer.model.world.InteractTiles.SpikeTrapObject;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.andi.DungeonExplorer.model.world.World;
import com.andi.DungeonExplorer.model.world.cutscene.ActorVisibilityEvent;
import com.andi.DungeonExplorer.model.world.cutscene.ActorWalkEvent;
import com.andi.DungeonExplorer.model.world.cutscene.CutsceneEventQueuer;
import com.andi.DungeonExplorer.model.world.cutscene.CutscenePlayer;
import com.andi.DungeonExplorer.model.world.cutscene.SpikeEvent;
import com.andi.DungeonExplorer.model.world.cutscene.WaitEvent;


/**
 * Created by Kenneth on 9/10/2017.
 */


public class SpikeTrap extends Tile {

    private CutscenePlayer player;
    private CutsceneEventQueuer broadcaster;
    private int x,y;
    /* destination */





    public SpikeTrap(TERRAIN terrain, CutscenePlayer player, CutsceneEventQueuer broadcaster,int x, int y) {
        super(terrain);
        this.x = x;
        this.y = y;
        this.player = player;
        this.broadcaster = broadcaster;
    }
    @Override
    public void actorStep(Actor a) {
        if (a instanceof PlayerActor) {
            if (this.getObject()!= null) {
                if (this.getObject() instanceof SpikeTrapObject) {
                    SpikeTrapObject trap = (SpikeTrapObject)this.getObject();
                    broadcaster.queueEvent(new SpikeEvent(trap,true));
                    a.character.inflictDamage(200);
                    a.move(a.getOppositeFacing());
                    broadcaster.queueEvent(new SpikeEvent(trap,false));
                }
            }
        }
    }



}
