package com.andi.DungeonExplorer.battle.event;

import com.andi.DungeonExplorer.battle.BATTLE_PARTY;

/**
 * A BattleEvent where a Monster's name is changed, on the StatusBox.
 * This event takes no time.
 * 
 *
 */
public class NameChangeEvent extends BattleEvent {
	
	private String name;
	private BATTLE_PARTY party;
	
	public NameChangeEvent(String name, BATTLE_PARTY party) {
		this.name = name;
		this.party = party;
	}
	
	@Override
	public void begin(BattleEventPlayer player) {
		super.begin(player);
		player.getStatusBox(party).setText(name);
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public boolean finished() {
		return true;
	}

}
