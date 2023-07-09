package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.SkeletonThrowerMob;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = SkeletonThrowerMob.class, arguments = {}) // No arguments
public class SkeletonThrowerPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This SkeletonThrowerMob mob) {
        mob.setSpeed(60.0F);
    }
}
