package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.FrozenDwarfMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = FrozenDwarfMob.class, arguments = {}) // No arguments
public class FrozenDwarfPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This FrozenDwarfMob mob) {
        mob.setSpeed(60.0F);
        mob.setFriction(1.5f);
        mob.attackTime = 4;
    }
}
