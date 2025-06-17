package com.science.gtnl.common.effect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;

import com.science.gtnl.common.effect.effects.AweEffect;
import com.science.gtnl.common.effect.effects.PerfectPhysiqueEffect;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class GTNLEffect {

    public static Potion awe;
    public static Potion perfect_physique;

    public static void init() {
        extendPotionArray();

        awe = new AweEffect();
        perfect_physique = new PerfectPhysiqueEffect();
    }

    private static void extendPotionArray() {
        Potion[] potionTypes;

        for (Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.getName()
                    .equals("potionTypes")
                    || f.getName()
                        .equals("field_76425_a")) {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);

                    potionTypes = (Potion[]) f.get(null);
                    final Potion[] newPotionTypes = new Potion[256];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                }
            } catch (Exception e) {
                System.err.println("Severe error, please report this to the mod author:");
                log.error("e: ", e);
            }
        }
    }

}
