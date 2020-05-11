package de.ostfalia.algo.ws19.s2.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import de.ostfalia.algo.ws19.base.IManagement;
import de.ostfalia.algo.ws19.base.IMember;
import de.ostfalia.algo.ws19.base.KindOfSport;
import de.ostfalia.algo.ws19.base.Member;
import de.ostfalia.algo.ws19.s2.Management;
import de.ostfalia.junit.annotations.AfterMethod;
import de.ostfalia.junit.annotations.TestDescription;
import de.ostfalia.junit.base.IMessengerRules;
import de.ostfalia.junit.base.ITraceRules;
import de.ostfalia.junit.common.Enumeration;
import de.ostfalia.junit.common.Version;
import de.ostfalia.junit.conditional.Natural;
import de.ostfalia.junit.conditional.PassTrace;
import de.ostfalia.junit.rules.MessengerRule;
import de.ostfalia.junit.rules.RuleControl;
import de.ostfalia.junit.rules.TraceRule;
import de.ostfalia.junit.runner.TopologicalSortRunner;

@RunWith(TopologicalSortRunner.class)
public class LargeTestS2 {
	
	public boolean evalOperations = true;
	public static final int MAXVIEW = 20;
	
	public RuleControl opt = RuleControl.NONE;
	public IMessengerRules messenger = MessengerRule.newInstance(opt);	
	public ITraceRules trace = TraceRule.newInstance(opt);
	
	private String msgNotNull = "Methode %s(%s) darf nicht null liefern.";
		
	@Rule
	public TestRule chain = RuleChain
							.outerRule(trace)	
							.around(messenger);
	
	private int fileLength  = 10000;
	/**
	 * Datei mit 10000 Datensaetze fuer die JUnit-Tests.
	 */
	public String fileName = "Materialien/Mitglieder10000.txt";
	public String fileSort = "Materialien/JUnitTestdaten.txt";

	private FileReader fileReader;
	private BufferedReader bufferedReader;

	@Before
	public void setUp() throws Exception {	
		assertTrue(Version.INCOMPATIBLE, Version.request("4.5.2"));
		fileReader = new FileReader(new File(fileName));
		bufferedReader = new BufferedReader(fileReader);
	}
	
	@After
	public void after() throws Exception {		
		if (bufferedReader != null) bufferedReader.close();
		if (fileReader != null) fileReader.close();
	}

	/**
	 * <ul>
	 * 	<li>Testfall: Konstruktoraufruf Management(String) und anschliessendes 
	 * 		ueberpruefen der Anzahl der gespeicherten Eintraege in der 
	 * 		Mitgliederverwaltung. 
	 * 		Die verwendete Datensatzdatei enthaelt 10000 Eintraege.</li>
	 *	<li>Erwartet: 
	 *		<ul>
	 *			<li>Anzahl Eintraege in der Mitgliederverwaltung: 10000.</li>
	 *			<li>toArray() liefert ein Array der Groesse 10000.</li>
	 *			<li>Anzahl Operationen: 24833052 &plusmn; 1000.</li>
	 *		</ul> 
	 *	</li>
	 * </ul>
	 * @throws IOException 
	 * wird ausgeloest, wenn ein E/A-Fehler auftritt, also das Lesen aus 
	 * der Datensatzdatei fehlschlaegt.
	 */	
	@Test (timeout = 5000)
	@TestDescription("Testen des Kontruktors(String).")
	public void testKonstruktorString() throws IOException {
		trace.add("Konstruktoraufruf Management(\"%s\").", fileName);
		IManagement mgnt = new Management(fileName);
		
		trace.add("Aufruf der Methode numberOfOperations().");
		int count = mgnt.numberOfOperations();
		trace.add("Aufruf der Methode size().");
		int size = mgnt.size();
		trace.add("Aufruf der Methode toArray().");
		IMember[] members = mgnt.toArray();		
						
		evaluate(members, size, fileLength);
		evaluate(members, fileLength);
		evaluate(count, 24833052, 1000);
	}
	
