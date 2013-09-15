package com.gmail.leonidandand.tictactoe.game.player;

/**
 * Created by Leonid on 06.09.13.
 */
public class PlayersFactory {
    public static Player createFirstPlayer(Player.Type playerType) {
        return createPlayer(Player.Id.PLAYER_1, playerType);
    }

    public static Player createSecondPlayer(Player.Type playerType) {
        return createPlayer(Player.Id.PLAYER_2, playerType);
    }

    private static Player createPlayer(Player.Id playerId, Player.Type playerType) {

        switch (playerType) {

        case HUMAN:
            return new HumanPlayer(playerId);

        case AI_NORMAL:
            return new AINormalPlayer(playerId);

        case AI_HARD:
            return new AIHardPlayer(playerId);

        case REMOTE:
            return new RemotePlayer(playerId);

        default:
            throw new IllegalArgumentException("Unknown player type");
        }
    }
}
