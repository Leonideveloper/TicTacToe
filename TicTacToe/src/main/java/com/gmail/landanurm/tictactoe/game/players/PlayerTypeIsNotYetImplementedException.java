package com.gmail.landanurm.tictactoe.game.players;

/**
 * Created by Leonid on 22.09.13.
 */
class PlayerTypeIsNotYetImplementedException extends RuntimeException {
    PlayerTypeIsNotYetImplementedException(String playerType) {
        super("Player type \'" + playerType + "\' is not yet implemented.");
    }
}
