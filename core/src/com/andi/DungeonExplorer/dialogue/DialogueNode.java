package com.andi.DungeonExplorer.dialogue;

import java.util.List;

public interface DialogueNode {
	
	public int getID();
	
	public List<Integer> getPointers();

	public boolean isAction();


	
}
