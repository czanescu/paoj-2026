package com.pao.laboratory05.playlist;

import java.util.Arrays;

public class Playlist {
    Playlist(String name) {
        this.name = name;
        this.songs = new Song[0];
    }

    public void addSong(Song song) {
        Song[] newSongs = new Song[songs.length + 1];
        System.arraycopy(songs, 0, newSongs, 0, songs.length);
        newSongs[songs.length] = song;
        songs = newSongs;
    }

    public void printSortedByTitle() {
        Song[] sortedSongs = new Song[songs.length];
        System.arraycopy(songs, 0, sortedSongs, 0, songs.length);
        Arrays.sort(sortedSongs);
        for (Song song : sortedSongs) {
            System.out.println(song);
        }
    }

    public void printSortedByDuration(){
        Song[] sortedSongs = new Song[songs.length];
        System.arraycopy(songs, 0, sortedSongs, 0, songs.length);
        Arrays.sort(sortedSongs, new SongDurationComparator());
        for (Song song : sortedSongs) {
            System.out.println(song);
        }
    }

    public int getTotalDuration(){
        int durata = 0;
        for (Song song : songs) {
            durata += song.durationSeconds();
        }
        return durata;
    }

    public String getName() {
        return name;
    }

    private String name;
    private Song[] songs;
}
