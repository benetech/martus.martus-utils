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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class StreamCopier implements StreamFilter
{
	public void copyStream(InputStream in, OutputStream out) throws IOException
	{
		if(in == null)
			throw new IOException("Null InputStream");
		int got;
		byte[] bytes = new byte[streamBufferCopySize];
		while( (got=in.read(bytes)) >= 0)
			out.write(bytes, 0, got);
	}
	public final static int streamBufferCopySize = 1024;
}

