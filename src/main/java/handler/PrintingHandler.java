package handler;

public class PrintingHandler<S> implements Handler<S>{

    private final Handler<S> handler;

    public PrintingHandler(Handler<S> handler) {
        this.handler = handler;
    }

    @Override
    public void handle(S s) {
        System.out.println("Connected to " + s);
        try {
            handler.handle(s);
        } catch (Exception e) {
            // nevermind
        } finally {
            System.out.println("Disconnected from " + s);
        }
    }

}
