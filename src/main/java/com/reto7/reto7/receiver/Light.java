package com.reto7.reto7.receiver;

public class Light {
    private boolean on = false;
    private int brightness = 50; // 0..100

    public void turnOn() { on = true; }
    public void turnOff() { on = false; }
    public void setBrightness(int value) { brightness = clamp(value); }

    public boolean isOn() { return on; }
    public int getBrightness() { return brightness; }

    private int clamp(int v) { return Math.max(0, Math.min(100, v)); }

    @Override public String toString() {
        return "Light{on=" + on + ", brightness=" + brightness + "%}";
    }
}