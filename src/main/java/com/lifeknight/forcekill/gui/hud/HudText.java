package com.lifeknight.forcekill.gui.hud;

import net.minecraft.client.Minecraft;

import static com.lifeknight.forcekill.gui.hud.HudTextRenderer.textToRender;

public class HudText {
    private String text;
    private boolean visible;
    private boolean dropShadow;
    private int x;
    private int y;

    public HudText(int x, int y, String text, boolean visible, boolean dropShadow) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.visible = visible;
        this.dropShadow = dropShadow;
        textToRender.add(this);
    }

    public HudText(int x, int y, String text) {
        this(x, y, text, true, true);
    }

    public String getText() {
        return this.text;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDropShadow() {
        return dropShadow;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setText(String newText) {
        this.text = newText;
    }

    public void setX(int newX) {
        this.x = newX;
    }

    public void setY(int newY)  {
        this.y = newY;
    }

    public void setVisibility(boolean newVisibility) {
        this.visible = newVisibility;
    }

    public void toggleVisibility()  {
        this.visible = !this.visible;
    }

    public void setDropShadow(boolean dropShadow) {
        this.dropShadow = dropShadow;
    }

    public void render() {
        if (this.visible) {
            Minecraft.getMinecraft().fontRendererObj.drawString(text, x, y, 0xffffffff, dropShadow);
        }
    }
}
