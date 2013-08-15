package com.gmail.leonidandand.tictactoe.game.model;

import com.gmail.leonidandand.matrix.Dimension;
import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.tictactoe.game.CapableSaveRestoreState;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.GameJudge;
import com.gmail.leonidandand.tictactoe.game.model.opponent.Opponent;

import java.util.Map;

/**
 * Created by Leonid on 14.08.13.
 */
public class GameModelCapableSaveRestoreState extends GameModelImpl
                                implements CapableSaveRestoreState {

    private static final String OPPONENT_MOVES_FIRST_KEY = "GameModel.opponentMovesFirst";
    private static final String GAME_JUDGE_KEY = "GameModel.gameJudge";
    private static final String GAME_BOARD_KEY = "GameModel.gameBoard";
    private static final String OPPONENT_KEY = "GameModel.opponent";
    private static final String SCORE_KEY = "GameModel.score";


    public GameModelCapableSaveRestoreState(Dimension gameBoardDimension) {
        super(gameBoardDimension);
    }

    @Override
    public void saveState(Map<String, Object> bundle) {
        bundle.put(OPPONENT_MOVES_FIRST_KEY, opponentMovesFirst);
        bundle.put(GAME_JUDGE_KEY, gameJudge);
        bundle.put(GAME_BOARD_KEY, gameBoard);
        bundle.put(OPPONENT_KEY, opponent);
        bundle.put(SCORE_KEY, score);
    }

    @Override
    public void restoreState(Map<String, Object> bundle) {
        opponentMovesFirst = (Boolean) bundle.get(OPPONENT_MOVES_FIRST_KEY);
        gameJudge = (GameJudge) bundle.get(GAME_JUDGE_KEY);
        gameBoard = (Matrix<Cell>) bundle.get(GAME_BOARD_KEY);
        opponent = (Opponent) bundle.get(OPPONENT_KEY);
        score = (Score) bundle.get(SCORE_KEY);
    }
}
