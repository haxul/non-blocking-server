package handler;

import java.io.IOException;

public class ThreadedHandler<S> extends PrintingHandler<S>{

    public ThreadedHandler(Handler<S> handler) {
        super(handler);
    }

    @Override
    public void handle(S s) {
        new Thread(() -> {
            try {
                super.handle(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
