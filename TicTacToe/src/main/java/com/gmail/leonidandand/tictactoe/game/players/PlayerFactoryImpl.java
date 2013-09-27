package com.gmail.leonidandand.tictactoe.game.players;

import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;
import com.gmail.leonidandand.tictactoe.game.model.player.PlayerFactory;

/**
 * Created by Leonid on 06.09.13.
 */
public class PlayerFactoryImpl implements PlayerFactory {

    @Override
    public Player createFirstPlayer(String playerType, TicTacToeModel model) {
        return createPlayer(Player.Id.PLAYER_1, playerType, model);
    }

    @Override
    public Player createSecondPlayer(String playerType, TicTacToeModel model) {
        return createPlayer(Player.Id.PLAYER_2, playerType, model);
    }

    private Player createPlayer(Player.Id playerId, String playerType, TicTacToeModel model) {
        if (playerType.equals(PlayerTypes.HUMAN)) {
            return new HumanPlayer(playerId, model);
        } else if (playerType.equals(PlayerTypes.AI.NORMAL)) {
            return new AINormalPlayer(playerId, model);
        } else if (playerType.equals(PlayerTypes.AI.HARD)) {
            throwNotYetImplementedType(playerType);
        }
        throw new IllegalArgumentException("Unknown player type");
    }

    private void throwNotYetImplementedType(String playerType) {
        throw new NotYetImplementedException(
                "Player type \'" + playerType + "\' is not yet implemented."
        );
    }
}
