package net.glowmod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import com.mojang.brigadier.Command;
import static net.minecraft.server.command.CommandManager.literal;

public class GlowMod implements ClientModInitializer {

    public static boolean glowEnabled = false;

    @Override
    public void onInitializeClient() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(
                literal("highlight")
                    .executes(ctx -> {
                        glowEnabled = !glowEnabled;
                        MinecraftClient client = MinecraftClient.getInstance();
                        if (client.player != null) {
                            client.player.sendMessage(
                                Text.literal("§6[GlowMod] §fPlayer highlight: " + (glowEnabled ? "§aON" : "§cOFF")),
                                false
                            );
                        }
                        return Command.SINGLE_SUCCESS;
                    })
            );
        });

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null || client.player == null) return;
            for (PlayerEntity player : client.world.getPlayers()) {
                if (player == client.player) continue;
                player.setGlowing(glowEnabled);
            }
        });
    }
}
