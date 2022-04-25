package com.example.bmw3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.bmw3.model.User;
import com.example.bmw3.model.MD5;

public class Register extends AppCompatActivity {
    Button btnBai2_2;
    private EditText username;
    private EditText password;
    private EditText email;
    SQLiteConnector db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        db = new SQLiteConnector(this);
        findViewById();
        onClickHandler();
    }

    private void findViewById(){
        btnBai2_2 = findViewById(R.id.edit_register1);
        username = findViewById(R.id.reg_username);
        password = findViewById(R.id.reg_password);
        email = findViewById(R.id.reg_email);
    }

    private void onClickHandler(){
        btnBai2_2.setOnClickListener(view -> {
            register();
        });
    }
    private void register(){
        //if username is not valid
        if(TextUtils.isEmpty(username.getText().toString().trim())){
            username.requestFocus();
            username.setError(this.getResources().getString(R.string.error_field_required));
        }
        // if password is not valid
        else if(TextUtils.isEmpty(password.getText().toString().trim())){
            password.requestFocus();
            password.setError(this.getResources().getString(R.string.error_field_required));
        }
        //if email is not valid
        else if(TextUtils.isEmpty(email.getText().toString().trim()) || !(Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches())){
            email.requestFocus();
            email.setError(this.getResources().getString(R.string.error_field_required));
        }
        else {
            MD5 hash = new MD5();
            String password_hash = hash.main(password.getText().toString().trim());
            User user = new User();
            user.setName(username.getText().toString().trim());
            user.setEmail(email.getText().toString().trim());
            user.setPassword(password_hash);
            if(!(db.checkUser(email.getText().toString().trim()))) {
                db.addUser(user);
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this,"Email's already existed",Toast.LENGTH_LONG).show();
            }
        }

    }

}