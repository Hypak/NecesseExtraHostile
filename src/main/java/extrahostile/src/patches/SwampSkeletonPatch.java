package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.SwampSkeletonMob;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = SwampSkeletonMob.class, arguments = {}) // No arguments
public class SwampSkeletonPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This SwampSkeletonMob mob) {
        mob.setSpeed(55);
    }
}
