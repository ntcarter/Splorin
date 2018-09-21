package com.andi.DungeonExplorer.battle.event;

import com.andi.DungeonExplorer.battle.animation.BattleAnimation;
import com.andi.DungeonExplorer.battle.BATTLE_PARTY;

/**
 * A BattleEvent where a BattleAnimation is played.
 *
 */
public class AnimationBattleEvent extends BattleEvent {
	
	private BATTLE_PARTY primary;
	private BattleAnimation animation;

	public AnimationBattleEvent(BATTLE_PARTY primary, BattleAnimation animation) {
		this.animation = animation;
		this.primary = primary;
	}

	@Override
	public void begin(BattleEventPlayer player) {
		super.begin(player);
		player.playBattleAnimation(animation, primary);
	}
	
	@Override
	public void update(float delta) {
		animation.update(delta);
	}

	@Override
	public boolean finished() {
		return this.getPlayer().getBattleAnimation().isFinished();
	}

}
