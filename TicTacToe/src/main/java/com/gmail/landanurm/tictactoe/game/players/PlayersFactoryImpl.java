package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.tictactoe.game.model_view.model.ReadOnlyGameBoard;
import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.PlayersFactory;

/**
 * Created by Leonid on 06.09.13.
 */
public class PlayersFactoryImpl implements PlayersFactory {

    private final String firstPlayerType;
    private final String secondPlayerType;

    private Player.Id playerId;
    private ReadOnlyGameBoard gameBoard;
    private String playerType;
    private TicTacToeModel model;

    public PlayersFactoryImpl(String firstPlayerType, String secondPlayerType) {
        this.firstPlayerType = firstPlayerType;
        this.secondPlayerType = secondPlayerType;
    }

    @Override
    public Player createFirstPlayer(ReadOnlyGameBoard gameBoard, TicTacToeModel model) {
        return createPlayer(Player.Id.FIRST_PLAYER, gameBoard, model);
    }

    @Override
    public Player createSecondPlayer(ReadOnlyGameBoard gameBoard, TicTacToeModel model) {
        return createPlayer(Player.Id.SECOND_PLAYER, gameBoard, model);
    }

    private Player createPlayer(Player.Id playerId, ReadOnlyGameBoard gameBoard, TicTacToeModel model) {
        this.playerId = playerId;
        this.playerType = playerTypeById();
        this.gameBoard = gameBoard;
        this.model = model;
        return createPlayer();
    }

    private String playerTypeById() {
        return (playerId == Player.Id.FIRST_PLAYER) ? firstPlayerType : secondPlayerType;
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
