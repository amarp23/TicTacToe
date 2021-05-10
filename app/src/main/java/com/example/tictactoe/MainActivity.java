package com.example.tictactoe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;
    String player1 = "";
    String player2 = "";
    String player1Id;
    String player2Id;

    int i;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public void startGame(View view){
        if(player1.isEmpty() || player2.isEmpty()){
            toastMessage("Please sign up or log in before starting a game");
        } else {
            Intent intent = new Intent(this, gameScreen.class);
            intent.putExtra("player1", player1);
            intent.putExtra("player2", player2);
            intent.putExtra("player1Id", player1Id);
            intent.putExtra("player2Id", player2Id);
            startActivity(intent);
        }
    }

    public void signUp(View view){
        String username;
        if(view.getTag().toString().equals("1")){
            usernameEditText = (EditText) findViewById(R.id.player1username);
            i = 1;
        } else {
            usernameEditText = (EditText) findViewById(R.id.player2username);
            i = 2;
        }
        username = usernameEditText.getText().toString();

        checkSignUp(username);
    }

    public void checkSignUp(String username){
        if(username.isEmpty()){
            toastMessage("Please enter a username");
            return;
        }

        db.collection("users")
                .whereEqualTo("name".toLowerCase(), username.toLowerCase())
                .get()
               .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            if(task.getResult().isEmpty()) {
                                addUser(username);
                            }
                            else {
                                toastMessage("Username is already taken");
                            }

                        } else {
                            Log.w("MainActivity", "Error getting documents.", task.getException());
                        }
                   }
                });


    }

    public void addUser(String username){
        Map<String, Object> newUser = new HashMap<String, Object>();
        newUser.put("name", username);
        newUser.put("win", 0);
        newUser.put("loss", 0);
        newUser.put("tie", 0);
        if(i == 1){
            player1 = username;
        } else {
            player2 = username;
        }
        db.collection("users")
                .add(newUser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        toastMessage("You are signed up!");
                        usernameEditText.setEnabled(false);
                        getPlayerIdFromSignUp(documentReference);
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        toastMessage("Failed to sign up");
                    }
                });

    }

    public void getPlayerIdFromSignUp(DocumentReference documentReference){
        if(i == 1){
            player1Id = documentReference.getId();
        } else {
            player2Id = documentReference.getId();
        }
    }

    public void logIn(View view){
        String username;
        if(view.getTag().toString().equals("1")){
            usernameEditText = (EditText) findViewById(R.id.player1username);
            i = 1;
        } else {
            usernameEditText = (EditText) findViewById(R.id.player2username);
            i = 2;
        }
        username = usernameEditText.getText().toString();

        checkLogIn(username);
    }

    public void checkLogIn(String username){
        if(username.isEmpty()){
            toastMessage("Please enter a username");
            return;
        }

        db.collection("users")
                .whereEqualTo("name".toLowerCase(), username.toLowerCase())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            if(!task.getResult().isEmpty()) {
                                toastMessage("You are logged in!");
                                if(i == 1){
                                    player1 = username;
                                } else {
                                    player2 = username;
                                }
                                getPlayerIdFromLogIn(task);
                                usernameEditText.setEnabled(false);
                            }
                            else {
                                toastMessage("Username not found");
                            }

                        } else {
                            Log.w("MainActivity", "Error getting documents.", task.getException());
                        }
                    }
                });

//        QuerySnapshot query = db.collection("users").whereEqualTo("name", username).get().getResult();
//        getPlayerIdFromLogIn(query);
    }

    public void getPlayerIdFromLogIn(Task<QuerySnapshot> task){
        if(i == 1){
            for (QueryDocumentSnapshot document : task.getResult()) {
                player1Id = document.getId();
            }
        } else {
            for (QueryDocumentSnapshot document : task.getResult()) {
                player2Id = document.getId();
            }
        }

    }

//    public void getPlayerIdFromLogIn(QuerySnapshot q){
//        DocumentSnapshot[] docs = q.getDocuments().toArray(new DocumentSnapshot[0]);
//        if(i == 1){
//            for(int k = 0; k < docs.length; k++){
//                player1Id = docs[k].getId();
//            }
//        } else {
//            for(int k = 0; k < docs.length; k++){
//                player2Id = docs[k].getId();
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}