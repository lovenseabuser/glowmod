package net.glowmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class GlowMod implements ClientModInitializer {

    public static boolean renderBoost = false;

    // Отслеживаем предыдущее состояние чтобы реагировать только на нажатие (не удержание)
    private boolean wasAltPPressed = false;

    @Override
    public void onInitializeClient() {
        // Keybinding НЕ регистрируем — в "Управлении" ничего не будет видно

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            long window = MinecraftClient.getInstance().getWindow().getHandle();

            // Проверяем Alt (Left Alt = GLFW_KEY_LEFT_ALT) + P
            boolean altHeld = InputUtil.isKeyPressed(window, GLFW.GLFW_KEY_LEFT_ALT)
                           || InputUtil.isKeyPressed(window, GLFW.GLFW_KEY_RIGHT_ALT);
            boolean pHeld   = InputUtil.isKeyPressed(window, GLFW.GLFW_KEY_P);

            boolean isAltPPressed = altHeld && pHeld;

            // Срабатываем только на момент нажатия, не на удержание
            if (isAltPPressed && !wasAltPPressed) {
                renderBoost = !renderBoost;
                String status = renderBoost ? "Enabled" : "Disabled";
                client.player.sendMessage(Text.literal("[PerformanceTweaks] FPS Boost: " + status), false);
            }

            wasAltPPressed = isAltPPressed;
        });
    }
}
