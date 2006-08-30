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
import java.io.StringReader;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;


public class SimpleXmlParser extends DefaultHandler
{
	public static void parse(SimpleXmlDefaultLoader loaderToUse, String xmlText) throws 
		IOException, ParserConfigurationException, SAXException
	{
		parse(loaderToUse, new StringReader(xmlText));
	}
	
	public static void parse(SimpleXmlDefaultLoader loaderToUse, Reader xmlReader) throws 
		IOException, ParserConfigurationException, SAXException
	{
		new SimpleXmlParser(loaderToUse, xmlReader);
	}
	
	private SimpleXmlParser(SimpleXmlDefaultLoader loaderToUse, Reader xmlReader) throws 
		IOException, ParserConfigurationException, SAXException
	{
		loaders = new Vector();
		loaders.add(loaderToUse);
		SAXParser saxParser = factory.newSAXParser();
		saxParser.parse(new InputSource(xmlReader), this);
	}
	
	public void startDocument() throws SAXParseException
	{
	}
	
	public void startElement(
		String uri,
		String localName,
		String qName,
		Attributes attributes)
		throws SAXException
	{
		if(!gotFirstTag)
		{
			String expectedTag = getCurrentLoader().getTag();
			if(!qName.equals(expectedTag))
				throw new SAXParseException("SimpleXmlParser expected root: " + expectedTag, null);
			gotFirstTag = true;
			getCurrentLoader().startDocument(attributes);
			return;
		}
		SimpleXmlDefaultLoader newLoader = getCurrentLoader().startElement(qName);
		insertNewCurrentLoader(newLoader);
		getCurrentLoader().startDocument(attributes);
	}

	public void characters(char[] ch, int start, int length)
		throws SAXException
	{
		getCurrentLoader().addText(ch, start, length);
	}

	public void endElement(String uri, String localName, String qName)
		throws SAXException
	{
		if(loaders.size() > 1)
		{
			SimpleXmlDefaultLoader endingLoader = getCurrentLoader();
			endingLoader.endDocument();

			removeCurrentLoader();
			getCurrentLoader().endElement(endingLoader.getTag(), endingLoader);
		}
	}

	public void endDocument() throws SAXParseException
	{
		getCurrentLoader().endDocument();
	}


	private SimpleXmlDefaultLoader getCurrentLoader()
	{
		return (SimpleXmlDefaultLoader)loaders.get(0);
	}

	private void insertNewCurrentLoader(SimpleXmlDefaultLoader newLoader)
	{
		loaders.insertElementAt(newLoader, 0);
	}

	private void removeCurrentLoader()
	{
		loaders.remove(0);
	}

	boolean gotFirstTag;
	Vector loaders;

	private static SAXParserFactory factory = SAXParserFactory.newInstance();
}
