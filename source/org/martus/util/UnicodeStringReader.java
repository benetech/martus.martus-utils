/*
 * Copyright 2005, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class UnicodeStringReader extends UnicodeReader
{
	public UnicodeStringReader(String stringToRead) throws IOException
	{
		super(new ByteArrayInputStream(stringToRead.getBytes("UTF-8")));
	}

}
