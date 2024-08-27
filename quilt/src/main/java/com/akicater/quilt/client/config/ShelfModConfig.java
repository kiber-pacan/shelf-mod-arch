package com.akicater.quilt.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "item-placer")
public class ShelfModConfig implements ConfigData {
    public float size = 1;

    public boolean oldRendering = false;
}