	/**
	 * <ul>
	 * 	<li>Testfall: Konstruktoraufruf Management() und anschliessendes einfuegen
	 *  	von 10000 Datensaetzen in die Mitgliederverwaltung. 
	 * 		Die 10000 Datensaetze werden durch Aufruf der Methode insert(IMember) 
	 * 		einzeln	in die Mitgliederverwaltung eingefuegt.</li>
	 *	<li>Erwartet: 
	 *		<ul>
	 *			<li>Anzahl Eintraege in der Mitgliederverwaltung: 10000.</li>
	 *			<li>toArray() liefert ein Array der Groesse 10000.</li>
	 *			<li>Anzahl Operationen: 24833052 &plusmn; 1000.</li>
	 *		</ul> 
	 *	</li>
	 * </ul>
	 * @throws IOException 
	 * wird ausgeloest, wenn ein E/A-Fehler auftritt, also das Lesen aus 
	 * der Datensatzdatei fehlschlaegt.
	 */
	@Test (timeout = 5000)
	@AfterMethod ("testKonstruktorString")
	@TestDescription("Testen der Insert-Methode.")
	public void testEinfuegen() throws IOException {
		trace.add("Konstruktoraufruf Management().");
		IManagement mgnt = new Management();
		trace.add("Aufruf der Methode toArray().");
		IMember[] members = mgnt.toArray();	
		trace.add("Aufruf der Methode size().");
		int size = mgnt.size();
		
		evaluate(members, size, 0);
		
		trace.add("Einfuegen der Datensaetze in die Mitgliederverwaltung.");
		String line;
		int count = 0, n = 1;
		ITraceRules subTrace = trace.newSubtrace(RuleControl.NONE);
		while ((line = bufferedReader.readLine()) != null) {
			subTrace.add("Aufruf der Methode insert(\"%s\").", line);
			mgnt.insert(new Member(line));			
			subTrace.addInfo("Aufruf von numberOfOperations() und Aufsummieren der Anzahl an Operationen.");
			count += mgnt.numberOfOperations();
			subTrace.reset(++n);
		}
		trace.add("Aufruf der Methode toArray() nach dem Einfuegen.");
		members = mgnt.toArray();
		
		evaluate(members, mgnt.size(), fileLength);
		evaluate(members, fileLength);
		evaluate(count, 24833052, 1000);
	}
	
	/**
	 * <ul>
	 * 	<li>Testfall: Konstruktoraufruf Management(String) und anschliessendes 
	 * 		Suchen nach allen Schluesselwerten in der Mitgliederverwaltung. 
	 * 		Die Datensatzdatei enthaelt 10000 Eintraege.</li>
	 *	<li>Erwartet: 
	 *		<ul>
	 *			<li>Anzahl Eintraege in der Mitgliederverwaltung: 10000.</li>
	 *			<li>Alle Schluesselwerte muessen in der Mitgliederverwaltung 
	 *				gefunden werden.</li>
	 *			<li>Gesamtanzahl Operationen beim Suchen aller 10000 Eintraege: 
	 *				50005000 &plusmn; 10000.</li>
	 *		</ul> 
	 *	</li>
	 * </ul>
	 * @throws IOException 
	 * wird ausgeloest, wenn ein E/A-Fehler auftritt, also das Lesen aus 
	 * der Datensatzdatei fehlschlaegt.
	 */
	@Test (timeout = 6000)
	@AfterMethod("testKonstruktorString")
	@TestDescription("Testen der search(long)-Methode.")
	public void testSuchen() throws IOException {
		trace.add("Konstruktoraufruf Management(\"%s\").", fileName);
		IManagement mgnt = new Management(fileName);
		trace.add("Aufruf der Methode toArray().");
		IMember[] members = mgnt.toArray();	
		trace.add("Aufruf der Methode size().");
		int size = mgnt.size();
		
		evaluate(members, size, fileLength);
		
		trace.add("Suchen nach Datensaetzen in der Mitgliederverwaltung.");
		String line;
		int count = 0, n = 1;
		ITraceRules subTrace = trace.newSubtrace(RuleControl.NONE);
		while ((line = bufferedReader.readLine()) != null && trace.getOccurrences() < MAXVIEW) {					
			IMember exp = new Member(line);	
			subTrace.add("Aufruf der Methode search(\"%s\").", exp.getKey());
			IMember got = mgnt.search(exp.getKey());
			subTrace.addInfo(PassTrace.ifEquals("Suchen nach Schluessel \"%d\".", 
					         exp, got, exp.getKey()));
			subTrace.addInfo("Aufruf von numberOfOperations() und Aufsummieren der Anzahl an Operationen.");
			count += mgnt.numberOfOperations();
			trace.add(subTrace, subTrace.hasOccurrences());
			subTrace.reset(++n);
		}
		hint(trace);
		assertFalse("Fehlerhafte / fehlende Datensaetze in der Mitgliederverwaltung.",
				trace.hasOccurrences());
		evaluate(count, 50005000, fileLength);				
	}
	
