package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.R;
import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;

import com.gmail.landanurm.matrix.Matrix;
import com.gmail.landanurm.matrix.OnEachHandler;
import com.gmail.landanurm.matrix.Position;
import com.gmail.landanurm.matrix.ReadOnlyMatrix;
import com.gmail.landanurm.tictactoe.CurrentThemeProvider;
import com.gmail.landanurm.tictactoe.game.model_view.model.SquareMatrix;
import com.gmail.landanurm.tictactoe.game.model_view.model.judge.FireLine;
import com.gmail.landanurm.tictactoe.game.model_view.model.player.Player;
import com.gmail.landanurm.tictactoe.game.model_view.view.GameBoardView;
import com.gmail.landanurm.tictactoe.game.model_view.view.OnCellClickListener;
import com.gmail.landanurm.tictactoe.theme.CellsTheme;
import com.gmail.landanurm.tictactoe.theme.TicTacToeTheme;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

class GameBoardViewImpl implements GameBoardView {

    private static class MapKeys {
        final static String backgroundIconsIds = "GameBoardView.backgroundIconsIds";
        final static String moveIconsIds = "GameBoardView.moveIconsIds";
        final static String fireIconsIds = "GameBoardView.fireIconsIds";
        final static String movesBlocked = "GameBoardView.movesBlocked";
    }

    private static CellsTheme getCurrentCellsTheme() {
        TicTacToeTheme currentTheme = CurrentThemeProvider.getCurrentTheme();
        return currentTheme.getGameTheme().getGameBoardTheme().getCellsTheme();
    }

    private final CellsTheme cellsTheme = getCurrentCellsTheme();

    private final CellDrawableProvider cellDrawableProvider;
    private final Matrix<Integer> backgroundIconsIds;
    private final Matrix<Integer> moveIconsIds;
    private final Matrix<Integer> fireIconsIds;
    private final ReadOnlyMatrix<ImageView> cells;

    private boolean movesBlocked;


    GameBoardViewImpl(Activity activity, int gameBoardDimension) {
        cellDrawableProvider = new CellDrawableProvider(activity.getResources());
        cells = GameBoardViewCellsProvider.prepareCells(activity, gameBoardDimension);
        backgroundIconsIds = prepareCellsBackgroundIconsIds(gameBoardDimension);
        moveIconsIds = prepareTransparentIconsIds(gameBoardDimension);
        fireIconsIds = prepareTransparentIconsIds(gameBoardDimension);
        updateCells();
        movesBlocked = false;
    }

    GameBoardViewImpl(Activity activity, int gameBoardDimension,
                      Map<String, Serializable> savedState) {
        cellDrawableProvider = new CellDrawableProvider(activity.getResources());
        cells = GameBoardViewCellsProvider.prepareCells(activity, gameBoardDimension);
        backgroundIconsIds = (Matrix<Integer>) savedState.get(MapKeys.backgroundIconsIds);
        moveIconsIds = (Matrix<Integer>) savedState.get(MapKeys.moveIconsIds);
        fireIconsIds = (Matrix<Integer>) savedState.get(MapKeys.fireIconsIds);
        updateCells();
        movesBlocked = (Boolean) savedState.get(MapKeys.movesBlocked);
    }

    void saveStateInto(Map<String, Serializable> outState) {
        outState.put(MapKeys.backgroundIconsIds, (Serializable) backgroundIconsIds);
        outState.put(MapKeys.moveIconsIds, (Serializable) moveIconsIds);
        outState.put(MapKeys.fireIconsIds, (Serializable) fireIconsIds);
        outState.put(MapKeys.movesBlocked, movesBlocked);
    }

    private Matrix<Integer> prepareCellsBackgroundIconsIds(int gameBoardDimension) {
        final Matrix<Integer> ids = new SquareMatrix<Integer>(gameBoardDimension);
        ids.forEach(new OnEachHandler<Integer>() {
            @Override
            public void handle(Position position, Integer integer) {
                ids.set(position, cellsTheme.getEmptyCellIconId());
            }
        });
        return ids;
    }

    private Matrix<Integer> prepareTransparentIconsIds(int gameBoardDimension) {
        Matrix<Integer> ids = new SquareMatrix<Integer>(gameBoardDimension);
        ids.fill(android.R.color.transparent);
        return ids;
    }

    private void updateCells() {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(Position position, ImageView cell) {
                updateCellBackground(position);
                updateCellImage(position);
            }
        });
    }

    private void updateCellBackground(Position pos) {
        ImageView cell = cells.get(pos);
        cell.setBackgroundResource(backgroundIconsIds.get(pos));
    }

    private void updateCellImage(Position pos) {
        ImageView cell = cells.get(pos);
        int moveIconId = moveIconsIds.get(pos);
        int fireIconId = fireIconsIds.get(pos);
        Drawable cellDrawable = cellDrawableProvider.getCellDrawable(moveIconId, fireIconId);
        cell.setImageDrawable(cellDrawable);
    }

    @Override
    public void setOnCellClickListener(final OnCellClickListener listener) {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(final Position pos, ImageView elem) {
                ImageView cell = cells.get(pos);
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!movesBlocked()) {
                            listener.onCellClick(pos);
                        }
                    }
                });
            }
        });
    }

    private boolean movesBlocked() {
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
    public void clear() {
        cells.forEach(new OnEachHandler<ImageView>() {
            @Override
            public void handle(Position pos, ImageView cell) {
                moveIconsIds.set(pos, android.R.color.transparent);
                fireIconsIds.set(pos, android.R.color.transparent);
                updateCellImage(pos);
            }
        });
    }

    @Override
    public void showMove(Position pos, Player.Id playerId) {
        if (playerId == Player.Id.FIRST_PLAYER) {
            moveIconsIds.set(pos, cellsTheme.getFirstPlayerMoveIconId());
        } else {
            moveIconsIds.set(pos, cellsTheme.getSecondPlayerMoveIconId());
        }
        updateCellImage(pos);
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
            fireIconsIds.set(pos, cellsTheme.getFireIconId(fireLineType));
            updateCellImage(pos);
        }
    }


    private static class CellDrawableProvider {
        private final Map<Object, Drawable> drawablesCache;
        private final Resources resources;

        CellDrawableProvider(Resources resources) {
            this.resources = resources;
            this.drawablesCache = new HashMap<Object, Drawable>();
        }

        Drawable getCellDrawable(int moveIconId, int fireIconId) {
            if (fireIconId == R.color.transparent) {
                return getDrawable(moveIconId);
            } else {
                return getLayerDrawable(moveIconId, fireIconId);
            }
        }

        private Drawable getLayerDrawable(int moveIconId, int fireIconId) {
            Object layerDrawableKey = Pair.create(moveIconId, fireIconId);
            Drawable layerDrawable = drawablesCache.get(layerDrawableKey);
            if (layerDrawable == null) {
                layerDrawable = createLayerDrawable(moveIconId, fireIconId);
                drawablesCache.put(layerDrawableKey, layerDrawable);
            }
            return layerDrawable;
        }

        private Drawable createLayerDrawable(int moveIconId, int fireIconId) {
            Drawable[] layers = new Drawable[2];
            layers[0] = getDrawable(moveIconId);
            layers[1] = getDrawable(fireIconId);
            return new LayerDrawable(layers);
        }

        private Drawable getDrawable(int iconId) {
            Drawable drawable = drawablesCache.get(iconId);
            if (drawable == null) {
                drawable = resources.getDrawable(iconId);
                drawablesCache.put(iconId, drawable);
            }
            return drawable;
        }
    }
}
