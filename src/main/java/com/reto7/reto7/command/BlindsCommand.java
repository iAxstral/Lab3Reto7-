package com.reto7.reto7.command;

import com.reto7.reto7.receiver.Blinds;

public class BlindsCommand implements Command {
    private final Blinds blinds;
    private final int openPercent;

    private int prevOpenPercent;

    public BlindsCommand(Blinds blinds, int openPercent) {
        this.blinds = blinds;
        this.openPercent = openPercent;
    }

    @Override public void execute() {
        prevOpenPercent = blinds.getOpenPercent();
        blinds.setOpenPercent(openPercent);
    }

    @Override public void undo() {
        blinds.setOpenPercent(prevOpenPercent);
    }

    @Override public String description() {
        return "Ajustar persianas a " + openPercent + "%";
    }

    @Override public String deviceName() { return "Persianas"; }
}