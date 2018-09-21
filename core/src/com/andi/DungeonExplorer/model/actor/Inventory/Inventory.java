package com.andi.DungeonExplorer.model.actor.Inventory;


import com.andi.DungeonExplorer.model.world.InteractTiles.CrystalPiece;
import com.andi.DungeonExplorer.model.world.Items.Key;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;


import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Andi Li on 10/4/2017.
 */


public class Inventory {
    WorldObject object;
    private Array<Slot> slots;

    public Inventory() {

        slots = new Array<Slot>(25);
        for (int i = 0; i < 25; i++) {
            slots.add(new Slot(null, 0));
        }



        // create a few random empty slots
        for (int i = 0; i < 3; i++) {
            Slot randomSlot = slots.get(MathUtils.random(0, slots.size - 1));
            randomSlot.take(randomSlot.getAmount());
        }
    }
    public boolean isNotEmpty() {
        for (Slot slot : slots) {
            if (slot.getObject() != null) {
               return true;
            }
        }
        return false;
    }

    public int checkInventory(WorldObject o) {
        int amount = 0;

        for (Slot slot : slots) {
            if (slot.getObject() == o) {
                amount += slot.getAmount();
            }
        }

        return amount;
    }
    public Key getKey(int keyId){
        for(int i  =0; i<slots.size;i++){
            if(slots.get(i).getObject() instanceof  Key){
                if(keyId == ((Key) slots.get(i).getObject()).getKeyId()){
                    return ((Key) slots.get(i).getObject());
                }
            }
        }
        return null;
    }
    public CrystalPiece getCrystalPiece(int id){
        for(int i  =0; i<slots.size;i++){
            if(slots.get(i).getObject() instanceof CrystalPiece){
                if(id == ((CrystalPiece) slots.get(i).getObject()).getCrystalId()){
                    return ((CrystalPiece) slots.get(i).getObject());
                }
            }
        }
        return null;
    }

    public boolean store(WorldObject object, int amount) {

        // first check for a slot with the same object type
        Slot itemSlot = firstSlotWithItem(object);
        if (itemSlot != null) {

            itemSlot.add(object, amount);
            return true;
        } else {

            // now check for an available empty slot
            Slot emptySlot = firstSlotWithItem(null);
            if (emptySlot != null) {
                emptySlot.add(object, amount);
                return true;
            }
        }

        // no slot to add
        return false;
    }
    public boolean drop(WorldObject object){
       for(int i = 0;i<slots.size;i++){
           if(slots.get(i).getObject() == object){
               slots.get(i).take(1);

           }
       }
        return false;
    }
    public Array<Slot> getSlots() {
        return slots;
    }

    private Slot firstSlotWithItem(WorldObject object) {
        for (Slot slot : slots) {
            if (slot.getObject() == object) {
                return slot;
            }
        }

        return null;
    }


}

