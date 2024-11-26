import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Plural {
    private static final int DEFAULT_PORT = 8080;
    private static Socket socket;
    private static String pathDisk = " ";
    private static Scanner scanner = new Scanner(System.in);
    private static Runtime rt = Runtime.getRuntime();
    public static double version = 0.1;

    public static void main(String[] args) {
        new Plural().run();
    }

    private void run() {
        while (true) {
            try {
                int ip4 = 0;
                int port = DEFAULT_PORT;

                displayInfo("install");
                int answer = scanner.nextInt();
                scanner.nextLine(); // consume the newline

                while (answer != 9) {
                    switch (answer) {
                        case 0:
                            connectToServer(port);
                            break;
                        case 1:
                            scanForActiveIPs(ip4, port);
                            break;
                        case 2:
                            connectUsingIPTable(port);
                            break;
                        case 8:
                            changePort();
                            break;
                        default:
                            System.out.println("Неверный ввод. Попробуйте снова.");
                    }
                    displayInfo("logo");
                    answer = scanner.nextInt();
                    scanner.nextLine(); // consume the newline
                }
                break; // Выход из основного цикла
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Пожалуйста, введите число. Попробуйте снова.");
                scanner.nextLine(); // очистка ввода
            } catch (IOException | InterruptedException e) {
                System.err.println("Ошибка: " + e.getMessage());
            }
        }
    }

    private void connectToServer(int port) {
        while (true) {
            try {
                System.out.println("Введите IP:");
                String ip = scanner.nextLine();
                System.out.println("Попытка подключения к " + ip + ":" + port);
                socket = new Socket(ip, port);
                handleClientCommands();
                break; // Выход из цикла при успешном подключении
            } catch (IOException e) {
                System.err.println("Ошибка подключения: " + e.getMessage());
                System.out.println("Попробуйте ввести IP снова.");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void handleClientCommands() throws IOException, InterruptedException {
        int answer;
        while (true) {
            displayInfo("price2");
            answer = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            if (answer == 0) {
                sendMousePosition();
            } else if (answer == 1) {
                sendCommand();
            } else if (answer == 9) {
                break; // exit the loop
            } else {
                System.out.println("Неверный ввод. Попробуйте снова.");
            }
        }
    }

    private void sendMousePosition() throws IOException {
        String message = "mouse:" + MouseInfo.getPointerInfo().getLocation().x + ":" + MouseInfo.getPointerInfo().getLocation().y;
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(message);
        System.out.println("Отправлено: " + message);
    }

    private void sendCommand() throws IOException {
        System.out.println("Введите cmd команду:");
        String command = scanner.nextLine();
        String message = "cmd:" + command;
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        out.println(message);
        System.out.println("Отправлено: " + message);
    }

    private void scanForActiveIPs(int ip4, int port) {
        while (ip4 <= 255) {
            try {
                String localIp = InetAddress.getLocalHost().getHostAddress();
                String[] addressParts = localIp.split("\\.");
                String serverIp = String.join(".", addressParts[0], addressParts[1], addressParts[2], String.valueOf(ip4));

                System.out.print("Отправка запроса на " + serverIp + ":" + port+">");
                if (InetAddress.getByName(serverIp).isReachable(10)) {
                    System.out.print("\u001B[32m IP активен!");
                    logActiveIP(serverIp);
                } else {
                    System.err.println(serverIp + ":" + port + " не доступен");
                    Thread.sleep(100);
                }
                ip4++;
            } catch (IOException e) {
                System.err.println("Ошибка: " + e.getMessage());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    private void logActiveIP(String serverIp) throws IOException {
        rt.exec(new String[]{"cmd.exe", "/c", "echo " + serverIp + " > " + pathDisk + "\\Plural\\ip-table\\" + serverIp + ".txt"});
        System.out.println("Запись в таблицу >> \u001B[0m " + serverIp);
    }

    private void connectUsingIPTable(int port) {
        File directory = new File(pathDisk + "\\Plural\\ip-table");
        File[] filesList = directory.listFiles();

        if (filesList != null && filesList.length > 0) {
            for (File file : filesList) {
                if (file.isFile()) {
                    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                        String serverIp = br.readLine();
                        System.out.println("Подключение к серверу: " + serverIp);
                        connectToServerUsingIP(serverIp, port);
                    } catch (IOException e) {
                        System.err.println("Ошибка при чтении файла: " + e.getMessage());
                    }
                }
            }
        } else {
            System.out.println("Директория не существует или пуста.");
        }
    }

    private void connectToServerUsingIP(String serverIp, int port) {
        try (Socket socket = new Socket(serverIp, port)) {
            System.out.println("Сервер подключен!");
            while (true) {
                String message = MouseInfo.getPointerInfo().getLocation().x + ":" + MouseInfo.getPointerInfo().getLocation().y;
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
            }
        } catch (IOException e) {
            System.err.println("Ошибка подключения: " + e.getMessage());
        }
    }

    private void changePort() throws InterruptedException {
        System.out.println("Введите новый порт:");
        while (true) {
            try {
                int newPort = scanner.nextInt();
                scanner.nextLine(); // consume the newline
                System.out.println("Порт изменен на " + newPort);
                break; // Выход из цикла при успешном изменении порта
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Пожалуйста, введите число. Попробуйте снова.");
                scanner.nextLine(); // очистка ввода
            }
        }
    }

    private void displayInfo(String inf) throws InterruptedException, IOException {
        File[] paths = File.listRoots();
        int wait = 100;

        switch (inf) {
            case "logo":
                displayLogo(wait);
                break;
            case "price1":
                displayMainMenu(wait);
                break;
            case "price2":
                displayCommandMenu(wait);
                break;
            case "install":
                checkInstallation(paths, wait);
                break;
            default:
                break;
        }
    }

    private void displayLogo(int wait) throws InterruptedException, IOException {
        System.out.print("██████╗░██╗░░░░░██╗░░░██╗██████╗░░█████╗░██╗░░░░░\n");
        Thread.sleep(wait);
        System.out.print("██╔══██╗██║░░░░░██║░░░██║██╔══██╗██╔══██╗██║░░░░░\n");
        Thread.sleep(wait);
        System.out.print("██████╔╝██║░░░░░██║░░░██║██████╔╝███████║██║░░░░░\n");
        Thread.sleep(wait);
        System.out.print("██╔═══╝░██║░░░░░██║░░░██║██╔══██╗██╔══██║██║░░░░░\n");
        Thread.sleep(wait);
        System.out.print("██║░░░░░███████╗╚██████╔╝██║░░██║██║░░██║███████╗\n");
        Thread.sleep(wait);
        System.out.print("╚═╝░░░░░╚══════╝░╚═════╝░╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝\n");
        Thread.sleep(wait);
        System.out.println("root:" + pathDisk + "Plural\\Plural.java");
        Thread.sleep(wait);
        displayMainMenu(100);
    }

    private void displayMainMenu(int wait) throws InterruptedException {
        System.out.println("v" + version);
        Thread.sleep(wait);
        System.out.println("0 - Ручное подключение по IP");
        Thread.sleep(wait);
        System.out.println("1 - Добавить активный IP в таблицу");
        Thread.sleep(wait);
        System.out.println("2 - Подключение по таблице");
        Thread.sleep(wait);
        System.out.println("8 - Изменить порт");
        Thread.sleep(wait);
        System.out.println("9 - Выход");
    }

    private void displayCommandMenu(int wait) throws InterruptedException {
        System.out.println("v" + version);
        Thread.sleep(wait);
        System.out.println("0 - Управление мышью");
        Thread.sleep(wait);
        System.out.println("1 - cmd");
        Thread.sleep(wait);
        System.out.println("9 - Выход");
    }

    private void checkInstallation(File[] paths, int wait) throws InterruptedException, IOException {
        boolean install = false;
        for (File path : paths) {
            Thread.sleep(10);
            File file = new File(path + "\\Plural\\ip-table");
            if (file.exists()) {
                pathDisk = String.valueOf(path);
                System.out.println("Plural найден");
                install = true;
                displayInfo("logo");
                break;
            }
        }

        if (!install) {
            System.err.println("Plural не найден");
            promptForInstallation(paths, wait);
        }
    }

    private void promptForInstallation(File[] paths, int wait) throws InterruptedException, IOException {
        System.out.println("Выберите диск для установки\n");
        for (int i = 0; i < paths.length; i++) {
            System.out.println(i + " - " + paths[i] + " " + FileSystemView.getFileSystemView().getSystemTypeDescription(paths[i]));
        }
        while (true) {
            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline
                if (choice >= 0 && choice < paths.length) {
                    pathDisk = String.valueOf(paths[choice]);
                    createInstallationDirectories(wait);
                    break; // Выход из цикла при успешном выборе
                } else {
                    System.out.println("Неверный выбор. Попробуйте снова.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Ошибка ввода. Пожалуйста, введите число. Попробуйте снова.");
                scanner.nextLine(); // очистка ввода
            }
        }
    }

    private void createInstallationDirectories(int wait) throws InterruptedException, IOException {
        System.out.println("Создание корневой папки");
        Thread.sleep(wait * 10);
        rt.exec(new String[]{"cmd.exe", "/c", "mkdir " + pathDisk + "\\Plural"});
        Thread.sleep(wait * 4);
        System.out.println("Создание таблиц IP");
        Thread.sleep(wait * 10);
        rt.exec(new String[]{"cmd.exe", "/c", "mkdir " + pathDisk + "\\Plural\\ip-table"});
        Thread.sleep(wait * 10);
        System.out.println("Создание журнала подключений");
        rt.exec(new String[]{"cmd.exe", "/c", "mkdir " + pathDisk + "\\Plural\\history"});
        rt.exec(new String[]{"cmd.exe", "/c", "echo > " + pathDisk + "\\Plural\\history\\history.txt"});
        Thread.sleep(wait);
        rt.exec(new String[]{"cmd.exe", "/c", "mkdir " + pathDisk + "\\Plural\\Screenshot"});
        Thread.sleep(wait * 10);
        System.out.println("Установка завершена.");
    }
}
