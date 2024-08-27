package com.akciater.forge.client.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
#if MC_VER >= V1_19_4
    import net.minecraftforge.client.ConfigScreenHandler;
#else

#endif

import net.minecraft.screen.ScreenHandlerFactory;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ConfigTracker;


public class ClothConfigImpl {
    public static void registerModsPage() {
        /*AutoConfig.register(ShelfModConfigForge.class, Toml4jConfigSerializer::new);
        #if MC_VER != V1_20_4
        ModLoadingContext.get().registerExtensionPoint(
                #if MC_VER >= V1_19_4
                    ConfigScreenHandler.ConfigScreenFactory.class,
                #else
                ScreenHandlerFactory.class,
                #endif
                () -> new ScreenHandlerFactory(
                        (client, parent) -> AutoConfig.getConfigScreen(ShelfModConfigForge.class, parent).get()
                )
        );
        #endif*/
    }
}