package com.eltosheva.sporthouse.jobs;

import com.eltosheva.sporthouse.repositories.ShoppingCartRepository;
import com.eltosheva.sporthouse.services.ShoppingCartService;
import lombok.AllArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class QuartzJobFactory implements Job {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartService shoppingCartService;

    @Override
    public void execute(JobExecutionContext context) {
        shoppingCartRepository.findAll()
            .stream()
            .forEach(shoppingCart -> {
                shoppingCartService.removeProductById(shoppingCart.getId());
            });
    }
}