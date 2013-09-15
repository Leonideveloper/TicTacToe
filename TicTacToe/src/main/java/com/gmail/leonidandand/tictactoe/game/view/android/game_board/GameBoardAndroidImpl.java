package com.gmail.leonidandand.tictactoe.game.view.android.game_board;

import android.view.View;
import android.widget.ImageView;

import com.gmail.leonidandand.matrix.ArrayMatrix;
import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.OnEachHandler;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.CapableSaveRestoreState;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.FireLine;
import com.gmail.leonidandand.tictactoe.game.player.Player;
import com.gmail.leonidandand.tictactoe.game.view.GameBoard;
import com.gmail.leonidandand.tictactoe.game.view.OnCellClickListener;

import java.util.Collection;
import java.util.Map;

public class GameBoardAndroidImpl implements GameBoard, CapableSaveRestoreState {

    private static final String FIRST_LAYER_IMAGE_KEY = "GameBoard.FIRST_LAYER_IMAGE_KEY";
    private static final String SECOND_LAYER_IMAGE_KEY = "GameBoard.SECOND_LAYER_IMAGE_KEY";

    private final TicTacToeIconsProvider iconsProvider;
    private final Matrix<ImageView> cellsViews;
    private Matrix<Integer> firstLayerCellImageResourcesIds;
    private Matrix<Integer> secondLayerCellImageResourcesIds;

    GameBoardAndroidImpl(Matrix<ImageView> gameBoardCells) {
        cellsViews = gameBoardCells;
        firstLayerCellImageResourcesIds = new ArrayMatrix<Integer>(cellsViews.getDimension());
        secondLayerCellImageResourcesIds = new ArrayMatrix<Integer>(cellsViews.getDimension());
        iconsProvider = new PlainTicTacToeIconsProvider();
        clear();
    }

    @Override
    public void clear() {
        cellsViews.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(Position pos, ImageView elem) {
                clearCell(pos);
            }
        });
    }

    private void clearCell(Position cellPos) {
        setSecondLayerImageResource(cellPos, android.R.color.transparent);
        setFirstLayerImageResource(cellPos, iconsProvider.getEmptyIconId());
    }

    private void setFirstLayerImageResource(Position cellPos, int resId) {
        cellsViews.get(cellPos).setBackgroundResource(resId);
        firstLayerCellImageResourcesIds.set(cellPos, resId);
    }

    private void setSecondLayerImageResource(Position cellPos, int resId) {
        cellsViews.get(cellPos).setImageResource(resId);
        secondLayerCellImageResourcesIds.set(cellPos, resId);
    }

    @Override
    public void showMove(Position pos, Player.Id playerId) {
        int iconId = iconsProvider.getPlayerIconId(playerId);
        setFirstLayerImageResource(pos, iconId);
    }

    @Override
    public void showFireLines(Collection<FireLine> fireLines) {
        for (FireLine each : fireLines) {
            showFireLine(each);
        }
    }

    private void showFireLine(FireLine fireLine) {
        FireLine.Type fireLineType = fireLine.getFireLineType();
        Collection<Position> cellsPositions = fireLine.getCellsPositions();
        for (Position pos : cellsPositions) {
            setSecondLayerImageResource(pos, iconsProvider.getFireIconId(fireLineType));
        }
    }

    @Override
    public void setOnCellClickListener(final OnCellClickListener onCellClickListener) {
        cellsViews.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(final Position pos, ImageView elem) {
                ImageView cell = cellsViews.get(pos);
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCellClickListener.onCellClick(pos);
                    }
                });
            }
        });
    }

    @Override
    public void saveState(Map<String, Object> bundle) {
        bundle.put(FIRST_LAYER_IMAGE_KEY, firstLayerCellImageResourcesIds);
        bundle.put(SECOND_LAYER_IMAGE_KEY, secondLayerCellImageResourcesIds);
    }

    @Override
    public void restoreState(Map<String, Object> bundle) {
        firstLayerCellImageResourcesIds = (Matrix<Integer>) bundle.get(FIRST_LAYER_IMAGE_KEY);
        secondLayerCellImageResourcesIds = (Matrix<Integer>) bundle.get(SECOND_LAYER_IMAGE_KEY);
        secondLayerCellImageResourcesIds.forEach(new OnEachHandler<Integer>() {
            @Override
            public void handle(Position pos, Integer each) {
                int secondLayerImageResourceId = secondLayerCellImageResourcesIds.get(pos);
                setSecondLayerImageResource(pos, secondLayerImageResourceId);
                int firstLayerImageResourceId = firstLayerCellImageResourcesIds.get(pos);
                setFirstLayerImageResource(pos, firstLayerImageResourceId);
            }
        });
    }
}
