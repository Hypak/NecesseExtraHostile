package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.JackalMob;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = JackalMob.class, arguments = {}) // No arguments
public class JackalPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This JackalMob mob) {
        mob.setSpeed(65.0F);
    }
}
