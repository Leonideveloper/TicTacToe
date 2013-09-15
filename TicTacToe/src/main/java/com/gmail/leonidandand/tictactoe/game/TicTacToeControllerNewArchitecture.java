package com.gmail.leonidandand.tictactoe.game;

import android.app.Activity;

import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;
import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModelImpl;
import com.gmail.leonidandand.tictactoe.game.player.Player;
import com.gmail.leonidandand.tictactoe.game.player.PlayersFactory;
import com.gmail.leonidandand.tictactoe.game.view.OnCellClickListener;
import com.gmail.leonidandand.tictactoe.game.view.android.TicTacToeViewAndroidImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leonid on 06.09.13.
 */
public class TicTacToeControllerNewArchitecture {

    private static final Map<String, Object> BUNDLE = new HashMap<String, Object>();

    private static TicTacToeModel model;
    private static TicTacToeViewAndroidImpl view;
    private final Player player_1;
    private final Player player_2;

    public TicTacToeControllerNewArchitecture(Activity activity) {
        int gameBoardDimension = TicTacToeContext.getGameBoardDimension();
        player_1 = PlayersFactory.createFirstPlayer(TicTacToeContext.getFirstPlayerType());
        player_2 = PlayersFactory.createSecondPlayer(TicTacToeContext.getSecondPlayerType());
        model = new TicTacToeModelImpl(gameBoardDimension, player_1, player_2);
        view = new TicTacToeViewAndroidImpl(model, activity);
        connectViewWithPlayers();
    }

    private void connectViewWithPlayers() {
        connectViewWithPlayerIfPlayerIsOnCellClickListener(player_1);
        connectViewWithPlayerIfPlayerIsOnCellClickListener(player_2);
    }

    private void connectViewWithPlayerIfPlayerIsOnCellClickListener(Player player) {
        if (player instanceof OnCellClickListener) {
            view.addOnCellClickListener((OnCellClickListener) player);
        }
    }

    public void onSaveState(Activity activity) {
        BUNDLE.clear();
        view.saveState(BUNDLE);
    }

    public void onRestoreState(Activity activity) {
        view = new TicTacToeViewAndroidImpl(model, activity);
        view.restoreState(BUNDLE);
        connectViewWithPlayers();
    }

    public void onClose() {
        view.unplugModel();
        view = null;
        model = null;
    }
}
