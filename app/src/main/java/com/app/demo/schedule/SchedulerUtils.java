package com.app.demo.schedule;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

/**
 * 定时执行调度工具
 */
public class SchedulerUtils {

    private static SchedulerUtils schedulerUtils;

    private Context mContext;

    private JobScheduler jobScheduler;


    private SchedulerUtils(Context context){
        this.mContext = context;
    }
    //单例
    public static SchedulerUtils with(Context context){
        if(schedulerUtils == null){
            synchronized (SchedulerUtils.class){
                if(schedulerUtils == null){
                    schedulerUtils = new SchedulerUtils(context);
                }
            }
        }

        return  schedulerUtils;
    }

    private static final long EXCUTE_PERIODIC = 1*1000L;//每隔1s发送一次


    private  int JOB_ID = 100;//任务执行id



    /**
     * 开始调度
     */
    public void scheduler() {

        try {

            //创建执行调度器
            jobScheduler = (JobScheduler) mContext.getSystemService(JOB_SCHEDULER_SERVICE);
            JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, new ComponentName(mContext, ScheduleExecuteService.class));//指定哪个JobService执行操作
            //兼容android7.0以后的官方对执行时间间隔小于15分钟的限制
            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.N){
                builder.setMinimumLatency(EXCUTE_PERIODIC);//设置最小延迟时间
            }else{
                builder.setPeriodic(EXCUTE_PERIODIC);//设置执行周期
            }
            builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY); // 有网络的情况下执行
            builder.setPersisted(false);//设备重启后执行
            //创建调度任务，等待系统执行
            jobScheduler.schedule(builder.build());

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //停止定时调度执行
    public void stop(){
        if(jobScheduler!=null){
            jobScheduler.cancel(JOB_ID);
            jobScheduler = null;
        }
    }
    //取消所有任务
    public void stopAll(){
        if(jobScheduler!=null){
            jobScheduler.cancelAll();
            jobScheduler = null;
        }
    }
}
