package com.self.datatransferclient.schedule;

import com.self.datatransferclient.schedule.execute.CommonUploadJobLogic;
import com.self.datatransferclient.schedule.service.QuartzJobService;
import com.self.datatransferclient.schedule.task.TaskBuilder;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 通用上传文件定时任务
 */
@Component
@Scope("singleton")
public class CommonUploadSchedule {

    @Resource
    private QuartzJobService quartzJobService;

    //初始化JobKey
    private final JobKey jobKey = JobKey.jobKey("CommonUploadJob", "CommonGroup");

    /**
     * 启动任务
     * @return 启动结果提示
     * @throws SchedulerException SchedulerException
     */
    public String startTransferTestJob() throws SchedulerException {
        TaskBuilder task = TaskBuilder.builder()
                .jobKey(jobKey)
                .cronExpression("0 */1 * * * ?")
                .jobClass(CommonUploadJobLogic.class)
                .description("通用上传文件任务")
                .build();

        quartzJobService.scheduleJob(task);

        return "start common upload job success";
    }

    /**
     * 暂停任务
     * @return 暂停结果描述
     * @throws SchedulerException SchedulerException
     */
    public String pauseTransferTestJob() throws SchedulerException {
        quartzJobService.pauseJob(jobKey);

        return "pause common upload job success";
    }

    /**
     * 恢复任务
     * @return 恢复结果描述
     * @throws SchedulerException SchedulerException
     */
    public String resumeTransferTestJob() throws SchedulerException {
        quartzJobService.resumeJob(jobKey);

        return "resume common upload job success";
    }

    /**
     * 删除任务
     * @return 删除结果描述
     * @throws SchedulerException SchedulerException
     */
    public String deleteTransferTestJob() throws SchedulerException {
        quartzJobService.deleteJob(jobKey);

        return "delete common upload job success";
    }

    /**
     * 修改任务cron表达式
     * @return 修改结果描述
     */
    public String modifyTransferTestJobCron(){
        TaskBuilder modifyCronTask = TaskBuilder.builder()
                .jobKey(jobKey)
                .cronExpression("0 */2 * * * ?")
                .jobClass(CommonUploadJobLogic.class)
                .description("通用上传文件任务")
                .build();

        if(quartzJobService.modifyJobCron(modifyCronTask)){
            return "modify common upload job success";
        }else {
            return "modify common upload job fail";
        }
    }

}
