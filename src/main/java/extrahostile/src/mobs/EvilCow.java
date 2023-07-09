package extrahostile.src.mobs;

import necesse.engine.network.PacketReader;
import necesse.engine.network.PacketWriter;
import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry.Textures;
import necesse.engine.save.LoadData;
import necesse.engine.save.SaveData;
import necesse.engine.tickManager.TickManager;
import necesse.engine.util.GameRandom;
import necesse.entity.mobs.GameDamage;
import necesse.entity.mobs.Mob;
import necesse.entity.mobs.MobDrawable;
import necesse.entity.mobs.PlayerMob;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserWandererAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle.GType;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
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

public class EvilCow extends HostileMob {
    public static LootTable lootTable = new LootTable(new LootItemInterface[]{LootItem.between("beef", 1, 2), LootItem.between("leather", 2, 5)});
    public long nextMilkTime;

    public EvilCow() {
        super(120);
        this.setSpeed(80.0F);
        this.setSwimSpeed(3.5f);
        this.setFriction(1.2F);
        this.collision = new Rectangle(-12, -9, 24, 18);
        this.hitBox = new Rectangle(-16, -12, 32, 24);
        this.selectBox = new Rectangle(-18, -40, 36, 46);
    }

    public void addSaveData(SaveData save) {
        super.addSaveData(save);
        save.addLong("nextMilkTime", this.nextMilkTime);
    }

    public void applyLoadData(LoadData save) {
        super.applyLoadData(save);
        this.nextMilkTime = save.getLong("nextMilkTime", 0L, false);
    }

    public void applyMovementPacket(PacketReader reader, boolean isDirect) {
        super.applyMovementPacket(reader, isDirect);
        this.nextMilkTime = reader.getNextLong();
    }

    public void setupMovementPacket(PacketWriter writer) {
        super.setupMovementPacket(writer);
        writer.putNextLong(this.nextMilkTime);
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new CollisionPlayerChaserWandererAI<Mob>((() -> {return this.getHealth() * 2 < this.getMaxHealth();}),
                300, new GameDamage(24.0F), 500, 20000));
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
        DrawOptions shadow = this.getShadowTexture().initDraw().sprite(0, this.dir, 64).light(light).pos(drawX, drawY);
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

    public int getRockSpeed() {
        return 10;
    }

    private GameTexture getTexture() {
        return Textures.cow;
    }

    private GameTexture getShadowTexture() {
        return Textures.cow_shadow;
    }

    @Override
    public boolean isValidSpawnLocation(Server server, ServerClient client, int targetX, int targetY) {
        return true;
    }

    public boolean canMilk(InventoryItem item) {
        return this.nextMilkTime <= this.getWorldEntity().getWorldTime();
    }

    public InventoryItem onMilk(InventoryItem item, List<InventoryItem> products) {
        long timeToNextMorning = (long)((this.getWorldEntity().getDayTimeMax() - this.getWorldEntity().getDayTimeInt()) * 1000);
        this.nextMilkTime = this.getWorldEntity().getWorldTime() + Math.max(timeToNextMorning, 120000L);
        products.add(new InventoryItem("milk"));
        this.sendMovementPacket(false);
        return item;
    }
}

