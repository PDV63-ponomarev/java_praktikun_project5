package methods;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.nio.file.Files;
import java.nio.charset.StandardCharsets;
import methods.*;

public class FileTask {

    public static CreatTask createTask;
    protected static HistoryManager historyManager;


    public FileTask(CreatTask createTask) {
        this.createTask = createTask;
        this.historyManager = createTask.getHistoryManager();
    }

    public static void writeFile() throws IOException {

        try (Writer fileWriter = new FileWriter("tasks.csv", false)) {

            fileWriter.write("id,type,name,status,description,epic" + "\n");

            for (Task task : createTask.getAllTasks()) {
                fileWriter.write(writeTask(task) + "\n");
            }

            fileWriter.write("\n");

            fileWriter.write(historyToString(historyManager));
        }
    }

//    public static FileBackedTasksManager loadFromFile(File file) {
//        FileBackedTasksManager manager = new FileBackedTasksManager(file);
//
//        if (!file.exists() || file.length() == 0) {
//            return manager; // файла нет — стартуем с пустым менеджером
//        }
//
//        FileTask.createTask = manager.creatTask; // см. примечание ниже
//
//        List<String> lines;
//        try {
//            lines = Files.readAllLines(file.toPath(), StandardCharsets.UTF_8);
//        } catch (IOException e) {
//            throw new ManagerSaveException("Ошибка загрузки из файла", e);
//        }
//
//        int cursor = 1;  // строка 0 — шапка id,type,name,...
//        int maxId = 0;
//
//        // блок задач: всё до первой пустой строки
//        while (cursor < lines.size() && !lines.get(cursor).isBlank()) {
//            Task task = FileTask.fromString(lines.get(cursor));
//
//            if (task instanceof TaskEpic) {
//                manager.creatTask.getEpics().put(task.getTaskID(), (TaskEpic) task);
//            } else if (task instanceof TaskSubtask) {
//                TaskSubtask sub = (TaskSubtask) task;
//                manager.creatTask.getSubtasks().put(sub.getTaskID(), sub);
//                // эпик уже восстановлен (в файле эпики идут раньше) — восстанавливаем связь
//                TaskEpic epic = manager.creatTask.getEpics().get(sub.getEpicId());
//                if (epic != null) {
//                    epic.addSubtaskId(sub.getTaskID());
//                }
//            } else {
//                manager.creatTask.getTasks().put(task.getTaskID(), task);
//            }
//
//            maxId = Math.max(maxId, task.getTaskID());
//            cursor++;
//        }
//
//        manager.creatTask.setNextId(maxId + 1); // чтобы новые ID не пересеклись со старыми
//
//        // блок истории: строка после пустой
//        cursor++;
//        if (cursor < lines.size()) {
//            for (int id : FileTask.historyFromString(lines.get(cursor))) {
//                Task task = findTaskById(manager.creatTask, id);
//                if (task != null) {
//                    manager.creatTask.getHistoryManager().add(task);
//                }
//            }
//        }
//
//        return manager;
//    }

//    private static Task findTaskById(CreatTask ct, int id) {
//        if (ct.getTasks().containsKey(id))    return ct.getTasks().get(id);
//        if (ct.getEpics().containsKey(id))    return ct.getEpics().get(id);
//        return ct.getSubtasks().get(id);
//    }

    static String writeTask(Task task){
        String epicId = "";
        if (task instanceof TaskSubtask) {
            epicId = String.valueOf(((TaskSubtask) task).getEpicId());
        }
        return String.format("%d,%s,%s,%s,%s,%s",
                task.taskID, task.taskType, task.taskName, task.taskStatus, task.taskDescription, epicId);
    }

    public static Task fromString(String value) {
        String[] f = value.split(",", -1); // -1: не выбрасывать пустые поля в конце

        int id = Integer.parseInt(f[0].trim());
//        String typeString = f[1].trim();
        TaskType type = TaskType.valueOf(f[1].trim());
        String name = f[2].trim();
        EnumStatus status = EnumStatus.valueOf(f[3].trim());
        String description = f[4].trim();
        String epicField   = f[5].trim();

        switch (type) {
            case TaskType.TASK:
                return new Task(name, description, status, id, type);
            case TaskType.EPIC:
                return new TaskEpic(name, description, status, id, type);
            case TaskType.SUBTASK:
                int epicId = Integer.parseInt(epicField);
                TaskEpic epic = createTask.getEpics().get(epicId);
                String epicName = (epic != null) ? epic.getTaskName() : "";
                return new TaskSubtask(name, description, status, id, epicId, epicName, type);
            default:
                throw new IllegalArgumentException("Неизвестный тип задачи: " + type);
        }
    }


    static String historyToString(HistoryManager historyManager){

        StringBuilder sb = new StringBuilder();

        List<Task> history = historyManager.getHistory();

        for (int i = 0; i < history.size(); i++){
            if (i > 0) {
                sb.append(",");
            }
            sb.append(history.get(i).getTaskID());
        }
        return sb.toString();
    }


    public static List<Integer> historyFromString(String value) {
        List<Integer> ids = new ArrayList<>();
        if (value == null || value.isBlank()) {
            return ids;
        }
        for (String s : value.split(",")) {
            ids.add(Integer.parseInt(s.trim()));
        }
        return ids;
    }





}
