package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.SkeletonMinerMob;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = SkeletonMinerMob.class, arguments = {}) // No arguments
public class SkeletonMinerPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This SkeletonMinerMob mob) {
        mob.setSpeed(60.0F);
    }
}
