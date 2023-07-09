package extrahostile.src;

import necesse.engine.registries.ProjectileRegistry;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.ai.behaviourTree.Blackboard;
import necesse.entity.mobs.ai.behaviourTree.composites.SelectorAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.EscapeAINode;
import necesse.entity.mobs.ai.behaviourTree.leaves.WandererAINode;
import necesse.entity.mobs.ai.behaviourTree.trees.PlayerChaserAI;
import necesse.entity.projectile.Projectile;

import java.util.Random;
import java.util.function.Supplier;

public abstract class PlayerChaserWandererAIPlus<T extends Mob> extends SelectorAINode<T> {
    public final EscapeAINode<T> escapeAINode;
    public final PlayerChaserAI<T> playerChaserAI;
    public final WandererAINode<T> wandererAINode;

    public PlayerChaserWandererAIPlus(final Supplier<Boolean> shouldEscape, int searchDistance, int shootDistance, int wanderFrequency, boolean smartPositioning, boolean changePositionOnHit) {
        if (shouldEscape != null) {
            this.addChild(this.escapeAINode = new EscapeAINode<T>() {
                public boolean shouldEscape(T mob, Blackboard<T> blackboard) {
                    return (Boolean)shouldEscape.get();
                }
            });
        } else {
            this.escapeAINode = null;
        }

        this.addChild(this.playerChaserAI = new PlayerChaserAI<T>(searchDistance, shootDistance, smartPositioning, changePositionOnHit) {
            public boolean shootTarget(T mob, Mob target) {
                return PlayerChaserWandererAIPlus.this.shootTarget(mob, target);
            }
        });
        this.addChild(this.wandererAINode = new WandererAINode(wanderFrequency));
    }


    public abstract boolean shootTarget(T var1, Mob var2);

    public boolean shootSimpleProjectile(T mob, Mob target, String projectileID, int projectileDamage, int projectileSpeed, int projectileDistance) {
        return this.shootSimpleProjectile(mob, target, projectileID, projectileDamage, projectileSpeed, projectileDistance, 10);
    }

    public boolean shootSimpleProjectile(T mob, Mob target, String projectileID,int projectileDamage,
                                         int projectileSpeed, int projectileDistance, int moveDist) {
        if (mob.canAttack()) {
            forceShootSimpleProjectile(mob, target, projectileID, projectileDamage, projectileSpeed, projectileDistance, moveDist);
            return true;
        } else {
            return false;
        }
    }

    public void forceShootSimpleProjectile(T mob, Mob target, String projectileID,int projectileDamage,
                                              int projectileSpeed, int projectileDistance, int moveDist) {
        float predictAmount = new Random().nextFloat() * 45;
        float x = target.getX();
        float y = target.getY();
        for (int i = 0; i < 3; ++i) {
            float dist = mob.getDistance(target);
            float timeToTarget = predictAmount * dist / projectileSpeed;
            x = target.getX() + target.moveX * timeToTarget;
            y = target.getY() + target.moveY * timeToTarget;
        }
        int intX = (int)x;
        int intY = (int)y;
        mob.attack(intX, intY, false);
        Projectile projectile = ProjectileRegistry.getProjectile(projectileID, mob.getLevel(),
                mob.x, mob.y, intX, intY, (float)projectileSpeed, projectileDistance,
                new GameDamage((float)projectileDamage), mob);
        projectile.moveDist((double)moveDist);
        mob.getLevel().entityManager.projectiles.add(projectile);
    }
}
