package com.eljo.agileboard.service;

import com.eljo.agileboard.domain.User;
import com.eljo.agileboard.exception.InvalidUserException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Eljo.George on 11/2/2018.
 */

@Log
@Service
public class DashboardService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserService userService;

    public Map<String, Object> barChartOfStoryCountByStatusAndOwner() throws InvalidUserException {
        User currentUser = userService.getCurrentUser();

        List<Map<String, Object>> data = new ArrayList<>();
        Set<String> keys = new HashSet<>();
        Map<String, Object> response = new HashMap<>();
        response.put("data", data);
        response.put("keys", keys);

        Map<String, Object> newStories = new HashMap<>();
        newStories.put("status", "New");
        data.add(newStories);
        Map<String, Object> readyStories = new HashMap<>();
        readyStories.put("status", "Ready");
        data.add(readyStories);
        Map<String, Object> inProgressStories = new HashMap<>();
        inProgressStories.put("status", "InProgress");
        data.add(inProgressStories);
        Map<String, Object> completedStories = new HashMap<>();
        completedStories.put("status", "Completed");
        data.add(completedStories);
        Map<String, Object> acceptedStories = new HashMap<>();
        acceptedStories.put("status", "Accepted");
        data.add(acceptedStories);

        String query = "select s.status,u.username,count(s.id) from agileboard.story s join agileboard.app_user u on u.id=s.owner_id where s.project_id=? group by s.status,u.username";
        jdbcTemplate.query(query, new Object[]{currentUser.getProject().getId()},
                (rs, rowNum) -> new Object[]{rs.getString("status"), rs.getString("username"), rs.getInt("count")})
                .forEach(row -> {
                    populateResponse(row, data);
                    keys.add(String.valueOf(row[1]));
                });

        inProgressStories.put("status", "In Progress");
        return response;
    }

    public List<Map<String, Object>> tableOfStoryCountByOwnerAndStatus() throws InvalidUserException {
        Long projectId = userService.getCurrentUser().getProject().getId();

        List<Map<String, Object>> data = new ArrayList<>();

        String query = "select\n" +
                "u.username,\n" +
                "u.name,\n" +
                "(select count(*) from agileboard.story s where s.owner_id=u.id and s.project_id=? and s.status='New') \"New\",\n" +
                "(select count(*) from agileboard.story s where s.owner_id=u.id and s.project_id=? and s.status='Ready') \"Ready\",\n" +
                "(select count(*) from agileboard.story s where s.owner_id=u.id and s.project_id=? and s.status='InProgress') \"InProgress\",\n" +
                "(select count(*) from agileboard.story s where s.owner_id=u.id and s.project_id=? and s.status='Completed') \"Completed\",\n" +
                "(select count(*) from agileboard.story s where s.owner_id=u.id and s.project_id=? and s.status='Accepted') \"Accepted\"\n" +
                "from agileboard.app_user u where u.project_id=?";

        jdbcTemplate.query(query, new Object[]{projectId, projectId, projectId, projectId, projectId, projectId},
                (rs, rowNum) -> new Object[]{rs.getString("username"),
                        rs.getString("name"),
                        rs.getInt("New"),
                        rs.getInt("Ready"),
                        rs.getInt("InProgress"),
                        rs.getInt("Completed"),
                        rs.getInt("Accepted")}
        ).forEach(row -> {
            Map<String, Object> record = new HashMap<>();
            record.put("id", row[0]);
            record.put("name", row[1]);
            record.put("New", row[2]);
            record.put("Ready", row[3]);
            record.put("InProgress", row[4]);
            record.put("Completed", row[5]);
            record.put("Accepted", row[6]);

            data.add(record);
        });

        return data;
    }

    public List<Map<String, Object>> pieChartOfStoryCountByOwner() throws InvalidUserException {
        User currentUser = userService.getCurrentUser();

        List<Map<String, Object>> data = new ArrayList<>();

        String query = "select u.username,u.name,count(s.id) from agileboard.story s join agileboard.app_user u on u.id=s.owner_id where s.project_id=? group by u.username, u.name";
        jdbcTemplate.query(query, new Object[]{currentUser.getProject().getId()},
                (rs, rowNum) -> new Object[]{rs.getString("username"), rs.getString("name"), rs.getInt("count")})
                .forEach(row -> {
                    Map<String, Object> record = new HashMap<>();
                    record.put("id", row[0]);
                    record.put("label", row[1]);
                    record.put("value", row[2]);

                    data.add(record);
                });

        return data;
    }


    private void populateResponse(Object[] row, List<Map<String, Object>> data) {
        System.out.println(Arrays.toString(row));
        for (Map<String, Object> statusEntry : data) {
            if (statusEntry.containsValue(String.valueOf(row[0]))) {
                statusEntry.put(String.valueOf(row[1]), row[2]);
            }
        }
    }
}
