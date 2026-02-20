package com.reto7.reto7.command;

public interface Command {
    void execute();
    void undo();
    String description();  // para historial/resumen
    String deviceName();   // luces, puerta, música, persianas
}