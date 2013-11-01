package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CombinedDrawablesProvider {

    private final int transparentDrawableId = android.R.color.transparent;

    private final Map<Object, Drawable> drawablesCache;
    private final Resources resources;

    CombinedDrawablesProvider(Activity activity) {
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

    private Drawable getLayerDrawable(List<Integer> ids) {
        Drawable layerDrawable = drawablesCache.get(ids);
        if (layerDrawable == null) {
            layerDrawable = createLayerDrawable(ids);
            drawablesCache.put(ids, layerDrawable);
        }
        return layerDrawable;
    }

    private Drawable createLayerDrawable(List<Integer> ids) {
        int numberOfLayers = ids.size();
        Drawable[] layers = new Drawable[numberOfLayers];
        for (int i = 0; i < numberOfLayers; ++i) {
            layers[i] = getDrawable(ids.get(i));
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
