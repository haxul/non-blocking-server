import java.io.IOException;
import java.net.Socket;

public class NastyChump {
    public static void main(String[] args) throws IOException, InterruptedException {
        var sockets = new Socket[3000];
        for (int i = 0; i < sockets.length; i++) {
            sockets[i] = new Socket("localhost", 8080);

        }

        while (true) {

        }
    }
}
