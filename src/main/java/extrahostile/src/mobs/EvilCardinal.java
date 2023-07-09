package extrahostile.src.mobs;

import extrahostile.src.PlayerChaserWandererAIPlus;
import necesse.engine.registries.MobRegistry.Textures;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.friendly.critters.BirdMob;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootTable;

public class EvilCardinal extends BirdMob {
    public EvilCardinal() {
        this.setHealth(30);
        this.attackTime = 20;
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new PlayerChaserWandererAIPlus<Mob>(() -> {
            return 2 * this.getHealth() < this.getMaxHealth();
        }, 256, 128, 4000, true, true) {
            public boolean shootTarget(Mob mob, Mob target) {
                if (mob.canAttack()) {
                    for (int i = 0; i < 4; ++i) {
                        float speed = 10 + 50 * GameRandom.globalRandom.nextFloat();
                        this.forceShootSimpleProjectile(mob, target, "stone", 5, (int) speed, 180, 10);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    protected GameTexture getTexture() {
        return Textures.cardinalBird;
    }

    public LootTable getLootTable() {
        return super.getLootTable();
    }
}
