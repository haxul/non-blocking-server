package server;

import handler.PrintingHandler;
import handler.ThreadedHandler;
import handler.TransformHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedBlockingServer {

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(8080);
        var transformHandler = new TransformHandler();
        var threadedHandler = new ThreadedHandler<>(transformHandler);

        while (true) {
            Socket socket = ss.accept();
            threadedHandler.handle(socket);
        }
    }
}
