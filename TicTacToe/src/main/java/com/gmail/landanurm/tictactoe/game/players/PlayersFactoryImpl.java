package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.tictactoe.game.model_view.model.game_board.ReadOnlyGameBoard;
import com.gmail.landanurm.tictactoe.game.model_view.model.OnMoveListener;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.PlayersFactory;

/**
 * Created by Leonid on 06.09.13.
 */
public class PlayersFactoryImpl implements PlayersFactory {

    @Override
    public Player createFirstPlayer(String playerType, ReadOnlyGameBoard gameBoard,
                                    OnMoveListener onMoveListener) {
        return createPlayer(playerType, gameBoard, onMoveListener, Player.Id.FIRST_PLAYER);
    }

    @Override
    public Player createSecondPlayer(String playerType, ReadOnlyGameBoard gameBoard,
                                     OnMoveListener onMoveListener) {
        return createPlayer(playerType, gameBoard, onMoveListener, Player.Id.SECOND_PLAYER);
    }

    private Player createPlayer(String playerType, ReadOnlyGameBoard gameBoard,
                                OnMoveListener onMoveListener, Player.Id playerId) {
        if (playerType.equals(PlayerTypes.HUMAN)) {
            return new HumanPlayer(playerId, gameBoard, onMoveListener);
        } else if (playerType.equals(PlayerTypes.AI.NORMAL)) {
            return new AIPlayer(playerId, onMoveListener, new NormalAIMoveCalculator(gameBoard));
        } else if (playerType.equals(PlayerTypes.AI.HARD)) {
            throw new PlayerTypeIsNotYetImplementedException(playerType);
        }
        throw new UnknownPlayerTypeException(playerType);
    }

    private static class PlayerTypeIsNotYetImplementedException extends RuntimeException {
        PlayerTypeIsNotYetImplementedException(String playerType) {
            super("Player type \'" + playerType + "\' is not yet implemented.");
        }
    }

    private static class UnknownPlayerTypeException extends RuntimeException {
        UnknownPlayerTypeException(String playerType) {
            super("Unknown player type: " + playerType);
        }
    }
}
