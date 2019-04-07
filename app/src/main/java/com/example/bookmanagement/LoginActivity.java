package com.example.bookmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText txtUsername, txtPassword;
    private Button btnLogin;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        btnLogin.setOnClickListener(this);
        createTable();
    }

    private void createTable() {
        BaseApplication.bookDBHelper.QueryData("CREATE TABLE IF NOT EXISTS user(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username VARCHAR(45), " +
                "password VARCHAR(45))");
    }

    public void bindView() {
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword);
        btnLogin = findViewById(R.id.btnLogin);
    }

    @Override
    public void onClick(View view) {
        String username = txtUsername.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();
        User user = login(username, password);
        if (user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Sai thong tin dang nhap!", Toast.LENGTH_SHORT).show();
        }

    }

    public User login(String username, String password) {
        User user = new User();
        Cursor userFomrSQLite = BaseApplication.bookDBHelper.GetData("SELECT * FROM user WHERE username='" + username +
                "' AND password='" + password +
                "'");
        if (userFomrSQLite.moveToFirst()){
            user.setId(userFomrSQLite.getInt(0));
            user.setUsername(userFomrSQLite.getString(1));
            user.setPassword(userFomrSQLite.getString(2));

            return user;
        }
        return null;
    }

    public void addUser(){
        BaseApplication.bookDBHelper.QueryData("INSERT INTO user(`username`, `password`) VALUES('thuannd', '123')");
    }
}
