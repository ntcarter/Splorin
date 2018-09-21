package com.andi.DungeonExplorer.model.world.InteractTiles;


import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.actor.PlayerActor;
import com.andi.DungeonExplorer.model.world.Items.Key;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.andi.DungeonExplorer.model.world.cutscene.ChestEvent;
import com.andi.DungeonExplorer.model.world.cutscene.CutsceneEventQueuer;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;

/**
 * Created by Andi Li on 10/10/2017.
 */

public class Chest extends WorldObject {
    private CutsceneEventQueuer broadcaster;


    private int chestId;
    private Animation openAnimation;
    private ArrayList<WorldObject> chestsItem;

    private float animationTimer = 0f;
    private float animationTime = 0.5f;
    private boolean chestChecker;
    private STATE state = STATE.CLOSED;
    public ArrayList<WorldObject> getChestsItem() {
        return chestsItem;
    }


    public enum STATE {
        OPEN,
        CLOSED,
        OPENING,
        ;
    }




    public Chest(int x, int y, Animation openAnimation, int chestID,CutsceneEventQueuer broadcaster) {
        super(x, y, false, (TextureRegion) openAnimation.getKeyFrames()[0], 1f, 1f, new GridPoint2(0,0),true,0);
        chestsItem = new ArrayList<WorldObject>();
        this.openAnimation = openAnimation;
        this.chestId = chestID;
        this.broadcaster = broadcaster;


    }

    public void open() {
        state = STATE.OPENING;
    }



    @Override
    public void update(float delta) {
        super.update(delta);

        if (state == STATE.OPENING) {
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


    @Override
    public void actorInteract(Actor a) {
        if(a instanceof PlayerActor){
            interactChest(a);
            if(chestChecker == true){
                chestChecker= false;
                broadcaster.queueEvent(new ChestEvent(this,true));

            }

        }

    }
    private Chest interactChest( Actor a){
        int checkId = this.getChestId();
        Key key = a.getInventory().getKey(checkId);
        if(this.state== STATE.OPEN){
            Dialogue open = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode("You have already opened this chest",0,false);
            open.addNode(node1);
            this.setDialogue(open);
        }
        else if(checkId == 0){
            setDialogueStarter(true);
            chestChecker = true;
            for(int i = 0;i<this.getChestsItem().size();i++){
                a.getInventory().store(this.getChestsItem().get(i),1);
                Dialogue open = new Dialogue();
                LinearDialogueNode node1 = new LinearDialogueNode("You have received:" + this.getChestsItem().toString(),0,false);
                open.addNode(node1);
                this.setDialogue(open);
                this.getChestsItem().clear();
                return this;
            }

        }
        else if(key == null || key.getKeyId()!=checkId){
            setDialogueStarter(true);
            chestChecker = false;
            Dialogue closed = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode("You need a key to open this chest.", 0,false);
            closed.addNode(node1);
            this.setDialogue(closed);

            return this;

        }
        else if(key.getKeyId() == checkId){
            setDialogueStarter(true);
            chestChecker = true;
            for(int i = 0;i<this.getChestsItem().size();i++){
                a.getInventory().store(this.getChestsItem().get(i),1);
                Dialogue open = new Dialogue();
                LinearDialogueNode node1 = new LinearDialogueNode("You have received:" + this.getChestsItem().toString(),0,false);
                open.addNode(node1);
                this.setDialogue(open);
                this.getChestsItem().clear();
                return this;
            }
        }

        return null;
    }
    public  int getChestId() {
        return chestId;
    }

    public void addObject(WorldObject obj){
        chestsItem.add(obj);
    }
}
