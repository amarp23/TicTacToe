package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

//        int player1wins = (int) intent.getLongExtra("player1wins", 0);
//        int player1loss = (int) intent.getLongExtra("player1loss", 0);
//        int player1ties = (int) intent.getLongExtra("player1ties", 0);


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


        TextView resultsText = (TextView) findViewById(R.id.resultsText);
        resultsText.setText(result);

        TextView player1name = (TextView) findViewById(R.id.player1name);
        player1name.setText(player1);

        TextView player2name = (TextView) findViewById(R.id.player2name);
        player2name.setText(player2);

        TextView wins1 = (TextView) findViewById(R.id.player1wins);
        wins1.setText(Integer.toString(player1wins));

        TextView loss1 = (TextView) findViewById(R.id.player1loss);
        loss1.setText(Integer.toString(player1loss));

        TextView ties1 = (TextView) findViewById(R.id.player1ties);
        ties1.setText(Integer.toString(player1ties));


    }

    public void getPlayer1Stats(DocumentSnapshot document){
        player1wins = document.getLong("win").intValue();
        player1loss = document.getLong("loss").intValue();
        player1ties = document.getLong("tie").intValue();
    }


}