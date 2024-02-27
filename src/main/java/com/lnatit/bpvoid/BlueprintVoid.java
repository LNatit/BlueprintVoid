package com.lnatit.bpvoid;

import com.mojang.logging.LogUtils;
import com.simibubi.create.AllCreativeModeTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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
    public static final RegistryObject<Block> BLUEPRINT_VOID = BLOCKS.register("blueprint_void", BPVoidBlock::new);
    public static final RegistryObject<Item> BLUEPRINT_VOID_ITEM = ITEMS.register("blueprint_void", BPVoidBlock.BPVoidItem::new);

    public static double px = 0.5;
    public static double py = 0.5;
    public static double pz = 0.5;

    public BlueprintVoid() {
        IEventBus mod = FMLJavaModLoadingContext.get().getModEventBus();
        ITEMS.register(mod);
        BLOCKS.register(mod);
    }

    @SubscribeEvent
    public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey())
            event.accept(BLUEPRINT_VOID_ITEM);
    }

}
