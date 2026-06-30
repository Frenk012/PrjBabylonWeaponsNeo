package com.rave.projectbabylonweapons;

import com.mojang.logging.LogUtils;
import com.rave.projectbabylonweapons.config.PBConfig;
import com.rave.projectbabylonweapons.gameasset.PBAnimations;
import com.rave.projectbabylonweapons.gameasset.PBSkills;
import com.rave.projectbabylonweapons.init.CreativeTabRegistry;
import com.rave.projectbabylonweapons.init.PBModBlocks;
import com.rave.projectbabylonweapons.init.PBModEntities;
import com.rave.projectbabylonweapons.init.PBModItems;
import com.rave.projectbabylonweapons.init.PBModParticles;
import com.rave.projectbabylonweapons.init.PBWSounds;
import com.rave.projectbabylonweapons.network.PBNetworkManager;
import com.rave.projectbabylonweapons.passive.data.WeaponPassivePatchManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

import com.rave.projectbabylonweapons.world.capabilities.item.PBWeaponCapabilityPresets;

import static com.rave.projectbabylonweapons.init.PBModEffects.EFFECTS;

@Mod(ProjectBabylonWeapons.MODID)
public class ProjectBabylonWeapons {
    public static final String MODID = "project_babylon_weapons";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ProjectBabylonWeapons(IEventBus modBus, ModContainer modContainer) {
        IEventBus gameBus = NeoForge.EVENT_BUS;

        modContainer.registerConfig(ModConfig.Type.COMMON, PBConfig.SPEC);

        modBus.addListener(this::commonSetup);
        modBus.addListener(this::addCreative);
        modBus.addListener(this::addPackFindersEvent);
        modBus.addListener(PBAnimations::registerAnimations);
        modBus.addListener(PBNetworkManager::register);

        PBSkills.REGISTRY.register(modBus);

        CreativeTabRegistry.TABS.register(modBus);
        PBModBlocks.register(modBus);
        PBModItems.ITEMS.register(modBus);
        PBModParticles.PARTICLES.register(modBus);
        EFFECTS.register(modBus);
        PBWSounds.register(modBus);
        PBModEntities.ENTITIES.register(modBus);

        // Epic Fight weapon-capability presets are registered through EF's own event system,
        // not the NeoForge bus (the registry event is not a bus Event).
        PBWeaponCapabilityPresets.register();

        gameBus.addListener(this::addReloadListeners);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            LOGGER.info("Project Babylon common setup started");
        });
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
    }

    private void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(WeaponPassivePatchManager.INSTANCE);
    }

    public void addPackFindersEvent(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) {
            event.addPackFinders(
                    ResourceLocation.fromNamespaceAndPath(MODID, "projectbabylonpack"),
                    PackType.CLIENT_RESOURCES,
                    Component.translatable("pack.projectbabylonpack.title"),
                    PackSource.BUILT_IN,
                    false,
                    Pack.Position.TOP);
        }
    }
}
