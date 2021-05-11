package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.CountDownTimer;
import android.widget.Button;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Random;

public class gameScreen extends AppCompatActivity {
    boolean gameActive = true;

    String player1;
    String player2;

    String player1Id;
    String player2Id;

    int currentPlayer = new Random().nextInt(2);
    int[] gameBoard = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    // 0- X
    // 1- O
    // 2- empty

    int[][] winningSpots = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7},
            {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};

    String result;

    int squaresClicked = 0;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    // on click- when each square is clicked, assign and set appropriate image to square
    // check if there is a winner or if the game is finished
    // if there is, update stats in firestore and call displayResults
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
                TextView status = findViewById(R.id.status);
                status.setText(player2 + "'s turn");
            } else {
                img.setImageResource(R.drawable.o_image);
                currentPlayer = 0;
                TextView status = findViewById(R.id.status);
                status.setText(player1 + "'s turn");
            }
        }
        boolean isWinner = false;

        DocumentReference player1Doc = db.collection("users").document(player1Id);
        DocumentReference player2Doc = db.collection("users").document(player2Id);

        for(int[] win: winningSpots){
            if(gameBoard[win[0]] == gameBoard[win[1]] && gameBoard[win[1]] == gameBoard[win[2]] &&
                gameBoard[win[0]] != 2){
                gameActive = false;
                isWinner = true;
                if(gameBoard[win[0]] == 0){
                    result = player1 + " won";

                    player1Doc.update("win", FieldValue.increment(1));
                    player2Doc.update("loss", FieldValue.increment(1));

                } else {
                    result = player2 + " won!";

                    player1Doc.update("loss", FieldValue.increment(1));
                    player2Doc.update("win", FieldValue.increment(1));
                }
                displayResults(view);
            }
        }

        if(squaresClicked == 9 && isWinner == false){
            gameActive = false;
            result = "Tie";

            player1Doc.update("tie", FieldValue.increment(1));
            player2Doc.update("tie", FieldValue.increment(1));
            displayResults(view);
        }

        if (!gameActive) {
            gameReset(view);
        }
    }

    // sends results and stats to stats screen and goes to stats screen
    public void displayResults(View view){
        Intent intent = new Intent(this, stats.class);
        intent.putExtra("result", result);
        intent.putExtra("player1", player1);
        intent.putExtra("player2", player2);
        intent.putExtra("player1Id", player1Id);
        intent.putExtra("player2Id", player2Id);

        startActivity(intent);
    }

    // clears game board
    public void gameReset(View view) {
        for (int i = 0; i < gameBoard.length; i++) {
            gameBoard[i] = 2;
        }

        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_screen);

        Intent intent = getIntent();
        player1 = intent.getStringExtra("player1");
        player2 = intent.getStringExtra("player2");
        player1Id = intent.getStringExtra("player1Id");
        player2Id = intent.getStringExtra("player2Id");

        TextView status = findViewById(R.id.status);
        if(currentPlayer == 0){
            status.setText(player1 + "'s Turn - Tap to play");
        } else {
            status.setText(player2 + "'s Turn - Tap to play");
        }
    }
    /*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button= (Button) findViewById(R.id.button);
        textView= (TextView) findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                new CountDownTimer(3000, 1000){
                    public void onTick(long millisUntilFinished){
                        textView.setText(String.valueOf(counter));
                        counter++;
                    }
                    public  void onFinish(){
                        textView.setText("FINISH!!");
                    }
                }.start();
            }
        });
    }
    */
}