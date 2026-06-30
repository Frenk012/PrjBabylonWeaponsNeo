package com.rave.projectbabylonweapons.init;

import software.bernie.geckolib.animatable.GeoItem;

import net.minecraftforge.fml.common.Mod;
import net.neoforged.bus.api.SubscribeEvent;
import net.minecraftforge.event.TickEvent;

import net.minecraft.world.item.ItemStack;

import com.rave.projectbabylonweapons.item.battlescythe.*;
import com.rave.projectbabylonweapons.item.battlehammer.*;
import com.rave.projectbabylonweapons.item.greatsword.*;

@Mod.EventBusSubscriber
public class GeoAnimations {
    @SubscribeEvent
    public static void animatedItems(TickEvent.PlayerTickEvent event) {
        String animation = "";
        ItemStack mainhandItem = event.player.getMainHandItem().copy();
        ItemStack offhandItem = event.player.getOffhandItem().copy();
        if (event.phase == TickEvent.Phase.START && (mainhandItem.getItem() instanceof GeoItem || offhandItem.getItem() instanceof GeoItem)) {
            if (mainhandItem.getItem() instanceof IceBattleScytheItem animatable) {
                animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
                if (!animation.isEmpty()) {
                    event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
                    if (event.player.level().isClientSide()) {
                        ((IceBattleScytheItem) event.player.getMainHandItem().getItem()).animationprocedure = animation;
                    }
                }
            }
            if (offhandItem.getItem() instanceof IceBattleScytheItem animatable) {
                animation = offhandItem.getOrCreateTag().getString("geckoAnim");
                if (!animation.isEmpty()) {
                    event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
                    if (event.player.level().isClientSide()) {
                        ((IceBattleScytheItem) event.player.getOffhandItem().getItem()).animationprocedure = animation;
                    }
                }
            }
            if (mainhandItem.getItem() instanceof NetheriteBattleScytheItem animatable) {
                animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
                if (!animation.isEmpty()) {
                    event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
                    if (event.player.level().isClientSide()) {
                        ((NetheriteBattleScytheItem) event.player.getMainHandItem().getItem()).animationprocedure = animation;
                    }
                }
            }
            if (offhandItem.getItem() instanceof NetheriteBattleScytheItem animatable) {
                animation = offhandItem.getOrCreateTag().getString("geckoAnim");
                if (!animation.isEmpty()) {
                    event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
                    if (event.player.level().isClientSide()) {
                        ((NetheriteBattleScytheItem) event.player.getOffhandItem().getItem()).animationprocedure = animation;
                    }
                }
            }
            if (mainhandItem.getItem() instanceof IceGreatswordItem animatable) {
                animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
                if (!animation.isEmpty()) {
                    event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
                    if (event.player.level().isClientSide()) {
                        ((IceGreatswordItem) event.player.getMainHandItem().getItem()).animationprocedure = animation;
                    }
                }
            }
            if (offhandItem.getItem() instanceof IceGreatswordItem animatable) {
                animation = offhandItem.getOrCreateTag().getString("geckoAnim");
                if (!animation.isEmpty()) {
                    event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
                    if (event.player.level().isClientSide()) {
                        ((IceGreatswordItem) event.player.getOffhandItem().getItem()).animationprocedure = animation;
                    }
                }
            }
            if (mainhandItem.getItem() instanceof IceBattleHammerItem animatable) {
                animation = mainhandItem.getOrCreateTag().getString("geckoAnim");
                if (!animation.isEmpty()) {
                    event.player.getMainHandItem().getOrCreateTag().putString("geckoAnim", "");
                    if (event.player.level().isClientSide()) {
                        ((IceBattleHammerItem) event.player.getMainHandItem().getItem()).animationprocedure = animation;
                    }
                }
            }
            if (offhandItem.getItem() instanceof IceBattleHammerItem animatable) {
                animation = offhandItem.getOrCreateTag().getString("geckoAnim");
                if (!animation.isEmpty()) {
                    event.player.getOffhandItem().getOrCreateTag().putString("geckoAnim", "");
                    if (event.player.level().isClientSide()) {
                        ((IceBattleHammerItem) event.player.getOffhandItem().getItem()).animationprocedure = animation;
                    }
                }
            }
        }
    }
}