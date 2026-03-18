package com.pao.laboratory03.bonus;

import com.pao.laboratory03.bonus.enums.Priority;
import com.pao.laboratory03.bonus.enums.Status;
import com.pao.laboratory03.bonus.exceptions.InvalidTransitionException;
import com.pao.laboratory03.bonus.exceptions.TaskNotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class TaskService {
    private static final TaskService instance = new TaskService();

    private TaskService() {
        tasks = new HashMap<>();
        tasksByPriority = new HashMap<>();
        auditLog = new ArrayList<>();
        currId = 1;
    }

    public static TaskService getInstance() {
        return instance;
    }

    public Task addTask(String title, Priority priority) {
        String id = String.format("T%03d", currId++);
        Task task = new Task(id,title,priority);
        if (tasks.containsKey(id)) throw new TaskNotFoundException("Task-ul cu id-ul " + id + "există deja");
        tasks.put(id, task);
        tasksByPriority.computeIfAbsent(priority, k -> new ArrayList<>()).add(task);
        auditLog.add(String.format("[ADD] %s: '%s' (%s)", id, title, priority));
        System.out.println("Adăugat: Task{id = " + id + ", title = " + title + ", priority = " + priority + ", status = " + task.getStatus() + "}");
        return task;
    }

    public void assignTask(String taskId, String assignee) {
        if (tasks.containsKey(taskId)) {
            tasks.get(taskId).setAsignee(assignee);
            auditLog.add(String.format("[ASSIGN] %s: %s", taskId, assignee));
            System.out.println(taskId + " -> " + assignee);
        }
        else throw new TaskNotFoundException("Nu s-a găsit id-ul cerut la asignarea Task-ului");
    }

    public void changeStatus(String taskId, Status newStatus) {
        if (!tasks.containsKey(taskId)) throw new TaskNotFoundException("Nu s-a găsit id-ul cerut la schimbarea statusului");
        Status statusVechi = tasks.get(taskId).getStatus();
        if (!statusVechi.canTransitionTo(newStatus)) throw new InvalidTransitionException(statusVechi, newStatus);
        tasks.get(taskId).setStatus(newStatus);
        auditLog.add(String.format("[STATUS] %s: %s -> %s", taskId, statusVechi, newStatus));
        System.out.println(taskId + ": " + statusVechi + " -> " + newStatus);
    }

    public List<Task> getTasksByPriority(Priority priority) {
        return tasksByPriority.getOrDefault(priority, new ArrayList<>());
    }

    public Map<Status, Long> getStatusSummary() {
        Map<Status, Long> statusSummary = new HashMap<>();
        tasks.values().forEach(task -> statusSummary.put(task.getStatus(), statusSummary.getOrDefault(task.getStatus(), 0L) + 1));
        return statusSummary;
    }

    public List<Task> getUnassignedTasks() {
        return tasks.values().stream().filter(task -> task.getAsignee() == null).toList();
    }

    public void printAuditLog() {
        auditLog.forEach(System.out::println);
    }

    public double getTotalUrgencyScore(int baseDays) {
        double sum = 0;
        for (Task task : tasks.values()) {
            if (task.getStatus() != Status.DONE && task.getStatus() != Status.CANCELLED)
                sum += task.getPriority().calculateScore(baseDays);
        }
        return sum;
    }

    Map<String, Task> tasks;
    Map<Priority, List<Task>> tasksByPriority;
    List<String> auditLog;
    int currId;
}