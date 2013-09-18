package com.gmail.leonidandand.tictactoe.game.players;

import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;
import com.gmail.leonidandand.tictactoe.game.model.player.PlayersFactory;

/**
 * Created by Leonid on 06.09.13.
 */
public class PlayersFactoryImpl implements PlayersFactory {

    public Player createFirstPlayer(String playerType, TicTacToeModel model) {
        return createPlayer(Player.Id.PLAYER_1, playerType, model);
    }

    public Player createSecondPlayer(String playerType, TicTacToeModel model) {
        return createPlayer(Player.Id.PLAYER_2, playerType, model);
    }

    private Player createPlayer(Player.Id playerId, String playerType, TicTacToeModel model) {
        if (playerType.equals(PlayerTypes.HUMAN)) {
            return new HumanPlayer(playerId, model);
        } else if (playerType.equals(PlayerTypes.Ai.NORMAL)) {
            return new AINormalPlayer(playerId, model);
        } else if (playerType.equals(PlayerTypes.Ai.HARD)) {
            throwNotYetImplementedType(playerType);
        } else if (playerType.equals(PlayerTypes.Remote.BLUE_TOOTH)) {
            throwNotYetImplementedType(playerType);
        } else if (playerType.equals(PlayerTypes.Remote.WIFI)) {
            throwNotYetImplementedType(playerType);
        }
        throw new IllegalArgumentException("Unknown player type");
    }

    public static class NotYetImplementedException extends RuntimeException {
        public NotYetImplementedException(String message) {
            super(message);
        }
    }

    private void throwNotYetImplementedType(String playerType) {
        throw new NotYetImplementedException(
                "Player type \'" + playerType + "\' is not yet implemented."
        );
    }
}
