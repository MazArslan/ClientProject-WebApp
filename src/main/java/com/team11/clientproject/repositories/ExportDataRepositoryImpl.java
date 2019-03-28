package com.team11.clientproject.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSetMetaData;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ExportDataRepositoryImpl implements ExportDataRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ExportDataRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<LinkedHashMap<String, String>> getAllDatabaseInformation() {
        return jdbcTemplate.query("SELECT * from votes\n" +
                " LEFT JOIN users on users.user_id=votes.FK_user_id\n" +
                " LEFT JOIN equality_diversity ON equality_diversity.FK_user_id=users.user_id\n" +
                " LEFT JOIN images ON images.image_id=votes.FK_image_id\n" +
                " LEFT JOIN tutorial_completion ON tutorial_completion.FK_user_id=users.user_id\n" +
                " LEFT JOIN tutorials ON tutorials.tutorial_id=tutorial_completion.FK_tutorial_id\n", (rs, n) -> {
            ResultSetMetaData resultData = rs.getMetaData();
            var map = new LinkedHashMap<String, String>();
            for (var i = 1; i <= resultData.getColumnCount(); i++) {
                map.put(
                        resultData.getColumnName(i),
                        rs.getString(resultData.getColumnName(i))
                );
            }
            return map;
        });
    }
}
