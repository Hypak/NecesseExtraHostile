package extrahostile.src.mobs;


import extrahostile.src.PlayerChaserWandererAIPlus;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry.Textures;
import necesse.engine.registries.TileRegistry;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.MobSpawnLocation;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle.GType;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.TilePosition;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class LaserDuck extends HostileMob {
    public static LootTable lootTable = new LootTable(new LootItemInterface[]{new LootItem("duckbreast")});

    public LaserDuck() {
        super(60);
        this.setSpeed(30.0F);
        this.setFriction(3.0F);
        this.setSwimSpeed(5.0F);
        this.attackTime = 8;
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-12, -14, 24, 24);
        this.selectBox = new Rectangle(-16, -28, 32, 34);
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new PlayerChaserWandererAIPlus<Mob>(() -> {
            return 2 * this.getHealth() < this.getMaxHealth();
        }, 480, 256, 4000, true, true) {
            public boolean shootTarget(Mob mob, Mob target) {
                boolean shoot;
                if (mob.getDistance(target) > 150 && mob.inLiquid()) {
                    int speed = (int)(mob.getDistance(target) / 2);
                    shoot = this.shootSimpleProjectile(mob, target, "ironbomb", 30, speed, 256);
                    if (shoot) {
                        attackCooldown = 1600;
                    }
                } else {
                    float random = GameRandom.globalRandom.nextFloat();
                    int speed = 50;
                    if (random < 0.15f) {
                        speed = 80;
                    }
                    shoot = this.shootSimpleProjectile(mob, target, "waterbolt", 12, speed, 512);
                    if (shoot) {
                        attackCooldown = 200;
                    }
                }
                return shoot;
            }
        });
    }



    public LootTable getLootTable() {
        return lootTable;
    }

    public int getTileWanderPriority(TilePosition pos) {
        if (pos.tileID() == TileRegistry.waterID) {
            return 1000;
        } else {
            int height = pos.level.liquidManager.getHeight(pos.tileX, pos.tileY);
            return height >= 0 && height <= 3 ? 1000 : super.getTileWanderPriority(pos);
        }
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for(int i = 0; i < 5; ++i) {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), Textures.duck.body, i, 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), GType.IMPORTANT_COSMETIC);
        }

    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 30;
        int drawY = camera.getDrawY(y) - 48;
        Point sprite = this.getAnimSprite(x, y, this.dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        if (this.inLiquid(x, y)) {
            drawY -= 12;
        }

        final DrawOptions options = Textures.duck.body.initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
        TextureDrawOptions shadow = Textures.duck.shadow.initDraw().sprite(0, this.dir, 64).light(light).pos(drawX, drawY);
        tileList.add((tm) -> {
            shadow.draw();
        });
    }

    protected int getRockSpeed() {
        return 8;
    }

    @Override
    public boolean isValidSpawnLocation(Server server, ServerClient client, int targetX, int targetY) {
        return true;
    }

    public MobSpawnLocation checkSpawnLocation(MobSpawnLocation location) {
        return location.checkNotLevelCollides().checkTile((tileX, tileY) -> {
            int tileID = this.getLevel().getTileID(tileX, tileY);
            if (tileID == TileRegistry.waterID) {
                return true;
            } else {
                int height = this.getLevel().liquidManager.getHeight(tileX, tileY);
                return height >= 0 && height <= 3;
            }
        });
    }
}

