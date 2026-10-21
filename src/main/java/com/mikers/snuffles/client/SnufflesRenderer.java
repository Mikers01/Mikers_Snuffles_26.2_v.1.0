package com.mikers.snuffles.client;

import com.mikers.snuffles.entity.SnufflesEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.GeoRenderLayer; // Если будешь добавлять слои
import software.bernie.geckolib.renderer.state.GeoRenderState;

public class SnufflesRenderer extends GeoEntityRenderer<SnufflesEntity, GeoRenderState> {
    
    public SnufflesRenderer(EntityRendererProvider.Context context) {
        super(context, new SnufflesModel());
        this.shadowRadius = 0.55F;
    }
}
