package loteca.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class LotecaJob implements Job {
	@Override
	public void execute(final JobExecutionContext ctx)
			throws JobExecutionException {

		System.out.println("Executing LotecaJob");

	}
}
