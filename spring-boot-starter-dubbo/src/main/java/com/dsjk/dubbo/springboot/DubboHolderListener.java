package com.dsjk.dubbo.springboot;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author fengcheng
 * @version 2017/7/26
 */
@SuppressWarnings("rawtypes")
public class DubboHolderListener implements ApplicationListener {
    private static Thread holdThread;
    private static Boolean running = Boolean.FALSE;

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationPreparedEvent) {
            if (running == Boolean.FALSE)
                running = Boolean.TRUE;
            if (holdThread == null) {
                holdThread = new Thread(() -> {
                    System.out.println(Thread.currentThread().getName());
                    while (running && !Thread.currentThread().isInterrupted()) {
                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException ignored) {
                        }
                    }
                }, "Dubbo-Holder");
                holdThread.setDaemon(false);
                holdThread.start();
            }
        }
        if (event instanceof ContextClosedEvent) {
            running = Boolean.FALSE;
            if (null != holdThread) {
                holdThread.interrupt();
                holdThread = null;
            }
        }
    }

    public static void stopApplicationContext(Boolean stop) {
        running = stop;
        if (null != holdThread) {
            holdThread.interrupt();
            holdThread = null;
        }
    }
}
