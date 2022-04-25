package com.example.bmw3;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;
import android.os.Bundle;

public class Hello_word extends AppCompatActivity {
    TextView hello_user;
    SQLiteConnector db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_word);
        db = new SQLiteConnector(this);
        findViewByID();
        hello_user.setText("Hello again " + db.getAllUser().get(0).getName() ,null);
    }
    private void findViewByID()
    {
       hello_user  = findViewById(R.id.hello);
    }

}