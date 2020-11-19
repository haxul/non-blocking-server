package server;

import handler.AcceptHandler;
import handler.ReadHandler;
import handler.TransformChannelHandler;
import handler.WriteHandler;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

public class SingleThreadedSelectNioServer {
    public static void main(String[] args) throws Exception {

        ServerSocketChannel ss = ServerSocketChannel.open();
        ss.bind(new InetSocketAddress(8080));
        ss.configureBlocking(false);

        Selector selector = Selector.open();
        ss.register(selector, SelectionKey.OP_ACCEPT);

        Map<SocketChannel, Queue<ByteBuffer>> pendingData = new HashMap<>();
        var acceptHandler = new AcceptHandler(pendingData);
        var readHandler = new ReadHandler(pendingData);
        var writeHandler = new WriteHandler(pendingData);

        while (true) {
            selector.select();
            Set<SelectionKey> keys = selector.selectedKeys();
            for (var it = keys.iterator(); it.hasNext(); ) {
                SelectionKey key = it.next();
                it.remove();
                if (key.isValid()) {
                    if (key.isAcceptable()) acceptHandler.handle(key);
                    else if (key.isReadable()) readHandler.handle(key);
                    else if (key.isWritable()) writeHandler.handle(key);
                }
            }
        }
    }
}
