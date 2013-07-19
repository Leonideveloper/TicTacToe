package com.gmail.leonidandand.tictactoe.game;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.controller.GameController;
import com.gmail.leonidandand.tictactoe.game.controller.GameControllerImpl;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.model.GameModelImpl;
import com.gmail.leonidandand.tictactoe.utils.Dimension;

public class TicTacToeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GameModel model = new GameModelImpl(new Dimension(3, 3));
        GameController controller = new GameControllerImpl(model, this);
    }
    
}
