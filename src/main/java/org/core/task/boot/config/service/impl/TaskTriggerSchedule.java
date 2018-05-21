package org.core.task.boot.config.service.impl;


import lombok.val;
import org.core.task.boot.config.property.TaskPropertyConfig;
import org.core.task.boot.config.schedule.base.IJob;
import org.core.task.boot.config.service.ITaskTriggerSchedule;
import org.core.task.boot.config.tuple.JobIdentity;
import org.core.task.boot.config.tuple.JobProperty;
import org.core.task.boot.config.tuple.JobYMLProperty;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskTriggerSchedule implements ITaskTriggerSchedule {

    @Autowired
    private Scheduler scheduler;

    @Autowired
    TaskPropertyConfig taskPropertyConfig;

    @Autowired
    IJob[] scheduledJobs;

    public void execute(){

        if(scheduledJobs==null||scheduledJobs.length==0) return;
        if(scheduledJobs==null||scheduledJobs.length==0)return;
        val scheduledJobsMap = Arrays.stream(scheduledJobs).collect(
                Collectors.groupingBy(
                        s->s.getClass().getName()
                )
        );

        taskPropertyConfig.getJobConfigList()
                                .stream()
                                .filter(s->s.isEnable())
                                .forEach(
                                        jobConfig->{
                                            val configClazz = scheduledJobsMap.get(jobConfig.getClazz());
                                            if (configClazz==null||configClazz.size()==0||configClazz.size()>1) return;
                                            Class clazz = configClazz.get(0).getClass();

                                            JobIdentity jobIdentity =
                                                    JobIdentity
                                                            .builder()
                                                            .group(clazz.getPackage().getName())
                                                            .name(clazz.getSimpleName())
                                                            .build();
                                            if (jobConfig.getJobDetail()==null){
                                                jobConfig.setJobDetail(jobIdentity);
                                            }
                                            if (jobConfig.getJobTrigger()==null){
                                                jobConfig.setJobTrigger(jobIdentity);
                                            }
                                            JobDetail jobDetail = JobBuilder.newJob(clazz)
                                                    .withIdentity(jobConfig.getJobDetail().getName(),jobConfig.getJobDetail().getGroup()).build();
                                            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(jobConfig.getCronTab());

                                            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                                                    .withIdentity(jobConfig.getJobTrigger().getName(),jobConfig.getJobTrigger().getGroup())
                                                    .withSchedule(scheduleBuilder).build();
                                            try {
                                                scheduler.scheduleJob(jobDetail,cronTrigger);
                                            } catch (SchedulerException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                );
    }
}
