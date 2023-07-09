package tweaks.boss;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.bosses.PortalMinion;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = PortalMinion.class, arguments = {}) // No arguments
public class PortalMinionPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This PortalMinion mob) {
        mob.setMaxHealth(50);
        mob.setHealth(50);
        mob.setSpeed(100);
        mob.setSwimSpeed(4);
        mob.setFriction(1.2f);
    }
}
