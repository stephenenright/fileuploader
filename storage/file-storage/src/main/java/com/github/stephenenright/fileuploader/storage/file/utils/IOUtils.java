package com.github.stephenenright.fileuploader.storage.file.utils;

import java.io.*;

public abstract class IOUtils {

    public static void write(InputStream is, File f) throws IOException {
        try (is; OutputStream os = new FileOutputStream(f)) {
            int read = 0;
            byte[] buffer = new byte[8096];
            while ((read = is.read(buffer)) > 0) {
                os.write(buffer, 0, read);
            }
        }
    }
}
