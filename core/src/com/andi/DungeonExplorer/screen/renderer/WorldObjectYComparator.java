package com.andi.DungeonExplorer.screen.renderer;

import java.util.Comparator;


public class WorldObjectYComparator implements Comparator<com.andi.DungeonExplorer.model.YSortable> {

	@Override
	public int compare(com.andi.DungeonExplorer.model.YSortable o1, com.andi.DungeonExplorer.model.YSortable o2) {
		if (o1.getWorldY() < o2.getWorldY()) {
			return -1;
		} else if (o1.getWorldY() > o2.getWorldY()) {
			return 1;
		}
		return 0;
	}
}
