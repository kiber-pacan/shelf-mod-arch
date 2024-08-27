package com.akciater.forge.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import static com.akciater.ShelfModCommon.MODID;

@Config(name = MODID)
public class ShelfModConfigForge implements ConfigData {
    public float size = 1.0f;
}