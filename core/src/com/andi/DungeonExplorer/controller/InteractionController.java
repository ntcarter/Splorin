package com.andi.DungeonExplorer.controller;

import com.andi.DungeonExplorer.dialogue.Dialogue;
import com.andi.DungeonExplorer.model.DIRECTION;
import com.andi.DungeonExplorer.model.actor.Actor;
import com.andi.DungeonExplorer.model.world.InteractTiles.Boulder;
import com.andi.DungeonExplorer.model.world.Items.WorldObject;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 * Controller that interacts with what is in front of the Actor being controlled.
 *
 */
public class InteractionController extends InputAdapter {

	private Actor a;
	private DialogueController dialogueController;


	public InteractionController(Actor a, DialogueController dialogueController) {
		this.a = a;
		this.dialogueController = dialogueController;
	}

	@Override
	public boolean keyUp(int keycode) {

		if (keycode == Keys.Z) {
			WorldObject obj = a.interact();
			if (obj != null && obj.getDialogue() != null && obj.isDialogueStarter()) {
				dialogueController.startDialogue(obj.getDialogue());
			}
			else{
				Actor targetActor = a.getFacingActor();
				if(targetActor instanceof Boulder){
					targetActor.move(a.getFacing());
				}
				else if (targetActor != null && targetActor.getDialogue() != null) {
					if (targetActor.refaceWithoutAnimation(DIRECTION.getOpposite(a.getFacing()))) {
						Dialogue dialogue = targetActor.getDialogue();
						dialogueController.startDialogue(dialogue);
					}
				}
			}


		}
		return false;
	}
}




