package com.andi.DungeonExplorer.battle.moves;

import com.andi.DungeonExplorer.battle.animation.BattleAnimation;
import com.andi.DungeonExplorer.battle.BATTLE_PARTY;
import com.andi.DungeonExplorer.battle.BattleMechanics;
import com.andi.DungeonExplorer.battle.event.AnimationBattleEvent;
import com.andi.DungeonExplorer.model.Monster;

/**
 * We're going to do some real important shit around here Morty.
 * 
 *
 */
public class DamageMove extends Move {

	public DamageMove(MoveSpecification spec, Class<? extends BattleAnimation> clazz) {
		super(spec, clazz);
	}

	@Override
	public BattleAnimation animation() throws IllegalAccessException, InstantiationException {

			return animationClass.newInstance();


	}

	@Override
	public String message() {
		return null;
	}

	@Override
	public boolean isDamaging() {
		return true;
	}
	
	@Override
	public int useMove(BattleMechanics mechanics, Monster user, Monster target, BATTLE_PARTY party, com.andi.DungeonExplorer.battle.event.BattleEventQueuer broadcaster) throws InstantiationException, IllegalAccessException {
		int hpBefore = target.getCurrentHitpoints();
		int damage = super.useMove(mechanics, user, target, party, broadcaster);
		
		/* Broadcast animations */
		broadcaster.queueEvent(new AnimationBattleEvent(party, animation()));
		
		/* Broadcast blinking */
		broadcaster.queueEvent(new AnimationBattleEvent(BATTLE_PARTY.getOpposite(party), new com.andi.DungeonExplorer.battle.animation.BlinkingAnimation(1f, 5)));
		
		//float hpPercentage = ((float)target.getCurrentHitpoints())/(float)target.getStat(STAT.HITPOINTS);
		
		/* Broadcast HP change */
		broadcaster.queueEvent(
				new com.andi.DungeonExplorer.battle.event.HPAnimationEvent(
						BATTLE_PARTY.getOpposite(party), 
						hpBefore,
						target.getCurrentHitpoints(), 
						target.getStat(com.andi.DungeonExplorer.battle.STAT.HITPOINTS),
						0.5f));
		
		if (mechanics.hasMessage()) {
			broadcaster.queueEvent(new com.andi.DungeonExplorer.battle.event.TextEvent(mechanics.getMessage(), 0.5f));
		}
		return damage;
	}

	@Override
	public Move clone() {
		return new DamageMove(spec, animationClass);
	}
}
