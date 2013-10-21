package com.gmail.landanurm.tictactoe.game.players;

import com.gmail.landanurm.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.PlayersFactory;

/**
 * Created by Leonid on 06.09.13.
 */
public class PlayersFactoryImpl implements PlayersFactory {

    @Override
    public Player createFirstPlayer(String playerType, TicTacToeModel model) {
        return createPlayer(playerType, model, Player.Id.FIRST_PLAYER);
    }

    @Override
    public Player createSecondPlayer(String playerType, TicTacToeModel model) {
        return createPlayer(playerType, model, Player.Id.SECOND_PLAYER);
    }

    private Player createPlayer(String playerType, TicTacToeModel model, Player.Id playerId) {
        if (playerType.equals(PlayerTypes.HUMAN)) {
            return new HumanPlayer(playerId, model);
        } else if (playerType.equals(PlayerTypes.AI.NORMAL)) {
            return new AIPlayer(playerId, model, new NormalAIMoveCalculator());
        } else if (playerType.equals(PlayerTypes.AI.HARD)) {
            throw new PlayerTypeIsNotYetImplementedException(playerType);
        }
        throw new IllegalArgumentException("Unknown player type: " + playerType);
    }
}
