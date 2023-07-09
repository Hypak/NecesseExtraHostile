package tweaks.boss;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.bosses.EvilsProtectorMob;
import net.bytebuddy.asm.Advice;
import tweaks.Tweaks;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = EvilsProtectorMob.class, arguments = {}) // No arguments
public class EvilsProtectorPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This EvilsProtectorMob mob) {
        mob.setMaxHealth((int) (mob.getMaxHealth() * Tweaks.bossHealthMult));
        mob.setSpeed(100);
    }
}
