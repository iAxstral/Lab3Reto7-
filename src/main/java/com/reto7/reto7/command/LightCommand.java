package com.reto7.reto7.command;

import com.reto7.reto7.receiver.Light;

public class LightCommand implements Command {
    public enum Action { ON, OFF, SET_BRIGHTNESS }

    private final Light light;
    private final Action action;
    private final Integer brightness;

    private boolean prevOn;
    private int prevBrightness;

    public LightCommand(Light light, Action action, Integer brightness) {
        this.light = light;
        this.action = action;
        this.brightness = brightness;
    }

    @Override public void execute() {
        prevOn = light.isOn();
        prevBrightness = light.getBrightness();

        switch (action) {
            case ON -> light.turnOn();
            case OFF -> light.turnOff();
            case SET_BRIGHTNESS -> light.setBrightness(brightness == null ? prevBrightness : brightness);
        }
    }

    @Override public void undo() {
        if (prevOn) light.turnOn(); else light.turnOff();
        light.setBrightness(prevBrightness);
    }

    @Override public String description() {
        return switch (action) {
            case ON -> "Encender luces";
            case OFF -> "Apagar luces";
            case SET_BRIGHTNESS -> "Ajustar brillo a " + brightness + "%";
        };
    }

    @Override public String deviceName() { return "Luces"; }
}