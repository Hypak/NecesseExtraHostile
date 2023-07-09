package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.GoblinMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = GoblinMob.class, arguments = {}) // No arguments
public class GoblinPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This GoblinMob mob) {
        mob.setSpeed(45.0F);
        mob.attackTime = 5;
    }
}
