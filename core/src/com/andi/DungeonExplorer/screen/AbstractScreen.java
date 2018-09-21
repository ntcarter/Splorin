package com.andi.DungeonExplorer.screen;

import com.badlogic.gdx.Screen;


public abstract class AbstractScreen implements Screen {
	
	private com.andi.DungeonExplorer.DungeonExplorer app;
	
	public AbstractScreen(com.andi.DungeonExplorer.DungeonExplorer app) {
		this.app = app;
	}

	@Override
	public abstract void dispose();

	@Override
	public abstract void hide();

	@Override
	public abstract void pause();
	
	public abstract void update(float delta);

	@Override
	public abstract void render(float delta);

	@Override
	public abstract void resize(int width, int height);

	@Override
	public abstract void resume();

	@Override
	public abstract void show();
	
	public com.andi.DungeonExplorer.DungeonExplorer getApp() {
		return app;
	}

}
