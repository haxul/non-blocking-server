package server;

import handler.BlockingChanelHandler;
import handler.ExecutorHandler;
import handler.TransformChannelHandler;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executors;

public class BlockingNioServer {
    public static void main(String[] args) throws Exception {

        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(8080));

        var transformHandler = new TransformChannelHandler();
        var blockingChanelHandler = new BlockingChanelHandler(transformHandler);
        var threadedHandler = new ExecutorHandler<>(
                blockingChanelHandler,
                Executors.newCachedThreadPool(),
                (t, e) -> System.out.println("Error is " + e));

        while (true) {
            SocketChannel socket = ss.accept();
            threadedHandler.handle(socket);
        }
    }
}
