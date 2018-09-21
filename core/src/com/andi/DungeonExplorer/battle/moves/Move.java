package com.andi.DungeonExplorer.battle.moves;

import com.andi.DungeonExplorer.battle.animation.BattleAnimation;
import com.andi.DungeonExplorer.battle.BATTLE_PARTY;
import com.andi.DungeonExplorer.battle.BattleMechanics;
import com.andi.DungeonExplorer.battle.event.BattleEventQueuer;
import com.andi.DungeonExplorer.model.Monster;

/**
 * Represents a move a pokemon can do in battle. 
 * 
 * Do not make new instances of these! 
 * Instead, use {@link #clone()}.
 * 
 *
 */
public abstract class Move {
	
	protected MoveSpecification spec;
	protected Class<? extends BattleAnimation> animationClass;
	
	public Move(MoveSpecification spec, Class<? extends BattleAnimation> animationClass) {
		this.spec = spec;
		this.animationClass = animationClass;
	}
	
	public int useMove(BattleMechanics mechanics, Monster user, Monster target, BATTLE_PARTY party, BattleEventQueuer broadcaster) throws InstantiationException, IllegalAccessException {
		int damage = mechanics.calculateDamage(this, user, target);
		target.applyDamage(damage);
		return damage;
	}
	
	public abstract BattleAnimation animation() throws IllegalAccessException, InstantiationException;
	
	public abstract String message();
	
	/**
	 * @return If this move deals damage
	 */
	public abstract boolean isDamaging();
	
	public String getName() {
		return spec.getName();
	}
	
	public MOVE_TYPE getType(){
		return spec.getType();
	}
	
	public com.andi.DungeonExplorer.battle.moves.MOVE_CATEGORY getCategory() {
		return spec.getCategory();
	}
	
	public int getPower() {
		return spec.getPower();
	}
	
	public float getAccuracy() {
		return spec.getAccuracy();
	}
	
	public MoveSpecification getMoveSpecification() {
		return spec;
	}
	
	/**
	 * @return A copy of this instance.
	 */
	public abstract Move clone();
}
