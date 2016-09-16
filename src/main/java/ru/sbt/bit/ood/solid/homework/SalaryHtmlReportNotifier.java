package ru.sbt.bit.ood.solid.homework;

import java.sql.Connection;
import java.sql.ResultSet;

public class SalaryHtmlReportNotifier {

    private final Connection connection;

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        this.connection = databaseConnection;
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, DateRange dateRange, String recipients) {

        SqlQuery sqlQuery = new SqlQuery(connection);
        ResultSet results = sqlQuery.getResultFromDepartament(departmentId, dateRange);

        GenerateHtml generateHtml = new GenerateHtml();
        StringBuilder resultingHtml = generateHtml.getStringBuilderHtml(results);

        MailService mailService = new MailService();
        mailService.sendMail(recipients, resultingHtml);
    }
}