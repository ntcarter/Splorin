package com.andi.DungeonExplorer.model.world;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.andi.DungeonExplorer.model.TERRAIN;
import com.andi.DungeonExplorer.model.Tile;
import com.andi.DungeonExplorer.model.TileMap;
import com.andi.DungeonExplorer.model.actor.ActorBehavior;
import com.andi.DungeonExplorer.model.actor.Character.Character;
import com.andi.DungeonExplorer.model.world.InteractTiles.Boulder;
import com.andi.DungeonExplorer.model.world.InteractTiles.Crystal;
import com.andi.DungeonExplorer.model.world.InteractTiles.SpikeTrapObject;
import com.andi.DungeonExplorer.model.world.Items.Key;
import com.andi.DungeonExplorer.model.world.Items.Sword;
import com.andi.DungeonExplorer.model.world.Items.Weapon;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.andi.DungeonExplorer.model.world.InteractTiles.Portal;
import com.andi.DungeonExplorer.model.world.SpecialTiles.SpikeTrap;
import com.andi.DungeonExplorer.model.world.SpecialTiles.Teleporter;
import com.andi.DungeonExplorer.model.world.cutscene.CutsceneEventQueuer;
import com.andi.DungeonExplorer.model.world.cutscene.CutscenePlayer;
import com.andi.DungeonExplorer.model.world.editor.Index;
import com.andi.DungeonExplorer.util.AnimationSet;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.andi.DungeonExplorer.model.actor.Actor;

/**
 * Contains data about the game world, such as references to Actors, and WorldObjects.
 * Query the world from here.
 */
public class World {

	/** Unique name used to refer to this world */
	private String name;

	private AssetManager assetManager;

	private TileMap map;
	private List<Actor> actors;
	private HashMap<Actor,ActorBehavior> brains;
	private List<WorldObject> objects;

	private Index index;


	public Key key;

	//animations
	public Animation flowerAnimation;
	public Animation doorOpen;
	public Animation doorClose;
	public Animation chestOpen;
	public Animation crystalChange;
	public Animation flippedChestOpen;
	public Animation spikeUp;
	public Animation spikeDown;
	private int width;
	private int height;
	private AnimationSet npcAnimations;


	public World(String name, int width, int height, AssetManager AssetManager, AnimationSet npcAnimations) {
		this.name = name;
		this.assetManager = AssetManager;
		this.map = new TileMap(width, height);
		actors = new ArrayList<Actor>();
		brains = new HashMap<Actor,ActorBehavior>();
		objects = new ArrayList<WorldObject>();
		this.width = width;
		this.height = height;
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		flowerAnimation = new Animation(0.8f, atlas.findRegions("flowers"), Animation.PlayMode.LOOP_PINGPONG);
		doorOpen = new Animation(0.8f/4f, atlas.findRegions("woodenDoor"), Animation.PlayMode.NORMAL);
		doorClose = new Animation(0.5f/4f, atlas.findRegions("woodenDoor"), Animation.PlayMode.REVERSED);
		chestOpen = new Animation(0.8f/4f, atlas.findRegions("chest"), Animation.PlayMode.NORMAL);
		flippedChestOpen = new Animation(0.8f/4f, atlas.findRegions("flippedChest"), Animation.PlayMode.NORMAL);
		crystalChange = new Animation(0.8f/4f, atlas.findRegions("crystalChange"), Animation.PlayMode.NORMAL);
		spikeUp = new Animation(0.8f/4f, atlas.findRegions("spike"), Animation.PlayMode.NORMAL);
		spikeDown = new Animation(0.8f/4f, atlas.findRegions("spike"), Animation.PlayMode.REVERSED);

		this.npcAnimations = npcAnimations;
	}

	public void addTree(int x, int y) {
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion treeRegion = atlas.findRegion("large_tree");
		GridPoint2[] gridArray = new GridPoint2[2*2];
		gridArray[0] = new GridPoint2(0,0);
		gridArray[1] = new GridPoint2(0,1);
		gridArray[2] = new GridPoint2(1,1);
		gridArray[3] = new GridPoint2(1,0);
		WorldObject tree = new WorldObject(x, y, false, treeRegion, 2f, 3f, gridArray,false);
		this.addObject(tree);
	}

