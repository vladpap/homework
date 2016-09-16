package ru.sbt.bit.ood.solid.homework;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SqlQuery {
    private final Connection connection;

    public SqlQuery(Connection connection) {
        this.connection = connection;
    }

    public ResultSet getResultFromDepartament(String departmentId, DateRange dateRange) {
        try {
            PreparedStatement ps = connection.prepareStatement("select emp.id as emp_id, emp.name as" +
                    " amp_name, sum(salary) as salary from employee emp left join" +
                    "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
                    " sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
            // inject parameters to sql
            ps.setString(0, departmentId);
            ps.setDate(1, new java.sql.Date(dateRange.getDateFrom().toEpochDay()));
            ps.setDate(2, new java.sql.Date(dateRange.getDateTo().toEpochDay()));
            return ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
