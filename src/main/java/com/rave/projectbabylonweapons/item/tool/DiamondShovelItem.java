package com.rave.projectbabylonweapons.item.tool;

import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.Tiers;

public class DiamondShovelItem extends ShovelItem {

    public DiamondShovelItem(Properties props) {
        super(Tiers.DIAMOND, 1.5F, -3.0F, props);
    }
}
