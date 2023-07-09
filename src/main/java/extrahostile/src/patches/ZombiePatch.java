package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.EnchantedZombieMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = EnchantedZombieMob.class, arguments = {}) // No arguments
public class ZombiePatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This EnchantedZombieMob mob) {
        mob.setSpeed(80);
        mob.setFriction(1.0F);
    }
}
