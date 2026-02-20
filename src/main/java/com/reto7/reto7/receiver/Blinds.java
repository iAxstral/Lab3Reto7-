package com.reto7.reto7.receiver;

public class Blinds {
    private int openPercent = 0; // 0..100

    public void setOpenPercent(int value) { openPercent = clamp(value); }
    public int getOpenPercent() { return openPercent; }

    private int clamp(int v) { return Math.max(0, Math.min(100, v)); }

    @Override public String toString() {
        return "Blinds{openPercent=" + openPercent + "%}";
    }
}