package com.reto7.reto7.receiver;

public class Door {
    private boolean open = false;
    private boolean locked = false;

    public void open() { if (!locked) open = true; }
    public void close() { open = false; }
    public void lock() { locked = true; open = false; }
    public void unlock() { locked = false; }

    public boolean isOpen() { return open; }
    public boolean isLocked() { return locked; }

    @Override public String toString() {
        return "Door{open=" + open + ", locked=" + locked + "}";
    }
}