package com.github.dreamroute.tracer.starter;

import com.alibaba.ttl.TransmittableThreadLocal;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.util.concurrent.TimeUnit.SECONDS;

class TtlTest {

    private static final ThreadLocal<String> TL = new TransmittableThreadLocal<>();
    private static final ExecutorService pool = Executors.newFixedThreadPool(10);

    @Test
    void mm() throws Exception {
        TL.set("w.dehai");
        String name = TL.get();
        System.err.println(name);

        pool.submit(() -> {
            System.err.println(TL.get());
        });

        SECONDS.sleep(2);

    }

}
