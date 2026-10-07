package dev.snowshadows;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.item.ItemGroups;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.Blocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnowAndShadows implements ModInitializer {
    public static final String MOD_ID = "snowshadows";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("Snow & Shadows initializing... Connecting VN worlds!");
        ItemRegistry.register();

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(content -> {
            content.add(ItemRegistry.GHOST_LANTERN);
            content.add(ItemRegistry.FOX_TAIL);
            content.add(ItemRegistry.ELVEN_BOW);
            content.add(ItemRegistry.SUCCUBUS_SCROLL);
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClient) return ActionResult.PASS;
            
            var state = world.getBlockState(hitResult.getBlockPos());
            var item = player.getStackInHand(hand).getItem();
            
            if (state.isOf(Blocks.PACKED_ICE) && item == ItemRegistry.GHOST_LANTERN) {
                player.sendMessage(Text.literal("§b[Snow & Shadows] §fВрата в Snow Town активированы!"), false);
                return ActionResult.SUCCESS;
            }
            if (state.isOf(Blocks.MOSSY_STONE_BRICKS) && item == ItemRegistry.SUCCUBUS_SCROLL) {
                player.sendMessage(Text.literal("§a[Snow & Shadows] §fВрата в Elven Forest активированы!"), false);
                return ActionResult.SUCCESS;
            }
            return ActionResult.PASS;
        });
    }
}
