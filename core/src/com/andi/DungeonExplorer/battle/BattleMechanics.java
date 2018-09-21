package com.andi.DungeonExplorer.battle;

import com.andi.DungeonExplorer.battle.moves.MOVE_CATEGORY;
import com.andi.DungeonExplorer.battle.moves.Move;
import com.andi.DungeonExplorer.model.Monster;
import com.andi.DungeonExplorer.model.actor.Character.Character;
import com.badlogic.gdx.math.MathUtils;

/**
 * Contains methods useful for calculations during battle. 
 * 
 * Some say this is a ShoddyBattle tactic, and they're probably right.
 * 
 *
 */
public class BattleMechanics {
	
	private String message = "";
	
	private boolean criticalHit(Move move, Monster user, Monster target) {
		float probability = 1f/16f;
		if (probability >= MathUtils.random(1.0f)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * @return True if the player goes first.
	 */
	public boolean goesFirst(Character player, Character opponent) {
		if (player.statSpd > opponent.statSpd) {
			return true;
		} else if (opponent.statSpd > player.statSpd) {
			return false;
		} else {
			return MathUtils.randomBoolean();
		}
	}
	
	public boolean attemptHit(Move move, Monster user, Monster target) {
		float random = MathUtils.random(1.0f);
		if (move.getAccuracy() >= random) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Formula found here {@link http://bulbapedia.bulbagarden.net/wiki/Damage#Damage_formula}
	 */
	public int calculateDamage(Move move, Monster user, Monster target) {
		message = "";
		
		float attack = 0f;
		if (move.getCategory() == MOVE_CATEGORY.PHYSICAL) {
			attack = user.getStat(STAT.ATTACK);
		} else {
			attack = user.getStat(STAT.SPECIAL_ATTACK);
		}
		
		float defence = 0f;
		if (move.getCategory() == MOVE_CATEGORY.PHYSICAL) {
			defence = target.getStat(STAT.DEFENCE);
		} else {
			defence = target.getStat(STAT.SPECIAL_DEFENCE);
		}
		
		boolean isCritical = criticalHit(move, user, target);
		
		int level = user.getLevel();
		float base = move.getPower();
		float modifier = MathUtils.random(0.85f, 1.00f);
		if (isCritical) {
			modifier = modifier * 2f;
			message = "A critical hit!";
		}
		
		int damage = (int) ((  (2f*level+10f)/250f   *   (float)attack/defence   * base + 2   ) * modifier);
		
		return damage;
	}
	
	public boolean hasMessage() {
		return !message.isEmpty();
	}
	
	public String getMessage() {
		return message;
	}
}
