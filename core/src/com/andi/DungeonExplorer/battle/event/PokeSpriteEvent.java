package com.andi.DungeonExplorer.battle.event;

import com.badlogic.gdx.graphics.Texture;
import com.andi.DungeonExplorer.battle.BATTLE_PARTY;

/**
 * A BattleEvent where a Monster's sprite is changed.
 * This event takes no time.
 * 
 *
 */
public class PokeSpriteEvent extends BattleEvent {
	
	private Texture region;
	private BATTLE_PARTY party;
	
	public PokeSpriteEvent(Texture region, BATTLE_PARTY party) {
		this.region = region;
		this.party = party;
	}
	
	@Override
	public void begin(BattleEventPlayer player) {
		super.begin(player);
		player.setPokemonSprite(region, party);
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public boolean finished() {
		return true;
	}

}
