package dev.echoabyss;

import java.util.function.Function;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public final class ModItems {
	// Right click: loud wave around you (big radius, long cooldown). Quest item from the colossus.
	public static final Item TUNING_FORK = register("tuning_fork",
		p -> new WaveItem(p, 24.0, 0.0, 100, SoundEvents.AMETHYST_BLOCK_CHIME, 0.5F),
		new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));

	// Right click: wave 12 blocks in front of you, to lure mobs away.
	public static final Item ECHO_BELL = register("echo_bell",
		p -> new WaveItem(p, 12.0, 12.0, 40, SoundEvents.BELL_BLOCK, 1.2F),
		new Item.Properties().stacksTo(1).rarity(Rarity.RARE));

	// Right click: travel to the Echo Abyss dimension and back.
	public static final Item ECHO_KEY = register("echo_key", AbyssKeyItem::new, new Item.Properties().stacksTo(1).rarity(Rarity.EPIC));

	// Material item (no use yet).
	public static final Item BELL_CRYSTAL = register("bell_crystal", Item::new, new Item.Properties());

	public static final ResourceKey<CreativeModeTab> TAB_KEY =
		ResourceKey.create(BuiltInRegistries.CREATIVE_MODE_TAB.key(), EchoAbyss.id("main"));

	public static final CreativeModeTab TAB = FabricCreativeModeTab.builder()
		.icon(() -> new ItemStack(TUNING_FORK))
		.title(Component.translatable("creativeTab.echo_abyss"))
		.displayItems((params, output) -> {
			output.accept(TUNING_FORK);
			output.accept(ECHO_KEY);
			output.accept(ECHO_BELL);
			output.accept(BELL_CRYSTAL);
		})
		.build();

	private ModItems() {
	}

	private static <T extends Item> T register(String name, Function<Item.Properties, T> factory, Item.Properties properties) {
		ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, EchoAbyss.id(name));
		T item = factory.apply(properties.setId(key));
		Registry.register(BuiltInRegistries.ITEM, key, item);
		return item;
	}

	public static void initialize() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, TAB_KEY, TAB);
	}
}
