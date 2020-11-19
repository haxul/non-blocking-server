package server;

import handler.TransformChannelHandler;

import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collection;

public class SingleThreadedPollingNioServer {
    public static void main(String[] args) throws Exception {

        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(8080));
        ss.configureBlocking(false);

        var transformChannelHandler = new TransformChannelHandler();
        Collection<SocketChannel> sockets = new ArrayList<>();
        while (true) {
            SocketChannel socket = ss.accept();
            if (socket != null) {
                sockets.add(socket);
                System.out.println("Connected to " + ss);
                socket.configureBlocking(false);
            }
            for (var s : sockets) {
                if (s.isConnected()) transformChannelHandler.handle(s);
                else sockets.remove(s);
            }
        }
    }
}
