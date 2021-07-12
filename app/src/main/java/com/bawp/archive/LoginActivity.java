package com.bawp.archive;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bawp.archive.roomdatabase.UserDao;
import com.bawp.archive.roomdatabase.UserEntity;
import com.bawp.archive.util.TaskRoomDatabase;


import at.favre.lib.crypto.bcrypt.BCrypt;

public class LoginActivity extends AppCompatActivity {
    RegistrationActivity regi;

    EditText userId, password;
    Button login;
    private String userIdText;
    private String passwordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        getSupportActionBar().hide();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userIdText = userId.getText().toString();
                passwordText = password.getText().toString();

                if (userIdText.isEmpty() || passwordText.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }else {
                    //Perform Query
                    TaskRoomDatabase userDatabase = TaskRoomDatabase.getDatabase(getApplicationContext());
                    final UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            UserEntity userDB = userDao.loginuser();
                            UserEntity passDB = userDao.loginpass();
                            String struserDB = userDB.getUserId();
                            String strpassDB = passDB.getPassword();
                            BCrypt.Result resultUser = BCrypt.verifyer().verify(userIdText.toCharArray(), struserDB);
                            BCrypt.Result resultPass = BCrypt.verifyer().verify(passwordText.toCharArray(), strpassDB);
                            if (!resultUser.verified && !resultPass.verified) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Invalid Credentials! Please try agin", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else {
                                String name = "userDB.name";
                                startActivity(new Intent(
                                        LoginActivity.this,MainActivity.class)
                                        .putExtra("name", name));
                            }
                        }
                    }).start();
                }
            }
        });
    }
}