package com.self.datatransferclient.schedule.execute;

import com.self.datatransferclient.common.FileUploader;
import com.self.datatransferclient.config.CustomProperties;
import com.self.datatransferclient.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 定时任务执行逻辑类
 */
@Slf4j
@DisallowConcurrentExecution
public class CommonUploadJobLogic implements Job {

    @Autowired
    private CustomProperties customProperties;

    @Autowired
    private FileUploader fileUploader;

    /**
     * 定时任务逻辑实现
     * @param jobExecutionContext jobExecutionContext
     * @throws JobExecutionException JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //初始化文件上传目录
        initUploadDir();

        //获取待上传目录下所有未上传文件
        File[] files = new File(customProperties.getDirRoot() + customProperties.getDirUpload()).listFiles();
        if(files == null || files.length <= 0){
            log.info("待上传目录为空，任务结束");
            return;
        }

        //遍历待上传文件列表
        for (File file : files) {
            try {
                Integer statusCode = fileUploader.uploadFileToServer(customProperties.getReqServerAddr() + "/binFile/receive/" + customProperties.getClientSender() + "/" + file.getName(), "binFile", file);
                if(statusCode == 200){
                    log.info("上传文件：{} 成功", file.getName());

                    //移动文件到上传成功目录
                    Files.move(file.toPath(), Paths.get(customProperties.getDirRoot() + customProperties.getDirUploadSuccess(), file.getName()));
                }else {
                    log.error("上传文件：{} 失败", file.getName());
                }
            }catch (Exception e){
                log.error("上传文件：{} 异常", file.getName());
            }
        }

    }

    /**
     * 初始化文件上传目录
     */
    private void initUploadDir(){
        //临时接收目录
        File tempFile = new File(customProperties.getDirRoot() + customProperties.getDirTemp());
        if(!tempFile.exists()){
            FileUtil.mkdirs(tempFile.toPath());
        }

        //文件待上传目录
        File uploadFile = new File(customProperties.getDirRoot() + customProperties.getDirUpload());
        if(!uploadFile.exists()){
            FileUtil.mkdirs(uploadFile.toPath());
        }

        //文件上传成功目录
        File uploadSuccessFile = new File(customProperties.getDirRoot() + customProperties.getDirUploadSuccess());
        if(!uploadSuccessFile.exists()){
            FileUtil.mkdirs(uploadSuccessFile.toPath());
        }
    }

}
