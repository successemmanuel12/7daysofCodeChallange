package com.dairy.successemmanuel.personaldairyy.accountActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dairy.successemmanuel.personaldairyy.Database.DatabaseHelper;
import com.dairy.successemmanuel.personaldairyy.MainActivity;
import com.dairy.successemmanuel.personaldairyy.R;

public class LoginActivity extends AppCompatActivity {
    EditText e1,e2;
    Button login;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        e1= (EditText)findViewById(R.id.username) ;
        e2= (EditText)findViewById(R.id.textPassword);

        login = (Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = e1.getText().toString();
                String password = e2.getText().toString();

                boolean checkemailpass = db.emailPassword(email,password);
                if (checkemailpass){
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }else
                    Toast.makeText(getApplicationContext(),"Wrong Email or Password",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void register(View view) {
        startActivity(new Intent(this, SignUpActivity.class));
    }
}
