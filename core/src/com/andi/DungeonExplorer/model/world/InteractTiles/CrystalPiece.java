package com.andi.DungeonExplorer.model.world.InteractTiles;

import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.actor.PlayerActor;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.andi.DungeonExplorer.model.world.cutscene.ChestEvent;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 11/8/2017.
 */

public class CrystalPiece extends WorldObject {
    private int crystalId;




    public CrystalPiece(int x, int y, TextureRegion texture, float sizeX, float sizeY, GridPoint2 tile, boolean grabbable, int id) {
        super(x, y,true,texture, sizeX, sizeY, tile, true);
        crystalId = id;
    }
    public int getCrystalId() {
        return crystalId;
    }
    @Override
    public String toString() {
        return "CrystalPiece{" +
                "crystalId=" + crystalId +
                '}';
    }

}
