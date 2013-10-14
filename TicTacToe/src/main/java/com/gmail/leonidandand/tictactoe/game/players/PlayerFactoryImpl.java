package com.gmail.leonidandand.tictactoe.game.players;

import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.PlayerFactory;

/**
 * Created by Leonid on 06.09.13.
 */
public class PlayerFactoryImpl implements PlayerFactory {

    @Override
    public Player createFirstPlayer(String playerType, TicTacToeModel model) {
        return createPlayer(playerType, model, Player.Id.PLAYER_1);
    }

    @Override
    public Player createSecondPlayer(String playerType, TicTacToeModel model) {
        return createPlayer(playerType, model, Player.Id.PLAYER_2);
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
