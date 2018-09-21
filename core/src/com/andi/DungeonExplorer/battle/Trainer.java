package com.andi.DungeonExplorer.battle;

import com.andi.DungeonExplorer.model.Monster;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Trainer {
	
	private List<Monster> team;
	
	public Trainer(Monster monster) {
		team = new ArrayList<Monster>();
		team.add(monster);
	}
	
	public boolean addPokemon(Monster monster) {
		if (team.size() >= 6) {
			return false;
		} else {
			team.add(monster);
			return true;
		}
	}
	
	public Monster getPokemon(int index) {
		return team.get(index);
	}
	
	public int getTeamSize() {
		return team.size();
	}
}
