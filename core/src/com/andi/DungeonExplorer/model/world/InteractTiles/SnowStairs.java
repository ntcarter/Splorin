package com.andi.DungeonExplorer.model.world.InteractTiles;

import com.andi.DungeonExplorer.model.TERRAIN;
import com.andi.DungeonExplorer.model.Tile;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.andi.DungeonExplorer.model.world.World;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Ntcarter on 11/21/2017.
 */

public class SnowStairs extends WorldObject {
    private int x;
    private int y;
    private World snow;


    public SnowStairs(int x, int y, TextureRegion texture,  int actId,World world) {
        super(x, y, true, texture, 4f, 3f, new GridPoint2(0,0), true, actId);
        this.x = x;
        this.y = y;
        this.snow = world;
    }


    @Override
    public void activate() {
        snow.addObject(this);
        snow.getMap().setTile(new Tile(TERRAIN.SNOW_WALL_BOTTOM, true), 37, 35);
        snow.getMap().setTile(new Tile(TERRAIN.SNOW_WALL_BOTTOM, true), 38, 35);
        snow.getMap().setTile(new Tile(TERRAIN.SNOW_WALL_BOTTOM, true), 39, 35);
        snow.getMap().setTile(new Tile(TERRAIN.SNOW_WALL_BOTTOM, true), 40, 35);
    }
}
