package extrahostile.src;

import extrahostile.src.mobs.*;
import necesse.engine.modLoader.annotations.ModEntry;
import necesse.engine.registries.MobRegistry;
import necesse.level.maps.biomes.Biome;
import tweaks.Tweaks;

@ModEntry
public class ExampleMod {
    public void init() {
        MobRegistry.registerMob("laserduck", LaserDuck.class, true);
        MobRegistry.registerMob("evilcow", EvilCow.class, true);
        MobRegistry.registerMob("evilsheep", EvilSheep.class, true);
        MobRegistry.registerMob("evilrabbit", EvilRabbit.class, true);
        MobRegistry.registerMob("evilcardinal", EvilCardinal.class, true);

        Tweaks.init();
    }

    public void initResources() {
        Tweaks.initResources();
    }

    public void postInit() {
        Biome.defaultSurfaceMobs.add(50, "laserduck");
        Biome.defaultSurfaceMobs.add(100, "evilcow");
        Biome.defaultSurfaceMobs.add(100, "evilsheep");
        Biome.defaultSurfaceMobs.add(100, "evilrabbit");
        Biome.defaultSurfaceMobs.add(100, "evilcardinal");
        Biome.defaultCaveMobs.add(50, "enchantedcrawlingzombie");
        Biome.defaultCaveMobs.add(50, "enchantedzombie");
        Biome.defaultCaveMobs.add(50, "enchantedzombiearcher");

        Tweaks.postInit();
    }

}