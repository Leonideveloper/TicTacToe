package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;
import android.os.Bundle;

import com.gmail.leonidandand.tictactoe.R;

//
// TODO:
// New structure of processing configuration changes:
//
// startGame():
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
    private TicTacToeController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tic_tac_toe_activity);

        controller = new TicTacToeController(this);

        boolean activityLaunched = (savedInstanceState == null);
        if (activityLaunched) {
            controller.startGame();
        } else {
            controller.restoreState(savedInstanceState);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        controller.saveState(outState);
    }
}
