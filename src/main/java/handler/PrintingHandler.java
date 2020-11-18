package handler;

import java.io.IOException;

public class PrintingHandler<S> implements Handler<S>{

    private final Handler<S> handler;

    public PrintingHandler(Handler<S> handler) {
        this.handler = handler;
    }

    @Override
    public void handle(S s) throws IOException {
        System.out.println("Connected to " + s);
        try {
            handler.handle(s);
        } finally {
            System.out.println("Disconnected from " + s);
        }
    }

}
