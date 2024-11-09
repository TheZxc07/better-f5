// In your mixin package
package com.thezxc07.betterf5.mixin.client;

import net.minecraft.client.sound.SoundListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SoundListener.class)
public interface SoundListenerAccessor {
//    @Invoker("setPosition")
//    void invokeSetPosition(double x, double y, double z);
}
