package com.rave.projectbabylonweapons.init;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.world.entity.effect.TectonicFallingBlockEntity;
import com.rave.projectbabylonweapons.world.entity.projectile.SickleProjectileEntity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class PBModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, ProjectBabylonWeapons.MODID);

    public static final RegistryObject<EntityType<SickleProjectileEntity>> SICKLE_PROJECTILE =
            ENTITIES.register("sickle_projectile", () ->
                    EntityType.Builder.<SickleProjectileEntity>of(SickleProjectileEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("sickle_projectile"));

    public static final RegistryObject<EntityType<TectonicFallingBlockEntity>> TECTONIC_FALLING_BLOCK =
            ENTITIES.register("tectonic_falling_block", () ->
                    EntityType.Builder.<TectonicFallingBlockEntity>of(TectonicFallingBlockEntity::new, MobCategory.MISC)
                            .sized(0.98f, 0.98f)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("tectonic_falling_block"));
}
