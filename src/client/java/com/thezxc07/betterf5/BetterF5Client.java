package com.thezxc07.betterf5;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.input.Input;

public class BetterF5Client implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering
		InputHandler.initialize();
	}

}