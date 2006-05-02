/*
 * Copyright 2006, The Benetech Initiative
 * 
 * This file is confidential and proprietary
 */
package org.martus.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;

public class DirectoryLock
{
	public static class AlreadyLockedException extends Exception {}

	public void lock(File directory) throws IOException, AlreadyLockedException
	{
		lockFile = new File(directory, "lock");
		FileOutputStream tempLockStream = new FileOutputStream(lockFile);
		FileLock lock = tempLockStream.getChannel().tryLock();
		if(lock == null)
			throw new AlreadyLockedException();
		lockStream = tempLockStream;
	}
	
	public void close() throws IOException
	{
		if(lockStream == null)
			return;
		
		FileOutputStream tempLockStream = lockStream;
		lockStream = null;
		tempLockStream.close();
		lockFile.delete();
	}
	
	public boolean isLocked()
	{
		return (lockStream != null);
	}
	
	private File lockFile;
	private FileOutputStream lockStream;
}
