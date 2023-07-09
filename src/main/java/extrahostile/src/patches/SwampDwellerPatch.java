package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.SwampDwellerMob;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = SwampDwellerMob.class, arguments = {}) // No arguments
public class SwampDwellerPatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This SwampDwellerMob mob) {
        mob.setSpeed(55.0F);
    }
}
