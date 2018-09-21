package com.andi.DungeonExplorer.model.world.InteractTiles;

import com.andi.DungeonExplorer.model.DIRECTION;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 10/15/2017.
 */

public class Portal extends WorldObject {
 
    public Portal(int x, int y, boolean walkable, TextureRegion texture, float sizeX, float sizeY, GridPoint2 tile, boolean grabbable) {
        super(x, y, walkable, texture, sizeX, sizeY, tile, grabbable);
    }

}
