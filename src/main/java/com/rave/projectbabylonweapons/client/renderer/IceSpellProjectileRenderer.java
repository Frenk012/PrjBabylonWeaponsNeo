package com.rave.projectbabylonweapons.client.renderer;

import com.rave.projectbabylonweapons.client.model.IceSpellProjectileEntityModel;
import com.rave.projectbabylonweapons.world.entity.projectile.IceSpellProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class IceSpellProjectileRenderer extends GeoEntityRenderer<IceSpellProjectileEntity> {
    public IceSpellProjectileRenderer(EntityRendererProvider.Context context) {
        super(context, new IceSpellProjectileEntityModel());
        this.shadowRadius = 0.0F;
    }
}