package extrahostile.src.mobs;

import necesse.engine.network.server.Server;
import necesse.engine.network.server.ServerClient;
import necesse.engine.registries.MobRegistry.Textures;
import necesse.engine.registries.TileRegistry;
import necesse.engine.tickManager.TickManager;
import necesse.entity.mobs.*;
import necesse.entity.mobs.ai.behaviourTree.BehaviourTreeAI;
import necesse.entity.mobs.ai.behaviourTree.trees.CollisionPlayerChaserWandererAI;
import necesse.entity.mobs.hostile.HostileMob;
import necesse.entity.particle.FleshParticle;
import necesse.entity.particle.Particle.GType;
import necesse.gfx.camera.GameCamera;
import necesse.gfx.drawOptions.DrawOptions;
import necesse.gfx.drawOptions.texture.TextureDrawOptions;
import necesse.gfx.drawables.OrderableDrawables;
import necesse.gfx.gameTexture.GameTexture;
import necesse.inventory.lootTable.LootItemInterface;
import necesse.inventory.lootTable.LootTable;
import necesse.inventory.lootTable.lootItem.LootItem;
import necesse.level.maps.Level;
import necesse.level.maps.light.GameLight;

import java.awt.*;
import java.util.List;

public class EvilRabbit extends HostileMob {
    public static LootTable lootTable = new LootTable(new LootItemInterface[]{new LootItem("rabbitleg")});

    public EvilRabbit() {
        super(30);
        this.setSpeed(60.0F);
        this.setFriction(6.0F);
        this.collision = new Rectangle(-10, -7, 20, 14);
        this.hitBox = new Rectangle(-12, -14, 24, 24);
        this.selectBox = new Rectangle(-16, -28, 32, 34);
    }

    public void init() {
        super.init();
        this.ai = new BehaviourTreeAI<>(this, new CollisionPlayerChaserWandererAI<Mob>((() -> {return this.getHealth() * 2 < this.getMaxHealth();}),
                300, new GameDamage(6), -100, 20000));
    }

    public LootTable getLootTable() {
        return lootTable;
    }

    public void spawnDeathParticles(float knockbackX, float knockbackY) {
        for(int i = 0; i < 4; ++i) {
            this.getLevel().entityManager.addParticle(new FleshParticle(this.getLevel(), Textures.rabbit, i, 8, 32, this.x, this.y, 20.0F, knockbackX, knockbackY), GType.IMPORTANT_COSMETIC);
        }

    }

    public void addDrawables(List<MobDrawable> list, OrderableDrawables tileList, OrderableDrawables topList, Level level, int x, int y, TickManager tickManager, GameCamera camera, PlayerMob perspective) {
        super.addDrawables(list, tileList, topList, level, x, y, tickManager, camera, perspective);
        GameLight light = level.getLightLevel(x / 32, y / 32);
        int drawX = camera.getDrawX(x) - 32;
        int drawY = camera.getDrawY(y) - 46;
        Point sprite = this.getAnimSprite(x, y, this.dir);
        drawY += this.getBobbing(x, y);
        drawY += this.getLevel().getTile(x / 32, y / 32).getMobSinkingAmount(this);
        boolean mirror = (this.dir == 0 || this.dir == 2) && this.moveX < 0.0F;
        final DrawOptions options = Textures.rabbit.initDraw().sprite(sprite.x, sprite.y, 64).mirror(mirror, false).light(light).pos(drawX, drawY);
        list.add(new MobDrawable() {
            public void draw(TickManager tickManager) {
                options.draw();
            }
        });
        this.addShadowDrawables(tileList, x, y, light, camera);
    }

    protected TextureDrawOptions getShadowDrawOptions(int x, int y, GameLight light, GameCamera camera) {
        GameTexture shadowTexture = Textures.small_shadow;
        int drawX = camera.getDrawX(x) - shadowTexture.getWidth() / 2;
        int drawY = camera.getDrawY(y) - shadowTexture.getHeight() / 2;
        drawY += this.getBobbing(x, y);
        return shadowTexture.initDraw().light(light).pos(drawX, drawY);
    }

    @Override
    public boolean isValidSpawnLocation(Server server, ServerClient client, int targetX, int targetY) {
        return true;
    }

    public int getRockSpeed() {
        return 10;
    }

    public MobSpawnLocation checkSpawnLocation(MobSpawnLocation location) {
        return super.checkSpawnLocation(location).checkTile((tileX, tileY) -> {
            return this.getLevel().getTileID(tileX, tileY) == TileRegistry.grassID;
        });
    }
}
