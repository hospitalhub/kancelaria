package pl.kalisz.szpital.kancelaria.scheduler;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

@SuppressWarnings("serial")
public class QuartzSchedulerServlet extends HttpServlet {

	// archiwizuj w godz 8-16 co dwie w dni pracy
	private static final String CRON = "0 15 8,10,12,14,16 ? * MON-FRI";
	Scheduler scheduler = null;
	
	@Override
	public void init() throws ServletException {
		try {
			scheduler = StdSchedulerFactory.getDefaultScheduler();
			scheduler.start();

			JobDetail job = newJob(ArchiviseJob.class).withIdentity("job1",
					"group1").build();

			Trigger trigger = newTrigger().withIdentity("trigger2", "group1")
					.withSchedule(cronSchedule(CRON)).build();
			scheduler.scheduleJob(job, trigger);

		} catch (SchedulerException se) {
			se.printStackTrace();
		}
	}

}
