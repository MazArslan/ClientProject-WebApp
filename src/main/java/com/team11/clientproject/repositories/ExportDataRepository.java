package com.team11.clientproject.repositories;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface ExportDataRepository {
    List<LinkedHashMap<String, String>> getAllDatabaseInformation();
}
