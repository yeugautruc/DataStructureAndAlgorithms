package de.ostfalia.algo.ws19.s2.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.DisableOnDebug;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;

import de.ostfalia.algo.ws19.base.Gender;
import de.ostfalia.algo.ws19.base.IMember;
import de.ostfalia.algo.ws19.base.KindOfSport;
import de.ostfalia.algo.ws19.base.Member;
import de.ostfalia.junit.annotations.TestDescription;
import de.ostfalia.junit.base.IMessengerRules;
import de.ostfalia.junit.base.ITraceRules;
import de.ostfalia.junit.common.Enumeration;
import de.ostfalia.junit.common.Version;
import de.ostfalia.junit.conditional.PassTrace;
import de.ostfalia.junit.processing.Spaces;
import de.ostfalia.junit.rules.MessengerRule;
import de.ostfalia.junit.rules.RuleControl;
import de.ostfalia.junit.rules.TraceRule;
import de.ostfalia.junit.runner.TopologicalSortRunner;

/**
 * 
 * @author M. Gruendel
 *
 */
@RunWith(TopologicalSortRunner.class)
public class MemberTest {

	public RuleControl opt = RuleControl.NONE;
	public IMessengerRules messenger = MessengerRule.newInstance(opt);	
	public ITraceRules trace = TraceRule.newInstance(opt);
		

	@Rule
	public TestRule chain = RuleChain
							.outerRule(trace)	
							.around(messenger);
	
	@Rule
    public TestRule timeout = new DisableOnDebug(
                              new Timeout(1000, TimeUnit.MILLISECONDS));
	
	private String callCntr  = "Konstruktoraufruf \"Member(%s)\".";	
	private String callMsg   = "Aufruf der Methode \"%s()\".";
	private String returnMsg = "Zurueckgelieferter Wert der \"%s\" - Methode fuer Datensatz %d.";

	/**
	 * Datensatz mit 10 Eintraegen als Testdaten fuer die JUnit-Tests.
	 */
	public String[] data = {"Ackerman, Niklas, 1979-04-08, M, SCHWIMMEN",	//[0]
					 		"Bauer, Juliane, 1939-01-16, F, FECHTEN",		//[1]
					 		"Kirsch, Antje, 1960-07-13, F, TURNEN",			//[2]
					 		"Koertig, Dominik, 1933-09-28, M, HANDBALL",	//[3]
					 		"Trommler, Ines, 1924-10-29, F, TURNEN",		//[4]
					 		"Hirsch, Manuela, 1948-05-10, F, RUDERN",		//[5]
					 		"Schaefer, Stephanie, 1997-05-31, F, FECHTEN",	//[6]
					 		"Schultz, Katrin, 1952-08-18, F, REITEN",		//[7]
					 		"Fried, Leonie, 1965-09-24, F, TURNEN",			//[8]
					 		"Lemann, Philipp, 1936-10-30, M, RADSPORT"};	//[9]

	/**
	 * Datensatz mit 3 Eintraegen als Testdaten fuer die JUnit-Tests.
	 */
	public String[] test = {"Ackerman, Niklas, 1979-04-08, M, SCHWIMMEN",
				     		"Acker, Nadine, 1979-04-08, F, HANDBALL", 
				     		"Acker, Nadine, 1979-04-10, F, HANDBALL"};

	/**
	 * Schluesselwerte fuer den Datensatz data (10 Eintraege).
	 */
	public long[] keys = {11408041979L, 21016011939L, 110113071960L, 110428091933L, 
				   		  200929101924L, 81310051948L, 191931051997L, 191118081952L,
				   		  61224091965L, 121630101936L};

	@Before
	public void setUp() throws Exception {
		assertTrue(Version.INCOMPATIBLE, Version.request("4.5.2"));
		PassTrace.preProcessor(PassTrace.CONDITIONS, Spaces.toTilde);
		PassTrace.preProcessor(PassTrace.EQUALS);
		trace.enumeration(new Enumeration(0, Enumeration.array));
	}

