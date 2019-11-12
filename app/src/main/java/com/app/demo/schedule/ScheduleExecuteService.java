package com.app.demo.schedule;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Build;

/**
 * 执行消息发送
 * @author xww
 */
public class ScheduleExecuteService extends JobService {


    @Override
    public boolean onStartJob(JobParameters params) {

        start(params);
        return true;
    }

    /**
     * 发送消息
     * @param params
     */
    private void start(JobParameters params) {
        //去后台请求
        startService(new Intent(this, ReflushDataService.class));
        //通知执行结束
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.N){
            SchedulerUtils.with(this).scheduler();
        }
        jobFinished(params,false);
    }

    @Override
    public boolean onStopJob(JobParameters params) {

        return false;
    }
}
