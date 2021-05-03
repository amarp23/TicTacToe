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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText usernameEditText;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void startGame(View view){
        Intent intent = new Intent(this, gameScreen.class);
        startActivity(intent);
    }

    public void signUp(View view){
        String username = usernameEditText.getText().toString();

        if(username.isEmpty()){
            toastMessage("Please enter a username");
            return;
        }
        final boolean[] isNameTaken = {false};
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(username.toLowerCase() == document.getString("name").toLowerCase()){
                                    toastMessage("This username is taken. Please enter a different one");
                                    isNameTaken[0] = true;
                                    return;
                                }
                            }

                        } else {
                            Log.w("MainActivity", "Error getting documents.", task.getException());
                        }
                    }
                });
        if(isNameTaken[0]){
            return;
        }
        Map<String, Object> newUser = new HashMap<String, Object>();
        newUser.put("name", username);
        newUser.put("win", 0);
        newUser.put("loss", 0);
        newUser.put("tie", 0);

        db.collection("users")
                .add(newUser)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>(){
                    @Override
                    public void onSuccess(DocumentReference documentReference){
                        toastMessage("You are signed up!");
                        usernameEditText.setEnabled(false);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        toastMessage("Failed to sign up");
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameEditText = (EditText) findViewById(R.id.player1username);
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}