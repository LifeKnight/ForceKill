package com.lifeknight.forcekill.gui;

import com.lifeknight.forcekill.variables.LifeKnightBoolean;

public class LifeKnightBooleanButton extends LifeKnightButton {
    private final LifeKnightBoolean lifeKnightBoolean;

    public LifeKnightBooleanButton(int componentId, LifeKnightBoolean lifeKnightBoolean, LifeKnightButton connectedButton) {
        super(componentId, lifeKnightBoolean.getAsString());
        this.lifeKnightBoolean = lifeKnightBoolean;
        if (connectedButton != null) {
            connectedButton.xPosition = this.xPosition + this.width + 10;
        }
    }

    @Override
    public void work() {
        lifeKnightBoolean.toggle();
        displayString = lifeKnightBoolean.getAsString();
    }
}
