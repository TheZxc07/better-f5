package com.thezxc07.betterf5.mixin.client;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.gen.Invoker;


@Mixin(Camera.class)
public interface CameraAccessor {
    @Invoker("clipToSpace")
    float invokeClipToSpace(float desiredCameraDistance);

    @Invoker("moveBy")
    void invokeMoveBy(float x, float y, float z);

    @Invoker("setRotation")
    void invokeSetRotation(float yaw, float pitch);
}