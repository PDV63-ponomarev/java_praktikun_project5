package methods;

import java.util.ArrayList;
import java.util.List;

enum EnumStatus{
    NEW,
    IN_PROGRESS,
    DONE
}

public class Task {
    protected  String taskName;
    protected  String taskDescription;
    protected  EnumStatus taskStatus;
    protected  int taskID;

    public Task(String taskName, String taskDescription, EnumStatus status, int taskID) {
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskStatus = status;
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public EnumStatus getTaskStatus() {
        return taskStatus;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskStatus(EnumStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Override
    public String toString() {
        return String.format("Задача {Название: %s; Описание: %s; Статус: %s; ID: %d}",
               taskName,
               taskDescription,
               taskStatus,
               taskID);
    }
}

class TaskEpic extends Task{
    private List<Integer> subtaskIds;

    public TaskEpic(String taskName, String taskDescription, EnumStatus status, int taskID) {
        super(taskName, taskDescription, status, taskID);
        this.subtaskIds = new ArrayList<>();
    }

    public List<Integer> getSubtaskIds() {
        return subtaskIds;
    }

    public void addSubtaskId(int subtaskId) {
        subtaskIds.add(subtaskId);
    }

    public void removeSubtaskId(int subtaskId) {
        subtaskIds.remove(Integer.valueOf(subtaskId));
    }

    public void updateStatus(List<TaskSubtask> subtasks) {
        if (subtaskIds.isEmpty()) {
            this.taskStatus = EnumStatus.NEW;
            return;
        }

        boolean allNew = true;
        boolean allDone = true;

        for (int id : subtaskIds) {
            for (TaskSubtask subtask : subtasks) {
                if (subtask.getTaskID() == id) {
                    if (!subtask.getTaskStatus().equals(EnumStatus.NEW)) {
                        allNew = false;
                    }
                    if (!subtask.getTaskStatus().equals(EnumStatus.DONE)) {
                        allDone = false;
                    }
                    break;
                }
            }
        }

        if (allNew) {
            this.taskStatus = EnumStatus.NEW;
        } else if (allDone) {
            this.taskStatus = EnumStatus.DONE;
        } else {
            this.taskStatus = EnumStatus.IN_PROGRESS;
        }
    }

    @Override
    public String toString() {
        return String.format("Задача {Название: %s; Описание: %s; Статус: %s; ID: %d; Подзадачи: %s}",
                taskName,
                taskDescription,
                taskStatus,
                taskID,
                subtaskIds
        );
    }
}
class TaskSubtask extends Task{
    private int epicId;
    private String epicName;

    public TaskSubtask(String taskName, String taskDescription, EnumStatus status, int taskID, int epicId, String epicName) {
        super(taskName, taskDescription, status, taskID);
        this.epicId = epicId;
        this.epicName = epicName;
    }

    public int getEpicId() {
        return epicId;
    }

    @Override
    public String toString() {
        return String.format("Задача {Название: %s; Описание: %s; Статус: %s; ID: %d; Глобальная задача: %s (%d)}",
                taskName,
                taskDescription,
                taskStatus,
                taskID,
                epicName,
                epicId);
    }
}