    public void add6x5SnowTreeRegion(int x, int y) {
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureRegion treeRegion = atlas.findRegion("5x6SnowTrees");
        GridPoint2[] gridArray = new GridPoint2[(int)6*(int)4];
        int index = 0;
        for(int i = 0;i<6;i++){
            for(int j = 0;j<4;j++){
                gridArray[index] = new GridPoint2(i,j);
                index++;
            }
        }
        WorldObject tree = new WorldObject(x, y, false, treeRegion, 6f, 5f, gridArray,false);
        this.addObject(tree);
    }


    public void add4x19SnowTreeRegion(int x, int y) {
        TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
        TextureRegion treeRegion = atlas.findRegion("4x19SnowTrees");
        GridPoint2[] gridArray = new GridPoint2[(int)4*(int)19];
        int index = 0;
        for(int i = 0;i<4;i++){
            for(int j = 0;j<19;j++){
                gridArray[index] = new GridPoint2(i,j);
                index++;
            }
        }
        WorldObject tree = new WorldObject(x, y, false, treeRegion, 4f, 19f, gridArray,false);
        this.addObject(tree);
    }


    public void addHouse( int x, int y) {
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion houseRegion = atlas.findRegion("small_house");
		GridPoint2[] gridArray = new GridPoint2[5*4-1];
		int index = 0;
		for (int loopX = 0; loopX < 5; loopX++) {
			for (int loopY = 0; loopY < 4; loopY++) {
				if (loopX==3&&loopY==0) {
					continue;
				}
				gridArray[index] = new GridPoint2(loopX, loopY);
				index++;
			}
		}
		WorldObject house = new WorldObject(x, y, false, houseRegion, 5f, 5f, gridArray,false);
		this.addObject(house);
	}

	public void addFlowers(int x, int y) {
		GridPoint2[] gridArray = new GridPoint2[1];
		gridArray[0] = new GridPoint2(0,0);
		WorldObject flowers = new WorldObject(x, y, true, flowerAnimation, 1f, 1f, gridArray,false);
		this.addObject(flowers);
	}

