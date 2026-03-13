package com.rave.projectbabylonweapons.client;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.block.renderer.FrozenDebuffIceBlockTileRenderer;
import com.rave.projectbabylonweapons.client.renderer.SickleChainRenderer;
import com.rave.projectbabylonweapons.client.renderer.TectonicFallingBlockRenderer;
import com.rave.projectbabylonweapons.init.PBModBlocks;
import com.rave.projectbabylonweapons.init.PBModEntities;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, bus = Bus.MOD, value = Dist.CLIENT)
public class ClientRegistries {
    @SubscribeEvent
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(PBModEntities.SICKLE_PROJECTILE.get(), SickleChainRenderer::new);
        event.registerEntityRenderer(PBModEntities.TECTONIC_FALLING_BLOCK.get(), TectonicFallingBlockRenderer::new);
        event.registerBlockEntityRenderer(PBModBlocks.FROZEN_DEBUFF_ICE_BLOCK_ENTITY.get(), context -> new FrozenDebuffIceBlockTileRenderer());
    }
}
