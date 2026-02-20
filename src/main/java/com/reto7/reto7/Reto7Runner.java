package com.reto7.reto7;

import com.reto7.reto7.command.*;
import com.reto7.reto7.history.ActionRecord;
import com.reto7.reto7.invoker.MagicRemote;
import com.reto7.reto7.receiver.*;

import java.util.*;
import java.util.stream.Collectors;

public class Reto7Runner {

    public static void run() {
        Scanner sc = new Scanner(System.in);

        Light light = new Light();
        Door door = new Door();
        MusicSystem music = new MusicSystem();
        Blinds blinds = new Blinds();

        MagicRemote remote = new MagicRemote();

        System.out.println("=== RETO 7: Control Remoto Mágico ===");
        System.out.print("¿Cuántas acciones vas a ejecutar (X)? ");
        int x = readInt(sc, 1, 10_000);

        for (int i = 1; i <= x; i++) {
            System.out.println("\nAcción #" + i);
            System.out.print("Usuario: ");
            String user = sc.nextLine().trim();

            Command cmd = chooseCommand(sc, light, door, music, blinds);
            ActionRecord r = remote.execute(user, cmd);

            System.out.println("✅ Ejecutada (id=" + r.getId() + "): " + cmd.deviceName() + " -> " + cmd.description());

            System.out.print("¿Deseas DESHACER alguna acción ahora? (s/n): ");
            String ans = sc.nextLine().trim().toLowerCase();
            if (ans.equals("s")) {
                printHistory(remote);
                System.out.print("Ingresa el id a deshacer: ");
                int id = readInt(sc, 1, Integer.MAX_VALUE);
                boolean ok = remote.undoById(id);
                System.out.println(ok ? "↩️ Acción deshecha." : "⚠️ No se pudo deshacer (id inválido o ya deshecha).");
            }
        }

        System.out.println("\n=== HISTORIAL COMPLETO ===");
        printHistory(remote);

        System.out.println("\n=== ¿Quién desconfiguró qué? (última acción NO deshecha por dispositivo) ===");
        Map<String, Optional<ActionRecord>> lastByDevice = remote.history().stream()
                .filter(r -> !r.isUndone())
                .collect(Collectors.groupingBy(
                        r -> r.getCommand().deviceName(),
                        Collectors.maxBy(Comparator.comparing(ActionRecord::getWhen))
                ));

        for (String d : List.of("Luces", "Puerta", "Música", "Persianas")) {
            Optional<ActionRecord> r = lastByDevice.getOrDefault(d, Optional.empty());
            if (r.isPresent()) {
                System.out.println("• " + d + " -> " + r.get().getUser() + " (" + r.get().getCommand().description() + ")");
            } else {
                System.out.println("• " + d + " -> Nadie (sin acciones activas)");
            }
        }

        System.out.println("\n=== ESTADO FINAL DE DISPOSITIVOS ===");
        System.out.println(light);
        System.out.println(door);
        System.out.println(music);
        System.out.println(blinds);

        System.out.println("\n=== RESUMEN FINAL (acciones y usuarios) ===");
        remote.history().forEach(r ->
                System.out.println("#" + r.getId() + " | " + r.getUser() + " | " + r.getCommand().deviceName()
                        + " | " + r.getCommand().description()
                        + (r.isUndone() ? " | (DESHECHA)" : ""))
        );
    }

    private static void printHistory(MagicRemote remote) {
        if (remote.history().isEmpty()) {
            System.out.println("(vacío)");
            return;
        }
        remote.history().forEach(r ->
                System.out.println("#" + r.getId()
                        + " | user=" + r.getUser()
                        + " | device=" + r.getCommand().deviceName()
                        + " | action=" + r.getCommand().description()
                        + (r.isUndone() ? " | (DESHECHA)" : ""))
        );
    }

    private static Command chooseCommand(Scanner sc, Light light, Door door, MusicSystem music, Blinds blinds) {
        System.out.println("Elige dispositivo:");
        System.out.println("1) Luces  2) Puerta  3) Música  4) Persianas");
        int opt = readInt(sc, 1, 4);

        return switch (opt) {
            case 1 -> {
                System.out.println("Luces: 1) Encender  2) Apagar  3) Brillo(0-100)");
                int a = readInt(sc, 1, 3);
                yield switch (a) {
                    case 1 -> new LightCommand(light, LightCommand.Action.ON, null);
                    case 2 -> new LightCommand(light, LightCommand.Action.OFF, null);
                    default -> {
                        System.out.print("Brillo (0..100): ");
                        int b = readInt(sc, 0, 100);
                        yield new LightCommand(light, LightCommand.Action.SET_BRIGHTNESS, b);
                    }
                };
            }
            case 2 -> {
                System.out.println("Puerta: 1) Abrir  2) Cerrar  3) Bloquear  4) Desbloquear");
                int a = readInt(sc, 1, 4);
                yield new DoorCommand(door, DoorCommand.Action.values()[a - 1]);
            }
            case 3 -> {
                System.out.println("Música: 1) Play  2) Stop  3) Volumen(0-100)");
                int a = readInt(sc, 1, 3);
                yield switch (a) {
                    case 1 -> {
                        System.out.print("Nombre de pista: ");
                        String t = sc.nextLine();
                        yield new MusicCommand(music, MusicCommand.Action.PLAY, t, null);
                    }
                    case 2 -> new MusicCommand(music, MusicCommand.Action.STOP, null, null);
                    default -> {
                        System.out.print("Volumen (0..100): ");
                        int v = readInt(sc, 0, 100);
                        yield new MusicCommand(music, MusicCommand.Action.SET_VOLUME, null, v);
                    }
                };
            }
            default -> {
                System.out.print("Persianas % abiertas (0..100): ");
                int p = readInt(sc, 0, 100);
                yield new BlindsCommand(blinds, p);
            }
        };
    }

    private static int readInt(Scanner sc, int min, int max) {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                int v = Integer.parseInt(s);
                if (v < min || v > max) {
                    System.out.print("Valor inválido, rango [" + min + ".." + max + "]. Intenta: ");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.print("Número inválido. Intenta: ");
            }
        }
    }
}