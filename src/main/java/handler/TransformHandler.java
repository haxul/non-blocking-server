package handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class TransformHandler implements Handler<Socket> {

    @Override
    public void handle(Socket s) throws IOException {
        try (
                s;
                InputStream in = s.getInputStream();
                OutputStream out = s.getOutputStream()
        ) {
            int data;
            while ((data = in.read()) != -1) {
                if (data == '%') throw new IOException("haha");
                int result = Character.isLetter(data) ? data ^ ' ' : data;
                out.write(result);
            }
        }
    }
}
