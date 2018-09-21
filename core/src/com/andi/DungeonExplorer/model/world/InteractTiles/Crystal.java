package com.andi.DungeonExplorer.model.world.InteractTiles;

import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.dialogue.LinearDialogueNode;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.actor.PlayerActor;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.andi.DungeonExplorer.model.world.cutscene.CrystalEvent;
import com.andi.DungeonExplorer.model.world.cutscene.CutsceneEventQueuer;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;

/**
 * Created by Andi Li on 11/8/2017.
 */

public class Crystal extends WorldObject {
    private int crystalId;
    private boolean crystalChecker;
    private float animationTimer = 0f;
    private float animationTime = 0.5f;
    private STATE state = STATE.RED;
    private Animation openAnimation;
    private CutsceneEventQueuer broadcaster;
    private boolean activated;

    public enum STATE {
        PURPLE,
        RED,
        CHANGING,
        ;
    }



    public Crystal(int x, int y, Animation openAnimation, int chestID, CutsceneEventQueuer broadcaster) {
        super(x, y, false, (TextureRegion) openAnimation.getKeyFrames()[0], 1f, 1f, new GridPoint2(0,0),true,0);
        this.openAnimation = openAnimation;
        this.crystalId = chestID;
        this.broadcaster = broadcaster;


    }

    @Override
    public void actorInteract(Actor a) {
        if(a instanceof PlayerActor){
            interactCrystal(a);
            if(crystalChecker == true){
                crystalChecker= false;
                broadcaster.queueEvent(new CrystalEvent(this,true));
            }
            /*for(int i = 0;i<a.getWorld().getMap().getWidth();i++){
                for(int j = 0;j<a.getWorld().getMap().getHeight();j++){
                    if(a.getWorld().getMap().getTile(i,j).getObject() instanceof Crystal){

                    }
                }
            }*/
    //TODO Check if all crystals are purple then switch worlds
        }

    }

    private Crystal interactCrystal(Actor a){
        int checkId = crystalId;
        CrystalPiece key = a.getInventory().getCrystalPiece(checkId);
        if(key == null || key.getCrystalId()!=checkId){
            setDialogueStarter(true);
            crystalChecker = false;
            Dialogue closed = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode("You have no piece or this is the wrong piece", 0,false);
            closed.addNode(node1);
            this.setDialogue(closed);

            return this;

        }
        else if(this.state== STATE.PURPLE){
            setDialogueStarter(true);
            crystalChecker = true;
            Dialogue closed = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode("This Crystal is Transformed Already", 0,false);
            closed.addNode(node1);
            this.setDialogue(closed);
        }
        else if(key.getCrystalId() == checkId){
            setDialogueStarter(true);
            crystalChecker = true;
            Dialogue closed = new Dialogue();
            LinearDialogueNode node1 = new LinearDialogueNode("The Crystal Transforms", 0,false);
            closed.addNode(node1);
            this.setDialogue(closed);
            this.activated = true;
        }


        return null;
    }
    @Override
    public void update(float delta) {
        super.update(delta);

        if ( state == STATE.CHANGING) {
            animationTimer += delta;
            if (animationTimer >= animationTime) {
                if (state == STATE.CHANGING) {
                    state = STATE.PURPLE;
                }
                animationTimer = 0f;
            }
        }
    }
    public STATE getState() {
        return state;
    }

    public TextureRegion getSprite() {
        if (state == STATE.PURPLE) {
            return (TextureRegion) openAnimation.getKeyFrames()[3];
        } else if (state == STATE.RED) {
            return (TextureRegion)  openAnimation.getKeyFrames()[0];
        } else if (state == STATE.CHANGING) {
            return (TextureRegion)  openAnimation.getKeyFrame(animationTimer);
        }
        return null;
    }
    public void change(){
        state = STATE.CHANGING;
    }
    public boolean isActivated() {
        return activated;
    }
}
