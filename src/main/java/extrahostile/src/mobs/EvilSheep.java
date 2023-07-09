package extrahostile.src.mobs;

import extrahostile.src.PlayerChaserWandererAIPlus;
import necesse.engine.Screen;
import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry.Textures;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.engine.sound.SoundEffect;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle.GType;
import necesse.gfx.GameResources;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.InventoryItem;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class EvilSheep extends HostileMob {
    public static LootTable lootTable = new LootTable(new LootItemInterface[]{LootItem.between("rawmutton", 1, 2), LootItem.between("wool", 1, 2)});
    public long nextShearTime;

    public EvilSheep() {
        super(100);
        this.setSpeed(20.0F);
        this.setSwimSpeed(2);
        this.setFriction(3.0F);
        this.collision = new Rectangle(-12, -9, 24, 18);
        this.hitBox = new Rectangle(-16, -12, 32, 24);
        this.selectBox = new Rectangle(-18, -30, 36, 36);
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new PlayerChaserWandererAIPlus<Mob>(() -> {
            return 2 * this.getHealth() < this.getMaxHealth();
        }, 480, 256, 4000, false, true) {
            public boolean shootTarget(Mob mob, Mob target) {
                boolean shoot;
                if (mob.getDistance(target) > 180) {
                    shoot = this.shootSimpleProjectile(mob, target, "snowball", 10, 120, 384);
                    if (shoot) {
                        attackCooldown = 1000;
                    }
                } else {
                    shoot = this.shootSimpleProjectile(mob, target, "bone", 20, 50, 175);
                    if (shoot) {
                        attackCooldown = 400;
                    }
                }
                return shoot;
            }

        });
    }

    public void addSaveData(SaveData save) {
        super.addSaveData(save);
        save.addLong("nextShearTime", this.nextShearTime);
    }

    public void applyLoadData(LoadData save) {
        super.applyLoadData(save);
        this.nextShearTime = save.getLong("nextShearTime", 0L, false);
    }

    public void applyMovementPacket(PacketReader reader, boolean isDirect) {
        super.applyMovementPacket(reader, isDirect);
        this.nextShearTime = reader.getNextLong();
    }

    public void setupMovementPacket(PacketWriter writer) {
        super.setupMovementPacket(writer);
        writer.putNextLong(this.nextShearTime);
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        GameTexture texture = this.getTexture();

        for(int i = 0; i < 4; ++i) {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), texture, GameRandom.globalRandom.nextInt(5), 8, 32, this.x, this.y, 10.0F, knockbackX, knockbackY), GType.IMPORTANT_COSMETIC);
        }

    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 22 - 10;
        int drawY = camera.getDrawY(y) - 44 - 7;
        Point sprite = this.getAnimSprite(x, y, this.dir);
        drawY += this.getBobbing(x, y);
        TextureDrawOptions shadow = this.getShadowTexture().initDraw().sprite(0, this.dir, 64).light(light).pos(drawX, drawY);
        tileList.add((tm) -> {
            shadow.draw();
        });
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        final DrawOptions options = this.getTexture().initDraw().sprite(sprite.x, sprite.y, 64).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
    }

    private GameTexture getTexture() {
        return this.hasWool() ? Textures.sheep : Textures.sheep_sheared;
    }

    private GameTexture getShadowTexture() {
        return Textures.sheep_shadow;
    }

    protected int getRockSpeed() {
        return 10;
    }

    @Override
    public boolean isValidSpawnLocation(Server server, ServerClient client, int targetX, int targetY) {
        return true;
    }

    public boolean hasWool() {
        return this.nextShearTime <= this.getWorldEntity().getWorldTime();
    }

    public boolean canShear(InventoryItem item) {
        return this.hasWool();
    }

    public InventoryItem onShear(InventoryItem item, List<InventoryItem> products) {
        this.nextShearTime = this.getWorldEntity().getWorldTime() + (long)GameRandom.globalRandom.getIntBetween(1200000, 1800000);
        int items = GameRandom.globalRandom.getIntBetween(1, 3);

        for(int i = 0; i < items; ++i) {
            products.add(new InventoryItem("wool"));
        }

        if (this.getLevel().isClientLevel()) {
            Screen.playSound(GameResources.shears, SoundEffect.effect(this).volume(0.4F));
        }

        this.sendMovementPacket(false);
        return item;
    }
}
