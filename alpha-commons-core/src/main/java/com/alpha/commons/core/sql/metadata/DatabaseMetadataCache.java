package com.alpha.commons.core.sql.metadata;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class DatabaseMetadataCache implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2558995811032947730L;

    private static Map<String, Database> databases = new HashMap<String, Database>();

    public static Database getDatabase() {
        return getDatabase("Mysql");
    }

    public static Database getDatabase(String key) {
        return databases.get(key);
    }

    public static void setDatabbase(String key, Database database) {
        databases.put(key, database);
    }
}
