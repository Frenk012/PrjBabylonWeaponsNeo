package com.rave.projectbabylonweapons.init;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PBModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ProjectBabylonWeapons.MODID);

    public static final RegistryObject<MobEffect> BLEED_DEBUFF =
            EFFECTS.register("bleed_debuff", BleedDebuff::new);

    public static final RegistryObject<MobEffect> BROKEN_ARMOR_DEBUFF =
            EFFECTS.register("broken_armor_debuff", BrokenArmorDebuff::new);

    public static final RegistryObject<MobEffect> IMPACT_BREAK =
            EFFECTS.register("impact_break_debuff", ImpactBreakDebuff::new);

    public static final RegistryObject<MobEffect> MAGIC_BROKEN_ARMOR =
            EFFECTS.register("magic_broken_armor_debuff", MagicBrokenArmorDebuff::new);

    public static final RegistryObject<MobEffect> FEAR_DEBUFF =
            EFFECTS.register("fear_debuff", FearDebuff::new);

    public static final RegistryObject<MobEffect> MARKED =
            EFFECTS.register("marked_debuff", MarkedDebuff::new);

    public static final RegistryObject<MobEffect> CHAINED =
            EFFECTS.register("chained_debuff", ChainedDebuff::new);

    public static final RegistryObject<MobEffect> FROZEN =
            EFFECTS.register("frozen_debuff", FrozenDebuff::new);

    public static final RegistryObject<MobEffect> BRIMSTONE_FLAMES =
            EFFECTS.register("brimstone_flames_debuff", BrimstoneFlamesDebuff::new);

    public static final RegistryObject<MobEffect> BRIMSTONE_FIRE =
            EFFECTS.register("brimstone_fire_debuff", BrimstoneFireDebuff::new);

    public static final RegistryObject<MobEffect> CONCUSSED =
            EFFECTS.register("concussed_debuff", ConcussedDebuff::new);

}
