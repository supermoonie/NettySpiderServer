package xyz.supermoonie;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author supermoonie
 * @date 2018/12/28
 */
public class WebViewDriverPool {

    public static final Map<String, WebViewDriver> DRIVER_POOL = new ConcurrentHashMap<>();

    public static void start() {
        try {
            new Thread(WebViewDriverPool::run).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void run() {
        while (true) {
            try {
                for (String id : DRIVER_POOL.keySet()) {
                    WebViewDriver webViewDriver = DRIVER_POOL.get(id);
                    if (System.currentTimeMillis() > webViewDriver.getDeadLine()) {
                        webViewDriver.close();
                        DRIVER_POOL.remove(id);
                    }
                    Thread.sleep(10);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
