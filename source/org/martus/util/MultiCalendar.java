/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2005, Beneficent
Technology, Inc. (Benetech).

Martus is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; either
version 2 of the License, or (at your option) any later
version with the additions and exceptions described in the
accompanying Martus license file entitled "license.txt".

It is distributed WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, including warranties of fitness of purpose or
merchantability.  See the accompanying Martus License and
GPL license for more details on the required license terms
for this software.

You should have received a copy of the GNU General Public
License along with this program; if not, write to the Free
Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA 02111-1307, USA.

*/

package org.martus.util;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

public class MultiCalendar
{
	public static MultiCalendar createFromGregorianYearMonthDay(int year, int month, int day)
	{
		MultiCalendar cal = new MultiCalendar();
		cal.setGregorian(year, month, day);
		return cal;
	}

	public static MultiCalendar createFromIsoDateString(String storedDateString)
	{
		int yearStart = 0;
		int yearLength = 4;
		int yearEnd = yearStart + yearLength;
		int monthStart = yearEnd + 1;
		int monthLength = 2;
		int monthEnd = monthStart + monthLength;
		int dayStart = monthEnd + 1;
		int dayLength = 2;
		int dayEnd = dayStart + dayLength;
		int year = Integer.parseInt(storedDateString.substring(yearStart, yearEnd));
		int month = Integer.parseInt(storedDateString.substring(monthStart, monthEnd));
		int day = Integer.parseInt(storedDateString.substring(dayStart, dayEnd));
		int JANUARY = 1;
		int DECEMBER = 12;
		if(year < 0 || month < JANUARY || month > DECEMBER || day < 1 || day > 31)
			throw new RuntimeException("invalid date: " + storedDateString);
		return createFromGregorianYearMonthDay(year, month, day);
	}

	public MultiCalendar()
	{
		set(createGregorianCalendarToday());
	}
	
	public MultiCalendar(GregorianCalendar copyFrom)
	{
		set(copyFrom);
	}

	public MultiCalendar(MultiCalendar copyFrom)
	{
		set(copyFrom.getGregorianCalendar());
	}
	
	public int getGregorianYear()
	{
		return getGregorianCalendar().get(Calendar.YEAR);
	}
	
	public int getGregorianMonth()
	{
		return getGregorianCalendar().get(Calendar.MONTH) + 1;
	}
	
	public int getGregorianDay()
	{
		return getGregorianCalendar().get(Calendar.DAY_OF_MONTH);
	}
	
	public void setGregorian(int year, int month, int day)
	{
		gregorianYear = year;
		gregorianMonth = month;
		gregorianDay = day;
	}
	
	public void addDays(int value)
	{
		GregorianCalendar cal = getGregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, value);
		set(cal);
	}
	
	public boolean before(MultiCalendar other)
	{
		return getGregorianCalendar().before(other.getGregorianCalendar());
	}
	
	public boolean after(MultiCalendar other)
	{
		return getGregorianCalendar().after(other.getGregorianCalendar());
	}
	
	public Date getTime()
	{
		return getGregorianCalendar().getTime();
	}
	
	public void setTime(Date newTime)
	{
		if(newTime.getTime() < 0)
			newTime = new Date(0);
		GregorianCalendar cal = createGregorianCalendarToday();
		cal.setTime(newTime);
		set(cal);
	}

	public GregorianCalendar getGregorianCalendar()
	{
		return createGregorianCalendar(gregorianYear, gregorianMonth, gregorianDay);
	}
	
	public String toIsoDateString()
	{
		int year = getGregorianYear();
		int month = getGregorianMonth();
		int day = getGregorianDay();
		return fourDigit.format(year) + "-" + twoDigit.format(month) + "-" + twoDigit.format(day);
	}

	private static GregorianCalendar createGregorianCalendar(int year, int month, int day)
	{
		GregorianCalendar cal = createGregorianCalendarToday();
		cal.set(year, month - 1, day);
		return cal;
	}
	
	private static GregorianCalendar createGregorianCalendarToday()
	{
		GregorianCalendar cal = new GregorianCalendar(new SimpleTimeZone(UTC_OFFSET, "martus"));
		cal.set(Calendar.HOUR, 12);
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.AM_PM, Calendar.PM);
		return cal;
	}
	
	private void set(GregorianCalendar copyFrom)
	{
		setGregorian(copyFrom.get(Calendar.YEAR), copyFrom.get(Calendar.MONTH) + 1, copyFrom.get(Calendar.DAY_OF_MONTH));
	}
	
	private static DecimalFormat fourDigit = new DecimalFormat("0000");
	private static DecimalFormat twoDigit = new DecimalFormat("00");

	private static int UTC_OFFSET = 0;
	int gregorianYear;
	int gregorianMonth;
	int gregorianDay;
}
