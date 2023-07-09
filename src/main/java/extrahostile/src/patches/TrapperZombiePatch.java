package extrahostile.src.patches;

import necesse.engine.modLoader.annotations.ModConstructorPatch;
import necesse.entity.mobs.hostile.TrapperZombieMob;
import net.bytebuddy.asm.Advice;

@ModConstructorPatch(target = TrapperZombieMob.class, arguments = {}) // No arguments
public class TrapperZombiePatch {
    @Advice.OnMethodExit
    static void onExit(@Advice.This TrapperZombieMob mob) {
        mob.setSpeed(100);
        mob.setFriction(1f);
    }
}
