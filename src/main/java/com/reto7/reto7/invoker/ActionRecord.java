package com.reto7.reto7.invoker;
import com.reto7.reto7.command.Command;
import java.time.LocalDateTime;

public class ActionRecord {
    private final int id;
    private final String user;
    private final Command command;
    private final LocalDateTime when;
    private boolean undone;

    public ActionRecord(int id, String user, Command command) {
        this.id = id;
        this.user = user;
        this.command = command;
        this.when = LocalDateTime.now();
        this.undone = false;
    }

    public int getId() { return id; }
    public String getUser() { return user; }
    public Command getCommand() { return command; }
    public LocalDateTime getWhen() { return when; }
    public boolean isUndone() { return undone; }
    public void markUndone() { this.undone = true; }
}