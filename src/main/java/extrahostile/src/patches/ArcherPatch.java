package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModMethodPatch;
import necesse.entity.mobs.hostile.EnchantedZombieArcherMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModMethodPatch(target = EnchantedZombieArcherMob.class, name = "init", arguments = {}) // No arguments
public class ArcherPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This EnchantedZombieArcherMob mob) {
        mob.setSpeed(65);
        mob.setFriction(6);
        mob.attackTime = 7;
    }
}
