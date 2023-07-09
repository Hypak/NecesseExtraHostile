package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.FrostSentryMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = FrostSentryMob.class, arguments = {}) // No arguments
public class FrostSentryPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This FrostSentryMob mob) {
        mob.attackTime = 2;
    }
}
