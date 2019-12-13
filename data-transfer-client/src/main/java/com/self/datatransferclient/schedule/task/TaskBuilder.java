package com.self.datatransferclient.schedule.task;

import lombok.Builder;
import lombok.Data;
import org.quartz.Job;
import org.quartz.JobKey;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * 定时任务构造器
 */
@Data
@Builder
public class TaskBuilder {

    /**
     * 定时任务的名字和分组名
     */
    @NotNull(message = "定时任务的名字和组名不能为空")
    private JobKey jobKey;

    /**
     * 定时任务描述
     */
    private String description;

    /**
     * cron表达式
     */
    @NotNull(message = "定时任务执行cron不能为空")
    private String cronExpression;

    /**
     * 定时任务的元数据
     */
    private Map<?, ?> jobDataMap;

    /**
     * 定时任务逻辑执行类
     */
    @NotNull(message = "定时任务具体执行逻辑类不能为空")
    private Class<? extends Job> jobClass;

}
