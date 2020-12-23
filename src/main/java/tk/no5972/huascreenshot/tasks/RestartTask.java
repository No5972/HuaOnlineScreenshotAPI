package tk.no5972.huascreenshot.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;

// @Component
public class RestartTask {
    // @Scheduled(cron = "0 0 0/3 * * ? *")
    public void restart() throws IOException {
        Runtime.getRuntime().exec("shutdown -s -t 0");
    }
}
