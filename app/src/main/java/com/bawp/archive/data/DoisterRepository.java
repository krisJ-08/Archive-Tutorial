package com.bawp.archive.data;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.bawp.archive.model.Task;
import com.bawp.archive.roomdatabase.UserDao;
import com.bawp.archive.util.TaskRoomDatabase;

import java.util.List;

public class DoisterRepository {
    private final TaskDao taskDao;
    private final UserDao userDao;
    private final LiveData<List<Task>> allTasks;

    public DoisterRepository(Application application) {
        TaskRoomDatabase database = TaskRoomDatabase.getDatabase(application);
        this.taskDao = database.taskDao();
        this.userDao = database.userDao();
        allTasks = taskDao.getTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute( () -> taskDao.insertTask(task));
    }
    public LiveData<Task> get(long id) {return taskDao.get(id);}

    public void update(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute( () -> taskDao.update(task));
    }

    public void delete(Task task){
        TaskRoomDatabase.databaseWriterExecutor.execute( () ->taskDao.delete(task));
    }
}