	/**
	 * <ul>
	 * 	<li>Testfall: Konstruktoraufruf Management(String) und anschliessendes
	 * 		ueberpruefen der Anzahl der Eintraege der Sportarten in der 
	 * 		Mitgliederverwaltung. 
	 * 		Die Datensatzdatei enthaelt 10000 Eintraege.</li>
	 *	<li>Erwartet: <br>
	 *		Methode size(KindOfSport) muss folgende Werte liefern: 
	 *		<ul>		
	 *			<li>FUSSBALL: 985.</li>
	 *			<li>HANDBALL: 989.</li>
	 *			<li>SCHWIMMEN: 1037.</li>
	 *			<li>LEICHTATHLETIK: 973.</li>
	 * 			<li>REITEN: 1000.</li>
	 * 			<li>FECHTEN: 985.</li>
	 * 			<li>TURNEN: 1033.</li>
	 * 			<li>RADSPORT: 996.</li>
	 * 			<li>TANZEN: 992.</li>
	 * 			<li>RUDERN: 1010.</li>
	 * 		</ul>
	 * 	</li>
	 * 	<li>Erwartete Gesamtanzahl Operationen beim Suchen aller 10000 Eintraege: 
	 *	    100000 &plusmn; 10000.	
	 * 	</li>
	 * </ul>
	 * @throws IOException 
	 * wird ausgeloest, wenn ein E/A-Fehler auftritt, also das Lesen aus 
	 * der Datensatzdatei fehlschlaegt.
	 */
	@Test (timeout = 5000)
	@AfterMethod("testKonstruktorString")
	@TestDescription("Testen der size(KindOfSport)-Methode.")
	public void testTraversieren() throws IOException {
		final int[] exp = {985, 989, 1037, 973, 1000, 985, 1033, 996, 992, 1010};
		
		trace.add("Konstruktoraufruf Management(\"%s\").", fileName);
		IManagement mgnt = new Management(fileName); 

		String msg = "Anzahl der Mitglieder fuer die Sportarten %s.";
		int index = 0, count = 0;
		for (KindOfSport sport : KindOfSport.values()) {
			trace.add("Aufruf von size(%s).", sport);
			trace.addInfo(PassTrace.ifEquals(msg, exp[index++], mgnt.size(sport), sport));
			trace.addInfo("Aufruf von numberOfOperations() und Aufsummieren der Anzahl an Operationen.");						
			count += mgnt.numberOfOperations();
		}
		assertFalse("Methode size(KindOfSport) liefert falsches Ergebnis.",
				trace.hasOccurrences());
		evaluate(count, 10 * 10000, 10000);
	}
	
