package de.ostfalia.algo.ws19.base;

import java.time.LocalDate;

public class Member implements IMember {
	private long key;
	private String name;
	private String firstName;
	private String dateString;
	private LocalDate date;
	private Gender gender;
	private KindOfSport sport;

	public Member(String input) {
		String[] result = input.split(", ");
		this.name = result[0].trim();
		this.firstName = result[1].trim();
		this.dateString = result[2].trim();
		this.gender = Gender.valueOf(result[3].trim());
		this.sport = KindOfSport.valueOf(result[4].trim());
		int temp = name.charAt(0) - 64;
		key = temp;
		temp = firstName.charAt(0) - 64;
		key = key * 100 + temp;
		/*
		 * key = "name + first name + day + month + year"
		 */
		String syear = dateString.substring(0, 4);
		String smonth = dateString.substring(5, 7);
		String sday = dateString.substring(8, 10);
		date = LocalDate.now().withYear(Integer.parseInt(syear)).withMonth(Integer.parseInt(smonth))
				.withDayOfMonth(Integer.parseInt(sday));
		key = key * 100000000 + Integer.parseInt(sday + smonth + syear);
	}

	public Member(long key) {
		this.key = key;
	}

	public Member(String name, String firstName) {
		this.name = name;
		this.firstName = firstName;
	}

	@Override
    public int compareTo(IMember member) {
        if(member.getKey()==this.key) {
            return 0;
        }
        else if(member.getKey()<this.key){
            return 1;
        }
        else {
            return -1;
        }
    }

	@Override
	public long getKey() {
		return key;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFirstName() {
		return firstName;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

	@Override
	public LocalDate getDate() {
		return date;
	}

	@Override
	public KindOfSport getKindOfSport() {
		return sport;
	}

	@Override
	public String toString() {
		return key + ", " + name + ", " + firstName + ", " + date.toString() + ", " + gender.toString() + ", "
				+ sport.toString();

	}
}
