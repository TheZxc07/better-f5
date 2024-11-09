package com.thezxc07.betterf5;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;
import com.thezxc07.betterf5.mixin.client.CameraAccessor;

public class InputHandler {
    public static float cameraDistance = 4.0F; // Default camera distance
    public static float cameraYawOffset = 0.0F;
    public static float cameraPitchOffset = 0.0F;

    public static void initialize() {
        ClientTickEvents.END_CLIENT_TICK.register(clientTick -> {
            if (!clientTick.isPaused()) {
                handleInput(clientTick);
            }
        });

        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            GLFW.glfwSetScrollCallback(client.getWindow().getHandle(), (window, xOffset, yOffset) -> {
                onMouseScroll(xOffset, yOffset);
            });
        });
    }

    public static void onCameraPerspectiveChanged() {
        if (MinecraftClient.getInstance().options.getPerspective().isFirstPerson()) {
            // Reset camera adjustments when returning to first-person view
            resetCameraOffsets();
        }
    }

    private static void handleInput(MinecraftClient client) {
        if (client.options.getPerspective().isFirstPerson()) {
            resetCameraOffsets();
            return;
        }
        handleArrowKeys(client);
    }

    private static void handleArrowKeys(MinecraftClient client) {
        float rotationSpeed = 1.0F;

        long windowHandle = client.getWindow().getHandle();

        if (InputUtil.isKeyPressed(windowHandle, GLFW.GLFW_KEY_LEFT)) {
            cameraYawOffset = MathHelper.wrapDegrees(cameraYawOffset - rotationSpeed);
        }
        if (InputUtil.isKeyPressed(windowHandle, GLFW.GLFW_KEY_RIGHT)) {
            cameraYawOffset = MathHelper.wrapDegrees(cameraYawOffset + rotationSpeed);
        }
        if (InputUtil.isKeyPressed(windowHandle, GLFW.GLFW_KEY_UP)) {
            cameraPitchOffset = MathHelper.clamp(cameraPitchOffset - rotationSpeed, -90.0F, 90.0F);
        }
        if (InputUtil.isKeyPressed(windowHandle, GLFW.GLFW_KEY_DOWN)) {
            cameraPitchOffset = MathHelper.clamp(cameraPitchOffset + rotationSpeed, -90.0F, 90.0F);
        }
    }

    private static void onMouseScroll(double xOffset, double yOffset) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.options.getPerspective().isFirstPerson()) {
            return; // Do not process scroll events in first-person view
        }

        cameraDistance = MathHelper.clamp(cameraDistance - (float) yOffset * 2f, 2.0F, 50.0F);
    }

    private static void resetCameraOffsets() {
        cameraYawOffset = 0.0F;
        cameraPitchOffset = 0.0F;
        cameraDistance = 4.0F; // Reset to default distance
    }
}
