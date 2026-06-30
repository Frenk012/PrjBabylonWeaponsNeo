package com.rave.projectbabylonweapons.init;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PBModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, ProjectBabylonWeapons.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> BLEED_DEBUFF =
            EFFECTS.register("bleed_debuff", BleedDebuff::new);

    public static final DeferredHolder<MobEffect, MobEffect> BROKEN_ARMOR_DEBUFF =
            EFFECTS.register("broken_armor_debuff", BrokenArmorDebuff::new);

    public static final DeferredHolder<MobEffect, MobEffect> IMPACT_BREAK =
            EFFECTS.register("impact_break_debuff", ImpactBreakDebuff::new);

    public static final DeferredHolder<MobEffect, MobEffect> MAGIC_BROKEN_ARMOR =
            EFFECTS.register("magic_broken_armor_debuff", MagicBrokenArmorDebuff::new);

    public static final DeferredHolder<MobEffect, MobEffect> FEAR_DEBUFF =
            EFFECTS.register("fear_debuff", FearDebuff::new);

    public static final DeferredHolder<MobEffect, MobEffect> MARKED =
            EFFECTS.register("marked_debuff", MarkedDebuff::new);

    public static final DeferredHolder<MobEffect, MobEffect> CHAINED =
            EFFECTS.register("chained_debuff", ChainedDebuff::new);

    public static final DeferredHolder<MobEffect, MobEffect> FROZEN =
            EFFECTS.register("frozen_debuff", FrozenDebuff::new);

    public static final DeferredHolder<MobEffect, MobEffect> BRIMSTONE_FLAMES =
            EFFECTS.register("brimstone_flames_debuff", BrimstoneFlamesDebuff::new);

    public static final DeferredHolder<MobEffect, MobEffect> BRIMSTONE_FIRE =
            EFFECTS.register("brimstone_fire_debuff", BrimstoneFireDebuff::new);

    public static final DeferredHolder<MobEffect, MobEffect> CONCUSSED =
            EFFECTS.register("concussed_debuff", ConcussedDebuff::new);

}
