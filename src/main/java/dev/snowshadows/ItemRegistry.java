package dev.snowshadows;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemRegistry {
    public static final Item GHOST_LANTERN = new Item(new FabricItemSettings().maxCount(1));
    public static final Item FOX_TAIL = new Item(new FabricItemSettings().maxCount(1));
    public static final Item ELVEN_BOW = new Item(new FabricItemSettings().maxCount(1));
    public static final Item SUCCUBUS_SCROLL = new Item(new FabricItemSettings().maxCount(16));

    public static void register() {
        Registry.register(Registries.ITEM, new Identifier(SnowAndShadows.MOD_ID, "ghost_lantern"), GHOST_LANTERN);
        Registry.register(Registries.ITEM, new Identifier(SnowAndShadows.MOD_ID, "fox_tail"), FOX_TAIL);
        Registry.register(Registries.ITEM, new Identifier(SnowAndShadows.MOD_ID, "elven_bow"), ELVEN_BOW);
        Registry.register(Registries.ITEM, new Identifier(SnowAndShadows.MOD_ID, "succubus_scroll"), SUCCUBUS_SCROLL);
    }
}
