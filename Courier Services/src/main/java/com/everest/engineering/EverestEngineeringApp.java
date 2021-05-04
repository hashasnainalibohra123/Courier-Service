package com.everest.engineering;

import com.everest.engineering.services.ProcessInput;
import com.everest.engineering.services.impl.ProcessInputImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class EverestEngineeringApp {

    private ProcessInput processInput;

    private static Logger LOG = LogManager.getLogger(EverestEngineeringApp.class);

    public static void main( String[] args ) {
        LOG.debug("Demon Thread Started");
        ProcessInput service = new ProcessInputImpl();
        service.processInput();
    }
}