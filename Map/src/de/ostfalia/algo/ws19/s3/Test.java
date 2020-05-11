package de.ostfalia.algo.ws19.s3;

import java.util.Arrays;

import de.ostfalia.algo.ws19.base.IManagement;
import de.ostfalia.algo.ws19.base.KindOfSport;

public class Test {
	public static void main(String[] args) {
		IManagement m = new ManagementTree("Materialien/Mitglieder10000.txt");
		System.out.println(m.search("Aachen", "Dennis"));
		System.out.println(m.search( 160113121927l));
		System.out.println("size: " + m.size());
		System.out.println("height: " + m.height());
		System.out.println("delete member with key " + 160113121927l);
		m.deleteKey(160113121927l);
		System.out.println("search the one deleted: " + m.search( 160113121927l));
		System.out.println("size kind of sport: " + m.size(KindOfSport.TANZEN));
		System.out.println("BT to ARR length: " + m.toArray().length);
		System.out.println("Discipline " + Arrays.toString(m.discipline(KindOfSport.TANZEN)));
	}

}
