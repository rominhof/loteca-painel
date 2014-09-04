package loteca.job;

import javax.servlet.ServletContextEvent;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public class LotecaListener implements javax.servlet.ServletContextListener {

	// TODO - Externalizar
	private static final String CRON_LOTECA = "0 0/2 * 1/1 * ? *";
	private static final String CRON_PALPITE = "0 0/1 * 1/1 * ? *";

	private static final String TRIGGER_PALPITE = "dummyTriggerName2";
	private static final String TRIGGER_LOTECA = "dummyTriggerName1";
	private static final String JOB_PALPITE = "jobPalpite";
	private static final String JOB_LOTECA = "jobLoteca";
	private static final String GROUP_LOTECA = "groupLoteca";

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		JobKey jobKeyLoteca = new JobKey(JOB_LOTECA, GROUP_LOTECA);
		JobDetail jobLoteca = JobBuilder.newJob(LotecaJob.class)
				.withIdentity(jobKeyLoteca).build();

		JobKey jobKeyPalpite = new JobKey(JOB_PALPITE, GROUP_LOTECA);
		JobDetail jobPalpite = JobBuilder.newJob(PalpiteJob.class)
				.withIdentity(jobKeyPalpite).build();

		Trigger triggerLoteca = TriggerBuilder.newTrigger()
				.withIdentity(TRIGGER_LOTECA, GROUP_LOTECA)
				.withSchedule(CronScheduleBuilder.cronSchedule(CRON_LOTECA))
				.build();

		Trigger triggerPalpite = TriggerBuilder.newTrigger()
				.withIdentity(TRIGGER_PALPITE, GROUP_LOTECA)
				.withSchedule(CronScheduleBuilder.cronSchedule(CRON_PALPITE))
				.build();

		Scheduler scheduler;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();

			scheduler.start();
			scheduler.scheduleJob(jobLoteca, triggerLoteca);
			scheduler.scheduleJob(jobPalpite, triggerPalpite);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}