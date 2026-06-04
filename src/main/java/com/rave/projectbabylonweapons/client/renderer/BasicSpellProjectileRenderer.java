package com.rave.projectbabylonweapons.client.renderer;

import com.rave.projectbabylonweapons.client.model.BasicSpellProjectileEntityModel;
import com.rave.projectbabylonweapons.world.entity.projectile.BasicSpellProjectileEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BasicSpellProjectileRenderer extends GeoEntityRenderer<BasicSpellProjectileEntity> {
    public BasicSpellProjectileRenderer(EntityRendererProvider.Context context) {
        super(context, new BasicSpellProjectileEntityModel());
        this.shadowRadius = 0.0F;
    }
}
