package com.andi.DungeonExplorer.model;


import com.andi.DungeonExplorer.model.world.Items.WorldObject;

public class TileMap {
	
	private int width, height;
	private Tile[][] tiles;
	
	public TileMap(int width, int height) {
		this.width = width;
		this.height = height;
		tiles = new Tile[width][height];
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (Math.random() > 0.6d) {
					tiles[x][y] = new Tile(TERRAIN.GRASS_0);
				}else if(Math.random()>0.3d){
					tiles[x][y] = new Tile(TERRAIN.GRASS_1);
				}
				else {
					tiles[x][y] = new Tile(TERRAIN.GRASS_2);
				}
			}
		}
	}
	
	public Tile getTile(int x, int y) {
		return tiles[x][y];
	}
	public void setTile(Tile tile,int x,int y){
		tiles[x][y] = tile;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}


}
