package com.anle.todomvp.addedittask;

import androidx.annotation.NonNull;

import com.anle.todomvp.data.Task;
import com.anle.todomvp.data.source.TasksDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditTaskPresenter implements AddEditTaskContract.UserActionsListener,
        TasksDataSource.GetTaskCallback {

    private TasksDataSource mTasksRepository;

    private AddEditTaskContract.View mAddEditTaskView;

    public AddEditTaskPresenter(@NonNull TasksDataSource tasksRepository,
                                @NonNull AddEditTaskContract.View addTaskView) {
        mTasksRepository = checkNotNull(tasksRepository);
        mAddEditTaskView = checkNotNull(addTaskView);
        mAddEditTaskView.setUserActionListener(this);
    }

    @Override
    public void updateTask(String title, String content, String taskId) {
        mTasksRepository.saveTask(new Task(title, content, taskId));
        mAddEditTaskView.showTasksList(); // After an edit, go back to the list.
    }

    @Override
    public void createTask(String title, String content) {
        Task newTask = new Task(title, content);
        if (newTask.isEmpty()) {
            mAddEditTaskView.showEmptyTaskError();
        } else {
            mTasksRepository.saveTask(newTask);
            mAddEditTaskView.showTasksList();
        }
    }

    @Override
    public void populateTask(String taskId) {
        mTasksRepository.getTask(taskId, this);
    }

    @Override
    public void onTaskLoaded(Task task) {
        mAddEditTaskView.setTitle(task.getTitle());
        mAddEditTaskView.setContent(task.getContent());
    }

    @Override
    public void onDataNotAvailable() {
        mAddEditTaskView.showEmptyTaskError();
    }
}
