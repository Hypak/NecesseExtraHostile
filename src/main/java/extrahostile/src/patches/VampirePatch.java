package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.VampireMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = VampireMob.class, arguments = {}) // No arguments
public class VampirePatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This VampireMob mob) {
        mob.setSpeed(120.0F);
        mob.setFriction(1.0F);
        mob.attackTime = 5;
    }
}
