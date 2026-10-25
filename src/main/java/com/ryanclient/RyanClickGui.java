package com.ryanclient;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class RyanClickGui extends Screen {
    private static final int PANEL_WIDTH = 230;
    private static final int PANEL_HEIGHT = 205;
    private int left;
    private int top;

    public RyanClickGui() {
        super(Text.literal("RyanClient"));
    }

    @Override
    protected void init() {
        left = (this.width - PANEL_WIDTH) / 2;
        top = (this.height - PANEL_HEIGHT) / 2;
        int bx = left + 12;
        int bw = PANEL_WIDTH - 24;
        int bh = 20;
        int gap = 5;
        int y = top + 39;

        addDrawableChild(ButtonWidget.builder(moduleText("TriggerBot", RyanClient.triggerBotEnabled), b -> {
            RyanClient.triggerBotEnabled = !RyanClient.triggerBotEnabled;
            b.setMessage(moduleText("TriggerBot", RyanClient.triggerBotEnabled));
        }).dimensions(bx, y, bw, bh).build());

        y += bh + gap;
        addDrawableChild(ButtonWidget.builder(moduleText("AimAssist", RyanClient.aimAssistEnabled), b -> {
            RyanClient.aimAssistEnabled = !RyanClient.aimAssistEnabled;
            b.setMessage(moduleText("AimAssist", RyanClient.aimAssistEnabled));
        }).dimensions(bx, y, bw, bh).build());

        y += bh + gap;
        addDrawableChild(ButtonWidget.builder(moduleText("Rotation diagnostics", RyanClient.rotationDiagnosticsEnabled), b -> {
            RyanClient.rotationDiagnosticsEnabled = !RyanClient.rotationDiagnosticsEnabled;
            b.setMessage(moduleText("Rotation diagnostics", RyanClient.rotationDiagnosticsEnabled));
        }).dimensions(bx, y, bw, bh).build());

        y += bh + gap;
        addDrawableChild(ButtonWidget.builder(Text.literal("Trigger delay: " + RyanClient.triggerDelayMs + " ms"), b -> {
            RyanClient.triggerDelayMs += 50;
            if (RyanClient.triggerDelayMs > 500) RyanClient.triggerDelayMs = 0;
            b.setMessage(Text.literal("Trigger delay: " + RyanClient.triggerDelayMs + " ms"));
        }).dimensions(bx, y, bw, bh).build());

        addDrawableChild(ButtonWidget.builder(Text.literal("Close (O)"), b -> close())
                .dimensions(bx, top + PANEL_HEIGHT - 27, bw, 20).build());
    }

    private Text moduleText(String name, boolean enabled) {
        return Text.literal(name + ": " + (enabled ? "ON" : "OFF"));
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        context.fill(left, top, left + PANEL_WIDTH, top + PANEL_HEIGHT, 0xFF080808);
        context.fill(left, top, left + PANEL_WIDTH, top + 29, 0xFF202020);
        context.drawBorder(left, top, PANEL_WIDTH, PANEL_HEIGHT, 0xFF555555);
        context.drawText(textRenderer, "RyanClient", left + 10, top + 10, 0xFFFFFFFF, false);
        context.drawText(textRenderer, "Combat / Settings", left + 12, top + 31, 0xFFB9A1FF, false);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
