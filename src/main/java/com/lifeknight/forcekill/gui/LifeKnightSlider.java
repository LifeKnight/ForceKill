package com.lifeknight.forcekill.gui;

import com.lifeknight.forcekill.variables.LifeKnightDouble;
import com.lifeknight.forcekill.variables.LifeKnightInteger;
import net.minecraftforge.fml.client.config.GuiSlider;

import static com.lifeknight.forcekill.mod.Mod.config;
import static com.lifeknight.forcekill.mod.Utils.get2ndPanelCenter;

public class LifeKnightSlider extends GuiSlider {
    private final LifeKnightInteger lifeKnightInteger;
    private final LifeKnightDouble lifeKnightDouble;

    public LifeKnightSlider(int componentId, boolean showDecimals, LifeKnightInteger lifeKnightInteger) {
        super(componentId, get2ndPanelCenter() - 100,
                (20 + componentId * 30),
                200,
                20,  lifeKnightInteger.getName() + ": ", "", lifeKnightInteger.getMinimumValue(), lifeKnightInteger.getMaximumValue(), lifeKnightInteger.getValue(), showDecimals, true);
        this.lifeKnightInteger = lifeKnightInteger;
        lifeKnightDouble = null;
        super.parent = slider -> {
            this.lifeKnightInteger.setValue(getValueInt());
            config.updateConfigFromVariables();
        };
    }

    public LifeKnightSlider(int componentId, boolean showDecimals, LifeKnightDouble lifeKnightDouble) {
        super(componentId, get2ndPanelCenter() - 100,
                (20 + componentId * 30),
                200,
                20, lifeKnightDouble.getName() + ": ", "", lifeKnightDouble.getMinimumValue(), lifeKnightDouble.getMaximumValue(), lifeKnightDouble.getValue(), showDecimals, true);
        this.lifeKnightDouble = lifeKnightDouble;
        lifeKnightInteger = null;
        super.parent = slider -> {
            this.lifeKnightDouble.setValue(getValue());
            config.updateConfigFromVariables();
        };
    }
}
