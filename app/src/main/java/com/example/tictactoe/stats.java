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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        Intent intent = getIntent();
        String result = intent.getStringExtra("result");
        String player1 = intent.getStringExtra("player1");
        String player2 = intent.getStringExtra("player2");
        String player1wins = intent.getStringExtra("player1wins");
        String player1loss = intent.getStringExtra("player1loss");
        String player1ties = intent.getStringExtra("player1ties");


        TextView resultsText = (TextView) findViewById(R.id.resultsText);
        resultsText.setText(result);

        TextView player1name = (TextView) findViewById(R.id.player1name);
        player1name.setText(player1);

        TextView player2name = (TextView) findViewById(R.id.player2name);
        player2name.setText(player2);

        TextView wins1 = (TextView) findViewById(R.id.player1wins);
        wins1.setText(player1wins);

        TextView loss1 = (TextView) findViewById(R.id.player1loss);
        loss1.setText(player1loss);

        TextView ties1 = (TextView) findViewById(R.id.player1ties);
        ties1.setText(player1ties);

//        db.collection("users")
//                .whereEqualTo("name".toLowerCase(), player1.toLowerCase())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//
//                        } else {
//                            Log.w("MainActivity", "Error getting documents.", task.getException());
//                        }
//                    }
//                });
    }


}