package server_client_sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Client implements Callable {
    private Socket socket;
    private int port;

    public Client(Socket socket, int port) {
        this.socket = socket;
        this.port = port;
    }

    public String call() {
        try {
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader readerFromConsole = new BufferedReader(new InputStreamReader(System.in));

            while (true) {
                String message = readerFromConsole.readLine();//считываю сообщение с клавиатуры
                printWriter.println(message);//ушло на сервер от клиента
                printWriter.flush();
                String msgFromServer = bufferedReader.readLine();
                //пробуем считать ответ от сервера(принять сообщение от хендлера)
                System.out.println("Client -> message from handler is: " + msgFromServer);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Thread.currentThread().getName();

    }



    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getLocalHost();
        Socket socket = new Socket(inetAddress, 5959);
        Client client = new Client(socket, 5959);
        ExecutorService clientExecutor = Executors.newFixedThreadPool(3);
        clientExecutor.execute(new FutureTask(client));
        clientExecutor.execute(new FutureTask(client));
        clientExecutor.execute(new FutureTask(client));





//        new Client(socket, 5959).start();
    }
}
