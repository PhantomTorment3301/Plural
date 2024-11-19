import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.Objects;
import java.util.Scanner;


public class Plural {
    static Socket socket;
    static String pathDisk = " ";
    static int cycle = 0;
    static Scanner scanner = new Scanner(System.in);
    static Runtime rt = Runtime.getRuntime();
    public static double version = 0.1;

    public static void main(String[] args) throws IOException, InterruptedException {

        int ip4 = 0;
        int port = 8080;
        boolean serverfound = false;


        info("install");
        int answer = scanner.nextInt();

        while (answer!=9){
        if (answer == 0) {
            clr();

            System.out.println("Введите ip");
            String ip = scanner.nextLine();
            String ip2 = scanner.nextLine();
            clr();
            System.out.println("Попытка подключения к " + ip2 + ":" + port);
            try {
                socket = new Socket(ip2, port);


            info("price2");
            answer = scanner.nextInt();}catch (Exception e){
                System.err.println("ощибка");
            }
            while (answer!= 9){
            if (answer==0){
            while (true) {
                String message = "mouse"+":"+MouseInfo.getPointerInfo().getLocation().x + ":" + MouseInfo.getPointerInfo().getLocation().y;
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
                System.out.println("отправлено: " + message);
            }}
            else if (answer==1) {
                String message = "cmd"+":"+scanner.nextLine();
                clr();
                System.out.println("Введите cmd команду");
                message = "cmd"+":"+scanner.nextLine();
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                out.println(message);
                System.out.println("отправлено: " + message);
                clr();
                info("price2");
            }
                answer = scanner.nextInt();}
        }
        else if (answer == 1) {




        while (ip4<=255) {
            try {


                String ip = InetAddress.getLocalHost().getHostAddress();
                String[] addres = ip.split("\\.");


                int ip1 = Integer.parseInt(addres[0]);
                int ip2 = Integer.parseInt(addres[1]);
                int ip3 = Integer.parseInt(addres[2]);


                String serverIp = ip1 + "." + ip2 + "." + ip3 + "." + ip4;


                clr();

                System.out.println("Отправка запроса на " + serverIp + ":" + port);


                if (InetAddress.getByName(serverIp).isReachable(10)) {

                    System.out.println("ip активен!");
                    Thread.sleep(500);
                    clr();
                    System.out.println("Запись в таблицу...");
                    rt.exec(new String[]{"cmd.exe", "/k", "echo "+serverIp+" > "+pathDisk+"\\Plural\\ip-table\\"+serverIp+".txt"});
                    Thread.sleep(2000);
                    System.out.println("Успешно!");

                    ip4++;




                } else {
                    serverfound = false;
                    ip4++;
                    System.err.println(serverIp + ":" + port + " Не доступен");
                    Thread.sleep(300);
                }
            } catch (IOException e) {
                System.err.println(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }
        else if (answer == 2) {
            File directory = new File(pathDisk+"\\Plural\\ip-table");
            // Получаем список файлов и папок в директории
            File[] filesList = directory.listFiles();
            // Проверяем, что директория существует и не пуста
            if (filesList != null) {
                for (File file : filesList) {
                    // Проверяем, является ли объект файлом
                    if (file.isFile()) {
                        BufferedReader br = new BufferedReader(new FileReader(pathDisk+"\\Plural\\ip-table\\"+file.getName()));
                        String readFile;
                        try {
                            StringBuilder sb = new StringBuilder();
                            String line = br.readLine();

                            while (line != null) {
                                sb.append(line);
                                sb.append(System.lineSeparator());
                                line = br.readLine();
                            }
                            readFile = sb.toString();
                            System.out.println(readFile);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } finally {
                            br.close();
                        }
                        serverfound = true;
                        try {


                        Socket socket = new Socket("26.189.108.126", port);
                        System.out.println("Сервер подключен!");





                        while (serverfound) {
                            String message = MouseInfo.getPointerInfo().getLocation().x + ":" + MouseInfo.getPointerInfo().getLocation().y;
                            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                            out.println(message);
                        }
                        }catch (Exception e){
                            System.err.println(e);
                        }
                        System.out.println(file.getName());
                    }}
            } else {
                System.out.println("Директория не существует или пуста.");
            }
            if (!serverfound) {
                rt.exec(new String[]{"cmd.exe", "/k", "msg %username% Таблица ip пуста! Введите 1 чтобы добавить активные ip в таблицу"});

            }


        }
        else if (answer == 8) {
            clr();
            System.out.println("Введите порт");
            port = scanner.nextInt();
            clr();
            System.out.println("Порт изменен на "+ port);
            Thread.sleep(2000);
            clr();
        }
        info("logo");
        answer = scanner.nextInt();
    }

    }
    public static void info(String inf) throws InterruptedException, IOException {
        File[] paths;
        FileSystemView fsv = FileSystemView.getFileSystemView();

        paths = File.listRoots();
        int wait = 100;
        if (Objects.equals(inf, "logo")) {


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
            System.out.println("root:"+pathDisk+"Plural\\Plural.java");
            Thread.sleep(wait);

            info("price1");

        }
        if (Objects.equals(inf, "price1")) {
            System.out.println("v" + version);
            Thread.sleep(wait);
            System.out.println("0 - Ручное подключение по ip");
            Thread.sleep(wait);
            System.out.println("1 - Добавить активный ip в таблицу");
            Thread.sleep(wait);
            System.out.println("2 - Подключение по таблице");
            Thread.sleep(wait);
            System.out.println("8 - Изменить порт");
            Thread.sleep(wait);
            System.out.println("9 - Выход");


        }
        if (Objects.equals(inf, "price2")) {
            System.out.println("v" + version);
            Thread.sleep(wait);
            System.out.println("0 - Управление мышью");
            Thread.sleep(wait);
            System.out.println("1 - cmd");
            Thread.sleep(wait);
            System.out.println("9 - Выход");}

        if (Objects.equals(inf, "install")) {
            clr();
            System.out.println("Checking.");
            Thread.sleep(wait * 4);
            clr();
            System.out.println("Checking..");
            Thread.sleep(wait * 4);
            clr();
            System.out.println("Checking...");
            Thread.sleep(wait * 4);
            clr();
            boolean install = false;
            for (File path : paths) {
                // prints file and directory paths

                Thread.sleep(10);
                File file = new File(path + "\\Plural\\readme.txt");
                Thread.sleep(10);
                if (file.exists()) {
                    pathDisk = String.valueOf(path);
                    System.out.println("Plural найден");
                    Thread.sleep(1000);
                    install = true;
                    break;
                }

                System.out.println(" ");


            }
            int i = 0;

            if (!install) {
                System.err.println("Plural не найден");
                System.out.println(" ");

                System.out.println("Выберите диск для установки\n");
                for (File path : paths) {
                    System.out.println(i + " - " + path + " " + fsv.getSystemTypeDescription(path));
                    i++;
                }
                int chose = scanner.nextInt();
                i = 0;
                for (File path : paths) {
                    if (i == chose) {
                        pathDisk = String.valueOf(path);
                        clr();
                        System.out.println("Создание корневой папки");
                        Thread.sleep(wait*10);
                        rt.exec(new String[]{"cmd.exe", "/k", "mkdir " + path + "\\Plural"});
                        clr();
                        System.out.println("Открываю "+pathDisk+"\\Plural");
                        Thread.sleep(wait*4);
                        rt.exec(new String[]{"cmd.exe", "/k", "start " + path + "\\Plural"});
                        clr();
                        System.out.println("Создание таблиц ip");
                        Thread.sleep(wait*10);
                        rt.exec(new String[]{"cmd.exe", "/k", "mkdir " + path + "\\Plural\\ip-table"});

                        System.out.println("Создание журнала подключений");
                        Thread.sleep(wait*10);
                        rt.exec(new String[]{"cmd.exe", "/k", "mkdir " + path + "\\Plural\\history"});
                        rt.exec(new String[]{"cmd.exe", "/k", "echo > " + path + "\\Plural\\history\\history.txt"});

                        Thread.sleep(wait);
                        rt.exec(new String[]{"cmd.exe", "/k", "mkdir " + path + "\\Plural\\Screenshot"});
                        rt.exec(new String[]{"cmd.exe", "/k", "copy src/Plural.java " + path + "\\Plural.java "});
                        Thread.sleep(wait*10);

                        rt.exec(new String[]{"cmd.exe", "/k", "copy "+System.getProperty("user.dir")+" " + path + "\\Plural"});
                        Thread.sleep(wait*10);
                        rt.exec(new String[]{"cmd.exe", "/k", "copy "+System.getProperty("user.dir")+"\\src\\Plural.class " + path + "\\Plural\\scr"});
                        rt.exec(new String[]{"cmd.exe", "/k", "msg %username% done"});
                        clr();
                        System.out.println("done");
                        Thread.sleep(wait*2);

                    }
                    System.out.println(i + " - " + path + " " + fsv.getSystemTypeDescription(path));
                    i++;
                }
            }



            clr();
            info("logo");

        }


    }

    public static void clr() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
}

