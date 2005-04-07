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
package org.martus.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TokenReplacement 
{

	public static class TokenInvalidException extends Exception {}

	public static String replaceTokens(String original, Map tokenReplacement) throws TokenInvalidException 
	{
		String revised = original;
		for (Iterator keys = tokenReplacement.keySet().iterator(); keys.hasNext();)
		{
			String token = (String) keys.next();
			checkToken(token);
			String replacement = (String)tokenReplacement.get(token);
			//Replace all \'s in replacement with \\ so the replaceall regex will not throw away single \ in the case of pathnames. 
			replacement = replacement.replaceAll("\\\\","\\\\\\\\");
			revised = revised.replaceAll(token, replacement);
		}
		return revised;		
	}
	
	public static String[] replaceTokens(String[] original, Map tokenReplacement) throws TokenInvalidException
	{
		String[] revised = new String[original.length];
		for (int i = 0; i < original.length; ++i)
		{
			revised[i] = replaceTokens(original[i], tokenReplacement);
		}
		return revised;		
	}
	
	public static String replaceToken(String originalMessage, String token, String replacementValue) throws TokenInvalidException
	{
		HashMap map = new HashMap();
		map.put(token, replacementValue);
		return TokenReplacement.replaceTokens(originalMessage, map);
		
	}
	
	private static void checkToken(String token) throws TokenInvalidException
	{
		for(int i = 0; i<token.length(); ++i)
		{
			char singleChar = token.charAt(i);
			if(singleChar == '#')
				continue;
			if(!Character.isLetterOrDigit(singleChar))
				throw new TokenInvalidException();
		}
		
	}
}
