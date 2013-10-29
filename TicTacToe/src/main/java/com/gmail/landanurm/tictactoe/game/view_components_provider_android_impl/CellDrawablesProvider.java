package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Pair;

import java.util.HashMap;
import java.util.Map;

class CellDrawablesProvider {
    private final Map<Object, Drawable> drawablesCache;
    private final Resources resources;

    CellDrawablesProvider(Activity activity) {
        drawablesCache = new HashMap<Object, Drawable>();
        resources = activity.getResources();
    }

    Drawable getCellDrawable(int moveIconId, int fireIconId) {
        if (transparentDrawableId(fireIconId)) {
            return getDrawable(moveIconId);
        } else {
            return getLayerDrawable(moveIconId, fireIconId);
        }
    }

    private boolean transparentDrawableId(int iconId) {
        return (iconId == android.R.color.transparent);
    }

    private Drawable getDrawable(int iconId) {
        Drawable drawable = drawablesCache.get(iconId);
        if (drawable == null) {
            drawable = resources.getDrawable(iconId);
            drawablesCache.put(iconId, drawable);
        }
        return drawable;
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
}
