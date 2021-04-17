package com.eltosheva.sporthouse.jobs;

import com.eltosheva.sporthouse.repositories.ShoppingCartRepository;
import lombok.AllArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class QuartzJobFactory implements Job {

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public void execute(JobExecutionContext context) {
        shoppingCartRepository.deleteAll();
    }
}