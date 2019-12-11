package com.self.datatransferserver.schedule;

import com.self.datatransferserver.schedule.execute.TransferTestJobLogic;
import com.self.datatransferserver.schedule.service.QuartzJobService;
import com.self.datatransferserver.schedule.task.TaskBuilder;
import org.quartz.JobKey;
import org.quartz.SchedulerException;

import javax.annotation.Resource;

/**
 * 数据传输测试定时任务
 */
public class TransferTestSchedule {

    @Resource
    private QuartzJobService quartzJobService;

    //初始化JobKey
    private final JobKey jobKey = JobKey.jobKey("TransferTest", "Group1");

    /**
     * 启动任务
     * @return 启动结果提示
     * @throws SchedulerException SchedulerException
     */
    public String startTransferTestJob() throws SchedulerException {
        TaskBuilder task = TaskBuilder.builder()
                .jobKey(jobKey)
                .cronExpression("0 */1 * * * ?")
                .jobClass(TransferTestJobLogic.class)
                .description("数据传输测试任务")
                .build();

        quartzJobService.scheduleJob(task);

        return "start transfer test job success";
    }

    /**
     * 暂停任务
     * @return 暂停结果描述
     * @throws SchedulerException SchedulerException
     */
    public String pauseTransferTestJob() throws SchedulerException {
        quartzJobService.pauseJob(jobKey);

        return "pause transfer test job success";
    }

    /**
     * 恢复任务
     * @return 恢复结果描述
     * @throws SchedulerException SchedulerException
     */
    public String resumeTransferTestJob() throws SchedulerException {
        quartzJobService.resumeJob(jobKey);

        return "resume transfer test job success";
    }

    /**
     * 删除任务
     * @return 删除结果描述
     * @throws SchedulerException SchedulerException
     */
    public String deleteTransferTestJob() throws SchedulerException {
        quartzJobService.deleteJob(jobKey);

        return "delete transfer test job success";
    }

    /**
     * 修改任务cron表达式
     * @return 修改结果描述
     */
    public String modifyTransferTestJobCron(){
        TaskBuilder modifyCronTask = TaskBuilder.builder()
                .jobKey(jobKey)
                .cronExpression("0 */2 * * * ?")
                .jobClass(TransferTestJobLogic.class)
                .description("数据传输测试任务")
                .build();

        if(quartzJobService.modifyJobCron(modifyCronTask)){
            return "modify transfer test job success";
        }else {
            return "modify transfer test job fail";
        }
    }

}
