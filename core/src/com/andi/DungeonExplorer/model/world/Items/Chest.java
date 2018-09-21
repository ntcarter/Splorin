package com.andi.DungeonExplorer.model.world.Items;


import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;

/**
 * Created by Andi Li on 10/10/2017.
 */

public class Chest extends WorldObject {
    private int chestId;
    private Animation openAnimation;
    private ArrayList<WorldObject> chestsItem;

    private float animationTimer = 0f;
    private float animationTime = 0.5f;

    private STATE state = STATE.CLOSED;
    private Dialogue dialogue;
    public ArrayList<WorldObject> getChestsItem() {
        return chestsItem;
    }


    public enum STATE {
        OPEN,
        CLOSED,
        OPENING,
        ;
    }


    public Chest(int x, int y, Animation openAnimation, int chestID) {
        super(x, y, false, (TextureRegion) openAnimation.getKeyFrames()[0], 1f, 1f, new GridPoint2(0,0),false);
        chestsItem = new ArrayList<WorldObject>();
        this.openAnimation = openAnimation;
        this.chestId = chestID;
    }
    public  int getChestId() {
        return chestId;
    }
    public void open() {
        state = STATE.OPENING;
    }


    public void addObject(WorldObject obj){
        chestsItem.add(obj);
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        if ( state == STATE.OPENING) {
            animationTimer += delta;
            if (animationTimer >= animationTime) {
                if (state == STATE.OPENING) {
                    state = STATE.OPEN;
                }
                animationTimer = 0f;
            }
        }
    }
    public STATE getState() {
        return state;
    }

    public TextureRegion getSprite() {
        if (state == STATE.OPEN) {
            return (TextureRegion) openAnimation.getKeyFrames()[3];
        } else if (state == STATE.CLOSED) {
            return (TextureRegion)  openAnimation.getKeyFrames()[0];
        } else if (state == STATE.OPENING) {
            return (TextureRegion)  openAnimation.getKeyFrame(animationTimer);
        }
        return null;
    }
    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }


}
