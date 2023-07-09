package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.SandSpiritMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = SandSpiritMob.class, arguments = {}) // No arguments
public class SandSpiritPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This SandSpiritMob mob) {
        mob.setSpeed(80.0F);
        mob.setFriction(0.5F);
    }
}
