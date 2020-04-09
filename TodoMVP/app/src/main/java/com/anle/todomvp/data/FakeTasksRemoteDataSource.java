package com.anle.todomvp.data;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;

import com.anle.todomvp.data.source.TasksDataSource;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Flowable;

/**
 * Implementation of a remote data source with static access to the data for easy testing.
 */
public class FakeTasksRemoteDataSource implements TasksDataSource {

    private static FakeTasksRemoteDataSource INSTANCE;

    private static final Map<String, Task> TASKS_SERVICE_DATA = new LinkedHashMap<>();

    // Prevent direct instantiation.
    private FakeTasksRemoteDataSource() {
    }

    public static FakeTasksRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeTasksRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public Flowable<List<Task>> getTasks() {
        Collection<Task> values = TASKS_SERVICE_DATA.values();
        return Flowable.fromIterable(values).toList().toFlowable();
    }

    @Override
    public Flowable<Optional<Task>> getTask(@NonNull String taskId) {
        Task task = TASKS_SERVICE_DATA.get(taskId);
        return Flowable.just(Optional.of(task));
    }

    @Override
    public void saveTask(@NonNull Task task) {
        TASKS_SERVICE_DATA.put(task.getId(), task);
    }

    @Override
    public void completeTask(@NonNull Task task) {
        Task completedTask = new Task(task.getTitle(), task.getContent(), task.getId(), true);
        TASKS_SERVICE_DATA.put(task.getId(), completedTask);
    }

    @Override
    public void completeTask(@NonNull String taskId) {
        Task task = TASKS_SERVICE_DATA.get(taskId);
        Task completedTask = new Task(task.getTitle(), task.getContent(), task.getId(), true);
        TASKS_SERVICE_DATA.put(taskId, completedTask);
    }

    @Override
    public void activateTask(@NonNull Task task) {
        Task activeTask = new Task(task.getTitle(), task.getContent(), task.getId());
        TASKS_SERVICE_DATA.put(task.getId(), activeTask);
    }

    @Override
    public void activateTask(@NonNull String taskId) {
        Task task = TASKS_SERVICE_DATA.get(taskId);
        Task activeTask = new Task(task.getTitle(), task.getContent(), task.getId());
        TASKS_SERVICE_DATA.put(taskId, activeTask);
    }

    @Override
    public void clearCompletedTasks() {
        Iterator<Map.Entry<String, Task>> it = TASKS_SERVICE_DATA.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Task> entry = it.next();
            if (entry.getValue().isCompleted()) {
                it.remove();
            }
        }
    }

    public void refreshTasks() {
        // Not required because the {@link TasksRepository} handles the logic of refreshing the
        // tasks from all the available data sources.
    }

    @Override
    public void deleteTask(@NonNull String taskId) {
        TASKS_SERVICE_DATA.remove(taskId);
    }

    @Override
    public void deleteAllTasks() {
        TASKS_SERVICE_DATA.clear();
    }

    @VisibleForTesting
    public void addTasks(Task... tasks) {
        for (Task task : tasks) {
            TASKS_SERVICE_DATA.put(task.getId(), task);
        }
    }
}
