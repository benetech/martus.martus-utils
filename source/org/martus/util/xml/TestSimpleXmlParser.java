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

import java.util.Map;
import java.util.Vector;

import junit.framework.TestCase;

import org.xml.sax.SAXParseException;


public class TestSimpleXmlParser extends TestCase
{
	public TestSimpleXmlParser(String name)
	{
		super(name);
	}
	
	public void testEmptyXml() throws Exception
	{
		try
		{
			SimpleXmlDefaultLoader loader = new SimpleXmlStringLoader("ExpectedRootTag");
			String emptyXmlString = "";
			SimpleXmlParser.parse(loader, emptyXmlString);
			fail("Should have thrown!");
		}
		catch(SAXParseException ignoreExpected)
		{
			;
		}
	}
	
	public void testStringLoader() throws Exception
	{
		SimpleXmlStringLoader loader = new SimpleXmlStringLoader("name");
		SimpleXmlParser.parse(loader, "<name>testing</name>");
		assertEquals("testing", loader.getText());
	}

	public void testMapLoader() throws Exception
	{
		SimpleXmlMapLoader loader = new SimpleXmlMapLoader("SampleMap");
		SimpleXmlParser.parse(loader, "<SampleMap><key>value</key></SampleMap>");
		assertEquals("value", loader.getMap().get("key"));
	}

	public void testSimpleNesting() throws Exception
	{
		SimpleXmlMapLoader loader = new SimpleXmlMapLoader("object");
		SimpleXmlParser.parse(loader, "<object><a>text</a><big>yes</big><object>unrelated</object></object>");
		Map data = loader.getMap();
		assertEquals(3, data.size());
		assertEquals("text", data.get("a"));
		assertEquals("yes", data.get("big"));
		assertEquals("unrelated", data.get("object"));
	}
	
	public void testNestedBadly() throws Exception
	{
		CustomFieldsLoader loader = new CustomFieldsLoader(null, "fields");
		try
		{
			SimpleXmlParser.parse(loader, "<notfields></notfields>");
			fail("Should have thrown for wrong root tag");
		}
		catch(SAXParseException ignoreExpected)
		{
		}
		
		try
		{
			SimpleXmlParser.parse(loader, "<fields><oops></oops></fields>");
			fail("Should have thrown for unexpected tag");
		}
		catch(SAXParseException ignoreExpected)
		{
		}
	}
	
	public void testUnexpectedText() throws Exception
	{
		CustomFieldsLoader loader = new CustomFieldsLoader(null, "fields");
		SimpleXmlParser.parse(loader, "<fields>should be ignored</fields>");
	}

	public void testFullSample() throws Exception
	{
		CustomFields fields = new CustomFields();
		CustomFieldsLoader loader = new CustomFieldsLoader(fields, "fields");
		SimpleXmlParser.parse(loader, 
			"<fields>" + 
				"<style>bogus</style>" +
				"<field>" +
					"<tag>oh</tag><label>boo</label><type>hoo</type>" +
				"</field>" + 
				"<field>" + 
					"<tag>tweedle</tag><label>dee</label><type>tweedle</type><choices>dum</choices>" + 
				"</field>" + 
			"</fields>");

		assertEquals(2, fields.size());
		assertEquals("bogus", fields.getStyle());

		Map firstField = fields.getField(0);
		assertEquals(3, firstField.size());
		assertEquals("hoo", firstField.get("type"));

		Map secondField = fields.getField(1); 
		assertEquals(4, secondField.size());
		assertEquals("tweedle", secondField.get("tag"));
		assertEquals("tweedle", secondField.get("type"));
		assertEquals("dum", secondField.get("choices")); 
	}
	
	class CustomFields
	{
		CustomFields()
		{
			fields = new Vector();
		}
		
		int size()
		{
			return fields.size();
		}
		
		void setStyle(String style)
		{
			this.style = style;
		}

		String getStyle()
		{
			return style;
		}
		
		void addField(Map fieldData)
		{
			fields.add(fieldData);
		}
		
		Map getField(int index)
		{
			return (Map)fields.get(index);
		}

		private String style;
		private Vector fields;
	}

	class CustomFieldsLoader extends SimpleXmlDefaultLoader
	{
		CustomFieldsLoader(CustomFields customFieldSpecToFill, String tag)
		{
			super(tag);
			customFields = customFieldSpecToFill;
		}
		
		public SimpleXmlDefaultLoader startElement(String tag) throws SAXParseException
		{
			if(tag.equals(styleTag))
			{
				return new SimpleXmlStringLoader(tag); 
			}
			else if(tag.equals(fieldTag))
			{
				return new SimpleXmlMapLoader(tag);
			}
			else
				throw new SAXParseException(getTag() + ": Unexpected tag: " + tag, null);
		}

		public void endElement(SimpleXmlDefaultLoader ended) throws SAXParseException
		{
			String tag = ended.getTag();
			if(tag.equals(styleTag))
			{
				SimpleXmlStringLoader styleHandler = (SimpleXmlStringLoader)ended;
				customFields.setStyle(styleHandler.getText());
				styleHandler = null;
			}
			else if(tag.equals(fieldTag))
			{
				SimpleXmlMapLoader mapHandler = (SimpleXmlMapLoader)ended; 
				customFields.addField(mapHandler.getMap());
				mapHandler = null;
			}
			else
				throw new SAXParseException(getTag() + ": Unexpected end: " + tag, null);
		}
		
		CustomFields customFields;

		public static final String styleTag = "style";
		public static final String fieldTag = "field";
	}

}
