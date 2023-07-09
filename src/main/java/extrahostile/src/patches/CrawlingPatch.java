package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.EnchantedCrawlingZombieMob;
import net.bytebuddy.asm.Advice;

/**
 * Intercepts a constructor
 * Check out ExampleMethodPatch class for some documentation
 */
@ModConstructorPatch(target = EnchantedCrawlingZombieMob.class, arguments = {}) // No arguments
public class CrawlingPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This EnchantedCrawlingZombieMob mob) {
        mob.setSpeed(50.0F);
        mob.setFriction(2.5F);
    }
}
