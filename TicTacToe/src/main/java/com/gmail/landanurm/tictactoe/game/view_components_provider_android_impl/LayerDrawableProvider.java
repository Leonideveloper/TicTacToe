package com.gmail.landanurm.tictactoe.game.view_components_provider_android_impl;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.Pair;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leonid on 26.10.13.
 */
class LayerDrawableProvider {
    private final Map<Object, Drawable> drawables;
    private final Resources resources;

    LayerDrawableProvider(Resources resources) {
        this.resources = resources;
        this.drawables = new HashMap<Object, Drawable>();
    }

    Drawable getLayerDrawable(int moveIconId, int fireIconId) {
        Object layerDrawableKey = Pair.create(moveIconId, fireIconId);
        Drawable layerDrawable = drawables.get(layerDrawableKey);
        if (layerDrawable == null) {
            layerDrawable = createLayerDrawable(moveIconId, fireIconId);
            drawables.put(layerDrawableKey, layerDrawable);
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
        Drawable drawable = drawables.get(iconId);
        if (drawable == null) {
            drawable = resources.getDrawable(iconId);
            drawables.put(iconId, drawable);
        }
        return drawable;
    }
}
