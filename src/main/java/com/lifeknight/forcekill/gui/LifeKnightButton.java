package com.lifeknight.forcekill.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

import static com.lifeknight.forcekill.mod.Utils.get2ndPanelCenter;

public abstract class LifeKnightButton extends GuiButton {
    protected final String buttonText;

    public LifeKnightButton(int componentId, String buttonText) {
        super(componentId, get2ndPanelCenter() - 100,
                (20 + componentId * 30),
                200,
                20, buttonText);
        this.buttonText = buttonText;
        int j;
        if ((j = Minecraft.getMinecraft().fontRendererObj.getStringWidth(buttonText) + 30) > this.width) {
            this.width = j;
            this.xPosition = get2ndPanelCenter() - this.width / 2;
        }
    }

    public LifeKnightButton(int componentId, int x, int y, int width, int height, String buttonText) {
        super(componentId, x, y, width, height, buttonText);
        this.buttonText = buttonText;
    }

    public LifeKnightButton(String buttonText, int componentId, int x, int y, int width) {
        super(componentId, x,
                y,
                width,
                20,
                buttonText);
        this.buttonText = buttonText;
    }

    public String getButtonText() {
        return buttonText;
    }

    public abstract void work();
}
