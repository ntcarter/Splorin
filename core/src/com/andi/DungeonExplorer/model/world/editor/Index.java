package com.andi.DungeonExplorer.model.world.editor;

import com.andi.DungeonExplorer.model.DIRECTION;
import com.andi.DungeonExplorer.model.YSortable;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.actor.Character.Character;
import com.andi.DungeonExplorer.model.world.World;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.andi.DungeonExplorer.util.AnimationSet;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;

/**
 * Created by brytonhayes on 10/26/17.
 *
 * Cursor used to add and remove items within the map editor
 */
public class Index implements YSortable{

    private World world;            //world to be edited using this index
    private int x;                  //current x position of the index
    private int y;                  //current y position of the index

    private int firstX;             //x position of 2nd tile in selection range
    private int firstY;             //y position of 2nd tile in selection range
    private int rangeFlag = 0;      //flag to aid range selection

    private float worldX, worldY;   //middle of current view
    private TextureRegion cursor;   //index texture

    /**
     * Basic index constructor
     * @param world world for this index to edit
     * @param x starting x position
     * @param y starting y position
     */
    public Index(World world, int x, int y, TextureRegion sprite){
        this.world = world;
        this.x = x;
        this.y = y;
        this.cursor = sprite;

        //same as x,y when not selecting
        this.firstX = x;
        this.firstY = y;
    }

    /**
     * Move the index
     * @param dir Direction to move
     * @return	true if successful
     */
    public boolean move(DIRECTION dir) {
        //edge of world test
        if (x+dir.getDX() >= world.getMap().getWidth() || x+dir.getDX() < 0 ||
                y+dir.getDY() >= world.getMap().getHeight() || y+dir.getDY() < 0) {
            return false;
        }

        //set view coordinates
        this.worldX = x;
        this.worldY = y;

        //remove index from current tile
        world.getMap().getTile(x, y).setIndex(null);

        //update position
        x = x+dir.getDX();
        y = y+dir.getDY();

        //add index to new tile
        world.getMap().getTile(x, y).setIndex(this);

        //update view coordinates
        this.worldX = x;
        this.worldY = y;

        return true;
    }

    /**
     * change the world that this index will edit
     * @param world new world
     * @param newX new x pos
     * @param newY new y pos
     */
    public void changeWorld(World world, int newX, int newY) {
        this.world.removeIndex(this);   //remove index from this world
        this.teleport(newX, newY);      //set internal position
        this.world = world;             //set new world
        this.world.addIndex(this);      //add this index to the world
    }

    /**
     * checks for objects at the index position
     * @return true if an object exists at the index position
     */
    public boolean hasObject(){
        //if the object is null
        if(world.getMap().getTile(x, y).getObject() == null){
            return false;
        }
        return true;
    }

    /**
     * interact with object at index position
     * @return returns object if successful, null otherwise
     */
    public WorldObject interact(){
        //check for object
        WorldObject Object = world.getMap().getTile(this.getX(),this.getY()).getObject();
        if(Object != null){
            //not null, interact
            Object.indexInteract(this);
            return Object;
        }
        return null;
    }

    /**
     * Changes the Players position internally.
     */
    public void teleport(int x, int y) {
        this.x = x;
        this.y = y;
        this.worldX = x;
        this.worldY = y;
    }

    /**
     * remove the object at the index tile from the world
     */
    public void removeObject(){
        if(this.hasObject()){
            world.removeObject(this.getObject());
        }
    }

    /**
     * add bottom left of tree at this tile
     */
    public void addTree(){
        if(!this.hasObject()){
            world.addTree(x, y);
        }
    }

    /**
     * add flowers to this tile
     */
    public void addFlowers(){
        if(!this.hasObject()){
            world.addFlowers(x, y);
        }
    }

    /**
     * add wall or range of walls
     */
    public void addWall(){
        //first time through
        if(rangeFlag == 0){
            firstX = x;
            firstY = y;
            rangeFlag = 1;
        }
        //second time through
        else {
            //add walls from firstx,firsty to x,y
            world.addWall(firstX, firstY, (x - firstX) + 1, (y - firstY) + 1, 0);
            rangeFlag = 0;  //reset flag
        }
    }

    /**
     * currently uneeded update method
     * @param delta
     */
    public void update(float delta){

    }

    /**
     * Get the current x position of the index
     * @return x position in grid
     */
    public int getX() {
        return x;
    }

    /**
     * Get the current y position of the index
     * @return y position in grid
     */
    public int getY() { return y; }

    /**
     * Get the index texture
     * @return index texture
     */
    public TextureRegion getSprite() {
        return cursor;
    }

    /**
     * get width of sprite in tiles
     * @return width of sprite in tiles
     */
    @Override
    public float getSizeX() {
        return 1;
    }

    /**
     * get height of sprite in tiles
     * @return height of sprite in tiles
     */
    @Override
    public float getSizeY() {
        return 1;
    }

    /**
     * Get initial x position for selection range
     * @return first x position in section range
     */
    public int getFirstX() {
        return firstX;
    }

    /**
     * Get initial y position for selection range
     * @return first y position in section range
     */
    public int getFirstY() {
        return firstY;
    }

    /**
     * Get x position of view
     * @return x position of view
     */
    public float getWorldX() { return worldX; }

    /**
     * Get y position of view
     * @return y position of view
     */
    public float getWorldY() { return worldY; }

    /**
     * Get the world being edited
     * @return world being edited with this index
     */
    public World getWorld() { return world; }

    /**
     * Get object at index position
     * @return world object at the index's current position
     */
    public WorldObject getObject(){
        return world.getMap().getTile(x,y).getObject();
    }





}
