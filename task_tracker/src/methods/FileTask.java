package methods;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileTask {

    protected static CreatTask createTask;
    protected static HistoryManager historyManager;


    public FileTask(CreatTask createTask) {
        this.createTask = createTask;
        this.historyManager = createTask.getHistoryManager();
    }

    public static void writeFile() throws IOException {

        Writer fileWriter = new FileWriter("tasks.csv", false);

        fileWriter.write("id,type,name,status,description,epic" + "\n");

        for (Task task : createTask.getAllTasks()) {
            fileWriter.write(writeTask(task) + "\n");
        }

        fileWriter.write("\n");

        fileWriter.write(historyToString(historyManager));

        fileWriter.close();
    }


    static String writeTask(Task task){

        return String.format("%d, %s, %s, %s, %s, %s",
                task.taskID, task.taskType, task.taskName, task.taskStatus, task.taskDescription, task.taskType);
    }

    static void readTask(){
        // метод создания задачи из строки Task fromString(String value)
    }


    static String historyToString(HistoryManager historyManager){
        //!!!!!!!
        String idTask = "";
        return idTask;
    }


    static List historyFromString(String value){
        //чтение истории из файла
        return new ArrayList();
    }





}
