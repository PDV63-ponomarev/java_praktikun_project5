public class Main {
    public static void main(String[] args) {
        // Используем Managers для получения менеджера задач
        TaskManager taskManager = Managers.getDefault();

        // Запускаем менеджер (только если это InMemoryTaskManager)
        if (taskManager instanceof InMemoryTaskManager) {
            InMemoryTaskManager manager = (InMemoryTaskManager) taskManager;
            manager.start();
        } else {
            System.out.println("Ошибка: неизвестный тип менеджера");
        }
    }
}
