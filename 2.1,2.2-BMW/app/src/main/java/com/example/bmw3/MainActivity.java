package com.example.bmw3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;
import com.example.bmw3.model.MD5;
import com.example.bmw3.model.User;

public class MainActivity extends AppCompatActivity {
    private Button btn_sign_in;
    private Button btn_register;
    private EditText username;
    private EditText password;
    private User user = new User();
    SQLiteConnector db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new SQLiteConnector(this);
        findViewById();
        onClickHandler();
    }

    private void findViewById(){
        btn_sign_in = findViewById(R.id.edit_sign_in);
        btn_register = findViewById(R.id.edit_register);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    private void onClickHandler(){
        btn_register.setOnClickListener(view -> {
            register();
        });
        btn_sign_in.setOnClickListener(view -> {
            login();
        });
    }
    private void login()
    {
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
        else {
            MD5 hash = new MD5();
            String password_hash = hash.main(password.getText().toString().trim());
            if(db.checkUser(username.getText().toString().trim(),password_hash)) {
                user.setName(username.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());
                user.setId(db.getAllUser().get(0).getId() - 1);
                user.setEmail("temp@gmail.com");
                db.addUser(user);
                Intent intent = new Intent(MainActivity.this, Hello_word.class);
                startActivity(intent);
            }
            else {
                Toast.makeText(this,"Username or password not match. Try again", Toast.LENGTH_LONG).show();
            }
        }
    }
    private void  register()
    {
        Intent intent = new Intent(MainActivity.this, Register.class);
        startActivity(intent);
    }
}