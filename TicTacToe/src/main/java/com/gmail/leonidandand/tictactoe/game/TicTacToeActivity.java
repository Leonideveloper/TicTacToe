package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model_view.model.TicTacToeModelImpl;
import com.gmail.leonidandand.tictactoe.game.players.PlayerFactoryImpl;
import com.gmail.leonidandand.tictactoe.game.players.PlayerTypes;
import com.gmail.leonidandand.tictactoe.game.model_view.view.TicTacToeViewImpl;
import com.gmail.leonidandand.tictactoe.game.view_components_provider_android_impl.TicTacToeViewComponentsProviderAndroidImpl;

//
// TODO:
// New structure of processing configuration changes:
//
// start():
//     model = createModel();
//     view = createView(model);
//
// saveState(bundle):
//     bundle.put("model", model);
//
// restoreState(bundle):
//     model = bundle.get("model");
//     view = createView(model);
//

public class TicTacToeActivity extends Activity {

    private static TicTacToeModel model;
    private static TicTacToeViewComponentsProviderAndroidImpl viewComponentsProvider;
    private static TicTacToeViewImpl view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_activity);

        initContext();

        boolean restartingOfActivity = (savedInstanceState != null);
        if (restartingOfActivity) {
            restoreState(savedInstanceState);
        } else {
            start();
        }
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

        viewComponentsProvider = new TicTacToeViewComponentsProviderAndroidImpl(model, this);
        view = new TicTacToeViewImpl(viewComponentsProvider, model);
    }

    private void restoreState(Bundle savedInstanceState) {
        viewComponentsProvider =
                new TicTacToeViewComponentsProviderAndroidImpl(viewComponentsProvider, model, this);
        view = new TicTacToeViewImpl(viewComponentsProvider, view);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        saveState(outState);
    }

    private void saveState(Bundle outState) {
        // Do nothing
    }
}
