package com.example.example;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RateActivity extends AppCompatActivity {
    RatingBar ratingBar1,ratingBar2,ratingBar3,ratingBar4,ratingBar5;
    Button submit,back;
    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private static final String TAG = "RateActivity";
    String sessionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        ratingBar1 = (RatingBar) findViewById(R.id.ratingBar1);
        ratingBar2 = (RatingBar) findViewById(R.id.ratingBar2);
        ratingBar3 = (RatingBar) findViewById(R.id.ratingBar3);
        ratingBar4 = (RatingBar) findViewById(R.id.ratingBar4);
        ratingBar5 = (RatingBar) findViewById(R.id.ratingBar5);
        back= (Button) findViewById(R.id.backactivity);
        submit = (Button)findViewById(R.id.submit);
        sessionId = getIntent().getStringExtra("EXTRA_SESSION_ID");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float num1=ratingBar1.getRating();
                float num2=ratingBar2.getRating();
                float num3=ratingBar3.getRating();
                float num4=ratingBar4.getRating();
                float num5=ratingBar5.getRating();

                if (num1==0 || num2==0 || num3==0 || num4==0 || num5==0){
                    Toast.makeText(RateActivity.this, "Please answer all the questions", Toast.LENGTH_SHORT).show();
                }
                else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("Question1", num1);
                    data.put("Question2", num2);
                    data.put("Question3", num3);
                    data.put("Question4", num4);
                    data.put("Question5", num5);

                    db.collection("Rating")
                            .add(data)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                                    buildDialog();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RateActivity.this, MainCivilianActivity.class);
                intent.putExtra("EXTRA_SESSION_ID", sessionId);

                startActivity(intent);

            }
        });
    }

    public void buildDialog() {
        new AlertDialog.Builder(this)
                //.setTitle("Confirm")
                .setMessage("Thank You " + sessionId + " for your opinion")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent first = new Intent(RateActivity.this, MainCivilianActivity.class);
                        first.putExtra("EXTRA_SESSION_ID", sessionId);
                        startActivity(first);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_info)
                .show();

    }

}
