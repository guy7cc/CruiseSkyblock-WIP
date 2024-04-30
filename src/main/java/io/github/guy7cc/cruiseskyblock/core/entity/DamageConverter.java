package io.github.guy7cc.cruiseskyblock.core.entity;

import io.github.guy7cc.cruiseskyblock.core.system.Element;
import io.github.guy7cc.cruiseskyblock.core.system.ElementalVector;
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageConverter {
    public static ElementalVector vanillaDamageToElemental(EntityDamageEvent.DamageCause cause, double damage){
        ElementalVector elemental = ElementalVector.zero();
        switch(cause){

        }
    }

    public static ElementalVector vanillaEntityDamageToElemental(EntityType damager, double damage){
        ElementalVector elemental = ElementalVector.zero();
        switch(damager){
            case FIREBALL:
            case FIREWORK:
            case SMALL_FIREBALL:
                elemental.set(Element.FIRE, damage);
                break;
            case DROWNED:
                elemental.set(Element.WATER, damage);
                break;
            case PHANTOM:
                elemental.set(Element.WIND, damage);
                break;
            case WARDEN:
                elemental.set(Element.DARKNESS, damage);
                break;
            case ALLAY:
                elemental.set(Element.LIGHT, damage);
                break;


        }
    }
}
