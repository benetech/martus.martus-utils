package org.martus.util.xml;

import org.martus.util.TestCaseEnhanced;

public class TestXmlUtilities extends TestCaseEnhanced
{
	public TestXmlUtilities(String name)
	{
		super(name);
	}
	
	public void testStripXmlHeader()
	{
		verifyXmlHeaderStripped("", "");
		verifyXmlHeaderStripped("", "<?xml version=\"1.1\"?>");
		verifyXmlHeaderStripped("", "<?xml version=\"1.0\"?>");
		verifyXmlHeaderStripped("", "<?xml version=\"1.0\" ?>");
		verifyXmlHeaderStripped("", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		verifyXmlHeaderStripped("", "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
	}

	private void verifyXmlHeaderStripped(String expectedValue, String valueToStripHeaderFrom)
	{
		assertEquals("Xml header not removed?", expectedValue, XmlUtilities.stripXmlHeader(valueToStripHeaderFrom));
	}
}
