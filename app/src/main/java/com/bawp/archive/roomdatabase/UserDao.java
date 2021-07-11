package com.bawp.archive.roomdatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void registerUser(UserEntity userEntity);

    @Query("SELECT userId from user")
    UserEntity loginuser ();

    @Query("SELECT password from user")
    UserEntity loginpass ();

}
