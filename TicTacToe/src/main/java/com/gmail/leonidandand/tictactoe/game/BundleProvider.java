package com.gmail.leonidandand.tictactoe.game;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leonid on 16.08.13.
 */
public class BundleProvider {

    private static final Map<String, Object> bundle = new HashMap<String, Object>();

    public static Map<String, Object> getBundleToSaveState() {
        bundle.clear();
        return bundle;
    }

    public static Map<String, Object> getBundleToRestoreState() {
        return bundle;
    }
}