	/**
	 * <ul>
	 * 	<li>Testfall: Konstruktoraufruf Management(String) und anschliessendes
	 * 		ueberpruefen der Eintraege der 10 Sportarten in der Mitgliederverwaltung.
	 * 		Mit Hilfe einer Pruefsumme wird festgestellt, ob die Eintraege 
	 * 		korrekt sind. Die Datensatzdatei enthaelt 10000 Eintraege.</li>
	 *	<li>Erwartet: <br>
	 *		Methode discipline(KindOfSport) muss folgende Werte liefern: 
	 *		<ul>		
	 *			<li>FUSSBALL: 985 Eintraege (Pruefsumme: 225236503928).</li>
	 *			<li>HANDBALL: 989 Eintraege (Pruefsumme: 173758233713).</li>
	 *			<li>SCHWIMMEN: 1037 Eintraege (Pruefsumme: 183013252630).</li>
	 *			<li>LEICHTATHLETIK: 973 Eintraege (Pruefsumme: 161145468810).</li>
	 * 			<li>REITEN: 1000 Eintraege (Pruefsumme: 152686351492).</li>
	 * 			<li>FECHTEN: 985 Eintraege (Pruefsumme: 257673959686).</li>
	 * 			<li>TURNEN: 1033 Eintraege (Pruefsumme: 90525160369).</li>
	 * 			<li>RADSPORT: 996 Eintraege (Pruefsumme: 266557207632).</li>
	 * 			<li>TANZEN: 992 Eintraege (Pruefsumme: 74094229376).</li>
	 * 			<li>RUDERN: 1010 Eintraege (Pruefsumme: 58704317911).</li>
	 * 		</ul>
	 * 	</li> 
	 * </ul>
	 * @throws IOException 
	 * wird ausgeloest, wenn ein E/A-Fehler auftritt, also das Lesen aus 
	 * der Datensatzdatei fehlschlaegt.
	 */
	@Test (timeout = 4000)
	@AfterMethod("testKonstruktorString")
	@TestDescription("Testen der discipline(KindOfSport)-Methode.")
	public void testTraversieren2() throws IOException {
		final int[]  exp = {985, 989, 1037, 973, 1000, 985, 1033, 996, 992, 1010};
		final long[] chk = {225236503928L, 173758233713L, 183013252630L, 161145468810L, 
				            152686351492L, 257673959686L,  90525160369L, 266557207632L, 
							 74094229376L,  58704317911L};
		
		trace.add("Konstruktoraufruf Management(\"%s\").", fileName);
		IManagement mgnt = new Management(fileName); 

		String msg = "Anzahl der Mitglieder fuer die Sportarten %s.";
		int index = 0;
		for (KindOfSport sport : KindOfSport.values()) {
			trace.add("Aufruf von discipline(%s).", sport);
			IMember[] got = mgnt.discipline(sport);	
			
			ITraceRules subTrace = trace.newSubtrace(opt);	
			subTrace.enumeration(new Enumeration(Enumeration.roman));
			subTrace.add(PassTrace.ifFalse(msgNotNull, got == null, "discipline", sport));
			if (got != null) { 
				subTrace.add(PassTrace.ifEquals(msg, exp[index], got.length, sport));
				subTrace.add("Alle Eintraege muessen der Sportart %s entprechen.", sport);
				int idx = 0; 
				long chkSum = 0;
				ITraceRules sub2Trace = subTrace.newSubtrace(RuleControl.NONE);		
				sub2Trace.enumeration(new Enumeration(0, 1, Enumeration.array));
				while (idx < got.length && subTrace.getOccurrences() < MAXVIEW) {
					sub2Trace.reset(idx);
					sub2Trace.add(PassTrace.ifFalse("Datensatz darf nicht null sein.", got[idx] == null));					
					if (got[idx] != null) {
						sub2Trace.addInfo(PassTrace.ifEquals("Datensatz \"%s\".", 
								sport, got[idx].getKindOfSport(), got[idx]));
						chkSum ^= got[idx].getKey();
					}
					subTrace.add(sub2Trace, sub2Trace.hasOccurrences());
					idx++;
				}
				hint(subTrace);
				subTrace.add(PassTrace.ifEquals("Pruefsumme fuer die Sportart %s.", 
						chk[index], chkSum, sport));
			}
			trace.add(subTrace);
			index++;
		}
		assertFalse("Methode discipline(KindOfSport) liefert falsches Ergebnis.",
				trace.hasOccurrences());		
	}
	
