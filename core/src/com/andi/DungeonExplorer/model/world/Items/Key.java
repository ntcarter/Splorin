package com.andi.DungeonExplorer.model.world.Items;


import com.andi.DungeonExplorer.model.world.InteractTiles.Door;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 10/3/2017.
 */

public class Key extends WorldObject {
    private int keyId;


    public Key(int x, int y,TextureRegion texture,int id) {
        super(x, y,true,texture, 1f, 1f, new GridPoint2(0,0), true);
        keyId = id;
    }


    public int getKeyId() {
        return keyId;
    }


    @Override
    public String toString() {
        return "Key{" +
                "keyId=" + keyId +
                '}';
    }
}
