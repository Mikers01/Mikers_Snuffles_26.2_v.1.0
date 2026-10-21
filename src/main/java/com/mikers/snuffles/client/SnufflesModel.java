package com.mikers.snuffles.client;

import com.mikers.snuffles.MikersSnuffles;
import com.mikers.snuffles.entity.SnufflesEntity;
import net.minecraft.resources.Identifier;
import com.geckolib.model.GeoModel; // Пакет изменен с software.bernie на com.geckolib

public class SnufflesModel extends GeoModel<SnufflesEntity> {
    
    @Override
    public Identifier getModelResource(SnufflesEntity entity) {
        // В GeckoLib 5 не нужно писать папку "geo/", она подставляется автоматически
        return Identifier.fromNamespaceAndPath(MikersSnuffles.MOD_ID, "mikers_snuffles");
    }

    @Override
    public Identifier getTextureResource(SnufflesEntity entity) {
        // Если у тебя 4 текстуры, тут нужна логика выбора. Пока ставим первую.
        // В GeckoLib 5 не нужно писать папку "textures/entity/", она подставляется автоматически
        return Identifier.fromNamespaceAndPath(MikersSnuffles.MOD_ID, "mikers_snuffles1");
    }

    @Override
    public Identifier getAnimationResource(SnufflesEntity entity) {
        // В GeckoLib 5 не нужно писать папку "animations/"
        return Identifier.fromNamespaceAndPath(MikersSnuffles.MOD_ID, "mikers_snuffles");
    }
}
