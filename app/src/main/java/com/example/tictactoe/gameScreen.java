package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class gameScreen extends AppCompatActivity {
    boolean gameActive = true;

    int currentPlayer = new Random().nextInt(2);
    int[] gameBoard = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    // 0- X
    // 1- O
    // 2- empty

    int[][] winningSpots = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7},
            {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    String result;

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
        boolean isWinner = false;
        for(int[] win: winningSpots){
            if(gameBoard[win[0]] == gameBoard[win[1]] && gameBoard[win[1]] == gameBoard[win[2]] &&
                gameBoard[win[0]] != 2){
                gameActive = false;
                isWinner = true;
                if(gameBoard[win[0]] == 0){
                    result = "X won";
                } else {
                    result = "O won";
                }
                displayResults(view);
            }
        }

        if(squaresClicked == 9 && isWinner == false){
            gameActive = false;
            result = "Tie";
            displayResults(view);
        }
    }

    public void displayResults(View view){
        Intent intent = new Intent(this, stats.class);
        intent.putExtra("result", result);
        startActivity(intent);
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