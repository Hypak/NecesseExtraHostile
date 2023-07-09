package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.SwampZombieMob;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = SwampZombieMob.class, arguments = {}) // No arguments
public class SwampZombiePatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This SwampZombieMob mob) {
        mob.setSpeed(75);
        mob.setFriction(1.2f);
    }
}
