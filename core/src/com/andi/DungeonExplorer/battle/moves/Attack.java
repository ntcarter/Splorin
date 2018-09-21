package com.andi.DungeonExplorer.battle.moves;

import java.util.Collection;

import com.andi.DungeonExplorer.DungeonExplorer;
import com.andi.DungeonExplorer.model.actor.Character.Character;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.actor;

/**
 * Created by Kenneth on 10/1/2017.
 * Contains information on an attack carried out in combat, such as its base damage.
 */

public class Attack {
    public String name;
    public int baseDam;
    public int baseAcc;
    public int baseCrit;
    public int levelReq = 0;
    public float weight;
    public int range;
    public enum damageType{
        PIERCE, SLASH, IMPACT
    }
    public damageType type;
    public boolean magic;
    public Collection<SpecialEffect> specialEffects;

    /**
     * Full constructor
     * @param dam base damage
     * @param acc base accuracy percent
     * @param crit base crit chance percent
     * @param type damage type of the attack - NULL is acceptable here
     * @param magic if the attack should use Mag/Res instead of Atk/Def
     * @param levelReq minimum level of the player required to use this attack
     */
    public Attack (String name, int dam, int acc, int crit, damageType type, boolean magic, int levelReq, Collection<SpecialEffect> effects, float weight, int range){
        this.name = name;
        baseDam = dam;
        baseAcc = acc;
        baseCrit = crit;
        this.type = type;
        this.magic = magic;
        this.levelReq = levelReq;
        specialEffects = effects;
        this.weight = weight;
        this.range = range;
    }
    /**
     * Shorter constructor
     * @param dam base damage
     * @param acc base accuracy percent
     * @param crit base crit chance percent
     * @param type damage type of the attack - NULL is acceptable here
     */
    public Attack (String name, int dam, int acc, int crit, damageType type){
        this.name = name;
        baseDam = dam;
        baseAcc = acc;
        baseCrit = crit;
        this.type = type;
        this.weight = 1;
        this.range = 1;
    }

    public void applyEffects(Character attacker, Character defender, Attack attack, int damage, boolean crit){
        if(specialEffects == null){
            return;
        }
        for(SpecialEffect effect : specialEffects){
            effect.doEffect(attacker, defender, attack, damage, crit);
        }
    }
}

