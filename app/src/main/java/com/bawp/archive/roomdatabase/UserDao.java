package com.bawp.archive.roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT * from user where userId=(:userId) and password=(:password)")
    UserEntity login(String userId, String password);


}
