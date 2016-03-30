package server_client_sockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5959);
//        List<FutureTask> threads = new ArrayList<FutureTask>();
        while (true) {
            ClientHandler clientHandler = new ClientHandler(serverSocket.accept(), 5959);
//            FutureTask clientHandlerThread = new FutureTask(clientHandler);
//            threads.add(clientHandlerThread);
            ExecutorService clientHandlerExecutor = Executors.newFixedThreadPool(3);
            clientHandlerExecutor.execute(new FutureTask(clientHandler));
            clientHandlerExecutor.execute(new FutureTask(clientHandler));
            clientHandlerExecutor.execute(new FutureTask(clientHandler));
//            clientHandler.start();
        }
    }
}
