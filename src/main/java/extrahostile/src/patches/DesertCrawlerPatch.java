package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.CryoFlakeMob;
import necesse.entity.mobs.hostile.DesertCrawlerMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = DesertCrawlerMob.class, arguments = {}) // No arguments
public class DesertCrawlerPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This CryoFlakeMob mob) {
        mob.setSpeed(60);
        mob.attackTime = 6;
    }
}
