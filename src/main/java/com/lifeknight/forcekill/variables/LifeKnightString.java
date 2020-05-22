package com.lifeknight.forcekill.variables;

import static com.lifeknight.forcekill.mod.Mod.config;
import static com.lifeknight.forcekill.mod.Mod.variables;

public class LifeKnightString extends LifeKnightVariable {
    private String value;

    public LifeKnightString(String name, String group, String value) {
        super.name = name;
        super.group = group;
        this.value = value;
        variables.add(this);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        if (config != null) {
            config.updateConfigFromVariables();
            onSetValue();
        }
    }

    public void clear() {
        this.value = "";
        onClear();
    }

    public void onSetValue() {}

    public void onClear() {}

}