	//-----------------------------------------------------------------
	
	/**
	 * Ueberpruefung der Anzahl Mitglieder in der Mitgliederverwaltung.
	 * @param members - Mitglieder in der Mitgliederverwaltung: IMember[]. 
	 * @param size - erhaltene Anzahl der Mitglieder: int.
	 * @param exp - erwartete Anzahl der Mitglieder: int.
	 */
	private void evaluate(IMember[] members, int size, int exp) {
		trace.add(PassTrace
				.ifEquals("Anzahl der Datensaetze in der Mitgliederverealtung muss %d betragen.", 
				          exp, size, exp));

		trace.add(PassTrace.ifTrue("Methode toArray() darf nicht null liefern.", members != null));
		assertNotNull("Methode toArray() liefert null", members);

		trace.add(PassTrace
				.ifEquals("Methode toArray() muss ein Array der Laenge %d liefern.", 
						  exp, members.length, exp));
		assertFalse("Fehlerhafte Anzahl von Datensaetze in der Mitgliederverwaltung.",
				trace.hasOccurrences());
	}
	
	/**
	 * Ueberpueft die Datensaetze in der Mitgliederverwaltung mit den Datensaetzen
	 * in der eingelesenen Datei.
	 * @param members - Mitglieder in der Mitgliederverwaltung: IMember[]. 
	 * @param lines - Anzahl Zeilen (Datensaetze) in der eingelesenen Datei: int.
	 * @throws IOException
	 */
	private void evaluate(IMember[] members, int lines) throws IOException {
		FileReader fr = new FileReader(new File(fileSort));		
		BufferedReader br = new BufferedReader(fr);	
		
		int index = 0;
		String line;		
		trace.add("Ueberpruefung der Datensaetze im Array mit den erwarteten Daten.");
		ITraceRules subTrace = trace.newSubtrace(RuleControl.NONE);
		
		subTrace.enumeration(new Enumeration(0, 1, Enumeration.array));
		while ((line = br.readLine()) != null && trace.getOccurrences() < MAXVIEW) {
			subTrace.reset(index);
			IMember member = new Member(line);
			subTrace.add(PassTrace.ifEquals("Dateizeile %d.", 
					member, members[index++], index));
			trace.add(subTrace, subTrace.hasOccurrences());
		}	
		
		hint(trace);
		fr.close();
		br.close();
		assertFalse("Fehlerhafte Datensaetze in der Mitgliederverwaltung.",
				trace.hasOccurrences());
	}
	
	/**
	 * Ueberpruefung der Anzahl der Operationen.
	 * @param count - erhaltene Anzahl der Operationen: int.
	 * @param exp - erwartete Anzahl der Operationen: int.
	 * @param range - gueltiger Bereich um den erwarteten Wert: int.
	 */
	private void evaluate(int count, int exp, int range) {
		if (evalOperations) {
			int min = exp - range;
			int max = exp + range;
			Natural got = Natural.format(count);
			trace.add(PassTrace.ifTrue(
					"Anzahl Operationen muss im Bereich %d..%d liegen. Erhalten %d.", 
					got.rangeOf(min, max), min, max, count)
			);
			assertFalse("Fehlerhafte Anzahl Operationen.", trace.hasOccurrences());
		} else {
			System.err.println("Ueberpruefung der Anzahl der Operationen (evaluate()) ist aktuell deaktiviert!");
		}
	}

	/**
	 * Hinweistext dem Trace hinzufuegen, wenn die Anzahl der Eintraege 
	 * groesser MAXVIEW ist.
	 * @param subTrace - untergeordneter Trace. 
	 */
	private void hint(ITraceRules subTrace) {
		if (!(subTrace.getOccurrences() < MAXVIEW)) {
			subTrace.addInfo("ACHTUNG! Es werden nur maximal 20 Datensaetze angezeigt.");
		}
	}

}
