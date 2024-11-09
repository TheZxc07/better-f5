package com.thezxc07.betterf5.mixin.client;


import com.thezxc07.betterf5.InputHandler;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
@Mixin(Camera.class)
public abstract class CameraMixin {

    @Inject(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;moveBy(FFF)V", ordinal = 0, shift = At.Shift.BEFORE))
    private void onBeforeMoveBy(BlockView area, Entity focusedEntity, boolean thirdPerson, boolean inverseView, float tickDelta, CallbackInfo ci) {
        if (!thirdPerson) {
            return;
        }

        CameraAccessor camera = (CameraAccessor) this;

        // Adjust the desired camera distance
        float desiredDistance = InputHandler.cameraDistance;

        // Use the same scaling factor 'f' as in the update method
        float scale = 1.0F; // Assuming 'f' is 1.0F for simplicity

        // Calculate the clipped distance
        float clippedDistance = camera.invokeClipToSpace(desiredDistance * scale);

        // Adjust the moveBy parameters accordingly
        float x = -clippedDistance;

        // Now invoke moveBy with the adjusted distance
        camera.invokeMoveBy(x, 0.0F, 0.0F);

        // Adjust the camera rotation
        float yaw = ((Camera) (Object) this).getYaw() + InputHandler.cameraYawOffset;
        float pitch = ((Camera) (Object) this).getPitch() + InputHandler.cameraPitchOffset;

        camera.invokeSetRotation(yaw, pitch);
    }
}