	/**
	 * <ul>
	 * 	<li>Testfall: toString()-Methode der Klasse Member.<br> 
	 * 		Es werden alle 10 Datensaetze nacheinander druchlaufen.</li>
	 *	<li>Erwartet: Rueckgabe aller Attribute inkl. des berechneten Schluessels als String.</li>
	 *	<li>Beispiel  fuer data[0]: 11408041979, Ackerman, Niklas, 1979-04-08, M, SCHWIMMEN.</li>
	 * </ul>
	 */
	@Test
	@TestDescription("Testen der toString()-Methode.")
	public void testToString() {
		PassTrace.preProcessorDefaults();
		for (int i = 0; i < keys.length; i++) {
			trace.add(callCntr, data[i]);
			IMember member = new Member(data[i]);
			String exp = keys[i]  + ", " + data[i];			
			trace.addInfo(callMsg, "toString");
			String got = member.toString();
			trace.addInfo(PassTrace.ifEquals(returnMsg, exp, got, "toString", i));
		}
		assertFalse("Fehler bei der Implementierung der toString()-Methode.",
				trace.hasOccurrences());
	}

	/**
	 * <ul>
	 * 	<li>Testfall: getKey()-Methode der Klasse Member.</li>
	 *	<li>Erwartet: Rueckgabe des Schluessels als Long-Wert.</li>
	 *	<li>Beispiel fuer data[0]: 11408041979.</li>
	 * </ul>
	 */
	@Test
	@TestDescription("Testen der getKey()-Methode.")
	public void testGetKey() {
		for (int i = 0; i < keys.length; i++) {
			trace.add(callCntr, data[i]);
			IMember member = new Member(data[i]);
			Long exp = keys[i];
			trace.addInfo(callMsg, "getKey");
			Long got = member.getKey();
			trace.addInfo(PassTrace.ifEquals(returnMsg, exp, got, "getKey", i));
		}
		assertFalse("Fehler bei der Rueckgabe des Schluesselwertes.",
				trace.hasOccurrences());
	}

	/**
	 * <ul>
	 * 	<li>Testfall: getName()-Methode der Klasse Member.</li>
	 *	<li>Erwartet: Rueckgabe des Nachnamens als String.</li>
	 *	<li>Beispiel fuer data[0]: Ackerman.</li>
	 * </ul>
	 */
	@Test
	@TestDescription("Testen der getName()-Methode.")
	public void testGetName() {
		for (int i = 0; i < keys.length; i++) {
			trace.add(callCntr, data[i]);
			IMember member = new Member(data[i]);
			String exp = data[i].split("\\s*,\\s*")[0];
			trace.addInfo(callMsg, "getName");
			String got = member.getName();
			trace.addInfo(PassTrace.ifEquals(returnMsg, exp, got, "getName", i));
		}
		assertFalse("Fehler bei der Rueckgabe des Nachnamens.",
				trace.hasOccurrences());
	}

	/**
	 * <ul>
	 * 	<li>Testfall: getFirstName()-Methode der Klasse Member.</li>
	 *	<li>Erwartet: Rueckgabe des Vornamens als String.</li>
	 *	<li>Beispiel fuer data[0]: Niklas.</li>
	 * </ul>
	 */
	@Test
	@TestDescription("Testen der getFirstName()-Methode.")
	public void testGetFirstName() {
		for (int i = 0; i < keys.length; i++) {
			trace.add(callCntr, data[i]);
			IMember member = new Member(data[i]);
			String exp = data[i].split("\\s*,\\s*")[1];
			trace.addInfo(callMsg, "getFirstName");
			String got = member.getFirstName();
			trace.addInfo(PassTrace.ifEquals(returnMsg, exp, got, "getFirstName", i));
		}
		assertFalse("Fehler bei der Rueckgabe des Vornamens.",
				trace.hasOccurrences());
	}
	
