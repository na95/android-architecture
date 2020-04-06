package com.anle.todomvp.addedittask;

import androidx.annotation.NonNull;

import com.anle.todomvp.data.Task;
import com.anle.todomvp.data.source.TasksDataSource;

import static com.google.common.base.Preconditions.checkNotNull;

public class AddEditTaskPresenter implements AddEditTaskContract.Presenter,
        TasksDataSource.GetTaskCallback {

    private TasksDataSource mTasksRepository;

    private AddEditTaskContract.View mAddEditTaskView;

    private String mTaskId;

    public AddEditTaskPresenter(String taskId,
                                @NonNull TasksDataSource tasksRepository,
                                @NonNull AddEditTaskContract.View addTaskView) {
        mTaskId = taskId;
        mTasksRepository = checkNotNull(tasksRepository);
        mAddEditTaskView = checkNotNull(addTaskView);
        mAddEditTaskView.setPresenter(this);
    }

    @Override
    public void saveTask(String title, String content) {
        if (isNewTask()) {
            createTask(title, content);
        } else {
            updateTask(title, content);
        }
    }

    @Override
    public void populateTask() {
        if (!isNewTask()) {
            mTasksRepository.getTask(mTaskId, this); // this = GetTaskCallback is implemented inside this class
        }
    }

    private boolean isNewTask() {
        return mTaskId == null;
    }

    public void updateTask(String title, String content) {
        if (isNewTask()) {
            throw new RuntimeException("updateTask() was called but task is new.");
        }
        mTasksRepository.saveTask(new Task(title, content, mTaskId));
        mAddEditTaskView.showTasksList(); // After an edit, go back to the list.
    }

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
    public void onTaskLoaded(Task task) {
        mAddEditTaskView.setTitle(task.getTitle());
        mAddEditTaskView.setContent(task.getContent());
    }

    @Override
    public void onDataNotAvailable() {
        mAddEditTaskView.showEmptyTaskError();
    }
}
