package com.rave.projectbabylonweapons.client;

import com.lowdragmc.photon.client.fx.IEffect;
import com.lowdragmc.lowdraglib.utils.GradientColor;
import com.lowdragmc.photon.client.gameobject.emitter.data.number.color.Gradient;
import com.lowdragmc.photon.client.gameobject.emitter.data.VelocityOverLifetimeSetting;
import com.lowdragmc.photon.client.gameobject.emitter.data.material.TextureMaterial;
import com.lowdragmc.photon.client.gameobject.emitter.data.number.NumberFunction;
import com.lowdragmc.photon.client.gameobject.emitter.data.number.NumberFunction3;
import com.lowdragmc.photon.client.gameobject.emitter.data.shape.Cone;
import com.lowdragmc.photon.client.gameobject.emitter.data.shape.Dot;
import com.lowdragmc.photon.client.gameobject.emitter.particle.ParticleConfig;
import com.lowdragmc.photon.client.gameobject.emitter.particle.ParticleEmitter;
import com.rave.projectbabylonweapons.ProjectBabylonWeapons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ClientPlayerNetworkEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Mod.EventBusSubscriber(modid = ProjectBabylonWeapons.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public final class PhotonWeaponEffectHelper {
    private static final Map<Integer, OrbitState> ACTIVE_DRAGON_DESCEND_CASTS = new ConcurrentHashMap<>();
    private static final Quaternionf IDENTITY_ROTATION = new Quaternionf();
    private static final Vector3f UNIT_SCALE = new Vector3f(1.0F, 1.0F, 1.0F);
    private static final ResourceLocation PORTAL_TEXTURE = texture("dragon_descend_spell.png");
    private static final ResourceLocation HOLY_TEXTURE = texture("holy_particle.png");
    private static final ResourceLocation HEAL_TEXTURE = texture("heal_particle.png");
    private static final ResourceLocation SNOWFLAKE_TEXTURE = texture("snowflake_particle.png");
    private static final ResourceLocation SNOW_TEXTURE = texture("snow_particle.png");
    private static final ResourceLocation FIRE_TEXTURE_1 = texture("fire_particle_1.png");
    private static final ResourceLocation FIRE_TEXTURE_2 = texture("fire_particle_2.png");
    private static final ResourceLocation SMOKE_TEXTURE_1 = texture("smoke_particle_1.png");
    private static final ResourceLocation SMOKE_TEXTURE_2 = texture("smoke_particle_2.png");
    private static final float INNER_RADIUS = 1.35F;
    private static final float OUTER_RADIUS = 1.85F;
    private static final float INNER_HEIGHT = 1.05F;
    private static final float OUTER_HEIGHT = 1.55F;
    private static final float ROTATION_SPEED = 0.42F;
    private static final int ORBIT_PARTICLES_PER_TICK = 4;
    private static final int BURST_PARTICLE_COUNT = 32;
    private static final double BURST_SPEED = 0.36D;
    private static final int BREATH_PARTICLE_COUNT = 5;
    private static final double BREATH_DOWNWARD_BIAS = -0.24D;
    private static final int ENDER_PROJECTILE_PARTICLE_INTERVAL = 2;
    private static final int HOLY_PROJECTILE_PARTICLE_INTERVAL = 2;
    private static final int ICE_PROJECTILE_PARTICLE_INTERVAL = 2;
    private static final int FIRE_PROJECTILE_PARTICLE_INTERVAL = 2;
    private static final int TRAIL_VISUAL_INTERVAL = 2;
    private static final int TRAIL_VISUAL_LIFETIME = 60;
    private static final double TRAIL_HALF_WIDTH = 1.5D;

    private PhotonWeaponEffectHelper() {
    }

    public static void startDragonDescendCast(Entity entity) {
        if (entity == null || !entity.isAlive() || entity.level() == null || !entity.level().isClientSide) {
            return;
        }

        ACTIVE_DRAGON_DESCEND_CASTS.put(entity.getId(), new OrbitState(entity.getId()));
    }

    public static void burstDragonDescendCast(Entity entity) {
        if (entity == null || entity.level() == null || !entity.level().isClientSide) {
            return;
        }

        ACTIVE_DRAGON_DESCEND_CASTS.remove(entity.getId());
        spawnBurst(entity);
    }

    public static void stopDragonDescendCast(Entity entity) {
        if (entity == null) {
            return;
        }

        ACTIVE_DRAGON_DESCEND_CASTS.remove(entity.getId());
    }

    public static void spawnDragonDescendFlight(Entity projectile, Vec3 movement) {
        if (!(projectile.level() instanceof ClientLevel level) || movement.lengthSqr() < 1.0E-6D) {
            return;
        }

        StaticLevelEffect effect = new StaticLevelEffect(level);
        Vec3 normalized = movement.normalize();
        Vec3 breathDirection = new Vec3(normalized.x, normalized.y + BREATH_DOWNWARD_BIAS, normalized.z).normalize();
        Vec3 right = horizontalRight(normalized);

        Vec3 origin = projectile.position().subtract(normalized.scale(0.35D)).add(0.0D, 0.15D, 0.0D);
        Quaternionf breathRotation = quaternionFromDirection(breathDirection);
        ParticleEmitter breathEmitter = createBreathEmitter();
        breathEmitter.emmit(effect, toVector(origin), breathRotation, UNIT_SCALE);

        if ((projectile.tickCount % TRAIL_VISUAL_INTERVAL) != 0) {
            return;
        }

        Vec3 trailCenter = projectile.position().subtract(normalized.scale(0.9D)).add(0.0D, 0.05D, 0.0D);
        spawnTrailSegment(effect, trailCenter, right, normalized);
    }

    public static void spawnEnderProjectileFlight(Entity projectile, Vec3 movement) {
        if (!(projectile.level() instanceof ClientLevel level) || movement.lengthSqr() < 1.0E-6D) {
            return;
        }

        if ((projectile.tickCount % ENDER_PROJECTILE_PARTICLE_INTERVAL) != 0) {
            return;
        }

        StaticLevelEffect effect = new StaticLevelEffect(level);
        Vec3 normalized = movement.normalize();
        Vec3 center = projectile.position().subtract(normalized.scale(0.32D));
        Vec3 right = horizontalRight(normalized);
        Vec3 up = verticalAxis(normalized, right);

        float angle = projectile.tickCount * 0.55F;
        Vec3 portalOffset = right.scale(Math.cos(angle) * 0.14D).add(up.scale(Math.sin(angle) * 0.14D));
        Vec3 breathOffset = portalOffset.scale(-0.85D);

        createTrailEmitter(PORTAL_TEXTURE, 0.26F, 12, 0xFFE6D6FF, 0.0D, 0.01D, 0.0D, 22.0F)
                .emmit(effect, toVector(center.add(portalOffset)), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(PORTAL_TEXTURE, 0.22F, 10, 0xFFBA7BFF, 0.0D, 0.015D, 0.0D, -18.0F)
                .emmit(effect, toVector(center.add(breathOffset)), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(PORTAL_TEXTURE, 0.18F, 9, 0xFFFFFFFF, 0.0D, 0.005D, 0.0D, 30.0F)
                .emmit(effect, toVector(center), IDENTITY_ROTATION, UNIT_SCALE);
    }

    public static void spawnEnderProjectileImpact(Entity projectile, Vec3 hitPos) {
        if (!(projectile.level() instanceof ClientLevel level)) {
            return;
        }

        StaticLevelEffect effect = new StaticLevelEffect(level);
        for (int i = 0; i < 16; i++) {
            double angle = (Math.PI * 2.0D * i) / 16.0D;
            double speed = 0.12D + ((i & 1) * 0.035D);
            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;
            double vy = 0.015D + ((i % 3) * 0.01D);
            int color = (i % 4 == 0) ? 0xFFFFFFFF : ((i & 1) == 0 ? 0xFFE6D6FF : 0xFFBA7BFF);
            float size = (i % 4 == 0) ? 0.24F : 0.2F;
            float roll = ((i & 1) == 0) ? 30.0F : -30.0F;
            createTrailEmitter(PORTAL_TEXTURE, size, 12, color, vx, vy, vz, roll)
                    .emmit(effect, toVector(hitPos.add(0.0D, 0.08D, 0.0D)), IDENTITY_ROTATION, UNIT_SCALE);
        }

        createTrailEmitter(PORTAL_TEXTURE, 0.34F, 10, 0xFFFFFFFF, 0.0D, 0.02D, 0.0D, 34.0F)
                .emmit(effect, toVector(hitPos.add(0.0D, 0.08D, 0.0D)), IDENTITY_ROTATION, new Vector3f(1.35F, 1.0F, 1.35F));
    }

    public static void spawnHolyProjectileFlight(Entity projectile, Vec3 movement) {
        if (!(projectile.level() instanceof ClientLevel level) || movement.lengthSqr() < 1.0E-6D) {
            return;
        }

        if ((projectile.tickCount % HOLY_PROJECTILE_PARTICLE_INTERVAL) != 0) {
            return;
        }

        StaticLevelEffect effect = new StaticLevelEffect(level);
        Vec3 normalized = movement.normalize();
        Vec3 center = projectile.position().subtract(normalized.scale(0.3D));
        Vec3 right = horizontalRight(normalized);
        Vec3 up = verticalAxis(normalized, right);

        float angle = projectile.tickCount * 0.45F;
        Vec3 spiralOffset = right.scale(Math.cos(angle) * 0.1D).add(up.scale(Math.sin(angle) * 0.1D));
        Vec3 oppositeOffset = spiralOffset.scale(-1.0D);

        createTrailEmitter(HOLY_TEXTURE, 0.24F, 12, 0xFFFFFFFF, 0.0D, 0.005D, 0.0D, 20.0F)
                .emmit(effect, toVector(center.add(spiralOffset)), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(HEAL_TEXTURE, 0.2F, 10, 0xFFFFF1C8, 0.0D, 0.01D, 0.0D, -18.0F)
                .emmit(effect, toVector(center.add(oppositeOffset)), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(HOLY_TEXTURE, 0.16F, 9, 0xFFFFFFFF, 0.0D, 0.0D, 0.0D, 26.0F)
                .emmit(effect, toVector(center), IDENTITY_ROTATION, UNIT_SCALE);
    }

    public static void spawnHolyProjectileImpact(Entity projectile, Vec3 hitPos) {
        if (!(projectile.level() instanceof ClientLevel level)) {
            return;
        }

        StaticLevelEffect effect = new StaticLevelEffect(level);
        for (int i = 0; i < 14; i++) {
            double angle = (Math.PI * 2.0D * i) / 14.0D;
            double speed = 0.1D + ((i & 1) * 0.03D);
            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;
            double vy = 0.02D + ((i % 2) * 0.008D);
            ResourceLocation texture = (i % 3 == 0) ? HEAL_TEXTURE : HOLY_TEXTURE;
            int color = (i % 3 == 0) ? 0xFFFFF1C8 : 0xFFFFFFFF;
            createTrailEmitter(texture, 0.22F, 12, color, vx, vy, vz, (i & 1) == 0 ? 26.0F : -26.0F)
                    .emmit(effect, toVector(hitPos.add(0.0D, 0.08D, 0.0D)), IDENTITY_ROTATION, UNIT_SCALE);
        }

        createTrailEmitter(HOLY_TEXTURE, 0.3F, 10, 0xFFFFFFFF, 0.0D, 0.015D, 0.0D, 32.0F)
                .emmit(effect, toVector(hitPos.add(0.0D, 0.08D, 0.0D)), IDENTITY_ROTATION, new Vector3f(1.25F, 1.0F, 1.25F));
    }

    public static void spawnIceProjectileFlight(Entity projectile, Vec3 movement) {
        if (!(projectile.level() instanceof ClientLevel level) || movement.lengthSqr() < 1.0E-6D) {
            return;
        }

        if ((projectile.tickCount % ICE_PROJECTILE_PARTICLE_INTERVAL) != 0) {
            return;
        }

        StaticLevelEffect effect = new StaticLevelEffect(level);
        Vec3 normalized = movement.normalize();
        Vec3 center = projectile.position().subtract(normalized.scale(0.35D));
        Vec3 right = horizontalRight(normalized);
        Vec3 up = verticalAxis(normalized, right);

        float angle = projectile.tickCount * 0.6F;
        Vec3 spiralOffset = right.scale(Math.cos(angle) * 0.12D).add(up.scale(Math.sin(angle) * 0.12D));
        Vec3 oppositeOffset = spiralOffset.scale(-1.0D);

        createTrailEmitter(SNOWFLAKE_TEXTURE, 0.22F, 12, 0xFFFFFFFF, 0.0D, 0.01D, 0.0D, 22.0F)
                .emmit(effect, toVector(center.add(spiralOffset)), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(SNOW_TEXTURE, 0.2F, 11, 0xFFD8F4FF, 0.0D, 0.0D, 0.0D, -18.0F)
                .emmit(effect, toVector(center.add(oppositeOffset)), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(SNOWFLAKE_TEXTURE, 0.16F, 9, 0xFFFFFFFF, 0.0D, 0.004D, 0.0D, 28.0F)
                .emmit(effect, toVector(center), IDENTITY_ROTATION, UNIT_SCALE);
    }

    public static void spawnIceProjectileImpact(Entity projectile, Vec3 hitPos) {
        if (!(projectile.level() instanceof ClientLevel level)) {
            return;
        }

        StaticLevelEffect effect = new StaticLevelEffect(level);
        for (int i = 0; i < 16; i++) {
            double angle = (Math.PI * 2.0D * i) / 16.0D;
            double speed = 0.11D + ((i & 1) * 0.025D);
            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;
            double vy = 0.015D + ((i % 3) * 0.008D);
            ResourceLocation texture = (i % 2 == 0) ? SNOWFLAKE_TEXTURE : SNOW_TEXTURE;
            int color = (i % 2 == 0) ? 0xFFFFFFFF : 0xFFD8F4FF;
            createTrailEmitter(texture, 0.22F, 12, color, vx, vy, vz, (i & 1) == 0 ? 24.0F : -24.0F)
                    .emmit(effect, toVector(hitPos.add(0.0D, 0.08D, 0.0D)), IDENTITY_ROTATION, UNIT_SCALE);
        }

        createTrailEmitter(SNOWFLAKE_TEXTURE, 0.3F, 10, 0xFFFFFFFF, 0.0D, 0.015D, 0.0D, 30.0F)
                .emmit(effect, toVector(hitPos.add(0.0D, 0.08D, 0.0D)), IDENTITY_ROTATION, new Vector3f(1.25F, 1.0F, 1.25F));
    }

    public static void spawnFireProjectileFlight(Entity projectile, Vec3 movement) {
        if (!(projectile.level() instanceof ClientLevel level) || movement.lengthSqr() < 1.0E-6D) {
            return;
        }

        if ((projectile.tickCount % FIRE_PROJECTILE_PARTICLE_INTERVAL) != 0) {
            return;
        }

        StaticLevelEffect effect = new StaticLevelEffect(level);
        Vec3 normalized = movement.normalize();
        Vec3 center = projectile.position().subtract(normalized.scale(0.35D));
        Vec3 right = horizontalRight(normalized);
        Vec3 up = verticalAxis(normalized, right);

        float angle = projectile.tickCount * 0.6F;
        Vec3 flameOffset = right.scale(Math.cos(angle) * 0.14D).add(up.scale(Math.sin(angle) * 0.14D));
        Vec3 smokeOffset = flameOffset.scale(-0.75D);

        createTrailEmitter(FIRE_TEXTURE_1, 0.24F, 11, 0xFFFFC95A, 0.0D, 0.012D, 0.0D, 20.0F)
                .emmit(effect, toVector(center.add(flameOffset)), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(FIRE_TEXTURE_2, 0.2F, 10, 0xFFFF6A1E, 0.0D, 0.016D, 0.0D, -18.0F)
                .emmit(effect, toVector(center), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(SMOKE_TEXTURE_1, 0.22F, 13, 0xFF1C1C1C, 0.0D, 0.004D, 0.0D, 12.0F)
                .emmit(effect, toVector(center.add(smokeOffset)), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(SMOKE_TEXTURE_2, 0.16F, 11, 0xFF2B2B2B, 0.0D, 0.002D, 0.0D, -10.0F)
                .emmit(effect, toVector(center.add(smokeOffset.scale(0.55D))), IDENTITY_ROTATION, UNIT_SCALE);
    }

    public static void spawnFireProjectileImpact(Entity projectile, Vec3 hitPos) {
        if (!(projectile.level() instanceof ClientLevel level)) {
            return;
        }

        StaticLevelEffect effect = new StaticLevelEffect(level);
        for (int i = 0; i < 16; i++) {
            double angle = (Math.PI * 2.0D * i) / 16.0D;
            double speed = 0.11D + ((i & 1) * 0.03D);
            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;
            double vy = 0.02D + ((i % 3) * 0.01D);
            ResourceLocation texture = (i % 2 == 0) ? FIRE_TEXTURE_1 : FIRE_TEXTURE_2;
            int color = (i % 2 == 0) ? 0xFFFFC95A : 0xFFFF6A1E;
            createTrailEmitter(texture, 0.24F, 12, color, vx, vy, vz, (i & 1) == 0 ? 24.0F : -24.0F)
                    .emmit(effect, toVector(hitPos.add(0.0D, 0.08D, 0.0D)), IDENTITY_ROTATION, UNIT_SCALE);
        }

        for (int i = 0; i < 8; i++) {
            double angle = (Math.PI * 2.0D * i) / 8.0D;
            double speed = 0.07D + ((i & 1) * 0.02D);
            double vx = Math.cos(angle) * speed;
            double vz = Math.sin(angle) * speed;
            createTrailEmitter((i & 1) == 0 ? SMOKE_TEXTURE_1 : SMOKE_TEXTURE_2, 0.22F, 14, 0xFF242424, vx, 0.01D, vz, (i & 1) == 0 ? 12.0F : -12.0F)
                    .emmit(effect, toVector(hitPos.add(0.0D, 0.08D, 0.0D)), IDENTITY_ROTATION, UNIT_SCALE);
        }

        createTrailEmitter(FIRE_TEXTURE_1, 0.32F, 10, 0xFFFFE08A, 0.0D, 0.02D, 0.0D, 30.0F)
                .emmit(effect, toVector(hitPos.add(0.0D, 0.08D, 0.0D)), IDENTITY_ROTATION, new Vector3f(1.25F, 1.0F, 1.25F));
    }

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        ClientLevel level = minecraft.level;
        if (level == null) {
            ACTIVE_DRAGON_DESCEND_CASTS.clear();
            return;
        }

        StaticLevelEffect effect = new StaticLevelEffect(level);
        for (OrbitState state : ACTIVE_DRAGON_DESCEND_CASTS.values()) {
            Entity entity = level.getEntity(state.entityId);
            if (entity == null || !entity.isAlive()) {
                ACTIVE_DRAGON_DESCEND_CASTS.remove(state.entityId);
                continue;
            }

            spawnOrbit(effect, entity, state.tick);
            state.tick++;
        }
    }

    @SubscribeEvent
    public static void onClientLogout(ClientPlayerNetworkEvent.LoggingOut event) {
        ACTIVE_DRAGON_DESCEND_CASTS.clear();
    }

    private static void spawnOrbit(StaticLevelEffect effect, Entity entity, int tick) {
        float baseAngle = (tick * ROTATION_SPEED) % Mth.TWO_PI;
        for (int i = 0; i < ORBIT_PARTICLES_PER_TICK; i++) {
            float angle = baseAngle + ((Mth.TWO_PI / ORBIT_PARTICLES_PER_TICK) * i);
            emitPhotonParticle(effect, entity, angle, INNER_RADIUS, INNER_HEIGHT, 0.24F, 7, 0xFFFFFFFF, 0.0D, -0.01D, 0.0D, angle * Mth.RAD_TO_DEG, 26.0F);
            emitPhotonParticle(effect, entity, angle + (Mth.TWO_PI / 8.0F), OUTER_RADIUS, OUTER_HEIGHT, 0.32F, 7, 0xFFE8D8FF, 0.0D, 0.018D, 0.0D, -angle * Mth.RAD_TO_DEG, -34.0F);
        }
    }

    private static void emitPhotonParticle(StaticLevelEffect effect, Entity entity, float angle, float radius, float height,
                                           float size, int lifetime, int color, double vx, double vy, double vz,
                                           float startRollDegrees, float rollPerTickDegrees) {
        double x = entity.getX() + Mth.cos(angle) * radius;
        double y = entity.getY() + height;
        double z = entity.getZ() + Mth.sin(angle) * radius;
        ParticleEmitter emitter = createTrailEmitter(PORTAL_TEXTURE, size, lifetime, color, vx, vy, vz, rollPerTickDegrees, startRollDegrees);
        emitter.emmit(effect, new Vector3f((float) x, (float) y, (float) z), IDENTITY_ROTATION, UNIT_SCALE);
    }

    private static void spawnBurst(Entity entity) {
        if (!(entity.level() instanceof ClientLevel level)) {
            return;
        }

        StaticLevelEffect effect = new StaticLevelEffect(level);
        double baseY = entity.getY() + entity.getBbHeight() * 0.72D;
        for (int i = 0; i < BURST_PARTICLE_COUNT; i++) {
            double angle = (Math.PI * 2.0D * i) / BURST_PARTICLE_COUNT;
            double spread = BURST_SPEED * (0.85D + ((i & 1) * 0.2D));
            double vx = Math.cos(angle) * spread;
            double vz = Math.sin(angle) * spread;
            double vy = ((i % 4) - 1.5D) * 0.03D;
            float startRollDegrees = (float) Math.toDegrees(angle);
            float rollPerTickDegrees = ((i & 1) == 0) ? 42.0F : -42.0F;
            ParticleEmitter emitter = createTrailEmitter(PORTAL_TEXTURE, 0.34F, 11, 0xFFFFFFFF, vx, vy, vz, rollPerTickDegrees, startRollDegrees);
            emitter.emmit(effect, new Vector3f((float) entity.getX(), (float) baseY, (float) entity.getZ()), IDENTITY_ROTATION, UNIT_SCALE);
        }
    }

    private static void spawnTrailSegment(StaticLevelEffect effect, Vec3 center, Vec3 right, Vec3 forward) {
        Vec3 leftEdge = center.add(right.scale(TRAIL_HALF_WIDTH));
        Vec3 rightEdge = center.add(right.scale(-TRAIL_HALF_WIDTH));
        Vec3 forwardOffset = forward.scale(0.3D);
        Vec3 centerLeft = center.add(right.scale(0.5D));
        Vec3 centerRight = center.add(right.scale(-0.5D));

        createTrailEmitter(PORTAL_TEXTURE, 0.52F, TRAIL_VISUAL_LIFETIME, 0xFFD8C0FF, 0.0D, 0.01D, 0.0D, 10.0F)
                .emmit(effect, toVector(center), IDENTITY_ROTATION, new Vector3f(1.9F, 1.0F, 1.9F));
        createTrailEmitter(PORTAL_TEXTURE, 0.46F, TRAIL_VISUAL_LIFETIME, 0xFFE8D8FF, 0.0D, 0.012D, 0.0D, -8.0F)
                .emmit(effect, toVector(centerLeft), IDENTITY_ROTATION, new Vector3f(1.45F, 1.0F, 1.45F));
        createTrailEmitter(PORTAL_TEXTURE, 0.46F, TRAIL_VISUAL_LIFETIME, 0xFFE8D8FF, 0.0D, 0.012D, 0.0D, 8.0F)
                .emmit(effect, toVector(centerRight), IDENTITY_ROTATION, new Vector3f(1.45F, 1.0F, 1.45F));
        createTrailEmitter(PORTAL_TEXTURE, 0.42F, TRAIL_VISUAL_LIFETIME, 0xFFFFFFFF, right.x * 0.05D, 0.03D, right.z * 0.05D, 18.0F)
                .emmit(effect, toVector(leftEdge.add(forwardOffset)), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(PORTAL_TEXTURE, 0.42F, TRAIL_VISUAL_LIFETIME, 0xFFE8D8FF, -right.x * 0.05D, 0.03D, -right.z * 0.05D, -18.0F)
                .emmit(effect, toVector(rightEdge.add(forwardOffset)), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(PORTAL_TEXTURE, 0.3F, TRAIL_VISUAL_LIFETIME - 8, 0xFFF3EAFF, right.x * 0.03D, 0.015D, right.z * 0.03D, 24.0F)
                .emmit(effect, toVector(center.add(right.scale(0.75D))), IDENTITY_ROTATION, UNIT_SCALE);
        createTrailEmitter(PORTAL_TEXTURE, 0.3F, TRAIL_VISUAL_LIFETIME - 8, 0xFFF3EAFF, -right.x * 0.03D, 0.015D, -right.z * 0.03D, -24.0F)
                .emmit(effect, toVector(center.add(right.scale(-0.75D))), IDENTITY_ROTATION, UNIT_SCALE);
    }

    private static ParticleEmitter createBreathEmitter() {
        ParticleEmitter emitter = new ParticleEmitter();
        ParticleConfig config = emitter.config;

        emitter.setName("dragon_descend_breath");
        config.setDuration(1);
        config.setLooping(false);
        config.setMaxParticles(BREATH_PARTICLE_COUNT);
        config.setStartLifetime(NumberFunction.constant(10));
        config.setStartSpeed(NumberFunction.constant(0.9F));
        config.setStartSize(new NumberFunction3(0.42F, 0.42F, 0.42F));
        config.setStartRotation(new NumberFunction3(0.0F, 0.0F, 0.0F));
        config.setStartColor(NumberFunction.color(0xFFDBC3FF));
        config.emission.setEmissionRate(NumberFunction.constant(0.0F));

        var burst = new com.lowdragmc.photon.client.gameobject.emitter.data.EmissionSetting.Burst();
        burst.time = 0;
        burst.cycles = 1;
        burst.interval = 1;
        burst.probability = 1.0F;
        burst.setCount(NumberFunction.constant(BREATH_PARTICLE_COUNT));
        config.emission.getBursts().add(burst);

        Cone cone = new Cone();
        cone.setRadius(0.2F);
        cone.setRadiusThickness(0.0F);
        cone.setAngle(22.0F);
        cone.setArc(360.0F);
        config.shape.setShape(cone);
        config.shape.setPosition(new NumberFunction3(0.0F, 0.0F, 0.0F));

        applySharedMaterial(config, PORTAL_TEXTURE, 0xFFDBC3FF);

        config.velocityOverLifetime.setEnable(true);
        config.velocityOverLifetime.setOrbitalMode(VelocityOverLifetimeSetting.OrbitalMode.FixedVelocity);
        config.velocityOverLifetime.setLinear(new NumberFunction3(0.0F, 0.0F, 0.0F));
        config.velocityOverLifetime.setSpeedModifier(NumberFunction.constant(0.92F));

        config.rotationOverLifetime.setEnable(true);
        config.rotationOverLifetime.setRoll(NumberFunction.constant(36.0F));

        return emitter;
    }

    private static ParticleEmitter createTrailEmitter(ResourceLocation texture, float size, int lifetime, int color,
                                                      double vx, double vy, double vz, float rollPerTickDegrees) {
        return createTrailEmitter(texture, size, lifetime, color, vx, vy, vz, rollPerTickDegrees, 0.0F);
    }

    private static ParticleEmitter createTrailEmitter(ResourceLocation texture, float size, int lifetime, int color,
                                                      double vx, double vy, double vz, float rollPerTickDegrees,
                                                      float startRollDegrees) {
        ParticleEmitter emitter = new ParticleEmitter();
        ParticleConfig config = emitter.config;

        emitter.setName("projectile_trail");
        config.setDuration(1);
        config.setLooping(false);
        config.setMaxParticles(1);
        config.setStartLifetime(NumberFunction.constant(lifetime));
        config.setStartSpeed(NumberFunction.constant(0.0F));
        config.setStartSize(new NumberFunction3(size, size, size));
        config.setStartRotation(new NumberFunction3(startRollDegrees, 0.0F, 0.0F));
        config.setStartColor(NumberFunction.color(color));
        config.emission.setEmissionRate(NumberFunction.constant(0.0F));

        var burst = new com.lowdragmc.photon.client.gameobject.emitter.data.EmissionSetting.Burst();
        burst.time = 0;
        burst.cycles = 1;
        burst.interval = 1;
        burst.probability = 1.0F;
        burst.setCount(NumberFunction.constant(1));
        config.emission.getBursts().add(burst);

        config.shape.setShape(new Dot());
        config.shape.setPosition(new NumberFunction3(0.0F, 0.0F, 0.0F));

        applySharedMaterial(config, texture, color);

        config.velocityOverLifetime.setEnable(true);
        config.velocityOverLifetime.setOrbitalMode(VelocityOverLifetimeSetting.OrbitalMode.FixedVelocity);
        config.velocityOverLifetime.setLinear(new NumberFunction3(vx * 20.0D, vy * 20.0D, vz * 20.0D));
        config.velocityOverLifetime.setSpeedModifier(NumberFunction.constant(1.0F));

        config.colorOverLifetime.setEnable(true);
        Gradient fadeGradient = new Gradient();
        fadeGradient.getGradientColor().deserializeNBT(new GradientColor(color, color & 0x00FFFFFF).serializeNBT());
        config.colorOverLifetime.setColor(fadeGradient);

        config.rotationOverLifetime.setEnable(true);
        config.rotationOverLifetime.setRoll(NumberFunction.constant(rollPerTickDegrees));

        return emitter;
    }

    private static void applySharedMaterial(ParticleConfig config, ResourceLocation texture, int color) {
        config.material.setCull(false);
        config.material.setDepthMask(false);
        config.material.setDepthTest(true);
        TextureMaterial textureMaterial = new TextureMaterial(texture);
        textureMaterial.discardThreshold = 0.02F;
        config.material.setMaterial(textureMaterial);
        config.renderer.setBloomEffect(true);
        config.renderer.setBloomColor(color);
    }

    private static Vec3 horizontalRight(Vec3 normalized) {
        Vec3 right = new Vec3(-normalized.z, 0.0D, normalized.x);
        return right.lengthSqr() < 1.0E-6D ? new Vec3(1.0D, 0.0D, 0.0D) : right.normalize();
    }

    private static Vec3 verticalAxis(Vec3 normalized, Vec3 right) {
        Vec3 up = normalized.cross(right);
        return up.lengthSqr() < 1.0E-6D ? new Vec3(0.0D, 1.0D, 0.0D) : up.normalize();
    }

    private static ResourceLocation texture(String fileName) {
        return ResourceLocation.fromNamespaceAndPath(ProjectBabylonWeapons.MODID, "textures/particle/" + fileName);
    }

    private static Quaternionf quaternionFromDirection(Vec3 direction) {
        Vector3f target = new Vector3f((float) direction.x, (float) direction.y, (float) direction.z).normalize();
        return new Quaternionf().rotateTo(new Vector3f(0.0F, 1.0F, 0.0F), target);
    }

    private static Vector3f toVector(Vec3 vec3) {
        return new Vector3f((float) vec3.x, (float) vec3.y, (float) vec3.z);
    }

    private record StaticLevelEffect(Level level) implements IEffect {
        @Override
        public Level getLevel() {
            return this.level;
        }
    }

    private static final class OrbitState {
        private final int entityId;
        private int tick;

        private OrbitState(int entityId) {
            this.entityId = entityId;
            this.tick = 0;
        }
    }
}









