package com.bawp.archive.adapter;

import com.bawp.archive.model.Task;

public interface OnTodoClickListener {
    void onTodoClick(Task task);
    void onTodoRadioButtonClick(Task task);
}
