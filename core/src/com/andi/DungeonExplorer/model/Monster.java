package com.andi.DungeonExplorer.model;

import java.util.HashMap;
import java.util.Map;

import com.andi.DungeonExplorer.battle.STAT;
import com.badlogic.gdx.graphics.Texture;
import com.andi.DungeonExplorer.battle.moves.MoveDatabase;
import com.andi.DungeonExplorer.battle.moves.MoveSpecification;

/**
 * Represents a Monster. This representation is used globally.
 *
 */
public class Monster {

    private String name;

    private int level;

    private Map<STAT, Integer> stats;
    private int currentHitpoints;

    /*
     * This array holds prototypes. Use #clone to get an instance.
     */
    private com.andi.DungeonExplorer.battle.moves.Move[] moves = new com.andi.DungeonExplorer.battle.moves.Move[4];

    private Texture image;

    public Monster(String name, Texture image) {
        this.name = name;
        this.image = image;
        this.level = 5;

		/* init all stats to 1 */
        stats = new HashMap<STAT, Integer>();
        for (STAT stat : STAT.values()) {
            stats.put(stat, 15);
        }
        stats.put(STAT.HITPOINTS, 10);
        currentHitpoints = stats.get(STAT.HITPOINTS);
    }

    public Texture getSprite() {
        return image;
    }

    public int getCurrentHitpoints() {
        return currentHitpoints;
    }

    public void setCurrentHitpoints(int currentHitpoints) {
        this.currentHitpoints = currentHitpoints;
    }

    public String getName() {
        return name;
    }

    /**
     * @param index		Position of the move in the move-array. 0 - 3 allowed.
     * @param move		Move to put in
     */
    public void setMove(int index, com.andi.DungeonExplorer.battle.moves.Move move) {
        moves[index] = move;
    }

    /**
     * NOTE: This should deplete PP.
     * @param index		Move to use, 0 - 3 allowed
     * @return			Clone (safe) of move at index
     */
    public com.andi.DungeonExplorer.battle.moves.Move getMove(int index) {
        return moves[index].clone();
    }

    public MoveSpecification getMoveSpecification(int index) {
        return moves[index].getMoveSpecification();
    }

    public int getStat(STAT stat) {
        return stats.get(stat);
    }

    public void setStat(STAT stat, int value) {
        stats.put(stat, value);
    }

    public int getLevel() {
        return level;
    }

    public void applyDamage(int amount) {
        currentHitpoints -= amount;
        if (currentHitpoints < 0) {
            currentHitpoints = 0;
        }
    }


    public boolean isFainted() {
        return currentHitpoints == 0;
    }

    public static Monster generatePokemon(String name, Texture sprite, MoveDatabase moveDatabase) {
        Monster generated = new Monster(name, sprite);
        generated.setMove(0, moveDatabase.getMove(0));
        generated.setMove(1, moveDatabase.getMove(1));
        generated.setMove(2, moveDatabase.getMove(2));
        generated.setMove(3, moveDatabase.getMove(3));

        return generated;
    }
}

