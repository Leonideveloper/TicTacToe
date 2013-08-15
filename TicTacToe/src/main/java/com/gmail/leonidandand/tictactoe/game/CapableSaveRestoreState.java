package com.gmail.leonidandand.tictactoe.game;

import java.util.Map;

/**
 * Created by Leonid on 14.08.13.
 */
public interface CapableSaveRestoreState {
    void saveState(Map<String, Object> bundle);
    void restoreState(Map<String, Object> bundle);
}
