package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.SnowWolfMob;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = SnowWolfMob.class, arguments = {}) // No arguments
public class SnowWolfPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This SnowWolfMob mob) {
        mob.setSpeed(60.0F);
    }
}
