package com.mikers.snuffles.client;
import com.mikers.snuffles.MikersSnuffles;
import com.mikers.snuffles.entity.SnufflesEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib.model.GeoModel;
public class SnufflesModel extends GeoModel<SnufflesEntity> {
    @Override public ResourceLocation getModelResource(SnufflesEntity entity) { return ResourceLocation.fromNamespaceAndPath(MikersSnuffles.MOD_ID,"geo/mikers_snuffles.geo.json"); }
    @Override public ResourceLocation getTextureResource(SnufflesEntity entity) { return ResourceLocation.fromNamespaceAndPath(MikersSnuffles.MOD_ID,"textures/entity/mikers_snuffles"+(entity.getVariant()+1)+".png"); }
    @Override public ResourceLocation getAnimationResource(SnufflesEntity entity) { return ResourceLocation.fromNamespaceAndPath(MikersSnuffles.MOD_ID,"animations/mikers_snuffles.animation.json"); }
}
