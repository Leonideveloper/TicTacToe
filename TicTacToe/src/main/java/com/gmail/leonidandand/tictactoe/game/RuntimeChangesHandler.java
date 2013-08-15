package com.gmail.leonidandand.tictactoe.game;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Leonid on 14.08.13.
 */
public class RuntimeChangesHandler {

    private static final Map<String, Object> BUNDLE = new HashMap<String, Object>();
    private final CapableSaveRestoreState[] allCapableSaveRestoreState;

    public RuntimeChangesHandler(CapableSaveRestoreState... allCapableSaveRestoreState) {
        this.allCapableSaveRestoreState = allCapableSaveRestoreState;
    }

    public void saveStates() {
        for (CapableSaveRestoreState each : allCapableSaveRestoreState) {
            each.saveState(BUNDLE);
        }
    }

    public void restoreStates() {
        for (CapableSaveRestoreState each : allCapableSaveRestoreState) {
            each.restoreState(BUNDLE);
        }
        BUNDLE.clear();
    }
}
