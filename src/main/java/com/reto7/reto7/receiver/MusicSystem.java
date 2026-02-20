package com.reto7.reto7.receiver;

public class MusicSystem {
    private boolean playing = false;
    private int volume = 30; // 0..100
    private String track = "Silencio";

    public void play(String trackName) {
        this.track = (trackName == null || trackName.isBlank()) ? "Sin pista" : trackName;
        this.playing = true;
    }

    public void stop() { this.playing = false; }
    public void setVolume(int value) { this.volume = clamp(value); }

    public boolean isPlaying() { return playing; }
    public int getVolume() { return volume; }
    public String getTrack() { return track; }

    private int clamp(int v) { return Math.max(0, Math.min(100, v)); }

    @Override public String toString() {
        return "MusicSystem{playing=" + playing + ", volume=" + volume + "%, track='" + track + "'}";
    }
}