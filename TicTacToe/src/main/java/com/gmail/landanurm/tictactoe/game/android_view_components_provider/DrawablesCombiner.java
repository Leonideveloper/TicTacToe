package com.gmail.landanurm.tictactoe.game.android_view_components_provider;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DrawablesCombiner {

    private final int transparentDrawableId = android.R.color.transparent;

    private final Map<Object, Drawable> drawablesCache;
    private final Resources resources;

    DrawablesCombiner(Activity activity) {
        drawablesCache = new HashMap<Object, Drawable>();
        resources = activity.getResources();
    }

    Drawable getCombinedDrawable(int... ids) {
        return getCombinedDrawable(getNotFullyTransparentDrawablesIds(ids));
    }

    private List<Integer> getNotFullyTransparentDrawablesIds(int... ids) {
        List<Integer> notFullyTransparentDrawablesIds = new ArrayList<Integer>();
        for (int id : ids) {
            if (id != transparentDrawableId) {
                notFullyTransparentDrawablesIds.add(id);
            }
        }
        return notFullyTransparentDrawablesIds;
    }

    private Drawable getCombinedDrawable(List<Integer> ids) {
        int numberOfDrawables = ids.size();
        if (numberOfDrawables > 1) {
            return getLayerDrawable(ids);
        } else if (numberOfDrawables == 1) {
            return getDrawable(ids.get(0));
        } else {
            return getDrawable(transparentDrawableId);
        }
    }

    private Drawable getLayerDrawable(List<Integer> layersIds) {
        Drawable layerDrawable = drawablesCache.get(layersIds);
        if (layerDrawable == null) {
            layerDrawable = createLayerDrawable(layersIds);
            drawablesCache.put(layersIds, layerDrawable);
        }
        return layerDrawable;
    }

    private Drawable createLayerDrawable(List<Integer> layersIds) {
        int numberOfLayers = layersIds.size();
        Drawable[] layers = new Drawable[numberOfLayers];
        for (int i = 0; i < numberOfLayers; ++i) {
            int layerId = layersIds.get(i);
            layers[i] = getDrawable(layerId);
        }
        return new LayerDrawable(layers);
    }

    private Drawable getDrawable(int id) {
        Drawable drawable = drawablesCache.get(id);
        if (drawable == null) {
            drawable = resources.getDrawable(id);
            drawablesCache.put(id, drawable);
        }
        return drawable;
    }
}
