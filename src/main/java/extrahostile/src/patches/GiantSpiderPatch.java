package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.GiantCaveSpiderMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = GiantCaveSpiderMob.class, arguments = {}) // No arguments
public class GiantSpiderPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This GiantCaveSpiderMob mob) {
        mob.setSpeed(50.0F);
        mob.setFriction(2.0F);
        mob.attackTime = 10;
    }
}
