package com.thezxc07.betterf5;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.Perspective;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

public class InputHandler {
    public static float cameraDistance = 4.0F; // Default camera distance
    public static float cameraYawOffset = 0.0F;
    public static float cameraPitchOffset = 0.0F;

    public static void initialize() {
        // Handle client tick events
        ClientTickEvents.END_CLIENT_TICK.register(clientTick -> {
            if (!clientTick.isPaused()) {
                handleInput(clientTick);
            }
        });

        // Register GLFW scroll callback after the client has started
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            GLFW.glfwSetScrollCallback(client.getWindow().getHandle(), (window, xOffset, yOffset) -> {
                onMouseScroll(xOffset, yOffset);
            });
        });
    }

    private static void handleInput(MinecraftClient client) {
        if (client.options.getPerspective().isFirstPerson()) {
            // Reset camera adjustments when returning to first-person view
            resetCameraOffsets();
            return;
        }

        handleArrowKeys(client);
    }

    private static void handleArrowKeys(MinecraftClient client) {
        float rotationSpeed = 1.0F; // Adjust rotation speed as needed

        long windowHandle = client.getWindow().getHandle();

        if (InputUtil.isKeyPressed(windowHandle, GLFW.GLFW_KEY_LEFT)) {
            cameraYawOffset = MathHelper.clamp(cameraYawOffset - rotationSpeed, -45.0F, 45.0F);
        }
        if (InputUtil.isKeyPressed(windowHandle, GLFW.GLFW_KEY_RIGHT)) {
            cameraYawOffset = MathHelper.clamp(cameraYawOffset + rotationSpeed, -45.0F, 45.0F);
        }
        if (InputUtil.isKeyPressed(windowHandle, GLFW.GLFW_KEY_UP)) {
            cameraPitchOffset = MathHelper.clamp(cameraPitchOffset - rotationSpeed, -30.0F, 30.0F);
        }
        if (InputUtil.isKeyPressed(windowHandle, GLFW.GLFW_KEY_DOWN)) {
            cameraPitchOffset = MathHelper.clamp(cameraPitchOffset + rotationSpeed, -30.0F, 30.0F);
        }
    }

    private static void onMouseScroll(double xOffset, double yOffset) {
        MinecraftClient client = MinecraftClient.getInstance();

        if (client.options.getPerspective().isFirstPerson()) {
            return; // Do not process scroll events in first-person view
        }

        cameraDistance = MathHelper.clamp(cameraDistance - (float) yOffset * 0.5F, 2.0F, 10.0F);
    }
    private static void resetCameraOffsets() {
        cameraYawOffset = 0.0F;
        cameraPitchOffset = 0.0F;
        cameraDistance = 4.0F; // Reset to default distance
    }
}