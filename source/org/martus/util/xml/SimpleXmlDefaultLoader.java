/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2001-2004, Beneficent
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

package org.martus.util.xml;

import org.xml.sax.SAXParseException;


public abstract class SimpleXmlDefaultLoader
{
	public SimpleXmlDefaultLoader(String tag)
	{
		thisTag = tag;
	}
	
	public String getTag()
	{
		return thisTag;
	}
	
	public void startDocument()
	{
	}
	
	public SimpleXmlDefaultLoader startElement(String tag) throws SAXParseException
	{
		throw new SAXParseException(getTag() + ": Unexpected tag: " + tag, null);
	}
	
	public void addText(char[] ch, int start, int length) throws SAXParseException
	{
		throw new SAXParseException(getTag() + ": Unexpected text: " + new String(ch, start, length), null);
	}
	
	public void endElement(SimpleXmlDefaultLoader ended) throws SAXParseException
	{
	}
	
	public void endDocument()
	{
	}
	
	String thisTag;
}
