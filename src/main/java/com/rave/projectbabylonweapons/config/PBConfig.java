package com.rave.projectbabylonweapons.config;

import net.minecraftforge.common.ForgeConfigSpec;

public final class PBConfig {

    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue ENABLE_VANILLA_WEAPONS_TOOLS_RECIPES;

    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

        builder.push("recipes");
        ENABLE_VANILLA_WEAPONS_TOOLS_RECIPES = builder
                .comment("Enable vanilla weapon and tool crafting recipes (not armor).")
                .define("enable_vanilla_weapons_tools_recipes", true);
        builder.pop();

        SPEC = builder.build();
    }

    private PBConfig() {
    }
}