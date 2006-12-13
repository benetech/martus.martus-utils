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

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

public class DirectoryUtils
{
	
	static public void deleteEntireDirectoryTree(Vector directoriesToDelete)
	{
		for(int i = 0; i< directoriesToDelete.size(); ++i)
		{
			deleteEntireDirectoryTree((File)directoriesToDelete.get(i));
		}
	}
	
	static public void deleteEntireDirectoryTree(File startingDir)
	{
		deleteAllFilesOnlyInDirectory(startingDir);
				
		File[] foldersLeftToDelete = fileFilter(startingDir);
		if(foldersLeftToDelete != null)
		{
			for (int i = 0; i < foldersLeftToDelete.length; i++)
			{
				deleteEntireDirectoryTree(foldersLeftToDelete[i]);
			}
		}
		if(startingDir.exists() && !startingDir.delete())
			System.out.println("Unable to delete folder: " + startingDir.getPath());
	}			

	public static void deleteAllFilesOnlyInDirectory(File startingDir)
	{
		File[] filesToDelete = startingDir.listFiles();
		if(filesToDelete!=null)
		{	
			for (int i = 0; i < filesToDelete.length; i++)
			{
				if(filesToDelete[i].isFile() && !filesToDelete[i].delete())
					System.out.println("Unable to delete file: " + filesToDelete[i].getPath());
			}
		}
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
		if(foldersLeftToDelete != null)
		{
			for(int i = 0; i < foldersLeftToDelete.length; ++i)
			{	
				scrubAndDeleteEntireDirectoryTree(foldersLeftToDelete[i]);
			}
		}
		if(startingDir.exists() && !startingDir.delete())
			System.out.println("Unable to delete folder: " + startingDir.getPath());
	}
	
	public static void scrubAndDeleteFile(File fileToScrubAndDelete)
	{		
		try
		{
			ScrubFile.scrub(fileToScrubAndDelete);
		}
		catch (IOException nothingWeCanDoAboutThis)
		{	
		}			
		if(!fileToScrubAndDelete.delete())
			System.out.println("Unable to delete file: " + fileToScrubAndDelete.getPath());
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
	
	public static Vector getAllFilesLeastRecentFirst(File directory)
	{
		Vector sortedFileList = new Vector();
		if(!directory.exists())
			return sortedFileList;
		File[] allFilesAndDirectories = directory.listFiles();
		for(int i = 0; i < allFilesAndDirectories.length; ++i)
		{
			File fileToAdd = allFilesAndDirectories[i];
			if(!fileToAdd.isFile())
				continue;
			long fileToAddLastModified = fileToAdd.lastModified();
			int itemCount = sortedFileList.size();
			boolean fileAdded = false;
			for(int j= 0; j < itemCount; ++j)
			{
				if(fileToAddLastModified < ((File)sortedFileList.get(j)).lastModified())
				{
					sortedFileList.add(j, fileToAdd);
					fileAdded = true;
					break;
				}
			}
			if(!fileAdded)
				sortedFileList.add(fileToAdd);
		}
		return sortedFileList;
	}
	
	public static void copyDirectoryTree(File copyFrom, File copyTo) throws IOException
	{
		if(copyFrom.isDirectory())
		{
			copyTo.mkdirs();
			String fileList[] = copyFrom.list();

			for(int i = 0; i < fileList.length; i++)
			{
				File targetFile = new File(copyTo, fileList[i]);
				File sourceFile = new File(copyFrom, fileList[i]);
				copyDirectoryTree(sourceFile, targetFile);
			}
		}
		else
		{
			FileInputStream inputStream = new FileInputStream(copyFrom);
			FileOutputStream outputStream = new FileOutputStream(copyTo);
			int chararcterToCopy;
			while((chararcterToCopy = inputStream.read()) >= 0)
				outputStream.write(chararcterToCopy);
			inputStream.close();
			outputStream.close();
		}
	}
	
}
