import java.io.*;
import java.net.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.Scanner;

public class Server {
    private static final int port = 999; //-- Порт на котором будет запущен наш сервер
    private static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("Сервер запущен и ждет подключений...");

            // Поток для отправки сообщений от "сервера"
            new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String serverMessage = scanner.nextLine();
                    broadcast("[ADMIN]: " + serverMessage, null);
                }
            }).start();

            // Отслеживаем входящие соединения
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Новый пользователь вошел в чат: " + clientSocket);

                // Для вновь подключенного клиента потребуется новый поток
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Метод отправляющий сообщения всем пользователям чата
    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    // Класс клиента
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private PrintWriter out;
        private BufferedReader in;
        private String username;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;

            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            try {
                // Запрос имени пользователя для вновь подключаемго клиента
                out.println("Введите ваше имя:");
                username = in.readLine();
                System.out.println("Пользователь " + username + " присоединился.");
                out.println("Добро пожаловать, " + username + "!");
                out.println("Введите ваше сообщение");

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println("[" + username + "]: " + inputLine);
                    // отправим сообщение от имени клиента
                    broadcast("[" + username + "]: " + inputLine, this);
                }

                // Удалим клиента
                clients.remove(this);
                System.out.println("Пользователь " + username + " покинул чат.");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendMessage(String message) {
            out.println(message);
        }
    }
}