package org.example;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        try {
            // Create a JobDetail instance
            JobDetail job = JobBuilder.newJob(MyJob.class)
                    .withIdentity("myJob", "group1")
                    .build();

            // Create a Trigger that runs every 10 seconds
            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                            .withIntervalInSeconds(10)
                            .repeatForever())
                    .build();

            // Create Scheduler instance
            Scheduler scheduler = new StdSchedulerFactory().getScheduler();

            // Start the scheduler
            scheduler.start();
            logger.info("Scheduler started");

            // Schedule the job using the job and trigger
            scheduler.scheduleJob(job, trigger);
            logger.info("Job scheduled: " + job.getKey());

        } catch (SchedulerException e) {
            logger.error("SchedulerException occurred", e);
        }
    }
}