package cn.inkroom.web.quartz.scheduled;

import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/9/11
 * @Time 15:46
 * @Descorption 执行统计相册实际占用空间
 */
@Component
@Lazy(false)
@EnableScheduling
public class AlbumScheduled implements SchedulingConfigurer {
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {

    }
}
