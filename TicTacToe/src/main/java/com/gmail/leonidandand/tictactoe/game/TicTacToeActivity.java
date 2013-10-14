package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModelImpl;
import com.gmail.leonidandand.tictactoe.game.model_view.view.TicTacToeView;
import com.gmail.leonidandand.tictactoe.game.model_view.view.TicTacToeViewImpl;
import com.gmail.leonidandand.tictactoe.game.players.PlayerFactoryImpl;
import com.gmail.leonidandand.tictactoe.game.players.PlayerTypes;
import com.gmail.leonidandand.tictactoe.game.view_components_provider_android_impl.TicTacToeViewComponentsProviderAndroidImpl;

import java.io.Serializable;

//
// TODO:
// New structure of processing configuration changes:
//
// start():
//     model = createModel();
//     viewComponentsProvider = createViewComponentsProviderAndroidImpl();
//     view = createView(viewComponentsProvider, model);
//
// saveState(bundle):
//     bundle.put("model", model);
//     viewComponentsState = viewComponentsProvider.getState();
//     bundle.put("viewComponentState", viewComponentsState);
//
// restoreState(bundle):
//     model = bundle.get("model");
//     viewComponentsState = bundle.get("viewComponentState");
//     viewComponentsProvider = createViewComponentsProviderAndroidImpl(viewComponentsState);
//     view = createView(viewComponentsProvider, model);
//

public class TicTacToeActivity extends Activity {
    private TicTacToeModel model;
    private TicTacToeView view;
    private static TicTacToeViewComponentsProviderAndroidImpl viewComponentsProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_activity);

        boolean activityLaunched = (savedInstanceState == null);
        if (activityLaunched) {
            initContext();
            start();
        } else {
            restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState(outState);
    }

    private void initContext() {
        TicTacToeContext.setGameBoardDimension(13);
        TicTacToeContext.setFirstPlayerType(PlayerTypes.HUMAN);
        TicTacToeContext.setSecondPlayerType(PlayerTypes.HUMAN);
    }

    private void start() {
        model = new TicTacToeModelImpl(
            TicTacToeContext.getGameBoardDimension(),
            new PlayerFactoryImpl(),
            TicTacToeContext.getFirstPlayerType(),
            TicTacToeContext.getSecondPlayerType()
        );
        viewComponentsProvider = new TicTacToeViewComponentsProviderAndroidImpl(
            TicTacToeContext.getGameBoardDimension(),
            this
        );
        view = new TicTacToeViewImpl(viewComponentsProvider, model);
        model.startGame();
    }

    private void restoreState(Bundle savedInstanceState) {
        model = (TicTacToeModel) savedInstanceState.getSerializable("model");
        viewComponentsProvider = new TicTacToeViewComponentsProviderAndroidImpl(
            TicTacToeContext.getGameBoardDimension(),
            this,
            viewComponentsProvider
        );
        view = new TicTacToeViewImpl(viewComponentsProvider, model);
    }

    private void saveState(Bundle outState) {
        outState.putSerializable("model", (Serializable) model);
    }
}
