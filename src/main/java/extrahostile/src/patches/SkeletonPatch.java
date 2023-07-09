package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.SkeletonMob;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = SkeletonMob.class, arguments = {}) // No arguments
public class SkeletonPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This SkeletonMob mob) {
        mob.setSpeed(55.0F);
    }
}
