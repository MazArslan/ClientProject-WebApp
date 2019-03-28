package com.team11.clientproject.services;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface ExportDataService {
    List<LinkedHashMap<String, String>> getAllDatabaseInformation();
}
