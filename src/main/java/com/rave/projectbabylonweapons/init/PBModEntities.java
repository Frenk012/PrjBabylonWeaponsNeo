package com.rave.projectbabylonweapons.init;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.world.entity.effect.GlacierIceSpikeEntity;
import com.rave.projectbabylonweapons.world.entity.effect.TectonicFallingBlockEntity;
import com.rave.projectbabylonweapons.world.entity.projectile.BasicSpellProjectileEntity;
import com.rave.projectbabylonweapons.world.entity.projectile.FireSpellProjectileEntity;
import com.rave.projectbabylonweapons.world.entity.projectile.HolySpellProjectileEntity;
import com.rave.projectbabylonweapons.world.entity.projectile.IceSpellProjectileEntity;
import com.rave.projectbabylonweapons.world.entity.projectile.ManaBubbleProjectileEntity;
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

    public static final RegistryObject<EntityType<BasicSpellProjectileEntity>> BASIC_SPELL_PROJECTILE =
            ENTITIES.register("basic_spell_projectile", () ->
                    EntityType.Builder.<BasicSpellProjectileEntity>of(BasicSpellProjectileEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("basic_spell_projectile"));

    public static final RegistryObject<EntityType<IceSpellProjectileEntity>> ICE_SPELL_PROJECTILE =
            ENTITIES.register("ice_spell_projectile", () ->
                    EntityType.Builder.<IceSpellProjectileEntity>of(IceSpellProjectileEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("ice_spell_projectile"));

    public static final RegistryObject<EntityType<FireSpellProjectileEntity>> FIRE_SPELL_PROJECTILE =
            ENTITIES.register("fire_spell_projectile", () ->
                    EntityType.Builder.<FireSpellProjectileEntity>of(FireSpellProjectileEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("fire_spell_projectile"));


    public static final RegistryObject<EntityType<HolySpellProjectileEntity>> HOLY_SPELL_PROJECTILE =
            ENTITIES.register("holy_spell_projectile", () ->
                    EntityType.Builder.<HolySpellProjectileEntity>of(HolySpellProjectileEntity::new, MobCategory.MISC)
                            .sized(1.0f, 1.0f)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("holy_spell_projectile"));
    public static final RegistryObject<EntityType<ManaBubbleProjectileEntity>> MANA_BUBBLE_PROJECTILE =
            ENTITIES.register("mana_bubble_projectile", () ->
                    EntityType.Builder.<ManaBubbleProjectileEntity>of(ManaBubbleProjectileEntity::new, MobCategory.MISC)
                            .sized(4.5f, 4.5f)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("mana_bubble_projectile"));

    public static final RegistryObject<EntityType<GlacierIceSpikeEntity>> GLACIER_ICE_SPIKE =
            ENTITIES.register("glacier_ice_spike", () ->
                    EntityType.Builder.<GlacierIceSpikeEntity>of(GlacierIceSpikeEntity::new, MobCategory.MISC)
                            .sized(1.6f, 2.4f)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("glacier_ice_spike"));

    public static final RegistryObject<EntityType<TectonicFallingBlockEntity>> TECTONIC_FALLING_BLOCK =
            ENTITIES.register("tectonic_falling_block", () ->
                    EntityType.Builder.<TectonicFallingBlockEntity>of(TectonicFallingBlockEntity::new, MobCategory.MISC)
                            .sized(0.98f, 0.98f)
                            .clientTrackingRange(64)
                            .updateInterval(1)
                            .build("tectonic_falling_block"));
}
