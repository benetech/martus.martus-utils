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

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.SimpleTimeZone;

public class MartusCalendar
{
	public MartusCalendar()
	{
		cal = new GregorianCalendar(new SimpleTimeZone(UTC_OFFSET, "martus"));
		clearTimeOfDay();
	}
	
	public MartusCalendar(int year, int month, int day)
	{
		this();
		set(year, month, day);
	}
	
	public MartusCalendar(Calendar copyFrom)
	{
		this(copyFrom.get(Calendar.YEAR), copyFrom.get(Calendar.MONTH), copyFrom.get(Calendar.DAY_OF_MONTH));
	}
	
	public MartusCalendar(MartusCalendar copyFrom)
	{
		this(copyFrom.get(Calendar.YEAR), copyFrom.get(Calendar.MONTH), copyFrom.get(Calendar.DAY_OF_MONTH));
	}
	
	public int getGregorianYear()
	{
		return get(Calendar.YEAR);
	}
	
	public int getGregorianMonth()
	{
		return get(Calendar.MONTH);
	}
	
	public int getGregorianDay()
	{
		return get(Calendar.DAY_OF_MONTH);
	}
	
	private int get(int field)
	{
		return cal.get(field);
	}
	
	private void set(int field, int value)
	{
		cal.set(field, value);
	}
	
	public void set(int year, int month, int day)
	{
		set(Calendar.YEAR, year);
		set(Calendar.MONTH, month);
		set(Calendar.DAY_OF_MONTH, day);
	}
	
	public void add(int field, int value)
	{
		cal.add(field, value);
	}
	
	public boolean before(MartusCalendar other)
	{
		return cal.before(other.cal);
	}
	
	public boolean after(MartusCalendar other)
	{
		return cal.after(other.cal);
	}
	
	public Date getTime()
	{
		return cal.getTime();
	}
	
	public void setTime(Date newTime)
	{
		if(newTime.getTime() < 0)
			newTime = new Date(0);
		cal.setTime(newTime);
	}

	public Calendar getCalendar()
	{
		return cal;
	}
	
	private void clearTimeOfDay()
	{
		set(Calendar.HOUR, 12);
		set(Calendar.HOUR_OF_DAY, 12);
		set(Calendar.MINUTE, 0);
		set(Calendar.SECOND, 0);
		set(Calendar.MILLISECOND, 0);
		set(Calendar.AM_PM, Calendar.PM);
	}
	
	private static int UTC_OFFSET = 0;
	private Calendar cal;
}
