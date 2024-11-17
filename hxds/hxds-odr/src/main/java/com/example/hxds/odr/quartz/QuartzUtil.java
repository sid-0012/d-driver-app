package com.example.hxds.odr.quartz;


import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
@Slf4j
public class QuartzUtil {

    @Resource
    private Scheduler scheduler;

    public void addJob(JobDetail jobDetail, String jobName, String jobGroupName, Date start) {
        try {
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity(jobName, jobGroupName)
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withMisfireHandlingInstructionFireNow())
                    .startAt(start).build();
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("定时器添加失败", e);
        }
    }

    public boolean checkExists(String jobName, String jobGroupName) {
        TriggerKey key = new TriggerKey(jobName, jobGroupName);
        try {
            boolean bool = scheduler.checkExists(key);
            return bool;
        } catch (Exception e) {
            log.error("定时器查询失败", e);
            return false;
        }
    }

    public void deleteJob(String jobName, String jobGroupName) {
        TriggerKey key = new TriggerKey(jobName, jobGroupName);
        try {
            scheduler.resumeTrigger(key);
            scheduler.unscheduleJob(key);
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));
            log.debug("成功删除" + jobName + "定时器");
        } catch (SchedulerException e) {
            log.error("定时器删除失败", e);
        }
    }

}
