package com.andi.DungeonExplorer.model.world.InteractTiles;

import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.actor.PlayerActor;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.andi.DungeonExplorer.model.world.cutscene.CrystalEvent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 11/10/2017.
 */

public class ClueSign extends WorldObject {
    private String message;
    public ClueSign(int x, int y,TextureRegion texture,String message) {
        super(x, y, false, texture, 1f, 1f, new GridPoint2(0,0),false);
        this.message = message;
    }
    @Override
    public void actorInteract(Actor a) {
        if(a instanceof PlayerActor){
            setDialogueStarter(true);
            Dialogue closed = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode(message, 0,false);
            closed.addNode(node1);
            this.setDialogue(closed);
        }

    }


}
