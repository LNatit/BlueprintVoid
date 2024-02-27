package com.lnatit.bpvoid;

import com.mojang.logging.LogUtils;
import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import static com.lnatit.bpvoid.BlueprintVoid.MOD_ID;

@Mod(MOD_ID)
@Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlueprintVoid {
    public static final String MOD_ID = "bpvoid";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(Registries.BLOCK, MOD_ID);
    public static final RegistryObject<Block> BLUEPRINT_VOID = BLOCKS.register("blueprint_void", BlueprintVoidBlock::new);
    public static final RegistryObject<Item> BLUEPRINT_VOID_ITEM = ITEMS.register("blueprint_void", BlueprintVoidBlock.BPVoidItem::new);

    public BlueprintVoid() {
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(mod);
        BLOCKS.register(mod);
        // Register config
        var pair = new ForgeConfigSpec.Builder().configure(Config::new);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, pair.getValue());
    }

    @SubscribeEvent
    public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey())
            event.accept(BLUEPRINT_VOID_ITEM);
    }

    public static class Config {
        public static ForgeConfigSpec.BooleanValue replaceVoid;

        public Config(ForgeConfigSpec.Builder builder) {
             replaceVoid = builder
                    .comment(
                            " Whether to replace structure void with blueprint void",
                            " Only works before saving a structure!",
                            " Default value: true"
                    )
                    .translation("config.bpvoid.replacevoid")
                    .define("replace_void", true);
        }
    }
}
