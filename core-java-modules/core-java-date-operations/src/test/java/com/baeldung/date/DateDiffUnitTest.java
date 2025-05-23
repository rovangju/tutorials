package com.baeldung.date;

import org.joda.time.DateTime;
import org.joda.time.Weeks;
import org.joda.time.Days;
import org.joda.time.Minutes;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
 
import static org.junit.Assert.*;

public class DateDiffUnitTest {

    @Test
    public void givenTwoDatesBeforeJava8_whenDifferentiating_thenWeGetSix() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        Date firstDate = sdf.parse("06/24/2017");
        Date secondDate = sdf.parse("06/30/2017");

        long diffInMillis = Math.abs(secondDate.getTime() - firstDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

        assertEquals(6, diff);
    }

    @Test
    public void givenTwoDatesInJava8_whenUsingPeriodGetDays_thenWorks() {
        LocalDate aDate = LocalDate.of(2020, 9, 11);
        LocalDate sixDaysBehind = aDate.minusDays(6);

        Period period = Period.between(aDate, sixDaysBehind);
        int diff = Math.abs(period.getDays());

        assertEquals(6, diff);
    }

    @Test
    public void givenTwoDatesInJava8_whenUsingPeriodGetDays_thenDoesNotWork() {
        LocalDate aDate = LocalDate.of(2020, 9, 11);
        LocalDate sixtyDaysBehind = aDate.minusDays(60);
        Period period = Period.between(aDate, sixtyDaysBehind);
        int diff = Math.abs(period.getDays());
        //not equals
        assertNotEquals(60, diff);
    }

    @Test
    public void givenTwoDatesInJava8_whenUsingPeriod_thenWeGet0Year1Month29Days() {
        LocalDate aDate = LocalDate.of(2020, 9, 11);
        LocalDate sixtyDaysBehind = aDate.minusDays(60);
        Period period = Period.between(aDate, sixtyDaysBehind);
        int years = Math.abs(period.getYears());
        int months = Math.abs(period.getMonths());
        int days = Math.abs(period.getDays());
        assertArrayEquals(new int[] { 0, 1, 29 }, new int[] { years, months, days });
    }

    @Test
    public void givenTwoLocalDatesInJava8_whenUsingChronoUnitWeeksBetween_thenFindIntegerWeeks() {
        LocalDate startLocalDate = LocalDate.of(2024, 01, 10);
	LocalDate endLocalDate = LocalDate.of(2024, 11, 15);   

	long weeksDiff = ChronoUnit.WEEKS.between(startLocalDate, endLocalDate);  

        assertEquals(44, weeksDiff);
    }		

    @Test
    public void givenTwoDateTimesInJava8_whenDifferentiating_thenWeGetSix() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sixMinutesBehind = now.minusMinutes(6);

        Duration duration = Duration.between(now, sixMinutesBehind);
        long diff = Math.abs(duration.toMinutes());

        assertEquals(6, diff);
    }

    @Test
    public void givenTwoDateTimesInJava8_whenDifferentiatingInSeconds_thenWeGetTen() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenSecondsLater = now.plusSeconds(10);

        long diff = ChronoUnit.SECONDS.between(now, tenSecondsLater);

        assertEquals(10, diff);
    }

    @Test
    public void givenTwoZonedDateTimesInJava8_whenDifferentiating_thenWeGetSix() {
        LocalDateTime ldt = LocalDateTime.now();
        ZonedDateTime now = ldt.atZone(ZoneId.of("America/Montreal"));
        ZonedDateTime sixDaysBehind = now.withZoneSameInstant(ZoneId.of("Asia/Singapore"))
                                         .minusDays(6);
        long diff = ChronoUnit.DAYS.between(sixDaysBehind, now);
        assertEquals(6, diff);
    }

    @Test
    public void givenTwoZonedDateTimesInJava8_whenUsingChronoUnitWeeksBetween_thenFindIntegerWeeks() {
        ZonedDateTime startDateTime = ZonedDateTime.parse("2022-02-01T00:00:00Z[UTC]");
	ZonedDateTime endDateTime = ZonedDateTime.parse("2022-10-31T23:59:59Z[UTC]");

	long weeksDiff = ChronoUnit.WEEKS.between(startDateTime, endDateTime);  

        assertEquals(38, weeksDiff);
    }	

    @Test
    public void givenTwoDateTimesInJava8_whenDifferentiatingInSecondsUsingUntil_thenWeGetTen() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime tenSecondsLater = now.plusSeconds(10);

        long diff = now.until(tenSecondsLater, ChronoUnit.SECONDS);

        assertEquals(10, diff);
    }

    @Test
    public void givenTwoDatesInJodaTime_whenDifferentiating_thenWeGetSix() {
        org.joda.time.LocalDate now = org.joda.time.LocalDate.now();
        org.joda.time.LocalDate sixDaysBehind = now.minusDays(6);

        long diff = Math.abs(Days.daysBetween(now, sixDaysBehind).getDays());

        assertEquals(6, diff);
    }

    @Test
    public void givenTwoDateTimesInJodaTime_whenDifferentiating_thenWeGetSix() {
        org.joda.time.LocalDateTime now = org.joda.time.LocalDateTime.now();
        org.joda.time.LocalDateTime sixMinutesBehind = now.minusMinutes(6);

        long diff = Math.abs(Minutes.minutesBetween(now, sixMinutesBehind).getMinutes());
        assertEquals(6, diff);

    }

    @Test
    public void givenTwoDateTimesInJodaTime_whenComputingDistanceInWeeks_thenFindIntegerWeeks() {
        DateTime dateTime1 = new DateTime(2024, 1, 17, 15, 50, 30);
	DateTime dateTime2 = new DateTime(2024, 6, 3, 10, 20, 55);

        int weeksDiff = Weeks.weeksBetween(dateTime1, dateTime2).getWeeks();

        assertEquals(19, weeksDiff);
    }

    @Test
    public void givenTwoDateTimesInJodaTime_whenComputingDistanceInDecimalWeeks_thenFindDecimalWeeks() {
        DateTime dateTime1 = new DateTime(2024, 1, 17, 15, 50, 30);
	DateTime dateTime2 = new DateTime(2024, 6, 3, 10, 20, 55);

        int days = Days.daysBetween(dateTime1, dateTime2).getDays();
	float weeksDiff=(float) (days/7.0);    

        assertEquals(19.571428, weeksDiff,0.001);
    }
	
    @Test
    public void givenTwoDatesInDate4j_whenDifferentiating_thenWeGetSix() {
        hirondelle.date4j.DateTime now = hirondelle.date4j.DateTime.now(TimeZone.getDefault());
        hirondelle.date4j.DateTime sixDaysBehind = now.minusDays(6);

        long diff = Math.abs(now.numDaysFrom(sixDaysBehind));

        assertEquals(6, diff);
    }
}
