package com.reto7.reto7.command;

import com.reto7.reto7.receiver.MusicSystem;

public class MusicCommand implements Command {
    public enum Action { PLAY, STOP, SET_VOLUME }

    private final MusicSystem music;
    private final Action action;
    private final String track;
    private final Integer volume;

    private boolean prevPlaying;
    private int prevVolume;
    private String prevTrack;

    public MusicCommand(MusicSystem music, Action action, String track, Integer volume) {
        this.music = music;
        this.action = action;
        this.track = track;
        this.volume = volume;
    }

    @Override public void execute() {
        prevPlaying = music.isPlaying();
        prevVolume = music.getVolume();
        prevTrack = music.getTrack();

        switch (action) {
            case PLAY -> music.play(track);
            case STOP -> music.stop();
            case SET_VOLUME -> music.setVolume(volume == null ? prevVolume : volume);
        }
    }

    @Override public void undo() {
        music.setVolume(prevVolume);
        if (prevPlaying) music.play(prevTrack);
        else music.stop();
    }

    @Override public String description() {
        return switch (action) {
            case PLAY -> "Reproducir música: " + track;
            case STOP -> "Detener música";
            case SET_VOLUME -> "Ajustar volumen a " + volume + "%";
        };
    }

    @Override public String deviceName() { return "Música"; }
}