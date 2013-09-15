package com.gmail.leonidandand.tictactoe.game.view;


import com.gmail.leonidandand.tictactoe.game.model.TicTacToeModel;

/**
 * Created by Leonid on 12.09.13.
 */
public interface TicTacToeView {
    void addOnCellClickListener(OnCellClickListener listener);
    void plugModel(TicTacToeModel model);
    void unplugModel();
}
