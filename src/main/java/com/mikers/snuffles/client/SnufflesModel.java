package com.mikers.snuffles.client;

import com.mikers.snuffles.MikersSnuffles;
import com.mikers.snuffles.entity.SnufflesEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.state.GeoRenderState; // Важно: параметр теперь GeoRenderState

public class SnufflesModel extends GeoModel<SnufflesEntity> {

    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        // В GeckoLib 5 префикс пути и расширение файла указывать не нужно
        return Identifier.fromNamespaceAndPath(MikersSnuffles.MOD_ID, "mikers_snuffles");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        // В отличие от моделей/анимаций, префикс textures/ и .png указывать нужно
        return Identifier.fromNamespaceAndPath(MikersSnuffles.MOD_ID, "textures/entity/mikers_snuffles1.png");
    }

    @Override
    public Identifier getAnimationResource(SnufflesEntity entity) {
        // В GeckoLib 5 префикс пути и расширение файла указывать не нужно
        return Identifier.fromNamespaceAndPath(MikersSnuffles.MOD_ID, "mikers_snuffles");
    }
}
