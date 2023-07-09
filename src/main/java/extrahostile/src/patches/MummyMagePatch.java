package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.SandSpiritMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = SandSpiritMob.class, arguments = {}) // No arguments
public class MummyMagePatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This SandSpiritMob mob) {
        mob.setSpeed(75.0F);
        mob.attackTime = 4;
    }
}
