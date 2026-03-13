package com.rave.projectbabylonweapons.item.material;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public enum PBToolTiers implements Tier {
    GOLDEN_DURABLE(
            Tiers.DIAMOND.getLevel(),
            Tiers.DIAMOND.getUses(),
            Tiers.DIAMOND.getSpeed(),
            Tiers.DIAMOND.getAttackDamageBonus(),
            Tiers.GOLD.getEnchantmentValue(),
            () -> ingredientOrFallback(
                    new ResourceLocation("project_babylon_materials", "infused_gold_ingot"),
                    Items.GOLD_INGOT
            )
    ),
    ICE(
            Tiers.DIAMOND.getLevel(),
            1800,
            8.5F,
            3.5F,
            14,
            () -> ingredientOrFallback(
                    new ResourceLocation("project_babylon_materials", "everfrost_ingot"),
                    Items.DIAMOND
            )
    );

    private final int level;
    private final int uses;
    private final float speed;
    private final float attackDamageBonus;
    private final int enchantmentValue;
    private final LazyLoadedValue<Ingredient> repairIngredient;

    PBToolTiers(
            int level,
            int uses,
            float speed,
            float attackDamageBonus,
            int enchantmentValue,
            Supplier<Ingredient> repairIngredient
    ) {
        this.level = level;
        this.uses = uses;
        this.speed = speed;
        this.attackDamageBonus = attackDamageBonus;
        this.enchantmentValue = enchantmentValue;
        this.repairIngredient = new LazyLoadedValue<>(repairIngredient);
    }

    @Override
    public int getUses() {
        return uses;
    }

    @Override
    public float getSpeed() {
        return speed;
    }

    @Override
    public float getAttackDamageBonus() {
        return attackDamageBonus;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return repairIngredient.get();
    }

    private static Ingredient ingredientOrFallback(ResourceLocation id, Item fallback) {
        Item resolved = ForgeRegistries.ITEMS.getValue(id);
        if (resolved == null || resolved == Items.AIR) {
            return Ingredient.of(fallback);
        }
        return Ingredient.of(resolved);
    }
}