	/**
	 * Adds a self-contained wall. That is, properly adds edges for this section of wall.
	 * Don't use this for sections of wall smaller than 2x2, or it'll look weird.
	 * @param startX bottom left x coord
	 * @param startY bottom left y coord
	 * @param width
	 * @param height
	 * @param tileType use to distinguish between different tilesets
	 *                 0 = purple
	 *                 1 = dungeon cave
	 *                 2 = border
	 */
	public void addWall(int startX, int startY, int width, int height, int tileType){
		//TODO different paths for different tileTypes
		if(tileType==0){
			for(int i=startX; i<startX+width; i++){
				for(int j=startY; j<startY+height; j++){
					TERRAIN terrain;
					//left edge
					if(i==startX && j==startY){
						terrain = TERRAIN.INDOOR_WALL_LEFT_BOTTOM;
					}
					else if(i==startX && j==startY+height-1){
						terrain = TERRAIN.INDOOR_WALL_LEFT_TOP;
					}
					else if(i==startX){
						terrain = TERRAIN.INDOOR_WALL_LEFT_MIDDLE;
					}
					//top edge
					else if(j==startY+height-1 && i==startX+width-1){
						terrain = TERRAIN.INDOOR_WALL_RIGHT_TOP;
					}
					else if(j==startY+height-1){
						terrain = TERRAIN.INDOOR_WALL_MIDDLE_TOP;
					}
					//right edge
					else if(i==startX+width-1 && j==startY){
						terrain = TERRAIN.INDOOR_WALL_RIGHT_BOTTOM;
					}
					else if(i==startX+width-1){
						terrain = TERRAIN.INDOOR_WALL_RIGHT_MIDDLE;
					}
					//bottom edge
					else if(j==startY){
						terrain = TERRAIN.INDOOR_WALL_MIDDLE_BOTTOM;
					}
					//fill center
					else{
						terrain = TERRAIN.INDOOR_WALL_MIDDLE_MIDDLE;
					}
					//add the tile
					this.getMap().setTile(new Tile(terrain, false), i, j);
				}
			}
		}
		else if(tileType==1){
			for(int i=startX; i<startX+width; i++){
				for(int j=startY; j<startY+height; j++){
					TERRAIN terrain;
					//left edge
					if(i==startX && j==startY){
						terrain = TERRAIN.DUNG_WALL_LEFT_BOTTOM;
					}
					else if(i==startX && j==startY+height-1){
						terrain = TERRAIN.DUNG_WALL_LEFT_TOP;
					}
					else if(i==startX){
						terrain = TERRAIN.DUNG_WALL_LEFT_MIDDLE;
					}
					//top edge
					else if(j==startY+height-1 && i==startX+width-1){
						terrain = TERRAIN.DUNG_WALL_RIGHT_TOP;
					}
					else if(j==startY+height-1){
						terrain = TERRAIN.DUNG_WALL_MIDDLE_TOP;
					}
					//right edge
					else if(i==startX+width-1 && j==startY){
						terrain = TERRAIN.DUNG_WALL_RIGHT_BOTTOM;
					}
					else if(i==startX+width-1){
						terrain = TERRAIN.DUNG_WALL_RIGHT_MIDDLE;
					}
					//bottom edge
					else if(j==startY){
						terrain = TERRAIN.DUNG_WALL_MIDDLE_BOTTOM;
					}
					//fill center
					else{
						terrain = TERRAIN.DUNG_WALL_MIDDLE_MIDDLE;
					}
					//add the tile
					this.getMap().setTile(new Tile(terrain, false), i, j);
				}
			}
		}
		else if(tileType==2){
			for(int i=startX; i<startX+width; i++){
				for(int j=startY; j<startY+height; j++){
					TERRAIN terrain;
					//left edge
					if(i==startX && j==startY){
						terrain = TERRAIN.ds_WALL_LEFT_BOTTOM;
					}
					else if(i==startX && j==startY+height-1){
						terrain = TERRAIN.ds_WALL_LEFT_TOP;
					}
					else if(i==startX){
						terrain = TERRAIN.ds_WALL_LEFT_MIDDLE;
					}
					//top edge
					else if(j==startY+height-1 && i==startX+width-1){
						terrain = TERRAIN.ds_WALL_RIGHT_TOP;
					}
					else if(j==startY+height-1){
						terrain = TERRAIN.ds_WALL_MIDDLE_TOP;
					}
					//right edge
					else if(i==startX+width-1 && j==startY){
						terrain = TERRAIN.ds_WALL_RIGHT_BOTTOM;
					}
					else if(i==startX+width-1){
						terrain = TERRAIN.ds_WALL_RIGHT_MIDDLE;
					}
					//bottom edge
					else if(j==startY){
						terrain = TERRAIN.ds_WALL_MIDDLE_BOTTOM;
					}
					//fill center
					else{
						terrain = TERRAIN.ds_WALL_MIDDLE_MIDDLE;
					}
					//add the tile
					this.getMap().setTile(new Tile(terrain, false), i, j);
				}
			}
		}
        if(tileType==3){
            for(int i=startX; i<startX+width; i++){
                for(int j=startY; j<startY+height; j++){
                    TERRAIN terrain;
                    //left edge
                    if(i==startX && j==startY){
                        terrain = TERRAIN.SNOW_WALL_BOTTOM_LEFT_CORNER;
                        this.getMap().setTile(new Tile(terrain, false), i, j);
                    }
                    else if(i==startX && j==startY+height-1){
                        terrain = TERRAIN.SNOW_WALL_TOP_LEFT_CORNER;
                        this.getMap().setTile(new Tile(terrain, false), i, j);
                    }
                    else if(i==startX){
                        terrain = TERRAIN.SNOW_WALL_LEFT;
                        this.getMap().setTile(new Tile(terrain, false), i, j);
                    }
                    //top edge
                    else if(j==startY+height-1 && i==startX+width-1){
                        terrain = TERRAIN.SNOW_WALL_TOP_RIGHT_CORNER;
                        this.getMap().setTile(new Tile(terrain, false), i, j);
                    }
                    else if(j==startY+height-1){
                        terrain = TERRAIN.SNOW_WALL_TOP;
                        this.getMap().setTile(new Tile(terrain, false), i, j);
                    }
                    //right edge
                    else if(i==startX+width-1 && j==startY){
                        terrain = TERRAIN.SNOW_WALL_BOTTOM_RIGHT_CORNER;
                        this.getMap().setTile(new Tile(terrain, false), i, j);
                    }
                    else if(i==startX+width-1){
                        terrain = TERRAIN.SNOW_WALL_RIGHT;
                        this.getMap().setTile(new Tile(terrain, false), i, j);
                    }
                    //bottom edge
                    else if(j==startY){
                        terrain = TERRAIN.SNOW_WALL_BOTTOM;
                        this.getMap().setTile(new Tile(terrain, false), i, j);
                    }
                    //fill center
                    else{
                        terrain = TERRAIN.SNOW_WALL_FILLER;
                        this.getMap().setTile(new Tile(terrain, true), i, j);
                    }
                }
            }
        }
	}
	public Key addKey(int x, int y, String name,int id){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion keyRegion = atlas.findRegion(name);
		Key key = new Key(x,y,keyRegion,id);
		return key;
	}
	public void addLava( int startX, int startY,int width,int height){
		for(int i=startX; i<startX+width; i++) {
			for (int j = startY; j < startY + height; j++) {
				TERRAIN terrain = TERRAIN.DUNGEON_FLOOR;
				boolean walkable = false;
				//left edge
				if (i == startX && j == startY) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				} else if (i == startX && j == startY + height - 1) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				} else if (i == startX) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				}
				//top edge
				else if (j == startY + height - 1 && i == startX + width - 1) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				} else if (j == startY + height - 1) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				}
				//right edge
				else if (i == startX + width - 1 && j == startY) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				} else if (i == startX + width - 1) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				}
				//bottom edge
				else if (j == startY) {
					terrain = TERRAIN.lava_WALL_MIDDLE_MIDDLE;
				}
				else{
					terrain = TERRAIN.DUNGEON_FLOOR;
					 walkable = true;
				}
				//add the tile
				this.getMap().setTile(new Tile(terrain, walkable), i, j);
			}
		}
	}
	public void addTeleporters(World world,int startX,int startY,int bottomLeftX,int bottomLeftY,int topLeftX,int topLeftY,
							   int topRightX,int topRightY,int bottomRightX, int bottomRightY){
		world.getMap().setTile(new Teleporter(TERRAIN.TELEPORTER,bottomLeftX,bottomLeftY),startX+1,startY+1);
		world.getMap().setTile(new Teleporter(TERRAIN.TELEPORTER,topLeftX,topLeftY),startX+1,startY+3);
		world.getMap().setTile(new Teleporter(TERRAIN.TELEPORTER,topRightX,topRightY),startX+3,startY+3);
		world.getMap().setTile(new Teleporter(TERRAIN.TELEPORTER,bottomRightX,bottomRightY),startX+3,startY+1);
	}
	public void addCrystal(int x, int y, int id, CutsceneEventQueuer q){
		Crystal crystal = new Crystal(x,y,crystalChange,id,q);
		this.addObject(crystal);
	}

	public void addBoulderActor(int x, int y){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);

		TextureRegion boulderRegion = atlas.findRegion("boulder");
		Boulder boulder = new Boulder(this,x,y,boulderRegion);
		this.addActor(boulder);
	}
	public void addPortal(World world, int x, int y, String name, float sizeX, float sizeY){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);

		TextureRegion portalRegion = atlas.findRegion(name);
		Portal Portal = new Portal(x,y,true,portalRegion,sizeX,sizeY,new GridPoint2(0,0),false);
		world.addObject(Portal);
	}
	public void addWeapon(Weapon weapon,String texture,String weaponName, int x, int y){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion weaponRegion = atlas.findRegion(texture);
		if(weapon instanceof Sword){
			int[] stats = {5,0,0,0,0,0,0,0,0,0,0};
			Sword sword = new Sword(x,y,weaponRegion,1f,1f,new GridPoint2(0,0), true, stats,weaponName);
			this.addObject(sword);
		}

	}
	public void addSpikes(TERRAIN t,CutscenePlayer player, CutsceneEventQueuer broadcaster, int x, int y){
		this.getMap().setTile(new SpikeTrap(t,player,broadcaster,x,y),x,y);
		SpikeTrapObject spike = new SpikeTrapObject(x,y,spikeUp,spikeDown);
		this.addObject(spike);
	}

	public void addRug(World world, int x, int y) {
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion rugRegion = atlas.findRegion("rug");
		GridPoint2[] gridArray = new GridPoint2[3*2];
		gridArray[0] = new GridPoint2(0,0);
		gridArray[1] = new GridPoint2(0,1);
		gridArray[2] = new GridPoint2(0,2);
		gridArray[3] = new GridPoint2(1,0);
		gridArray[4] = new GridPoint2(1,1);
		gridArray[5] = new GridPoint2(1,2);
		WorldObject rug = new WorldObject(x, y, true, rugRegion, 3f, 2f, gridArray,false);
		world.addObject(rug);
	}


	public void addVisualObject(int x,int y,String name,boolean walkable,float sizeX,float sizeY){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion region = atlas.findRegion(name);
		GridPoint2[] gridArray = new GridPoint2[(int)sizeX*(int)sizeY];
		int index = 0;
		for(int i = 0;i<sizeX;i++){
			for(int j = 0;j<sizeY;j++){
				gridArray[index] = new GridPoint2(i,j);
				index++;
			}
		}
		WorldObject obj = new WorldObject(x,y,walkable,region,sizeX,sizeY,gridArray,false);
		this.addObject(obj);
	}
	public void addWallObject(int x,int y,String name,boolean walkable,float sizeX,float sizeY){
		TextureAtlas atlas = assetManager.get("res/graphics_packed/tiles/tilepack.atlas", TextureAtlas.class);
		TextureRegion region = atlas.findRegion(name);

		WorldObject obj = new WorldObject(x,y,walkable,region,sizeX,sizeY,new GridPoint2(0,0),false);
		this.addObject(obj);
	}


	public void addActor(Actor a) {
		map.getTile(a.getX(), a.getY()).setActor(a);
		actors.add(a);
	}

	public void addActor(Actor a, ActorBehavior b) {
		addActor(a);
		brains.put(a, b);
	}

	public void addIndex(Index i){
		map.getTile(i.getX(), i.getY()).setIndex(i);
	}

	public void addObject(WorldObject o) {
		for (GridPoint2 p : o.getTiles()) {
			map.getTile(o.getX()+p.x, o.getY()+p.y).setObject(o);
		}
		objects.add(o);
	}

	public WorldObject removeObject(WorldObject o){
		if(o == null){
			return null;
		}
		for (GridPoint2 p : o.getTiles()) {
			map.getTile(o.getX()+p.x, o.getY()+p.y).setObject(null);
		}
		objects.remove(o);
		return o;
	}

	public void removeActor(Actor actor) {
		map.getTile(actor.getX(), actor.getY()).setActor(null);
		actors.remove(actor);
		if (brains.containsKey(actor)) {
			brains.remove(actor);
		}
	}

	public void removeIndex(Index index) {
		map.getTile(index.getX(), index.getY()).setIndex(null);
		this.index = null;
	}

	public void update(float delta) {
		for (Actor a : actors) {
			if (brains.containsKey(a)) {
				brains.get(a).update(delta);
			}
			a.update(delta);
		}
		for (WorldObject o : objects) {
			o.update(delta);
		}
		if(index != null){
			index.update(delta);
		}

	}

	public TileMap getMap() {
		return map;
	}

	public List<Actor> getActors() {
		return actors;
	}

	public List<WorldObject> getWorldObjects() {
		return objects;
	}

	public String getName() {
		return name;
	}
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Character getPlayer(){
        for(Actor actor : actors){
            if(actor.isPlayer){
                return actor.character;
            }
        }
        return null;
    }



	/**
	 * Creates a line of path Terrain
	 * @param startX
	 * @param startY
	 * @param length
	 * @param rowOrCol
	 *        true for row false for columns.
	 */
	public void setPath(int startX, int startY, int length, boolean rowOrCol){
		//rowOrCol is true so we make a row path. Row will be constant increment Columns
		if(rowOrCol==true){
			for(int i=0;i<length;i++){
				getMap().getTile(startX,startY).setTerrain(TERRAIN.DIRT_PATH_MIDDLE);
				startX++;
			}

		}
		else{
			for(int i=0;i<length;i++){
				getMap().getTile(startX,startY).setTerrain(TERRAIN.DIRT_PATH_MIDDLE);
				startY++;
			}
		}

	}


    public void addBoulderPuzzle1(){
        addBoulderActor(37,43);
        addBoulderActor(37,44);
        addBoulderActor(37,45);
        addBoulderActor(37,46);
        addBoulderActor(37,47);
        addBoulderActor(39,43);
        addBoulderActor(39,44);
        addBoulderActor(39,45);
        addBoulderActor(39,46);
        addBoulderActor(39,47);
        addBoulderActor(38,48);
        addBoulderActor(38,49);
        addBoulderActor(40,48);
        addBoulderActor(40,47);
        addBoulderActor(37,51);
    }




}
