package com.mikers.snuffles.client;

import com.mikers.snuffles.MikersSnuffles;
import com.mikers.snuffles.entity.SnufflesEntity;
import net.minecraft.resources.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.state.GeoRenderState;

public class SnufflesModel extends GeoModel<SnufflesEntity> {

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(MikersSnuffles.MOD_ID, "mikers_snuffles");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(MikersSnuffles.MOD_ID, "textures/entity/mikers_snuffles1.png");
    }

    @Override
    public Identifier getAnimationResource(SnufflesEntity entity) {
        return Identifier.fromNamespaceAndPath(MikersSnuffles.MOD_ID, "mikers_snuffles");
    }
}
