package com.mikers.snuffles.client;

import com.mikers.snuffles.entity.SnufflesEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.state.GeoRenderState;

public class SnufflesRenderer extends GeoEntityRenderer<SnufflesEntity, GeoRenderState> {

    public SnufflesRenderer(EntityRendererProvider.Context context) {
        super(context, new SnufflesModel());
        this.shadowRadius = 0.55F;
    }
}
