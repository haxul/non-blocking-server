package handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class TransformChannelHandler implements Handler<SocketChannel> {

    @Override
    public void handle(SocketChannel s) throws IOException {
        ByteBuffer buf = ByteBuffer.allocateDirect(80);
        int read = s.read(buf);
        if (read == -1) {
            s.close();
            return;
        }
        if (read > 1) {
            buf.flip();
            for (int i = 0; i < buf.limit(); i++) {
                int result = Character.isLetter(buf.get(i)) ? buf.get(i) ^ ' ' : buf.get(i);
                buf.put(i, (byte) result);
            }
            while (buf.hasRemaining()) {
                s.write(buf);
            }
            buf.compact();
        }
    }
}
