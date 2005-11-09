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

import java.io.IOException;
import java.io.Reader;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class SimpleXmlDefaultLoader
{
	public SimpleXmlDefaultLoader(String tag)
	{
		thisTag = tag;
	}
	
	public void parse(String xml) throws IOException, ParserConfigurationException, SAXException
	{
		SimpleXmlParser.parse(this, xml);
	}
	
	public void parse(Reader reader) throws IOException, ParserConfigurationException, SAXException
	{
		SimpleXmlParser.parse(this, reader);
	}

	public void throwOnUnexpectedTags()
	{
		shouldThrowOnUnexpected = true;
	}
	
	public boolean foundUnknownTags()
	{
		return foundUnknown;
	}
	

	public String getTag()
	{
		return thisTag;
	}
	
	public void startDocument(Attributes attrs) throws SAXParseException
	{
	}
	
	public SimpleXmlDefaultLoader startElement(String tag) throws SAXParseException
	{
		if(shouldThrowOnUnexpected)
			throw new SAXParseException(getTag() + ": Unexpected tag: " + tag, null);

		foundUnknown = true;
		SimpleXmlDefaultLoader newLoader = new SimpleXmlDefaultLoader(tag);
		return newLoader;
	}
	
	public void addText(char[] ch, int start, int length) throws SAXParseException
	{
	}
	
	public void endElement(String tag, SimpleXmlDefaultLoader ended) throws SAXParseException
	{
		if(shouldThrowOnUnexpected)
			throw new SAXParseException(getTag() + ": Unexpected end: " + tag, null);
	}
	
	public void endDocument() throws SAXParseException
	{
	}
	
	String thisTag;
	boolean shouldThrowOnUnexpected;
	boolean foundUnknown;
}
