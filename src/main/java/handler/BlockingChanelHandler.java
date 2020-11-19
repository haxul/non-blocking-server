package handler;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class BlockingChanelHandler implements Handler<SocketChannel> {

    private final Handler<SocketChannel> other;

    public BlockingChanelHandler(Handler<SocketChannel> other) {
        this.other = other;
    }

    @Override
    public void handle(SocketChannel socketChannel) throws IOException {
        while (socketChannel.isConnected()) {
            other.handle(socketChannel);
        }
    }
}
