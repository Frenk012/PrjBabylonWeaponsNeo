package com.rave.projectbabylonweapons.tooltip;

import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import com.rave.projectbabylonweapons.item.MagicMeleeWeapon;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class MagicDamageTooltipEvents {
    private MagicDamageTooltipEvents() {
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!(stack.getItem() instanceof MagicMeleeWeapon magicWeapon)) {
            return;
        }

        List<Component> tooltip = event.getToolTip();
        String attackDamageLabel = Component.translatable("attribute.name.generic.attack_damage").getString();
        String magicDamageLabel = magicWeapon.getMagicDamageTooltipLabel().getString();

        for (int i = 0; i < tooltip.size(); i++) {
            Component line = tooltip.get(i);
            String text = line.getString();
            if (!text.contains(attackDamageLabel)) {
                continue;
            }

            String replaced = text.replace(attackDamageLabel, magicDamageLabel);
            tooltip.set(i, Component.literal(replaced).setStyle(line.getStyle()));
            return;
        }
    }
}
