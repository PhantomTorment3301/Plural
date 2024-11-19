import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Objects;

public class Main {
    static Runtime rt = Runtime.getRuntime();
    public static void main(String[] args) throws AWTException, IOException {


        go();
    }

    public static void go() throws AWTException {
        while (true) {


            Robot r = new Robot();
            try (ServerSocket serverSocket = new ServerSocket(8080)) {
                System.out.println("Сервер запущен и ожидает подключения...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключен: " + clientSocket.getInetAddress());
                while (true) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    String message = in.readLine();
                    System.out.println("position:" + message);


                    // Разделяем строку по символу ':'
                    String[] parts = message.split(":");

                    // Преобразуем части в целые числа
                    String z = parts[0];
                    if (Objects.equals(z, "mouse")) {
                        int x = Integer.parseInt(parts[1]);
                        int y = Integer.parseInt(parts[2]);
                        r.mouseMove(x, y);
                    } else if (Objects.equals(z, "cmd")) {
                        String x = parts[1];
                        rt.exec(new String[]{"cmd.exe", "/k", x});
                    }


                }
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Клиент отключен");
            }
        }
    }
}
