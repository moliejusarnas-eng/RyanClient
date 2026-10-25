package com.ryanclient;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class RyanClient implements ClientModInitializer {
    private static KeyBinding openMenuKey;
    private static KeyBinding triggerToggleKey;
    private static KeyBinding aimToggleKey;
    private static KeyBinding rotationToggleKey;

    static boolean triggerBotEnabled = false;
    static boolean aimAssistEnabled = false;
    static boolean rotationDiagnosticsEnabled = false;
    static int triggerDelayMs = 150;

    @Override
    public void onInitializeClient() {
        openMenuKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ryanclient.open_menu", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_O, KeyBinding.Category.MISC));
        triggerToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ryanclient.triggerbot", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R, KeyBinding.Category.MISC));
        aimToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ryanclient.aimassist", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F, KeyBinding.Category.MISC));
        rotationToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.ryanclient.rotation_diagnostics", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_U, KeyBinding.Category.MISC));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.wasPressed()) {
                if (client.currentScreen instanceof RyanClickGui) {
                    client.setScreen(null);
                } else if (client.currentScreen == null) {
                    client.setScreen(new RyanClickGui());
                }
            }
            while (triggerToggleKey.wasPressed()) {
                triggerBotEnabled = !triggerBotEnabled;
                notify(client, "TriggerBot", triggerBotEnabled);
            }
            while (aimToggleKey.wasPressed()) {
                aimAssistEnabled = !aimAssistEnabled;
                notify(client, "AimAssist", aimAssistEnabled);
            }
            while (rotationToggleKey.wasPressed()) {
                rotationDiagnosticsEnabled = !rotationDiagnosticsEnabled;
                notify(client, "Rotation diagnostics", rotationDiagnosticsEnabled);
            }
        });

        HudRenderCallback.EVENT.register((drawContext, tickCounter) -> renderHud(MinecraftClient.getInstance(), drawContext));
    }

    private static void notify(MinecraftClient client, String module, boolean enabled) {
        if (client.player != null) {
            client.player.sendMessage(Text.literal("§8[§fRyanClient§8] §7" + module + ": " + (enabled ? "§fON" : "§7OFF")), true);
        }
    }

    private static void renderHud(MinecraftClient client, DrawContext ctx) {
        if (client.player == null || client.options.hudHidden) return;

        int x = 6, y = 6;
        ctx.fill(x, y, x + 126, y + 15, 0xD9080808);
        ctx.drawText(client.textRenderer, Text.literal("RyanClient"), x + 5, y + 4, 0xFFFFFFFF, false);
        y += 19;

        ctx.fill(x, y, x + 126, y + 49, 0xB8080808);
        ctx.drawText(client.textRenderer, Text.literal("FPS: " + client.getCurrentFps()), x + 5, y + 4, 0xFFE0E0E0, false);
        ctx.drawText(client.textRenderer, Text.literal(String.format("XYZ: %.1f %.1f %.1f",
                client.player.getX(), client.player.getY(), client.player.getZ())),
                x + 5, y + 16, 0xFFE0E0E0, false);
        ctx.drawText(client.textRenderer, Text.literal("R Trigger  F Aim  U Rot"),
                x + 5, y + 32, 0xFFB9A1FF, false);
    }
}
