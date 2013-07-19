package com.gmail.leonidandand.tictactoe.game.view;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.controller.GameController;
import com.gmail.leonidandand.tictactoe.game.model.GameModel;
import com.gmail.leonidandand.tictactoe.game.model.GameResult;
import com.gmail.leonidandand.tictactoe.game.model.GameResultInfo;
import com.gmail.leonidandand.tictactoe.game.model.OnGameFinishedListener;
import com.gmail.leonidandand.tictactoe.game.model.OnOpponentMoveListener;
import com.gmail.leonidandand.tictactoe.utils.Dimension;
import com.gmail.leonidandand.tictactoe.utils.Matrix;

import java.util.List;

/**
 * Created by Leonid on 18.07.13.
 */
public class GameViewAndroidImpl implements GameView, OnCellClickListener,
                    OnOpponentMoveListener, OnGameFinishedListener {

    private final GameController controller;
    private final GameModel model;
    private final Activity activity;
    private final Matrix<ImageView> cells;
    private final GameResultDisplay gameResultDisplay;

    private CellIcon currentIcon;
    private boolean movesBlocked;
    private boolean gameFinished;

    public GameViewAndroidImpl(GameController controller, GameModel model, Activity activity) {
        this.controller = controller;
        this.model = model;
        this.activity = activity;
        this.cells = prepareCells();
        this.gameResultDisplay = new GameResultDisplayToastImpl(activity);

        prepareForStartGame();
        model.addOnOpponentMoveListener(this);
        model.addOnGameFinishedListener(this);
        unblockMoves();
    }

    private Matrix<ImageView> prepareCells() {
        GameBoardCreator gameBoardCreator = new GameBoardCreatorImpl(this, activity);
        return gameBoardCreator.prepareCells(model.getDimension());
    }

    private void prepareForStartGame() {
        cells.forEach(new Matrix.OnEachHandler<ImageView>() {
            @Override
            public void handle(Matrix<ImageView> matrix, Matrix.Position pos) {
                clearCell(cells.get(pos));
            }
        });
        currentIcon = CellIcon.X;
        gameFinished = false;
    }

    private void clearCell(ImageView cell) {
        cell.setImageResource(android.R.color.transparent);
        cell.setBackgroundResource(R.drawable.empty);
    }

    @Override
    public void onCellClick(Matrix.Position cellPos) {
        ImageView cell = cells.get(cellPos);
        if (gameFinished) {
            prepareForStartGame();
            controller.onViewIsReadyToStartGame();
        } else if (model.emptyCell(cellPos) && !movesBlocked()) {
            drawCell(cell);
            controller.onPlayerMove(cellPos);
        }
    }

    private void drawCell(ImageView cell) {
        int iconId;
        if (currentIcon == CellIcon.X) {
            iconId = IconsRandomizer.randomCrossIconId();
            currentIcon = CellIcon.O;
        } else {
            iconId = IconsRandomizer.randomZeroIconId();
            currentIcon = CellIcon.X;
        }
        cell.setBackgroundResource(iconId);
    }

    @Override
    public boolean movesBlocked() {
        return movesBlocked;
    }

    @Override
    public void blockMoves() {
        movesBlocked = true;
    }

    @Override
    public void unblockMoves() {
        movesBlocked = false;
    }

    @Override
    public void onOpponentMove(Matrix.Position movePos) {
        ImageView cell = cells.get(movePos);
        drawCell(cell);
    }

    @Override
    public void onGameFinished(GameResultInfo gameResultInfo) {
        gameFinished = true;
        gameResultDisplay.show(gameResultInfo.gameResult());
        fireLine(gameResultInfo.cellsOnFire());
    }

    private void fireLine(List<Matrix.Position> positions) {
        for (Matrix.Position pos : positions) {
            ImageView cell = cells.get(pos);
            cell.setImageResource(IconsRandomizer.randomFireIconId());
        }
    }
}
