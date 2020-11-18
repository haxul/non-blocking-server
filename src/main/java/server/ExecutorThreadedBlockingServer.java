package server;

import handler.ExecutorHandler;
import handler.ThreadedHandler;
import handler.TransformHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;

public class ExecutorThreadedBlockingServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        var transformHandler = new TransformHandler();
        var threadedHandler = new ExecutorHandler<>(
                transformHandler,
                Executors.newCachedThreadPool(),
                (t, e) -> System.out.println("Error is " + e));

        while (true) {
            Socket socket = ss.accept();
            threadedHandler.handle(socket);
        }
    }
}
