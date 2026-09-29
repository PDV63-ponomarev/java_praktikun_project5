import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class FileBackedTasksManager extends InMemoryTaskManager {

    public FileBackedTasksManager() {
        super();
    }

    @Override
    public void showAllTasks() {
        super.showAllTasks();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
    }

    @Override
    public void showTaskFromID() {
        super.showTaskFromID();
        save();
    }

    @Override
    public void creatingNewTask() {
        super.creatingNewTask();
        save();
    }

    @Override
    public void managerUpdateTask() {
        super.managerUpdateTask();
    }

    @Override
    public void deleteOneTask() {
        super.deleteOneTask();
    }

    @Override
    public void showHistory() {
        super.showHistory();
    }

    @Override
    public void exit() {
        super.exit();
    }

    @Override
    public void start() {
        super.start();
    }

    public void save(){

    }





}
