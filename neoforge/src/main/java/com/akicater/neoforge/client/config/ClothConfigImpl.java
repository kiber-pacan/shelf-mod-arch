package com.akicater.neoforge.client.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.gui.DefaultGuiProviders;
import me.shedaniel.autoconfig.gui.DefaultGuiTransformers;
import me.shedaniel.autoconfig.gui.registry.GuiRegistry;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import net.minecraft.client.gui.screen.Screen;
import java.util.function.Supplier;
import net.neoforged.fml.ModLoadingContext;
#if MC_VER >= V1_21
    import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
#else
    import net.neoforged.neoforge.client.ConfigScreenHandler;
#endif

public class ClothConfigImpl {
    public static void registerModsPage() {
        /*
        #if MC_VER != V1_20_4
        ModLoadingContext.get().registerExtensionPoint(
                #if MC_VER >= V1_21
                    IConfigScreenFactory.class,
                #else
                    ConfigScreenHandler.ConfigScreenFactory.class,
                #endif
                () -> (mc, screen) -> AutoConfig.getConfigScreen(ShelfModConfigNeoForge.class, screen)#if MC_VER >= V1_21 .get() #endif
        );
        #endif*/
    }
}
