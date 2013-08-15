package com.gmail.leonidandand.tictactoe.game.controller;

import android.app.Activity;

import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.view.GameView;
import com.gmail.leonidandand.tictactoe.game.view.android.GameViewAndroidImpl;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameControllerAndroidImpl implements GameController {
    private final GameModel model;
    private final GameView view;

    public GameControllerAndroidImpl(GameModel model, Activity activity) {
        this.model = model;
        view = new GameViewAndroidImpl(this, model, activity);
    }

    @Override
    public GameView getView() {
        return view;
    }

    @Override
    public void onViewIsReadyToStartGame() {
        model.onViewIsReadyToStartGame();
    }

    @Override
    public void onPlayerMove(Position movePos) {
        view.blockMoves();
        model.onPlayerMove(movePos);
        view.unblockMoves();
    }
}
