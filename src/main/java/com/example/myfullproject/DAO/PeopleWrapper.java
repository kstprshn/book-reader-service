package com.example.myfullproject.DAO;

import com.example.myfullproject.Entities.People;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PeopleWrapper implements RowMapper<People> {
    @Override
    public People mapRow(ResultSet rs, int rowNum) throws SQLException {
        People people = new People();
        people.setId(rs.getLong("person_id"));
        people.setName(rs.getString("name"));
        people.setDate_of_birth(rs.getDate("date_of_birth"));
        return people;
    }
}
