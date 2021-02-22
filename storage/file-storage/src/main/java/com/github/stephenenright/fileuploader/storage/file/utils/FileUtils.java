package com.github.stephenenright.fileuploader.storage.file.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public abstract class FileUtils {

    public static void forceDelete(File file) throws IOException {
        if (file.isDirectory()) {
            DirectoryUtils.deleteDirectory(file);
        } else {
            boolean filePresent = file.exists();
            if (!file.delete()) {
                if (!filePresent) {
                    throw new FileNotFoundException("File does not exist: " + file);
                }
                String message = "Unable to delete file: " + file;
                throw new IOException(message);
            }
        }
    }


}
