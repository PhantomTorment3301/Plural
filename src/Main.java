import java.awt.*;
import java.io.*;
import java.net.*;

public class Main {
    private static final int PORT = 8080;
    private static final Runtime rt = Runtime.getRuntime();

    public static void main(String[] args) {
        try {
            new Main().startServer();
        } catch (AWTException e) {
            System.err.println("Ошибка инициализации Robot: " + e.getMessage());
        }
    }

    public void startServer() throws AWTException {
        Robot robot = new Robot();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен и ожидает подключения...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Клиент подключен: " + clientSocket.getInetAddress());
                    handleClient(clientSocket, robot);
                } catch (IOException e) {
                    System.err.println("Ошибка при подключении к клиенту: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка запуска сервера: " + e.getMessage());
        }
    }

    private void handleClient(Socket clientSocket, Robot robot) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Получено сообщение: " + message);
                processMessage(message, robot);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при обработке клиента: " + e.getMessage());
        }
    }

    private void processMessage(String message, Robot robot) {
        String[] parts = message.split(":");
        if (parts.length < 2) {
            System.err.println("Некорректное сообщение: " + message);
            return;
        }

        String command = parts[0];
        switch (command) {
            case "mouse":
                moveMouse(parts);
                break;
            case "cmd":
                executeCommand(parts);
                break;
            default:
                System.err.println("Неизвестная команда: " + command);
        }
    }

    private void moveMouse(String[] parts) {
        try {
            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            Robot robot = new Robot();
            robot.mouseMove(x, y);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка преобразования координат: " + e.getMessage());
        } catch (AWTException e) {
            System.err.println("Ошибка инициализации Robot: " + e.getMessage());
        }
    }

    private void executeCommand(String[] parts) {
        if (parts.length < 2) {
            System.err.println("Не указана команда для выполнения");
            return;
        }
        String command = parts[1];
        try {
            rt.exec(new String[]{"cmd.exe", "/k", command});
        } catch (IOException e) {
            System.err.println("Ошибка выполнения команды: " + e.getMessage());
        }
    }
}
