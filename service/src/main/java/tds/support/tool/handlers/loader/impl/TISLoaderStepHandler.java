package tds.support.tool.handlers.loader.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import tds.support.job.Error;
import tds.support.job.*;
import tds.support.tool.handlers.loader.TestPackageHandler;

@Component
public class TISLoaderStepHandler implements TestPackageHandler {
    private static final Logger log = LoggerFactory.getLogger(TISLoaderStepHandler.class);

    @Override
    public void handle(final Job job, final Step step) {
        try {
            //TODO: Call the TIS Load API and update the step with results
            step.setStatus(Status.SUCCESS);
        } catch (Exception e) {
            Step.handleException(job, step, e);
        }

        step.setComplete(true);
    }
}
