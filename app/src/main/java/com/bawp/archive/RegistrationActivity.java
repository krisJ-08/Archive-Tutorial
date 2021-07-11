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

public class RegistrationActivity extends AppCompatActivity {

    EditText userId, password, name;
    Button register;
    Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        userId = findViewById(R.id.userId);
        password = findViewById(R.id.password);
        name = findViewById(R.id.name);
        register = findViewById(R.id.register);
        login = findViewById(R.id.login);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Creating User Entity
                UserEntity userEntity = new UserEntity();
                userEntity.setUserId(userId.getText().toString());
                userEntity.setPassword(password.getText().toString());
                userEntity.setName(name.getText().toString());
                System.out.println(userEntity);
                if (validateInput(userEntity)){
                    //Do insert operation
                    TaskRoomDatabase userDatabase = TaskRoomDatabase.getDatabase(getApplicationContext());
                    final UserDao userDao = userDatabase.userDao();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Register User
                            userDao.registerUser(userEntity);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(getApplicationContext(),"User Registered", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    }).start();
                }else{
                    Toast.makeText(getApplicationContext(), "Please full all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

            }
        });

    }

    private Boolean validateInput(UserEntity userEntity){
        if (userEntity.getUserId().isEmpty() ||
            userEntity.getPassword().isEmpty()||
            userEntity.getName().isEmpty()){
            return false;
        }else {
            return true;
        }
    }
}