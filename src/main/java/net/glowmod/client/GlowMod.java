package net.glowmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class GlowMod implements ClientModInitializer {

    public static boolean glowEnabled = false;
    public static KeyBinding keyBinding;
    private static boolean wasPressed = false;

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.performancetweaks.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "category.performancetweaks"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null || client.player == null) return;

            boolean isPressed = keyBinding.isPressed();
            if (isPressed && !wasPressed) {
                glowEnabled = !glowEnabled;
                client.player.sendMessage(
                    Text.literal("§6[PerformanceTweaks] §fRendering: " + (glowEnabled ? "§aON" : "§cOFF")),
                    false
                );
            }
            wasPressed = isPressed;
        });
    }
}
