package com.github.stephenenright.fileuploader.storage.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.function.Supplier;

import com.github.stephenenright.fileuploader.storage.file.utils.*;
import org.apache.commons.io.FilenameUtils;

import com.github.stephenenright.fileuploader.storage.api.BaseStorageProvider;
import com.github.stephenenright.fileuploader.storage.api.StorageApi;
import com.github.stephenenright.fileuploader.storage.api.StorageIOException;
import com.github.stephenenright.fileuploader.storage.api.StorageItemNotFoundException;

public class FileStorageImpl extends BaseStorageProvider implements StorageApi {

	private String rootDirectory;
	private Supplier<String> rootDirectorySupplier;
	
	
	public FileStorageImpl(String rootDirectory) {
		AssertUtils.strNotNullAndNotEmpty(rootDirectory, "rootDirectory is required");
		this.rootDirectory = rootDirectory;
	}
	
	public FileStorageImpl(Supplier<String> rootDirectorySupplier) {
		AssertUtils.notNull(rootDirectorySupplier, "rootDirectorySupplier is required");
		this.rootDirectorySupplier = rootDirectorySupplier;
	}

	@Override
	public FileStorageItem save(String parent, String name, InputStream inputStream, Map<String,Object> metaData) {
		try {
			
			Object[] fileDetails =  createOutputFile(concatParentWithRoot(parent), name);
			
			File outputFile = (File) fileDetails[0];
			
			if(!outputFile.getParentFile().exists()) {
				org.apache.commons.io.FileUtils.forceMkdir(outputFile.getParentFile());
			}
			
			IOUtils.write(inputStream, outputFile);
			return new FileStorageItem((String) fileDetails[1], outputFile.length());
		}
		catch(IOException ioe) {
			throw new StorageIOException("Unable to save file: " + name, ioe);
		}
	}
	

	@Override
    public boolean exists(String parent, String name) {
	    return new File(concatParentWithRoot(parent),name).exists();
    }

    @Override
	public InputStream get(String parent, String name) {
		File file = new File(concatParentWithRoot(parent),name);
		
		if(!file.exists()) {
			throw new StorageItemNotFoundException("File: " + file.getAbsolutePath() + " not found");
		}
		
		try {
			return new FileInputStream(file);
		}
		catch(FileNotFoundException fnfe) {
			throw new StorageItemNotFoundException("File: " + file.getAbsolutePath() + " not found", fnfe);
		}
	}

	@Override
	public InputStream getQuietly(String parent, String name) {
		try {
			return get(parent, name);
		}
		catch(Exception e) {
			return null;
		}
	}

	@Override
	public void delete(String parent, String name) {
		File file = new File(concatParentWithRoot(parent),name);
		
		if(!file.exists()) {
			throw new StorageItemNotFoundException("File: " + file.getAbsolutePath() + " not found");
		}
		
		try {
			FileUtils.forceDelete(file);
		}
		catch(IOException ioe) {
			throw new StorageIOException("Unable to delete file: " + file.getAbsolutePath(),ioe);
		}
		
	}

	@Override
	public void deleteParent(String parent ) {
		File file = new File(concatParentWithRoot(parent));
		
		if(!file.exists()) {
			throw new StorageItemNotFoundException("Directory: " + file.getAbsolutePath() + " not found");
		}
		
		if(!file.isDirectory()) {
			throw new StorageItemNotFoundException("Path: " + file.getAbsolutePath() + " is not a directory");
		}
		
		try {
			DirectoryUtils.deleteDirectory(file);
		}
		catch(IOException ioe) {
			throw new StorageIOException("Unable to delete directory: " + file.getAbsolutePath(), ioe);
		}
		
	}

	@Override
	public void deleteQuietly(String parent, String name) {
		File file = new File(concatParentWithRoot(parent),name);
		
		if(!file.exists()) {
			return;
		}
		
		try {
			FileUtils.forceDelete(file);
		}
		catch(IOException ioe) {
			return ;
		}
	}

	@Override
	public void deleteParentQuietly(String parent) {
		try {
			deleteParent(parent);
		}
		catch(Throwable t) {
			return;
		}
	}
	
	private Object[] createOutputFile(String parentDirectory, String fileName) {
		final String outputFileName = FilenameUtils.concat(parentDirectory, fileName);
		final String baseFileName = FilenameUtils.getBaseName(outputFileName);
		final String extension = FilenameUtils.getExtension(outputFileName);
		
		File outputFile = new File(outputFileName);
		
		int count = 1;
		File parentDir = outputFile.getParentFile();
		while(outputFile.exists()) {
			outputFile = new File(parentDir,baseFileName + "_" + count + "." + extension); 
			count++;
			
		}
		
		if(count > 1) {
			final String newFileName = fileName + "_" + count + "." + extension;
			return new Object[] {outputFile, newFileName};
		}
		
		return new Object[] {outputFile, fileName};
	}
	
	private String concatParentWithRoot(String parent) {
		if(StringUtils.isNullOrEmpty(parent)) {
			return getRootDirectory();
		}
		
		return FilenameUtils.concat(getRootDirectory(), parent);
	}
	
	
	@Override
	public String joinPaths(String... paths) {
		if(paths==null || paths.length==0) {
			return "";
		}
		
		String currentPath = "";
		for(String path : paths) {
			currentPath = FilenameUtils.concat(currentPath, path);
		}
		
		return currentPath;
	}

	@Override
	public String createRelativeName(String parent, String name) {
		return FilenameUtils.concat(parent, name);
	}

	private String getRootDirectory() {
		if(rootDirectorySupplier!=null) {
			return rootDirectorySupplier.get();
		}
		
		return rootDirectory;
	}
}
