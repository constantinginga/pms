package model;

public class MyDate {

	private int day;

	private int month;

	private int year;

	public MyDate(int day, int month, int year) {

	}

	public MyDate(MyDate object2) {

	}

	public MyDate() {

	}

	public MyDate copy() {
		return null;
	}

	public static MyDate today() {
		return null;
	}

	public void set(int day, int month, int year) {

	}

	public static int converToMonthNumber(String monthName) {
		return 0;
	}

	public String getMonthName() {
		return null;
	}

	public int getDay() {
		return 0;
	}

	public int getMonth() {
		return 0;
	}

	public int getYear() {
		return 0;
	}

	public boolean isLeapYear() {
		return false;
	}

	public int numberOfDaysInMonth() {
		return 0;
	}

	public void stepForwardOneDay() {

	}

	public boolean isBefore(MyDate other) {
		return false;
	}

	/**
	 * 
	 *     public int yearsBetween(MyDate other) {
	 *         if (other.year < this.year) {
	 *             return this.year - other.year;
	 *         } else {
	 *             return other.year - year;
	 *         }
	 *     }
	 * 
	 */
	public int yearsBetween(MyDate date2) {
		return 0;
	}

	public boolean getIfTheDateIsAfter() {
		return false;
	}

	public String dayOfWeek() {
		return null;
	}

	public boolean equals(MyDate other) {
		return false;
	}

	public String toString() {
		return null;
	}

}
