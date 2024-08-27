package com.akciater.fabric.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import static com.akciater.ShelfModCommon.MODID;

@Config(name = MODID)
public class ShelfModConfigFabric implements ConfigData {
    public float size = 1;
}