package de.devnytake.skrimeparty.gamestates;

/**
 * Copyright: DevNyTake
 * 15.07.2020 | 08:17
 */

public enum GameState {

    LOBBY, TNTRUN;

    public static GameState gameState;

    public static GameState getCurrentGameState() {
        return gameState;
    }

    public static void setGameState(GameState gameState) {
        GameState.gameState = gameState;
    }
}
