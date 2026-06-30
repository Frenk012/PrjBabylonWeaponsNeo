package com.rave.projectbabylonweapons.skill.weapon_innate;

import com.rave.projectbabylonweapons.gameasset.PBAnimations;
import com.rave.projectbabylonmaterials.init.PBMEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.SimpleWeaponInnateSkill;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener.EventType;

import java.util.UUID;

public class TheHarvestSkill extends SimpleWeaponInnateSkill {

    private static final UUID DAMAGE_UUID = UUID.fromString("d3a1f2c4-9b7e-4a11-8c3d-1f2e3a4b5c6d");
    private static final int MARKED_DURATION_TICKS = 12 * 20;

    public TheHarvestSkill(SimpleWeaponInnateSkill.Builder builder) {
        super(builder);
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        container.getExecutor().getEventListener().addEventListener(
                EventType.DEAL_DAMAGE_EVENT_HURT,
                DAMAGE_UUID,
                (event) -> {
                    var eventAnim = event.getDamageSource().getAnimation();
                    if (eventAnim == null) {
                        return;
                    }

                    if (!eventAnim.toString().equals(PBAnimations.THE_HARVEST.registryName().toString())) {
                        return;
                    }

                    LivingEntity target = event.getTarget();
                    if (target == null || !target.isAlive()) {
                        return;
                    }


                    float healAmount = event.getAttackDamage() * 0.18f;
                    if (healAmount > 0) {
                        container.getExecutor().getOriginal().heal(healAmount);
                    }


                    target.addEffect(new MobEffectInstance(
                            PBMEffects.MARKED.get(),
                            MARKED_DURATION_TICKS,
                            0,
                            false,
                            true,
                            true
                    ));
                }
        );
    }

    @Override
    public void onRemoved(SkillContainer container) {
        container.getExecutor().getEventListener().removeListener(EventType.DEAL_DAMAGE_EVENT_HURT, DAMAGE_UUID);
        super.onRemoved(container);
    }
}

