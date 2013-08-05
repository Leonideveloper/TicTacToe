package com.gmail.leonidandand.tictactoe.game;

import android.os.Bundle;
import android.app.Activity;

import com.gmail.leonidandand.tictactoe.game.controller.GameController;
import com.gmail.leonidandand.tictactoe.game.controller.GameControllerAndroidImpl;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.model.GameModelImpl;
import com.gmail.leonidandand.tictactoe.game.model.opponent.StupidAIOpponent;
import com.gmail.leonidandand.tictactoe.utils.Dimension;

public class TicTacToeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameModel model = new GameModelImpl(gameBoardDimension(5));
        model.setOpponent(new StupidAIOpponent());
        GameController controller = new GameControllerAndroidImpl(model, this);
    }

    private Dimension gameBoardDimension(int dimension) {
        return new Dimension(dimension, dimension);
    }
}
