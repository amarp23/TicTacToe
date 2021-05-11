package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class stats extends AppCompatActivity {
    int player1wins;
    int player1loss;
    int player1ties;

    int player2wins;
    int player2loss;
    int player2ties;

    // get result and stats from game and display them
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        String player1 = intent.getStringExtra("player1");
        String player2 = intent.getStringExtra("player2");
        String player1Id = intent.getStringExtra("player1Id");
        String player2Id = intent.getStringExtra("player2Id");

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DocumentReference player1Doc = db.collection("users").document(player1Id);
        DocumentReference player2Doc = db.collection("users").document(player2Id);

        player1Doc.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            getPlayer1Stats(document);

                        } else {
                            Log.w("MainActivity", "Error getting documents.", task.getException());
                        }
                    }
                });

        player2Doc.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            getPlayer2Stats(document);

                        } else {
                            Log.w("MainActivity", "Error getting documents.", task.getException());
                        }
                    }
                });



        TextView resultsText = (TextView) findViewById(R.id.resultsText);
        resultsText.setText(result);

        TextView player1name = (TextView) findViewById(R.id.player1name);
        player1name.setText(player1);

        TextView player2name = (TextView) findViewById(R.id.player2name);
        player2name.setText(player2);


    }

    public void getPlayer1Stats(DocumentSnapshot document){
        player1wins = document.getLong("win").intValue();
        player1loss = document.getLong("loss").intValue();
        player1ties = document.getLong("tie").intValue();

        TextView wins = (TextView) findViewById(R.id.player1wins);
        wins.setText(Integer.toString(player1wins));

        TextView loss = (TextView) findViewById(R.id.player1loss);
        loss.setText(Integer.toString(player1loss));

        TextView ties = (TextView) findViewById(R.id.player1ties);
        ties.setText(Integer.toString(player1ties));
    }

    public void getPlayer2Stats(DocumentSnapshot document){
        player2wins = document.getLong("win").intValue();
        player2loss = document.getLong("loss").intValue();
        player2ties = document.getLong("tie").intValue();

        TextView wins = (TextView) findViewById(R.id.player2wins);
        wins.setText(Integer.toString(player2wins));

        TextView loss = (TextView) findViewById(R.id.player2loss);
        loss.setText(Integer.toString(player2loss));

        TextView ties = (TextView) findViewById(R.id.player2ties);
        ties.setText(Integer.toString(player2ties));
    }

    // resets all text views to original and goes back to main screen
    public void backToHome(View view){
        TextView resultsText = (TextView) findViewById(R.id.resultsText);
        resultsText.setText("result");

        TextView player1name = (TextView) findViewById(R.id.player1name);
        player1name.setText("Player 1");

        TextView player2name = (TextView) findViewById(R.id.player2name);
        player2name.setText("Player 2");

        TextView wins1 = (TextView) findViewById(R.id.player1wins);
        wins1.setText(Integer.toString(0));

        TextView loss1 = (TextView) findViewById(R.id.player1loss);
        loss1.setText(Integer.toString(0));

        TextView ties1 = (TextView) findViewById(R.id.player1ties);
        ties1.setText(Integer.toString(0));

        TextView wins2 = (TextView) findViewById(R.id.player2wins);
        wins2.setText(Integer.toString(0));

        TextView loss2 = (TextView) findViewById(R.id.player2loss);
        loss2.setText(Integer.toString(0));

        TextView ties2 = (TextView) findViewById(R.id.player2ties);
        ties2.setText(Integer.toString(0));

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}