package tweaks;

import necesse.engine.network.gameNetworkData.GNDItemEnchantment;
import necesse.engine.network.gameNetworkData.GNDItemMap;
import necesse.engine.registries.RecipeTechRegistry;
import necesse.inventory.recipe.Ingredient;
import necesse.inventory.recipe.Recipe;
import necesse.inventory.recipe.Recipes;

public class Tweaks {
    public static float bossHealthMult = 0.3f;

    private static void addScrollRecipe(String enchantName, Ingredient[] ingredients) {
        GNDItemMap itemMap = new GNDItemMap().setItem("enchantment", new GNDItemEnchantment(enchantName));
        Recipe newRecipe = new Recipe("enchantingscroll", 1,
                RecipeTechRegistry.ALCHEMY, ingredients,false, itemMap);
        Recipes.registerModRecipe(newRecipe);
    }

    public static void init() {

    }

    public static void initResources() {
    }

    public static void postInit() {
        Recipes.registerModRecipe(new Recipe(
                "deepladderdown", 1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[] {
                        new Ingredient("piratemap", 10),
                        new Ingredient("mysteriousportal", 3),
                        new Ingredient("royalegg", 3),
                        new Ingredient("spikedfossil", 3),
                        new Ingredient("voidcaller", 3)
                }
        ).showBefore("deepladderdown"));
        Recipes.registerModRecipe(new Recipe(
                "steelboat", 1, RecipeTechRegistry.ADVANCED_WORKSTATION,
                new Ingredient[] {
                        new Ingredient("piratemap", 3),
                        new Ingredient("ironbar", 30)
                }
        ).showBefore("steelboat"));

        addScrollRecipe("keen", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("brokenirontool", 1),
                new Ingredient("copperbar", 3)
        });

        addScrollRecipe("nimble", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("wool", 16),
                new Ingredient("sugar", 5)
        });

        addScrollRecipe("quick", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("leather", 8),
                new Ingredient("wool", 8),
                new Ingredient("goldbar", 3)
        });

        addScrollRecipe("sturdy", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("leather", 16),
                new Ingredient("demonicbar", 1)
        });

        addScrollRecipe("precise", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("frostshard", 16),
                new Ingredient("carrot", 6)
        });

        addScrollRecipe("bulky", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("mushroom", 6),
                new Ingredient("sandstone", 50)
        });

        addScrollRecipe("sharp", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("brokencoppertool", 1),
                new Ingredient("ironbar", 6)
        });

        addScrollRecipe("shining", new Ingredient[] {
                new Ingredient("mapfragment", 6),
                new Ingredient("brokenirontool", 1),
                new Ingredient("goldbar", 6)
        });

        addScrollRecipe("master", new Ingredient[] {
                new Ingredient("mapfragment", 9),
                new Ingredient("obsidian", 6),
                new Ingredient("tungstenbar", 3)
        });

        addScrollRecipe("grand", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("knockbackpotion", 2),
                new Ingredient("tuna", 1)
        });

        addScrollRecipe("harmful", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("battlepotion", 3),
                new Ingredient("quartz", 8)
        });

        addScrollRecipe("agile", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("attackspeedpotion", 4),
                new Ingredient("cappuccino", 3),
                new Ingredient("sugar", 3)
        });

        addScrollRecipe("adamant", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("strawberrypie", 3),
                new Ingredient("frostshard", 8),
        });

        addScrollRecipe("berserk", new Ingredient[] {
                new Ingredient("mapfragment", 6),
                new Ingredient("batwing", 15),
                new Ingredient("bone", 5),
        });

        addScrollRecipe("trained", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("sugar", 3),
                new Ingredient("apple", 5)
        });

        addScrollRecipe("modern", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("donut", 1),
                new Ingredient("candyapple", 1)
        });

        addScrollRecipe("tightened", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("batwing", 10),
                new Ingredient("candyapple", 1)
        });

        addScrollRecipe("skillful", new Ingredient[] {
                new Ingredient("mapfragment", 5),
                new Ingredient("ectoplasm", 3),
                new Ingredient("firemone", 15)
        });

        addScrollRecipe("masterful", new Ingredient[] {
                new Ingredient("mapfragment", 8),
                new Ingredient("lifeelixir", 3),
                new Ingredient("myceliumbar", 3)
        });

        addScrollRecipe("apprentice", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("wool", 12),
        });

        addScrollRecipe("adept", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("wool", 12),
                new Ingredient("voidshard", 6),
        });

        addScrollRecipe("wise", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("book", 4),
                new Ingredient("willowlog", 15),
        });

        addScrollRecipe("wrathful", new Ingredient[] {
                new Ingredient("mapfragment", 5),
                new Ingredient("bloodbolt", 4),
                new Ingredient("cavespidergland", 15),
        });

        addScrollRecipe("divine", new Ingredient[] {
                new Ingredient("mapfragment", 8),
                new Ingredient("shadowbolt", 2),
                new Ingredient("lifeelixir", 3),
        });

        addScrollRecipe("aware", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("mysteriousportal", 1),
        });

        addScrollRecipe("proud", new Ingredient[] {
                new Ingredient("mapfragment", 3),
                new Ingredient("icecrown", 1),
        });

        addScrollRecipe("mindful", new Ingredient[] {
                new Ingredient("mapfragment", 5),
                new Ingredient("voidcaller", 1),
        });

        addScrollRecipe("athletic", new Ingredient[] {
                new Ingredient("mapfragment", 5),
                new Ingredient("shadowgate", 1),
        });

        addScrollRecipe("savage", new Ingredient[] {
                new Ingredient("mapfragment", 8),
                new Ingredient("dragonsouls", 1),
        });
    }

}