	/**
	 * <ul>
	 * 	<li>Testfall: getDate()-Methode der Klasse Member.</li>
	 *	<li>Erwartet: Rueckgabe des Geburtsdatum als LocalDate.</li>
	 *	<li>Beispiel fuer data[0]: 1979-04-08.</li>
	 * </ul>
	 */
	@Test
	@TestDescription("Testen der getDate()-Methode.")
	public void testGetDate() {
		for (int i = 0; i < keys.length; i++) {
			trace.add(callCntr, data[i]);
			IMember member = new Member(data[i]);
			String exp = data[i].split("\\s*,\\s*")[2];
			trace.addInfo(callMsg, "getDate");
			LocalDate got = member.getDate();			
			trace.addInfo(PassTrace.ifEquals(returnMsg, exp, got, "getDate", i));
		}
		assertFalse("Fehler bei der Rueckgabe des Geburtsdatums.",
				trace.hasOccurrences());
	}

	/**
	 * <ul>
	 * 	<li>Testfall: getGender()-Methode der Klasse Member.</li>
	 *	<li>Erwartet: Rueckgabe des Geschlechts als enum Gender.</li>
	 *	<li>Beispiel fuer data[0]: M.</li>
	 * </ul>
	 */
	@Test
	@TestDescription("Testen der getGender()-Methode.")
	public void testGender() {
		for (int i = 0; i < keys.length; i++) {
			trace.add(callCntr, data[i]);
			IMember member = new Member(data[i]);
			String exp = data[i].split("\\s*,\\s*")[3];
			trace.addInfo(callMsg, "getGender");
			Gender got = member.getGender();			
			trace.addInfo(PassTrace.ifEquals(returnMsg, exp, got, "getGender", i));
		}
		assertFalse("Fehler bei der Rueckgabe des Geschlechts.",
				trace.hasOccurrences());
	}

	/**
	 * <ul>
	 * 	<li>Testfall: getKindOfSport()-Methode der Klasse Member.</li>
	 *	<li>Erwartet: Rueckgabe der Sportart als enum KindOfSport.</li>
	 *	<li>Beispiel fuer data[0]: SCHWIMMEN.</li>
	 * </ul>
	 */
	@Test
	@TestDescription("Testen der getKindOfSport()-Methode.")
	public void testGetKindOfSport() {
		for (int i = 0; i < keys.length; i++) {
			trace.add(callCntr, data[i]);
			IMember member = new Member(data[i]);
			String exp = data[i].split("\\s*,\\s*")[4];
			trace.addInfo(callMsg, "getKindOfSport");
			KindOfSport got = member.getKindOfSport();
			trace.addInfo(PassTrace.ifEquals(returnMsg, exp, got, "getKindOfSport", i));
		}
		assertFalse("Fehler bei der Rueckgabe der Sportart.",
				trace.hasOccurrences());
	}
	
	/**
	 * <ul>
	 * 	<li>Testfall: equals()-Methode der Klasse Member.</li>
	 *	<li>Erwartet: true, wenn die Member inhaltlich gleich sind, sonst false.</li>
	 * </ul>
	 */
	@Ignore
	@Test
	@TestDescription("Testen der equals()-Methode.")
	public void testEqualsObject() {
		trace.add("Konstruktoraufruf Member(%s) fuer Mitglied %d", data[0], 1);
		IMember member1 = new Member(test[0]);
		trace.add("Konstruktoraufruf Member(%s) fuer Mitglied %d", data[0], 2);
		IMember duplikat = new Member(test[0]);
		trace.add("Konstruktoraufruf Member(%s) fuer Mitglied %d", data[1], 3);
		IMember member2 = new Member(test[1]);
		trace.add("Konstruktoraufruf Member(%s) fuer Mitglied %d", data[1], 4);
		IMember member3 = new Member(test[2]);
		
		trace.addInfo(PassTrace.ifTrue("[%s] gleich [%s]?",
				member1.equals(duplikat), member1, duplikat));		
		trace.addInfo(PassTrace.ifTrue("[%s] gleich [%s]",
				member1.equals(member2), member1, member2));
		trace.addInfo(PassTrace.ifFalse("[%s] gleich [%s]",
				member1.equals(member3), member1, member3));
		trace.addInfo(PassTrace.ifFalse("[%s] gleich [%s]",
				member2.equals(member3), member2, member3));
		
		assertFalse("equals()-Methode fehlerhaft implementiert.",
				trace.hasOccurrences());
	}

}
