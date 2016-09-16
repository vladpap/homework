package ru.sbt.bit.ood.solid.homework;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenerateHtml {
    public StringBuilder getStringBuilderHtml(ResultSet results) {
        StringBuilder resultingHtml = new StringBuilder();
        resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
        double totals = 0;
        try {
            while (results.next()) {
                // process each row of query results
                totals = getTotals(results, resultingHtml, totals);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        resultingHtml.append("</table></body></html>");
        return resultingHtml;
    }

    private double getTotals(ResultSet results, StringBuilder resultingHtml, double totals) throws SQLException {
        resultingHtml.append("<tr>"); // add row start tag
        resultingHtml.append("<td>").append(results.getString("emp_name")).append("</td>"); // appending employee name
        resultingHtml.append("<td>").append(results.getDouble("salary")).append("</td>"); // appending employee salary for period
        resultingHtml.append("</tr>"); // add row end tag
        totals += results.getDouble("salary"); // add salary to totals
        return totals;
    }
}
