package com.akicater.neoforge.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

import static com.akciater.ShelfModCommon.MODID;

@Config(name = MODID)
public class ShelfModConfigNeoForge implements ConfigData {
    public float size = 1;
}