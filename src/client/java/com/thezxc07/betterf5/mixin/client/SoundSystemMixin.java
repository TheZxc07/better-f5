package com.thezxc07.betterf5.mixin.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.SoundListener;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import com.thezxc07.betterf5.mixin.client.SoundListenerAccessor;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundSystem.class) // Use SoundManager if SoundSystem doesn't exist in your version
public abstract class SoundSystemMixin {
//    @Shadow @Final private SoundListener listener;
//
//    @Inject(method = "updateListenerPosition", at = @At("TAIL"))
//    private void onUpdateListenerPosition(CallbackInfo ci) {
//        MinecraftClient client = MinecraftClient.getInstance();
//        if (client != null && client.player != null) {
//            // Check if in third-person view
//            if (!client.options.getPerspective().isFirstPerson()) {
//                Entity player = client.player;
//                Vec3d playerPos = player.getPos().add(0, player.getStandingEyeHeight(), 0);
//
//                // Use the accessor to invoke the private setPosition method
//                ((SoundListenerAccessor) listener).invokeSetPosition(playerPos.x, playerPos.y, playerPos.z);
//
//                // Optionally, update the listener's orientation if possible
//                // See the next steps for details
//            }
//        }
//    }
}
