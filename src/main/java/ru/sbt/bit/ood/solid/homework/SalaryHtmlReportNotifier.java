package ru.sbt.bit.ood.solid.homework;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.sql.Connection;
import java.sql.ResultSet;

public class SalaryHtmlReportNotifier {

    private final Connection connection;

    public SalaryHtmlReportNotifier(Connection databaseConnection) {
        this.connection = databaseConnection;
    }

    public void generateAndSendHtmlSalaryReport(String departmentId, DateRange dateRange, String recipients) {
        // prepare statement with sql
        SqlQuery sqlQuery = new SqlQuery(connection);
        ResultSet results = sqlQuery.getResultFromDepartament(departmentId, dateRange);
        // create a StringBuilder holding a resulting html
        GenerateHtml generateHtml = new GenerateHtml();
        StringBuilder resultingHtml = generateHtml.getStringBuilderHtml(results);
        // now when the report is built we need to send it to the recipients list
        MailService mailService = new MailService();
        mailService.sendMail(recipients, resultingHtml);
    }

    public void sendMail(String recipients, StringBuilder resultingHtml) {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            // we're going to use google mail to send this message
            mailSender.setHost("mail.google.com");
            // construct the message
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipients);
            // setting message text, last parameter 'true' says that it is HTML format
            helper.setText(resultingHtml.toString(), true);
            helper.setSubject("Monthly department salary report");
            // send the message
            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}