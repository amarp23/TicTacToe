package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class gameScreen extends AppCompatActivity {
    boolean gameActive = true;

    int currentPlayer = 0;
    int[] gameBoard = {2, 2, 2, 2, 2, 2, 2, 2, 2,};
    // 0- X
    // 1- O
    // 2- empty

    int squaresClicked = 0;
    public void clicked(View view){
        ImageView img = (ImageView) view;
        int clickedImg = Integer.parseInt(img.getTag().toString());

        if(gameBoard[clickedImg] == 2){
            squaresClicked++;

            if(squaresClicked == 9){
                gameActive = false;
            }

            gameBoard[clickedImg] = currentPlayer;

            if(currentPlayer == 0){
                img.setImageResource(R.drawable.x_image);
                currentPlayer = 1;
            } else {
                img.setImageResource(R.drawable.o_image);
                currentPlayer = 0;
            }
        }
    }

    public void resetGame(View view){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        Intent intent = getIntent();
    }
}