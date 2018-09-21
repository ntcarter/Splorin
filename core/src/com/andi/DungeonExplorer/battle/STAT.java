package com.andi.DungeonExplorer.battle;

/**
 * Elements that determine certain aspects on battles.
 *
 */
public enum STAT {
	
	/** Determines show much damage a Monster can recieve before fainting */
	HITPOINTS,
	
	/** Partly dtermines how much damage a Monster deals when using a physical move */
	ATTACK,
	
	/** Partly determines how much damage a Monster recieves when hit from a physical move */
	DEFENCE,
	
	/** Partly determines how much damage a Monster deals when using a special move */
	SPECIAL_ATTACK,
	
	/** Partly determines how much damage a Monster recieves when hit from a special move */
	SPECIAL_DEFENCE,
	
	/** Determines how quickly a Monster can act in battle */
	SPEED,
	;
}
