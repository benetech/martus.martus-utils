/*

The Martus(tm) free, social justice documentation and
monitoring software. Copyright (C) 2001-2003, Beneficent
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

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

public class DirectoryTreeRemover
{
	static public void deleteEntireDirectoryTree(File startingDir)
	{
		File[] filesToDelete = startingDir.listFiles();
		if(filesToDelete!=null)
		{	
			for (int i = 0; i < filesToDelete.length; i++)
			{
				filesToDelete[i].delete();
			}
		}
				
		File[] foldersLeftToDelete = fileFilter(startingDir);
		deleteSubDirectory(foldersLeftToDelete);
		startingDir.delete();
	}			
	
	private static void scrubAndDeleteFile(File filesToDelete)
	{		
		try
		{
			ScrubFile.scrub(filesToDelete);
			filesToDelete.delete();
		}
		catch (IOException ioe)
		{		
		}			
	}
	
	private static void deleteSubDirectory(File[] foldersLeftToDelete)
	{		
		if(foldersLeftToDelete != null)
		{
			for (int i = 0; i < foldersLeftToDelete.length; i++)
			{
				deleteEntireDirectoryTree(foldersLeftToDelete[i]);
				foldersLeftToDelete[i].delete();
			}
		}
	}	
	
	private static void scrubAndDeleteSubDirectory(File[] foldersLeftToDelete)
	{
		if(foldersLeftToDelete != null)
		{
			for (int i = 0; i < foldersLeftToDelete.length; i++)
			{
				deleteEntireDirectoryTree(foldersLeftToDelete[i]);
				scrubAndDeleteFile(foldersLeftToDelete[i]);
			}
		}
	}	

	private static File[] fileFilter(File startingDir)
	{
		File[] foldersLeftToDelete = startingDir.listFiles(new FileFilter()
		{
			public boolean accept(File pathname)
			{
				return pathname.isDirectory();	
			}
		});
		return foldersLeftToDelete;
	}
	
	static public void scrubAndDeleteEntireDirectoryTree(File startingDir)
	{
		File[] filesToDelete = startingDir.listFiles();
		if(filesToDelete!=null)
		{	
			for (int i = 0; i < filesToDelete.length; i++)
			{
				scrubAndDeleteFile(filesToDelete[i]);
			}
		}
				
		File[] foldersLeftToDelete = fileFilter(startingDir);
		scrubAndDeleteSubDirectory(foldersLeftToDelete);
		startingDir.delete();
	}
	
	static public boolean isContainSubDir(File startingDir)
	{
		File[] files = startingDir.listFiles();
		
		if (files != null)
		{			
			for (int i = 0; i < files.length; i++)
			{				
				if (files[i].isDirectory())
					return true;
			}
		}		
		return false;
	}
}
