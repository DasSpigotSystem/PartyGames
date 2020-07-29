package de.devnytake.skrimeparty.gamestates.countdown;


public abstract class Countdown {

    protected int taskID, idleID;

    public abstract void start();
    public abstract void stop();
}
