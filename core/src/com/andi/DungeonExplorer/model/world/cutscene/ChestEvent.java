package com.andi.DungeonExplorer.model.world.cutscene;

import com.andi.DungeonExplorer.model.world.InteractTiles.Chest;


/**
 * Created by Andi Li on 10/10/2017.
 */

public class ChestEvent extends CutsceneEvent {
    private boolean opening;
    private Chest chest;
    private boolean finished = false;

    public ChestEvent(Chest chest, boolean opening){
        this.chest = chest;
        this.opening = opening;
    }

    @Override
    public void begin(CutscenePlayer player) {
        super.begin(player);
        if (chest.getState() == Chest.STATE.CLOSED && opening == true) {
            chest.open();
        }
    }
    @Override
    public void update(float delta) {
        if (opening == true && chest.getState() == Chest.STATE.OPEN) {
            finished = true;
        }
    }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void screenShow() {

    }
}
