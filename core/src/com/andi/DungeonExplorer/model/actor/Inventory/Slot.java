package com.andi.DungeonExplorer.model.actor.Inventory;

import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Andi Li on 10/4/2017.
 */

public class Slot {

    private WorldObject object;

    private int amount;

    private Array<SlotListener> slotListeners = new Array<SlotListener>();

    public Slot(WorldObject object, int amount) {
        this.object = object;
        this.amount = amount;
    }

    public boolean isEmpty() {
        return object == null || amount <= 0;
    }

    public boolean add(WorldObject item, int amount) {
        if (this.object == item || this.object == null) {
            this.object = item;
            this.amount += amount;
            notifyListeners();
            return true;
        }

        return false;
    }

    public boolean take(int amount) {
        if (this.amount >= amount) {
            this.amount -= amount;
            if (this.amount == 0) {
                object = null;
            }
            notifyListeners();
            return true;
        }

        return false;
    }

    public void addListener(SlotListener slotListener) {
        slotListeners.add(slotListener);
    }

    public void removeListener(SlotListener slotListener) {
        slotListeners.removeValue(slotListener, true);
    }

    private void notifyListeners() {
        for (SlotListener slotListener : slotListeners) {
            slotListener.hasChanged(this);
        }
    }

    public WorldObject getObject() {
        return object;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Slot[" + object + ":" + amount + "]";
    }
}
