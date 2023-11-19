package net.sf.dysis.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import net.sf.dysis.ServiceLocator;
import net.sf.dysis.planing.core.domain.ActivityImpl;
import net.sf.dysis.planing.core.domain.ProjectImpl;
import net.sf.dysis.planing.core.service.IActivityService;
import net.sf.dysis.planing.core.service.IProjectService;
import net.sf.dysis.resource.core.domain.PersonImpl;
import net.sf.dysis.resource.core.service.IPersonService;
import net.sf.dysis.util.core.service.ISessionService;

import org.junit.Assert;

public class DysisGeneralTest extends TestCase {

	/**
	 * Extracts the year, month and date information from the given {@link Date}
	 * and returns an instance containing only these values.
	 * 
	 * @param date
	 *            The {@link Date} to filter
	 * @return the filtered {@link Date}
	 */
	private Date dateOnly(Date date) {
		Calendar calendarOriginal = Calendar.getInstance();
		calendarOriginal.setTime(date);
		Calendar calendarModified = Calendar.getInstance();
		calendarModified.clear();
		calendarModified.set(calendarOriginal.get(Calendar.YEAR),
				calendarOriginal.get(Calendar.MONTH), calendarOriginal
						.get(Calendar.DATE));
		return calendarModified.getTime();
	}

	public void testDysisProjectCreate() {
		IProjectService projectService = ServiceLocator.instance()
				.getProjectService();
		ProjectImpl projectImpl_one = new ProjectImpl();
		projectImpl_one.setName("Enterprise Eclipse RCP");
		projectImpl_one
				.setDescription("Einsatz von Eclipse RCP in verteilten Systemen");
		projectImpl_one.setStartDate(dateOnly(new Date()));
		projectImpl_one.setEndDate(dateOnly(new Date()));
		projectImpl_one.setActivities(new ArrayList<ActivityImpl>());
		projectService.save(projectImpl_one);

		ProjectImpl projectImpl_two = new ProjectImpl();
		projectImpl_two.setName("Dysis");
		projectImpl_two.setDescription("Dysis Zeiterfassung");
		projectImpl_two.setStartDate(dateOnly(new Date()));
		projectImpl_two.setEndDate(dateOnly(new Date()));
		projectImpl_two.setActivities(new ArrayList<ActivityImpl>());
		projectService.save(projectImpl_two);
	}

	public void testDysisActivityCreate() {
		IProjectService projectService = ServiceLocator.instance()
				.getProjectService();
		IActivityService activityService = ServiceLocator.instance()
				.getActivityService();

		ProjectImpl projectImpl_one = projectService.load(1L);
		ActivityImpl activityImpl_one = new ActivityImpl();
		activityImpl_one.setName("Implementierung Dysis Client");
		activityImpl_one
				.setDescription("Fertigstellung der Clientseite der Referenzimplementierung");
		activityImpl_one.setProjectReference(projectImpl_one);
		activityImpl_one.setActive(true);
		activityService.save(activityImpl_one);

		ActivityImpl activityImpl_two = new ActivityImpl();
		activityImpl_two.setName("Implementierung Dysis Server");
		activityImpl_two
				.setDescription("Fertigstellung der Serverseite der Referenzimplementierung");
		activityImpl_two.setProjectReference(projectImpl_one);
		activityImpl_two.setActive(true);
		activityService.save(activityImpl_two);

		ProjectImpl projectImpl_two = projectService.load(2L);

		ActivityImpl activityImpl_three = new ActivityImpl();
		activityImpl_three.setName("Kapitel UI Architektur");
		activityImpl_three
				.setDescription("Fertigstellung des Kapitels Architektur");
		activityImpl_three.setProjectReference(projectImpl_two);
		activityImpl_three.setActive(true);
		activityService.save(activityImpl_three);

		ActivityImpl activityImpl_four = new ActivityImpl();
		activityImpl_four.setName("Grafiken");
		activityImpl_four.setDescription("Einbinden der Grafiken in das Buch");
		activityImpl_four.setProjectReference(projectImpl_two);
		activityImpl_four.setActive(true);
		activityService.save(activityImpl_four);

		ActivityImpl activityImpl_five = new ActivityImpl();
		activityImpl_five.setName("Quellcode Beispiele");
		activityImpl_five
				.setDescription("Einbinden der Quellcode-Beispiele in das Buch");
		activityImpl_five.setProjectReference(projectImpl_two);
		activityImpl_five.setActive(true);
		activityService.save(activityImpl_five);
	}

	public void testDysisPersonCreate() {
		IPersonService personService = ServiceLocator.instance()
				.getPersonService();
		IActivityService activityService = ServiceLocator.instance()
				.getActivityService();

		PersonImpl person = new PersonImpl();
		person.setUserId("sreichert");
		person.setFirstname("Stefan");
		person.setLastname("Reichert");
		person.setPassword("dysis");
		person.setEmploymentDate(new Date());
		person.setActive(true);
		person.setWeekHours(40);
		person.setActivities(activityService.loadAll());
		personService.save(person);
	}

	public void testDysisProjectLoad() {
		IProjectService projectService = ServiceLocator.instance()
				.getProjectService();
		List<ProjectImpl> projects = projectService.loadAll();
		for (ProjectImpl projectImpl : projects) {
			Assert.assertFalse(projectImpl.getActivities().isEmpty());
		}
	}

	public void testDysisLogin() {
		ISessionService sessionService = ServiceLocator.instance()
				.getSessionService();
		Assert.assertNotNull(sessionService.login("sreichert", "dysis"));
	}
}
