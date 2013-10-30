package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.tictactoe.game.model_view.model.ReadOnlyGameBoard;
import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.PlayersFactory;

/**
 * Created by Leonid on 06.09.13.
 */
public class PlayersFactoryImpl implements PlayersFactory {

    private Player.Id playerId;
    private ReadOnlyGameBoard gameBoard;
    private String playerType;
    private TicTacToeModel model;

    @Override
    public Player createFirstPlayer(String playerType, ReadOnlyGameBoard gameBoard, TicTacToeModel model) {
        return createPlayer(playerType, gameBoard, model, Player.Id.FIRST_PLAYER);
    }

    @Override
    public Player createSecondPlayer(String playerType, ReadOnlyGameBoard gameBoard, TicTacToeModel model) {
        return createPlayer(playerType, gameBoard, model, Player.Id.SECOND_PLAYER);
    }

    private Player createPlayer(String playerType, ReadOnlyGameBoard gameBoard,
                                TicTacToeModel model, Player.Id playerId) {
        this.playerType = playerType;
        this.playerId = playerId;
        this.gameBoard = gameBoard;
        this.model = model;
        return createPlayer();
    }

    private Player createPlayer() {
        if (playerType.equals(PlayerTypes.HUMAN)) {
            return createHumanPlayer();
        } else if (playerType.equals(PlayerTypes.AI.NORMAL)) {
            return createNormalAIPlayer();
        } else if (playerType.equals(PlayerTypes.AI.HARD)) {
            throw new PlayerTypeIsNotYetImplementedException(playerType);
        }
        throw new UnknownPlayerTypeException(playerType);
    }

    private Player createHumanPlayer() {
        return new HumanPlayer(playerId, gameBoard, model);
    }

    private Player createNormalAIPlayer() {
        return createAIPlayer(new NormalAIMoveCalculator(gameBoard));
    }

    private Player createAIPlayer(AIMoveCalculator aiMoveCalculator) {
        return new AIPlayer(playerId, model, aiMoveCalculator);
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
