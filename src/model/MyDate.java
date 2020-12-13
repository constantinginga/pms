package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

public class MyDate {
    private int day;
    private int month;
    private int year;

    public MyDate(int day, int month, int year) {
        set(day, month, year);
    }

    public MyDate(MyDate date) {
        this(date.day, date.month, date.year);
    }

    public MyDate() {
        this(today());
    }

    public MyDate(LocalDate date) {
        set(date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }

    public MyDate copy() {
        return new MyDate(day, month, year);
    }

    public static MyDate today() {
        GregorianCalendar currentDate = new GregorianCalendar();
        int currentDay = currentDate.get(GregorianCalendar.DATE);
        int currentMonth = currentDate.get(GregorianCalendar.MONTH) + 1;
        int currentYear = currentDate.get(GregorianCalendar.YEAR);

        return new MyDate(currentDay, currentMonth, currentYear);
    }

    public void set(int day, int month, int year) {
        if (year < 0) throw new IllegalArgumentException("Invalid year");
        else this.year = year;

        if (month < 1 || month > 12) throw new IllegalArgumentException("Invalid month");
        else this.month = month;

        if (day < 1 || day > numberOfDaysInMonth()) throw new IllegalArgumentException("Invalid day");
        else this.day = day;
    }

    public static int convertToMonthNumber(String monthName) {
        return switch (monthName.toLowerCase()) {
            case "january" -> 1;
            case "february" -> 2;
            case "march" -> 3;
            case "april" -> 4;
            case "may" -> 5;
            case "june" -> 6;
            case "july" -> 7;
            case "august" -> 8;
            case "september" -> 9;
            case "october" -> 10;
            case "november" -> 11;
            case "december" -> 12;
            default -> -1;
        };
    }

    public String getMonthName() {
        return switch (month) {
            case 1 -> "January";
            case 2 -> "February";
            case 3 -> "March";
            case 4 -> "April";
            case 5 -> "May";
            case 6 -> "June";
            case 7 -> "July";
            case 8 -> "August";
            case 9 -> "September";
            case 10 -> "October";
            case 11 -> "November";
            case 12 -> "December";
            default -> "";
        };
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public boolean isLeapYear() {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }

    public int numberOfDaysInMonth() {
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;

            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;

            case 2:
                return (isLeapYear()) ? 29 : 28;

            default:
                return -1;
        }
    }

    public void stepForwardOneDay() {
        day++;
        if (day > numberOfDaysInMonth()) {
            day = 1;
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
    }

    public boolean isBefore(MyDate other) {
        if (other.year > year) return true;
        else if (year == other.year && other.month > month) return true;
        else return year == other.year && other.day > day;
    }

    public int yearsBetween(MyDate date) {
        if (this.year == date.year) return 0;

        int diff = 0;
        MyDate before, after;

        if (date.isBefore(this)) {
            before = date;
            after = this;
        } else {
            before = this;
            after = date;
        }

        diff = after.year - before.year;
        if (before.month > after.month || before.day > after.day) {
            diff--;
        }

        return diff;
    }

    public String dayOfWeek() {
        int m, k, j, h, y;

        if (this.month == 1 || this.month == 2) {
            m = this.month + 12;
            y = this.year - 1;
        } else {
            m = this.month;
            y = this.year;
        }

        k = y % 100;
        j = y / 100;
        h = (this.day + 13 * (m + 1) / 5 + k + k / 4 + j / 4 + 5 * j) % 7;

        return switch (h) {
            case 0 -> "Saturday";
            case 1 -> "Sunday";
            case 2 -> "Monday";
            case 3 -> "Tuesday";
            case 4 -> "Wednesday";
            case 5 -> "Thursday";
            case 6 -> "Friday";
            default -> "";
        };
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MyDate)) return false;
        MyDate other = (MyDate) obj;
        return this.day == other.day && this.month == other.month && this.year == other.year;
    }

    public String toString() {
        return day + "/" + month + "/" + year;
    }

}
