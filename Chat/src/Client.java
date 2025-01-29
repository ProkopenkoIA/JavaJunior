import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String server = "localhost";//Наш сервер будет расположен на локальной машине
    private static final int port = 999;//Будет "слушать" 999 порт

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(server, port);
            System.out.println("Соединение установлено, добро пожаловать!");

            // Исходящие и входящие потоки
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // Запусти поток входящих сообщений
            new Thread(() -> {
                try {
                    String serverResponse;
                    while ((serverResponse = in.readLine()) != null) {
                        System.out.println(serverResponse);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // Передадим сообщение на сервер
            Scanner scanner = new Scanner(System.in);
            String userInput;
            while (true) {
                userInput = scanner.nextLine();
                out.println(userInput);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
