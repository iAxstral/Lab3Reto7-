package com.reto7.reto7.invoker;

import com.reto7.reto7.command.Command;
import com.reto7.reto7.history.ActionRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MagicRemote {
    private final List<ActionRecord> history = new ArrayList<>();
    private int nextId = 1;

    public ActionRecord execute(String user, Command command) {
        command.execute();
        ActionRecord record = new ActionRecord(nextId++, user, command);
        history.add(record);
        return record;
    }

    public boolean undoById(int id) {
        Optional<ActionRecord> opt = history.stream().filter(r -> r.getId() == id).findFirst();
        if (opt.isEmpty()) return false;

        ActionRecord r = opt.get();
        if (r.isUndone()) return false;

        r.getCommand().undo();
        r.markUndone();
        return true;
    }

    public List<ActionRecord> history() {
        return List.copyOf(history);
    }
}