package com.eltosheva.sporthouse.jobs;

import com.eltosheva.sporthouse.jobs.Info.ScheduleJob;
import org.modelmapper.ModelMapper;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class SchedulerService {
    private final Scheduler scheduler;
    private final ModelMapper modelMapper;

    public SchedulerService(Scheduler scheduler, ModelMapper modelMapper) {
        this.scheduler = scheduler;
        this.modelMapper = modelMapper;
    }

    public void addOrEditJob(ScheduleJob scheduleJob) throws Exception {
        if (scheduleJob.getJobId().isEmpty()) {
            addJob(scheduleJob);
        }else {
            editJob(scheduleJob);
        }
    }

    private void editJob(ScheduleJob scheduleJob) throws SchedulerException {
        TriggerKey triggerKey = TriggerKey.triggerKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        CronTrigger cronTrigger = (CronTrigger)scheduler.getTrigger(triggerKey);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCronExpression());
        cronTrigger = cronTrigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(cronScheduleBuilder).build();
        scheduler.rescheduleJob(triggerKey, cronTrigger);
    }

    private void addJob(ScheduleJob scheduleJob) throws Exception {
        scheduleJob.setJobId(UUID.randomUUID().toString());
        final JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(scheduleJob.getJobName(), scheduleJob);

        JobDetail jobDetail = JobBuilder
                .newJob(QuartzJobFactory.class)
                .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
                .setJobData(jobDataMap)
                .build();

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder
                .cronSchedule(scheduleJob.getCronExpression());
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(scheduleJob.getJobName(), scheduleJob.getJobGroup())
                .withSchedule(cronScheduleBuilder)
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    public List<ScheduleJob> getAllJobs() {
        List<ScheduleJob> jobsList = new ArrayList<>();
        try {
            for (String groupName : scheduler.getJobGroupNames()) {
                for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.jobGroupEquals(groupName))) {
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);
                    ScheduleJob scheduleJob =  modelMapper
                            .map(jobDetail.getJobDataMap().get(jobKey.getName()), ScheduleJob.class);
                    jobsList.add(scheduleJob);
                }
            }
        } catch (SchedulerException schedulerException) {
            System.out.println(schedulerException.getMessage());
        }
        return jobsList;
    }

    public void deleteJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.deleteJob(jobKey);
    }

    public void runJob(ScheduleJob scheduleJob) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(scheduleJob.getJobName(), scheduleJob.getJobGroup());
        scheduler.triggerJob(jobKey);
    }

    @PostConstruct
    public void init() {
        try{
           scheduler.start();
        } catch (SchedulerException e) {}
    }

    @PreDestroy
    public void preDestroy() {
        try {
            scheduler.shutdown();
        } catch (SchedulerException e) {}
    }
}
