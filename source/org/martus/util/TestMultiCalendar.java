/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2006, Beneficent
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


public class TestMultiCalendar extends TestCaseEnhanced
{
	public TestMultiCalendar(String name)
	{
		super(name);
	}

	public void testEquals()
	{
		MultiCalendar a = new MultiCalendar();
		MultiCalendar b = new MultiCalendar();
		assertEquals("not identical?", a, b);
		assertEquals("hash not the same?", a.hashCode(), b.hashCode());
		
		MultiCalendar c = new MultiCalendar(a);
		c.addDays(1000);
		assertNotEquals("not different?", a, c);
		// all MultiCalendar hashCodes are identical, due to
		// policy inside Java's Calendar.hashCode method
		
		assertNotEquals("didn't check type?", a, new Object());
		
	}
	
	public void testLegacyThaiDates()
	{
		assertFalse("Defaulting to adjusting legacy Thai dates?", MultiCalendar.adjustThaiLegacyDates);
		
		String legacyIso = "2548-10-20";
		MultiCalendar notLegacy = MultiCalendar.createFromIsoDateString(legacyIso);
		assertEquals("adjusted the year?", 2548, notLegacy.getGregorianYear());
		
		MultiCalendar.adjustThaiLegacyDates = true;
		try
		{
			MultiCalendar legacy = MultiCalendar.createFromIsoDateString(legacyIso);
			assertEquals("didn't adjust year?", 2005, legacy.getGregorianYear());
		}
		finally
		{
			MultiCalendar.adjustThaiLegacyDates = false;
		}
	}

	public void testLegacyPersianDates()
	{
		assertFalse("Defaulting to adjusting legacy persian dates?", MultiCalendar.adjustPersianLegacyDates);
		
		String legacyIso = "1384-07-28";
		MultiCalendar notLegacy = MultiCalendar.createFromIsoDateString(legacyIso);
		assertEquals("adjusted the year?", 1384, notLegacy.getGregorianYear());
		
		MultiCalendar.adjustPersianLegacyDates = true;
		try
		{
			MultiCalendar legacy = MultiCalendar.createFromIsoDateString(legacyIso);
			assertEquals("didn't adjust year?", 2005, legacy.getGregorianYear());
			assertEquals("didn't adjust month?", 10, legacy.getGregorianMonth());
			assertEquals("didn't adjust day?", 20, legacy.getGregorianDay());
		}
		finally
		{
			MultiCalendar.adjustPersianLegacyDates = false;
		}
	}
}
