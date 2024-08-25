package io.github.mmhelloworld.helloworld;

import io.github.mmhelloworld.helloworld.EmployeeRepository;
import io.github.mmhelloworld.helloworld.EmployeeService;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class InitDatabase implements ApplicationEventListener<StartupEvent>
{
    @Inject
    EmployeeRepository employeeRepository;

    @Override
    public void onApplicationEvent(StartupEvent startupEvent)
    {
        System.out.println("=====initializing db======");
        EmployeeService.initDatabase(employeeRepository);
    }
}
