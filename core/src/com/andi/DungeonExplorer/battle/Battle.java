package com.andi.DungeonExplorer.battle;

import com.andi.DungeonExplorer.battle.animation.PokeballAnimation;
import com.andi.DungeonExplorer.battle.moves.Attack;
import com.andi.DungeonExplorer.model.Monster;
import com.andi.DungeonExplorer.model.actor.Character.Character;
import com.andi.DungeonExplorer.model.world.Items.Weapon;

import java.util.Map;

import static com.andi.DungeonExplorer.battle.moves.Attack.damageType.SLASH;

/**
 * A 100% real Monster fight! Right in your livingroom.
 *
 */
public class Battle implements com.andi.DungeonExplorer.battle.event.BattleEventQueuer {
	
	public enum STATE {
		READY_TO_PROGRESS,
		SELECT_NEW_POKEMON,
		RAN,
		WIN,
		LOSE,
		;
	}
	
	private STATE state;
	
	private BattleMechanics mechanics;
	
	private Monster player;
	private Monster opponent;
	
	private Trainer playerTrainer;
	private Trainer opponentTrainer;

    private Character playerChar;
    private com.andi.DungeonExplorer.model.actor.Character.Monster enemy;

	private com.andi.DungeonExplorer.battle.event.BattleEventPlayer eventPlayer;
	
	public Battle(Character player, com.andi.DungeonExplorer.model.actor.Character.Monster opponent) {
		this.playerChar = player;
		//this.player = player.getPokemon(0);
		this.enemy = opponent;
		mechanics = new BattleMechanics();//for message and goesfirst only, rest handled by character
		this.state = STATE.READY_TO_PROGRESS;
	}
	
	/**
	 * Plays appropiate animation for starting a battle
	 */
	public void beginBattle() {
		queueEvent(new com.andi.DungeonExplorer.battle.event.PokeSpriteEvent(opponent.getSprite(), BATTLE_PARTY.OPPONENT));
		queueEvent(new com.andi.DungeonExplorer.battle.event.TextEvent("Go "+player.getName()+"!", 1f));
		queueEvent(new com.andi.DungeonExplorer.battle.event.PokeSpriteEvent(player.getSprite(), BATTLE_PARTY.PLAYER));
		queueEvent(new com.andi.DungeonExplorer.battle.event.AnimationBattleEvent(BATTLE_PARTY.PLAYER, new PokeballAnimation()));
	}
	
	
	/**
	 * Progress the battle one turn. 
	 * @param input		Index of the move used by the player
	 */
	public void progress(int input) throws InstantiationException, IllegalAccessException {
		if (state != STATE.READY_TO_PROGRESS) {
			return;
		}
		if (mechanics.goesFirst(playerChar, enemy)) {
			playTurn(BATTLE_PARTY.PLAYER, input);
			if (state == STATE.READY_TO_PROGRESS) {
				playTurn(BATTLE_PARTY.OPPONENT, 0);
			}
		} else {
			playTurn(BATTLE_PARTY.OPPONENT, 0);
			if (state == STATE.READY_TO_PROGRESS) {
				playTurn(BATTLE_PARTY.PLAYER, input);
			}
		}
		/*
		 * XXX: Status effects go here.
		 */
	}
	
	/**
	 * Sends out a new Monster, in the case that the old one fainted.
	 * This will NOT take up a turn.
	 * @param monster	Monster the trainer is sending in
	 */
	public void chooseNewPokemon(Monster monster) {
		this.player = monster;
		queueEvent(new com.andi.DungeonExplorer.battle.event.HPAnimationEvent(
				BATTLE_PARTY.PLAYER,
				monster.getCurrentHitpoints(),
				monster.getCurrentHitpoints(),
				monster.getStat(STAT.HITPOINTS),
				0f));
		queueEvent(new com.andi.DungeonExplorer.battle.event.PokeSpriteEvent(monster.getSprite(), BATTLE_PARTY.PLAYER));
		queueEvent(new com.andi.DungeonExplorer.battle.event.NameChangeEvent(monster.getName(), BATTLE_PARTY.PLAYER));
		queueEvent(new com.andi.DungeonExplorer.battle.event.TextEvent("Go get 'em, "+ monster.getName()+"!"));
		queueEvent(new com.andi.DungeonExplorer.battle.event.AnimationBattleEvent(BATTLE_PARTY.PLAYER, new PokeballAnimation()));
		this.state = STATE.READY_TO_PROGRESS;
	}
	
