package com.example.example;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class ForgotActivity extends AppCompatActivity {

    EditText username,father,school;
    Button send,back;
    TextView showpass ;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Checkfunction checkfunction=new Checkfunction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        username=(EditText)findViewById(R.id.usernameForgot);
        father=(EditText)findViewById(R.id.Fathername);
        school=(EditText)findViewById(R.id.school);
        send= (Button)findViewById(R.id.SendForgot);
        showpass= (TextView) findViewById(R.id.Showpass);
        back= (Button) findViewById(R.id.gobacktomenu);

        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final String daddy = father.getText().toString().trim();
                final String school1 = school.getText().toString().trim();
                String user = username.getText().toString().trim();
                if((checkfunction.validName(daddy) && checkfunction.validName(school1))==false){
                    Toast.makeText(ForgotActivity.this, "", Toast.LENGTH_SHORT).show();
                }
                if (user.equals("") || daddy.equals("") || school1.equals("")) {
                    Toast.makeText(ForgotActivity.this, "One or more field are empty !", Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("users").document(user).get()
                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if (documentSnapshot.exists()) {
                                        String dad = documentSnapshot.getString("Father");
                                        String school = documentSnapshot.getString("School");
                                        if (dad.equals(daddy) && school.equals(school1)) {
                                            String pass = documentSnapshot.getString("password");
                                            String password = "Your password : " + pass;
                                            showpass.setText(password);
                                        } else {
                                            Toast.makeText(ForgotActivity.this, "you insert wrong answers!", Toast.LENGTH_SHORT).show();
                                        }
                                    }


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ForgotActivity.this, "username not exist!", Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ForgotActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
