package com.self.datatransferserver.schedule.service;

import com.self.datatransferserver.schedule.task.TaskBuilder;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 定时任务调度器
 */
@Slf4j
@Service
public class QuartzJobService {

    //Quartz核心功能实现类
    private Scheduler scheduler;

    public QuartzJobService(@Autowired SchedulerFactoryBean schedulerFactoryBean){
        scheduler = schedulerFactoryBean.getScheduler();
    }

    /**
     * 创建和启动定时任务
     * @param taskBuilder 定时任务构造器
     */
    public void scheduleJob(TaskBuilder taskBuilder) throws SchedulerException {
        //定时任务的名称和组名
        JobKey jobKey = taskBuilder.getJobKey();

        //定时任务的元数据
        JobDataMap jobDataMap = getJobDataMap(taskBuilder.getJobDataMap());

        //定时任务的描述
        String description = taskBuilder.getDescription();

        //定时任务逻辑实现类
        Class<? extends Job> jobClass = taskBuilder.getJobClass();

        //定时任务cron表达式
        String cronExpression = taskBuilder.getCronExpression();

        JobDetail jobDetail = getJobDetail(jobKey, description, jobDataMap, jobClass);
        Trigger trigger = getTrigger(jobKey, description, jobDataMap, cronExpression);

        scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 暂停定时任务
     * @param jobKey 定时任务名称和组名
     * @throws SchedulerException SchedulerException
     */
    public void pauseJob(JobKey jobKey) throws SchedulerException {
        scheduler.pauseJob(jobKey);
    }

    /**
     * 恢复定时任务
     * @param jobKey 定时任务名称和组名
     * @throws SchedulerException SchedulerException
     */
    public void resumeJob(JobKey jobKey) throws SchedulerException {
        scheduler.resumeJob(jobKey);
    }

    /**
     * 删除定时任务
     * @param jobKey 定时任务名称和组名
     * @throws SchedulerException SchedulerException
     */
    public void deleteJob(JobKey jobKey) throws SchedulerException {
        scheduler.deleteJob(jobKey);
    }

    /**
     * 修改定时任务cron表达式
     * @param taskBuilder 定时任务构造器
     * @return 修改结果
     */
    public boolean modifyJobCron(TaskBuilder taskBuilder){
        String cronExpression = taskBuilder.getCronExpression();

        //校验cron表达式
        if(!CronExpression.isValidExpression(cronExpression)){
            return false;
        }

        JobKey jobKey = taskBuilder.getJobKey();
        TriggerKey triggerKey = new TriggerKey(jobKey.getName(), jobKey.getGroup());

        try {
            CronTrigger cronTrigger = (CronTrigger)scheduler.getTrigger(triggerKey);
            JobDataMap jobDataMap = getJobDataMap(taskBuilder.getJobDataMap());

            //当cron发生了变化，按新的cron触发，重新启动定时任务
            if(cronTrigger.getCronExpression().equalsIgnoreCase(cronExpression)){
                CronTrigger trigger = TriggerBuilder.newTrigger()
                        .withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                        .usingJobData(jobDataMap)
                        .build();

                scheduler.rescheduleJob(triggerKey, trigger);
            }

        }catch (SchedulerException e){
            log.error("修改cron表达式失败", e);
            return false;
        }

        return true;
    }

    /**
     * 获取定时任务的定义
     * @param jobKey 定时任务名称和组名
     * @param description 定时任务描述
     * @param jobDataMap 定时任务元数据
     * @param jobClass 定时任务逻辑实现类
     * @return 定时任务的定义
     */
    public JobDetail getJobDetail(JobKey jobKey, String description, JobDataMap jobDataMap, Class<? extends Job> jobClass){
        return JobBuilder.newJob(jobClass)
                .withIdentity(jobKey)
                .withDescription(description)
                .setJobData(jobDataMap)
                .usingJobData(jobDataMap)
                .requestRecovery()
                .storeDurably()
                .build();
    }

    /**
     * 获取触发器
     * @param jobKey 定时任务的名称和组名
     * @param description 定时任务的描述
     * @param jobDataMap 定时任务的元数据
     * @param cronExpression 定时任务执行的cron表达式
     * @return 触发器
     */
    public Trigger getTrigger(JobKey jobKey, String description, JobDataMap jobDataMap, String cronExpression){
        return TriggerBuilder.newTrigger()
                .withIdentity(jobKey.getName(), jobKey.getGroup())
                .withDescription(description)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .usingJobData(jobDataMap)
                .build();
    }

    private JobDataMap getJobDataMap(Map<?, ?> map){
        return map == null ? new JobDataMap() : new JobDataMap(map);
    }

}
