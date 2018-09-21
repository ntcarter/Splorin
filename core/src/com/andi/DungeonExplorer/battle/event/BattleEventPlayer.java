package com.andi.DungeonExplorer.battle.event;

import com.andi.DungeonExplorer.battle.animation.BattleAnimation;
import com.andi.DungeonExplorer.ui.DialogueBox;
import com.andi.DungeonExplorer.ui.StatusBox;
import com.badlogic.gdx.graphics.Texture;
import com.andi.DungeonExplorer.battle.BATTLE_PARTY;

import aurelienribon.tweenengine.TweenManager;


public interface BattleEventPlayer {
	
	public void playBattleAnimation(BattleAnimation animation, BATTLE_PARTY party);
	
	public void setPokemonSprite(Texture region, BATTLE_PARTY party);
	
	public DialogueBox getDialogueBox();
	
	public StatusBox getStatusBox(BATTLE_PARTY party);
	
	public BattleAnimation getBattleAnimation();
	
	public TweenManager getTweenManager();
	
	public void queueEvent(BattleEvent event);
}
