package de.devnytake.skrimeparty.games;


/**
 * Copyright: DevNyTake
 * 15.07.2020 | 08:19
 */

public enum Games {

    TNTRUN("TNTRun", 0), AMPELRENNEN("Ampelrennen", 0), JUMPANDRUN("Jump and Run", 0);

    Games(String name, int votes){
        this.name = name;
        this.votes = votes;
    }

    public String name;
    public int votes;

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