	/**
	 * Attempts to run away
	 */
	public void attemptRun() {
		queueEvent(new com.andi.DungeonExplorer.battle.event.TextEvent("Got away successfully...", true));
		this.state = STATE.RAN;
	}
	
	private void playTurn(BATTLE_PARTY user, int input) throws IllegalAccessException, InstantiationException {
		//BATTLE_PARTY target = BATTLE_PARTY.getOpposite(user);
		
		Character controlled = null;
		Character target = null;
		if (user == BATTLE_PARTY.PLAYER) {
			controlled = playerChar;
			target = enemy;
		} else if (user == BATTLE_PARTY.OPPONENT) {
			controlled = enemy;
			target = playerChar;
		}
		//TODO get selected attack from input
        Weapon weapon = (Weapon)playerChar.equipment.get("MainHand");
        Attack attack = null;
        int j=0;
        for(Map.Entry entry : weapon.attacks.entrySet()){
            if(j==input){
                attack = (Attack)entry.getValue();
                break;
            }
            j++;
        }
        //Attack attack = new Attack("Skeleton Slash", 5, 90, 5, SLASH);//for test purposes
		/* Broadcast the text graphics */
		queueEvent(new com.andi.DungeonExplorer.battle.event.TextEvent(controlled.owner.name +" used\n"+ attack.name+"!", 0.5f));

        /*
		if (mechanics.attemptHit(move, pokeUser, pokeTarget)) {
			move.useMove(mechanics, pokeUser, pokeTarget, user, this);
		} else { // miss
			//Broadcast the text graphics
			queueEvent(new com.andi.DungeonExplorer.battle.event.TextEvent(pokeUser.getName()+"'s\nattack missed!", 0.5f));
		}*/
		Character.attack(controlled, target, attack);
		
		if (player.isFainted()) {
			queueEvent(new com.andi.DungeonExplorer.battle.event.AnimationBattleEvent(BATTLE_PARTY.PLAYER, new com.andi.DungeonExplorer.battle.animation.FaintingAnimation()));
            queueEvent(new com.andi.DungeonExplorer.battle.event.TextEvent("Unfortunately, you've lost...", true));
			this.state = STATE.LOSE;
		} else if (opponent.isFainted()) {
			queueEvent(new com.andi.DungeonExplorer.battle.event.AnimationBattleEvent(BATTLE_PARTY.OPPONENT, new com.andi.DungeonExplorer.battle.animation.FaintingAnimation()));
			queueEvent(new com.andi.DungeonExplorer.battle.event.TextEvent("Congratulations! You Win!", true));
			this.state = STATE.WIN;
		}
	}

	public Monster getPlayerPokemon() {
		return player;
	}
	
	public Monster getOpponentPokemon() {
		return opponent;
	}
	
	public Trainer getPlayerTrainer() {
		return playerTrainer;
	}

	public Character getPlayerChar(){ return playerChar; }

    public com.andi.DungeonExplorer.model.actor.Character.Monster getEnemy(){ return enemy; }
	
	public Trainer getOpponentTrainer() {
		return opponentTrainer;
	}
	
	public STATE getState() {
		return state;
	}

	public void setEventPlayer(com.andi.DungeonExplorer.battle.event.BattleEventPlayer player) {
		this.eventPlayer = player;
	}
	
	@Override
	public void queueEvent(com.andi.DungeonExplorer.battle.event.BattleEvent event) {
		eventPlayer.queueEvent(event);
	}
}
