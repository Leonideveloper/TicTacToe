package com.gmail.leonidandand.tictactoe.game.view.android_impl.game_board_view;

import android.view.View;
import android.widget.ImageView;

import com.gmail.landanurm.matrix.*;
import com.gmail.leonidandand.tictactoe.game.model.player.Player;
import com.gmail.leonidandand.tictactoe.game.model.result.FireLine;
import com.gmail.leonidandand.tictactoe.game.view.GameBoardView;
import com.gmail.leonidandand.tictactoe.game.view.OnCellClickListener;

import java.util.Collection;

public class GameBoardViewAndroidImpl implements GameBoardView {
    private final Matrix<ImageView> cellViews;
    private final TicTacToeIconsProvider iconsProvider;
    private Matrix<Integer> firstLayerCellImageResourcesIds;
    private Matrix<Integer> secondLayerCellImageResourcesIds;

    GameBoardViewAndroidImpl(Matrix<ImageView> cellViews) {
        this.cellViews = cellViews;
        this.iconsProvider = new PlainTicTacToeIconsProvider();
        this.firstLayerCellImageResourcesIds = new ArrayMatrix<Integer>(cellViews.getDimension());
        this.secondLayerCellImageResourcesIds = new ArrayMatrix<Integer>(cellViews.getDimension());
        this.clear();
    }

    @Override
    public void clear() {
        cellViews.forEach(new OnEachHandler<ImageView>() {
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
        cellViews.get(cellPos).setBackgroundResource(resId);
        firstLayerCellImageResourcesIds.set(cellPos, resId);
    }

    private void setSecondLayerImageResource(Position cellPos, int resId) {
        cellViews.get(cellPos).setImageResource(resId);
        secondLayerCellImageResourcesIds.set(cellPos, resId);
    }

    GameBoardViewAndroidImpl(Matrix<ImageView> cellViews, GameBoardViewAndroidImpl toRestore) {
        this.cellViews = cellViews;
        this.iconsProvider = toRestore.iconsProvider;
        this.firstLayerCellImageResourcesIds = toRestore.firstLayerCellImageResourcesIds;
        this.secondLayerCellImageResourcesIds = toRestore.secondLayerCellImageResourcesIds;
        this.updateAllCellViews();
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
        int secondLayerImageResourceId = secondLayerCellImageResourcesIds.get(pos);
        setSecondLayerImageResource(pos, secondLayerImageResourceId);
        int firstLayerImageResourceId = firstLayerCellImageResourcesIds.get(pos);
        setFirstLayerImageResource(pos, firstLayerImageResourceId);
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
        cellViews.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(final Position pos, ImageView elem) {
                ImageView cell = cellViews.get(pos);
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
