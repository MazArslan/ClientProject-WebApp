package com.team11.clientproject.services;

import com.team11.clientproject.repositories.ExportDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class ExportDataServiceImpl implements ExportDataService {
    private final ExportDataRepository exportDataRepository;

    @Autowired
    public ExportDataServiceImpl(ExportDataRepository exportDataRepository) {
        this.exportDataRepository = exportDataRepository;
    }

    @Override
    public List<LinkedHashMap<String, String>> getAllDatabaseInformation() {
        return exportDataRepository.getAllDatabaseInformation();
    }
}
