package com.gmail.leonidandand.tictactoe.menu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.gmail.leonidandand.tictactoe.R;
import com.gmail.leonidandand.tictactoe.game.DifficultyLevel;
import com.gmail.leonidandand.tictactoe.game.TicTacToeActivity;

/**
 * Created by Leonid on 18.08.13.
 */
public class StartGameMenuActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game_menu_activity);

        findViewById(R.id.startGameButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onStartGame();
            }
        });
    }

    private void onStartGame() {
        Intent intent = new Intent(this, TicTacToeActivity.class);
        intent.putExtra(TicTacToeActivity.DIFFICULTY_LEVEL_KEY, getNameOfSelectedDifficultyLevel());
        startActivity(intent);
    }

    public String getNameOfSelectedDifficultyLevel() {
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.difficultyLevelRadioGroup);
        int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        DifficultyLevel difficultyLevel = difficultyLevelByRadioButtonId(checkedRadioButtonId);
        return difficultyLevel.name();
    }

    private DifficultyLevel difficultyLevelByRadioButtonId(int checkedRadioButtonId) {
        switch (checkedRadioButtonId) {
        case R.id.easyRadioButton:
            return DifficultyLevel.EASY;

        case R.id.mediumRadioButton:
            return DifficultyLevel.MEDIUM;

        case R.id.hardRadioButton:
            return DifficultyLevel.HARD;
        }

        throw new IllegalArgumentException("Unknown radio button id");
    }
}