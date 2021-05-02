package com.everest.engineering;

import com.everest.engineering.services.ProcessInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class EverestEngineeringApp {

    @Autowired
    private ProcessInput processInput;

    private static Logger LOG = LoggerFactory.getLogger(EverestEngineeringApp.class);

    public static void main( String[] args ) {
        ApplicationContext applicationContext = SpringApplication.run(EverestEngineeringApp.class , args);
        ProcessInput service = applicationContext.getBean(ProcessInput.class);
        service.processInput();
    }
}