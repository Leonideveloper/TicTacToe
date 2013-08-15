package com.gmail.leonidandand.tictactoe.game.view.android.game_board;

import android.view.View;
import android.widget.ImageView;

import com.gmail.leonidandand.matrix.ArrayMatrix;
import com.gmail.leonidandand.matrix.Matrix;
import com.gmail.leonidandand.matrix.OnEachHandler;
import com.gmail.leonidandand.matrix.Position;
import com.gmail.leonidandand.tictactoe.game.CapableSaveRestoreState;
import com.gmail.leonidandand.tictactoe.game.model.game_judge.FireLine;
import com.gmail.leonidandand.tictactoe.game.view.CellIcon;
import com.gmail.leonidandand.tictactoe.game.view.GameBoard;
import com.gmail.leonidandand.tictactoe.game.view.OnCellClickListener;

import java.util.Collection;
import java.util.Map;

public class GameBoardAndroidImpl implements GameBoard, CapableSaveRestoreState {

    private static final String CELLS_BACKGROUND_KEY = "GameBoard.cellsBackgroundResources";
    private static final String CELLS_IMAGE_KEY = "GameBoard.cellsImageResources";
    private static final String CURRENT_ICON_KEY = "GameBoard.currentIcon";

    private final IconsProvider iconsProvider;
    private final Matrix<ImageView> cellsViews;
    private Matrix<Integer> cellsBackgroundResourcesIds;
    private Matrix<Integer> cellsImageResourcesIds;
    private CellIcon currentIcon;

    GameBoardAndroidImpl(Matrix<ImageView> gameBoardCells) {
        cellsViews = gameBoardCells;
        cellsBackgroundResourcesIds = new ArrayMatrix<Integer>(cellsViews.getDimension());
        cellsImageResourcesIds = new ArrayMatrix<Integer>(cellsViews.getDimension());
        iconsProvider = new PlainIconsProvider();
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
        currentIcon = CellIcon.X;
    }

    private void clearCell(Position cellPos) {
        setCellImageResource(cellPos, android.R.color.transparent);
        setCellBackgroundResource(cellPos, iconsProvider.getEmptyIconId());
    }

    private void setCellImageResource(Position cellPos, int resId) {
        cellsViews.get(cellPos).setImageResource(resId);
        cellsImageResourcesIds.set(cellPos, resId);
    }

    private void setCellBackgroundResource(Position cellPos, int resId) {
        cellsViews.get(cellPos).setBackgroundResource(resId);
        cellsBackgroundResourcesIds.set(cellPos, resId);
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
    public void showMove(Position pos) {
        int iconId;
        if (currentIcon == CellIcon.X) {
            iconId = iconsProvider.getCrossIconId();
            currentIcon = CellIcon.O;
        } else {
            iconId = iconsProvider.getZeroIconId();
            currentIcon = CellIcon.X;
        }
        setCellBackgroundResource(pos, iconId);
    }

    @Override
    public void showFireLines(Collection<FireLine> fireLines) {
        for (FireLine each : fireLines) {
            showFireLine(each);
        }
    }

    private void showFireLine(FireLine fireLine) {
        Collection<Position> positions = fireLine.getCellsPositions();
        FireLine.Type fireLineType = fireLine.getFireLineType();
        for (Position pos : positions) {
            setCellImageResource(pos, iconsProvider.getFireIconId(fireLineType));
        }
    }

    @Override
    public void saveState(Map<String, Object> bundle) {
        bundle.put(CELLS_BACKGROUND_KEY, cellsBackgroundResourcesIds);
        bundle.put(CELLS_IMAGE_KEY, cellsImageResourcesIds);
        bundle.put(CURRENT_ICON_KEY, currentIcon);
    }

    @Override
    public void restoreState(Map<String, Object> bundle) {
        currentIcon = (CellIcon) bundle.get(CURRENT_ICON_KEY);
        cellsImageResourcesIds = (Matrix<Integer>) bundle.get(CELLS_IMAGE_KEY);
        cellsBackgroundResourcesIds = (Matrix<Integer>) bundle.get(CELLS_BACKGROUND_KEY);
        cellsImageResourcesIds.forEach(new OnEachHandler<Integer>() {
            @Override
            public void handle(Position pos, Integer each) {
                int imageResourceId = cellsImageResourcesIds.get(pos);
                setCellImageResource(pos, imageResourceId);
                int backgroundResourceId = cellsBackgroundResourcesIds.get(pos);
                setCellBackgroundResource(pos, backgroundResourceId);
            }
        });
    }
}
