package com.thezxc07.betterf5.mixin.client;

import com.thezxc07.betterf5.InputHandler;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.gen.Invoker;



/**
 * Mixin to adjust the camera position and rotation in third-person view.
 */
@Mixin(Camera.class)
public abstract class CameraMixin {

    //@Shadow @Final private GameRenderer gameRenderer;

    @Invoker("setPos")
    public abstract void invokeSetPos(double x, double y, double z);

    @Invoker("setRotation")
    public abstract void invokeSetRotation(float yaw, float pitch);

    @Inject(method = "update", at = @At("TAIL"))
    private void onUpdate(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo info) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.options.getPerspective().isFirstPerson()) {
            return;
        }

        adjustCameraPosition((Camera) (Object) this, focusedEntity, tickDelta);
    }

    private void adjustCameraPosition(Camera camera, Entity focusedEntity, float tickDelta) {
        float baseYaw = focusedEntity.getYaw(tickDelta);
        float basePitch = focusedEntity.getPitch(tickDelta);

        float yaw = baseYaw + InputHandler.cameraYawOffset;
        float pitch = MathHelper.clamp(basePitch + InputHandler.cameraPitchOffset, -90.0F, 90.0F);

        double horizontalDistance = InputHandler.cameraDistance * MathHelper.cos((float) Math.toRadians(pitch));
        double offsetX = -horizontalDistance * MathHelper.sin((float) Math.toRadians(yaw));
        double offsetY = -InputHandler.cameraDistance * MathHelper.sin((float) Math.toRadians(pitch));
        double offsetZ = horizontalDistance * MathHelper.cos((float) Math.toRadians(yaw));

        this.invokeSetPos(
                focusedEntity.getX() + offsetX,
                focusedEntity.getY() + focusedEntity.getStandingEyeHeight() + offsetY,
                focusedEntity.getZ() + offsetZ
        );

        this.invokeSetRotation(yaw, pitch);
    }
}
