package com.andi.DungeonExplorer.model.world.Items;

import com.andi.DungeonExplorer.model.actor.Inventory.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.HashMap;

/**
 * Created by Kenneth on 10/2/2017.
 */

public class Equipment extends WorldObject {
    public HashMap<String, Integer> stats;

    public String name;//to be displayed
    public String slot;//Head, Body, Arms, Legs, MainHand, OffHand

    public Equipment(int x, int y, boolean walkable, TextureRegion texture, float sizeX, float sizeY, GridPoint2 tile, boolean grabbable, String slot, int[] statArr, String name) {
        super(x, y, walkable, texture, sizeX, sizeY, tile, grabbable);
        this.slot = slot;
        stats = new HashMap<String, Integer>();
        setStatsFromArray(statArr);
        this.name = name;
    }

    public void setStatsFromArray(int[] statArr){
        stats.put("Atk", statArr[0]);
        stats.put("Def", statArr[1]);
        stats.put("Res", statArr[2]);
        stats.put("Acc", statArr[3]);
        stats.put("Eva", statArr[4]);
        stats.put("Luk", statArr[5]);
        stats.put("Mag", statArr[6]);
        stats.put("Spd", statArr[7]);
        stats.put("HP", statArr[8]);
        stats.put("MP", statArr[9]);
    }

    public int[] getArrayFromStats(){
        int[] out = new int[10];
        out[0] = stats.get("Atk");
        out[1] = stats.get("Def");
        out[2] = stats.get("Res");
        out[3] = stats.get("Acc");
        out[4] = stats.get("Eva");
        out[5] = stats.get("Luk");
        out[6] = stats.get("Mag");
        out[7] = stats.get("Spd");
        out[8] = stats.get("HP");
        out[9] = stats.get("MP");
        return out;
    }
}
