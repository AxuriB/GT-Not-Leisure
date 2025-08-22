package com.science.gtnl.loader;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import net.minecraft.potion.Potion;

import com.science.gtnl.common.effect.effects.AweEffect;
import com.science.gtnl.common.effect.effects.PerfectPhysiqueEffect;
import com.science.gtnl.common.effect.effects.PotionGhostlyShape;
import com.science.gtnl.common.effect.effects.ShimmeringEffect;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class EffectLoader {

    public static Potion awe;
    public static Potion perfect_physique;
    public static Potion shimmering;
    public static Potion ghostly_shape;

    public static void registry() {
        if (Potion.potionTypes.length < 256) extendPotionArray();
        int i = findNextFreePotionId();

        awe = new AweEffect(i++);
        perfect_physique = new PerfectPhysiqueEffect(i++);
        shimmering = new ShimmeringEffect(i++);
        ghostly_shape = new PotionGhostlyShape(i++);
    }

    public static int findNextFreePotionId() {
        for (int i = 1; i < Potion.potionTypes.length; i++) {
            if (Potion.potionTypes[i] == null) {
                return i;
            }
        }

        throw new RuntimeException("No free potion ID found.");
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
