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

    private void editJob(ScheduleJob scheduleJob) {

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

    @PostConstruct
    public void init() {
        try{
            System.out.println("Стъпка 4 - старт");
           scheduler.start();
        } catch (SchedulerException e) {
            System.out.println("Стъпка 4.1 - старт грешка");
        }
    }

    @PreDestroy
    public void preDestroy() {
        try {
            System.out.println("Стъпка 5 - стоп");
            scheduler.shutdown();
        } catch (SchedulerException e) {
            System.out.println("Стъпка 5.1 - стоп грешка");
        }
    }
}
