package com.mikers.snuffles.client;

import com.mikers.snuffles.entity.SnufflesEntity;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import com.geckolib.renderer.GeoEntityRenderer;

public class SnufflesRenderer extends GeoEntityRenderer<SnufflesEntity> {

    public SnufflesRenderer(EntityRendererProvider.Context context) {
        super(context, new SnufflesModel());
        this.shadowRadius = 0.55F;
    }
}
