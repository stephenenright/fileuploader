package com.github.stephenenright.fileuploader.storage.api;

import java.io.InputStream;
import java.util.Map;

public interface StorageApi {

    StorageItem save(String parent, String name, InputStream inputStream, Map<String, Object> metaData);

    boolean exists(String parent, String name);

    InputStream get(String parent, String name);

    InputStream getQuietly(String parent, String name);

    void delete(String parent, String name);

    void deleteQuietly(String parent, String name);

    void deleteParent(String parent);

    void deleteParentQuietly(String parent);

    String createRelativeName(String parent, String name);

    String joinPaths(String... paths);

    <RT> RT getProvider(Class<RT> cls);
}
