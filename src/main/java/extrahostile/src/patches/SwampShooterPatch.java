package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.SwampShooterMob;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = SwampShooterMob.class, arguments = {}) // No arguments
public class SwampShooterPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This SwampShooterMob mob) {
        mob.attackTime = 4;
    }
}
