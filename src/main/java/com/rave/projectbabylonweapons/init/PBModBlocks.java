package com.rave.projectbabylonweapons.init;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.block.renderer.FrozenDebuffIceBlock;
import com.rave.projectbabylonweapons.block.entity.FrozenDebuffIceBlockTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PBModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, ProjectBabylonWeapons.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, ProjectBabylonWeapons.MODID);

    public static final RegistryObject<Block> FROZEN_DEBUFF_ICE_BLOCK =
            BLOCKS.register("frozen_debuff_ice_block", FrozenDebuffIceBlock::new);

    public static final RegistryObject<BlockEntityType<FrozenDebuffIceBlockTileEntity>> FROZEN_DEBUFF_ICE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("frozen_debuff_ice_block",
                    () -> BlockEntityType.Builder.of(FrozenDebuffIceBlockTileEntity::new, FROZEN_DEBUFF_ICE_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITIES.register(eventBus);
    }
}
