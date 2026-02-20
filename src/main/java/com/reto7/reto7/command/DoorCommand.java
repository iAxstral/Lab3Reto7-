package com.reto7.reto7.command;

import com.reto7.reto7.receiver.Door;

public class DoorCommand implements Command {
    public enum Action { OPEN, CLOSE, LOCK, UNLOCK }

    private final Door door;
    private final Action action;

    private boolean prevOpen;
    private boolean prevLocked;

    public DoorCommand(Door door, Action action) {
        this.door = door;
        this.action = action;
    }

    @Override public void execute() {
        prevOpen = door.isOpen();
        prevLocked = door.isLocked();

        switch (action) {
            case OPEN -> door.open();
            case CLOSE -> door.close();
            case LOCK -> door.lock();
            case UNLOCK -> door.unlock();
        }
    }

    @Override public void undo() {
        if (prevLocked) door.lock(); else door.unlock();
        if (prevOpen) door.open(); else door.close();
    }

    @Override public String description() {
        return switch (action) {
            case OPEN -> "Abrir puerta";
            case CLOSE -> "Cerrar puerta";
            case LOCK -> "Bloquear puerta";
            case UNLOCK -> "Desbloquear puerta";
        };
    }

    @Override public String deviceName() { return "Puerta"; }
}