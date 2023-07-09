package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.CryoFlakeMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = CryoFlakeMob.class, arguments = {}) // No arguments
public class CryoFlakePatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This CryoFlakeMob mob) {
        mob.setSpeed(55);
        mob.attackTime = 4;
    }
}
