package com.gmail.leonidandand.tictactoe.game.view_components_provider_android_impl.game_board_view;

import android.view.View;
import android.widget.ImageView;

import com.gmail.landanurm.matrix.*;
import com.gmail.leonidandand.tictactoe.game.model_view.model.player.Player;
import com.gmail.leonidandand.tictactoe.game.model_view.model.judge.FireLine;
import com.gmail.leonidandand.tictactoe.game.model_view.view.GameBoardView;
import com.gmail.leonidandand.tictactoe.game.model_view.view.OnCellClickListener;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class GameBoardViewAndroidImpl implements GameBoardView {

    private static class MapKeys {
        final static String iconsProvider = "GameBoardView.iconsProvider";
        final static String firstLayerCellsIds = "GameBoardView.firstLayerCellsIds";
        final static String secondLayerCellsIds = "GameBoardView.secondLayerCellsIds";
    }

    private final ReadOnlyMatrix<ImageView> cells;
    private final TicTacToeIconsProvider iconsProvider;
    private Matrix<Integer> firstLayerCellImageResourcesIds;
    private Matrix<Integer> secondLayerCellImageResourcesIds;

    GameBoardViewAndroidImpl(ReadOnlyMatrix<ImageView> cells) {
        this.cells = cells;
        this.iconsProvider = new PlainTicTacToeIconsProvider();
        this.firstLayerCellImageResourcesIds = new ArrayMatrix<Integer>(cells.getDimension());
        this.secondLayerCellImageResourcesIds = new ArrayMatrix<Integer>(cells.getDimension());
        this.clear();
    }

    public void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.iconsProvider, (Serializable) iconsProvider);
        outState.put(MapKeys.firstLayerCellsIds, (Serializable) firstLayerCellImageResourcesIds);
        outState.put(MapKeys.secondLayerCellsIds, (Serializable) secondLayerCellImageResourcesIds);
    }

    GameBoardViewAndroidImpl(Matrix<ImageView> cells, Map<String, Serializable> savedState) {
        this.cells = cells;
        this.iconsProvider = (TicTacToeIconsProvider) savedState.get(MapKeys.iconsProvider);
        this.firstLayerCellImageResourcesIds =
                (Matrix<Integer>) savedState.get(MapKeys.firstLayerCellsIds);
        this.secondLayerCellImageResourcesIds =
                (Matrix<Integer>) savedState.get(MapKeys.secondLayerCellsIds);
        this.updateAllCellViews();
    }

    @Override
    public void clear() {
        cells.forEach(new OnEachHandler<ImageView>() {
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
        cells.get(cellPos).setBackgroundResource(resId);
        firstLayerCellImageResourcesIds.set(cellPos, resId);
    }

    private void setSecondLayerImageResource(Position cellPos, int resId) {
        cells.get(cellPos).setImageResource(resId);
        secondLayerCellImageResourcesIds.set(cellPos, resId);
    }

    private void updateAllCellViews() {
        secondLayerCellImageResourcesIds.forEach(new OnEachHandler<Integer>() {
            @Override
            public void handle(Position pos, Integer each) {
                updateCell(pos);
            }
        });
    }

    private void updateCell(Position pos) {
        setSecondLayerImageResource(pos, secondLayerCellImageResourcesIds.get(pos));
        setFirstLayerImageResource(pos, firstLayerCellImageResourcesIds.get(pos));
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
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(final Position pos, ImageView elem) {
                ImageView cell = cells.get(pos);
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onCellClickListener.onCellClick(pos);
                    }
                });
            }
        });
    }
}
