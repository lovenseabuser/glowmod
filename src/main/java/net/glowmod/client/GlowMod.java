package net.glowmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class GlowMod implements ClientModInitializer {

    public static boolean glowEnabled = false;
    private static KeyBinding keyBinding;
    private static boolean wasPressed = false;

    @Override
    public void onInitializeClient() {
        keyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.glowmod.toggle",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            "category.glowmod"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null || client.player == null) return;

            boolean isPressed = keyBinding.isPressed();
            if (isPressed && !wasPressed) {
                glowEnabled = !glowEnabled;
                client.player.sendMessage(
                    Text.literal("§6[GlowMod] §fPlayer highlight: " + (glowEnabled ? "§aON" : "§cOFF")),
                    false
                );
            }
            wasPressed = isPressed;

            for (PlayerEntity player : client.world.getPlayers()) {
                if (player == client.player) continue;
                player.setGlowing(glowEnabled);
            }
        });
    }
}
