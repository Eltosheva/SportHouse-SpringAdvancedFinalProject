package com.eltosheva.sporthouse.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class QuartzJobFactory implements Job {
    private static final Logger LOG = LoggerFactory.getLogger(QuartzJobFactory.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Финале");
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        LOG.info("Job execution");
    }
}
