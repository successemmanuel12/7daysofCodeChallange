package com.dairy.successemmanuel.personaldairyy.accountActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dairy.successemmanuel.personaldairyy.Database.DatabaseHelper;
import com.dairy.successemmanuel.personaldairyy.R;

public class SignUpActivity extends AppCompatActivity {
    EditText email,name,password,textPassword;
    Button register;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        email = (EditText)findViewById(R.id.email);
        name = (EditText)findViewById(R.id.name);
        password = (EditText)findViewById(R.id.textPassword);
        textPassword = (EditText)findViewById(R.id.cPassword);

        register = (Button)findViewById(R.id.register);

        db = new DatabaseHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = email.getText().toString();
                String s2 = name.getText().toString();
                String s3 = password.getText().toString();
                String s4 = textPassword.getText().toString();

                if (s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")){
                    Toast.makeText(getApplicationContext(),"fill in Empty Fields", Toast.LENGTH_LONG).show();
                }else {
                    if (s3.equals(s4)){
                        Boolean checkemail = db.checkemail(s1);
                        if (checkemail){
                            Boolean insert = db.insert(s1,s2,s3);
                            email.setText("");
                            name.setText("");
                            password.setText("");
                            textPassword.setText("");
                            if (insert){
                                Toast.makeText(getApplicationContext(),"Registration successful",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                            }
                        }else
                            Toast.makeText(getApplicationContext(),"Email Already Exist", Toast.LENGTH_LONG).show();

                    }else Toast.makeText(getApplicationContext(),"Password don't match", Toast.LENGTH_LONG).show();

                }
            }
        });

        /*register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open database
                db.openDB();

                //insert into database
                long result = db.add(email.getText().toString(),name.getText().toString(),password.getText().toString(),
                        textPassword.getText().toString());
                if (result > 3){
                    email.setText("");
                    name.setText("");
                    password.setText("");
                    textPassword.setText("");
                    Toast.makeText(getApplicationContext(),"Successfully registered", Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Please fill in all necessary information before submission",
                            Toast.LENGTH_LONG).show();

                    //close the database
                    db.closeDB();
                }
            }
        });*/
    }

    public void login(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
