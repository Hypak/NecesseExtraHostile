package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.VoidApprentice;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = VoidApprentice.class, arguments = {}) // No arguments
public class VoidApprenticePatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This VoidApprentice mob) {
        mob.setSpeed(60);
        mob.attackTime = 5;
    }
}
