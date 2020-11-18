package handler;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.FutureTask;

public class ExecutorHandler<S> extends PrintingHandler<S> {

    private final ExecutorService pool;
    private final Thread.UncaughtExceptionHandler exceptionHandler;
    public ExecutorHandler(Handler<S> handler, ExecutorService pool, Thread.UncaughtExceptionHandler exceptionHandler) {
        super(handler);
        this.pool = pool;
        this.exceptionHandler = exceptionHandler;
    }

    @Override
    public void handle(S s) {
        var task = new FutureTask<>(() -> {
            super.handle(s);
            return null;
        }) {
            @Override
            protected void setException(Throwable t) {
                exceptionHandler.uncaughtException(Thread.currentThread(), new IOException("haha"));
            }
        };
        pool.submit(task);
    }
}
