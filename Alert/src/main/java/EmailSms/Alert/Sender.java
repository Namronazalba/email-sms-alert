package EmailSms.Alert;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Sender {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private static final Logger logger = Logger.getLogger(Sender.class.getName());

    // Get company id of vehicle log
    public int initialAlert() {
        String sql = "SELECT v_log_id FROM pending_alerts WHERE history = 0 ORDER BY id ASC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            logger.severe("Error fetching initial alert: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    // Get alert type of vehicle log
    public String selectAlertFromLogs(int initialAlert) {
        String sql = "SELECT alert FROM vehicle_logs WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, initialAlert);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String platenum(String imei) {
        String sql = "SELECT platenumber FROM vehicles WHERE imei = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, imei);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String model(String imei) {
        String sql = "SELECT model FROM vehicles WHERE imei = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, imei);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String driverId(String imei) {
        String sql = "SELECT driver_id FROM vehicles WHERE imei = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, imei);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String driver() {
//    public String driver(String driverId) {
//        String sql = "SELECT driver_id FROM drivers WHERE imei = ?";
//        try {
//            return jdbcTemplate.queryForObject(sql, String.class, driverId);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
        return null;
    }
    public String bodyNum(String imei) {
        String sql = "SELECT body_num FROM vehicles WHERE imei = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, imei);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String chassisNum(String imei) {
        String sql = "SELECT chassis_num FROM vehicles WHERE imei = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, imei);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String engineNum(String imei) {
        String sql = "SELECT engine_num FROM vehicles WHERE imei = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, imei);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int selectAlertId(String selectAlertFromLogs) {
        String sql = "SELECT id FROM alert_types WHERE alert_type = ?";
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, selectAlertFromLogs);
        } catch (Exception e) {
            logger.severe("Error fetching initial alert: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    // Get log IMEI
    public String selectImeiFromLogs(int initialAlert) {
        String sql = "SELECT imei FROM vehicle_logs WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, initialAlert);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String lat(int initialAlert){
        String sql = "SELECT lat FROM vehicle_logs WHERE id = ?";
        try {
            String data = jdbcTemplate.queryForObject(sql, String.class, initialAlert);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String lng(int initialAlert){
        String sql = "SELECT lng FROM vehicle_logs WHERE id = ?";
        try {
            String data = jdbcTemplate.queryForObject(sql, String.class, initialAlert);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String address(int initialAlert){
        String sql = "SELECT address FROM vehicle_logs WHERE id = ?";
        try {
            String data = jdbcTemplate.queryForObject(sql, String.class, initialAlert);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String dateTime(int initialAlert){
        String sql = "SELECT datetime FROM vehicle_logs WHERE id = ?";
        try {
            String data = jdbcTemplate.queryForObject(sql, String.class, initialAlert);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String status(int initialAlert){
        String sql = "SELECT status FROM vehicle_logs WHERE id = ?";
        try {
            String data = jdbcTemplate.queryForObject(sql, String.class, initialAlert);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String speed(int initialAlert){
        String sql = "SELECT speed FROM vehicle_logs WHERE id = ?";
        try {
            String data = jdbcTemplate.queryForObject(sql, String.class, initialAlert);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Get vehice ID
    public String selectVehicleId(String imei) {
        String sql = "SELECT id FROM vehicles WHERE imei = ?";
        try {
            return jdbcTemplate.queryForObject(sql, String.class, imei);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Get company ID
    public int selectCompanyIdByImei(String imei){
        String sql = "SELECT company_id FROM vehicles WHERE imei = ?";
        try{
            return jdbcTemplate.queryForObject(sql, int.class, imei);
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }


    // Email alert
    public String senderEmail() {
        String sql = "SELECT email FROM email_sms_configs ORDER BY id ASC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String senderPass() {
        String sql = "SELECT password FROM email_sms_configs ORDER BY id ASC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public int pendingAlertId() {
        String sql = "SELECT id FROM pending_alerts WHERE v_log_id = ?";
        int initialAlertId = initialAlert();
        try {
            return jdbcTemplate.queryForObject(sql, Integer.class, initialAlertId);
        } catch (Exception e) {
            logger.severe("Error fetching pending alert ID: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public void successEmail() {
        int  pendingAlertId =  pendingAlertId();

        if ( pendingAlertId != -1) {
            String sql = "UPDATE pending_alerts SET email = 1 WHERE id = ?";
            try {
                int rowsAffected = jdbcTemplate.update(sql,  pendingAlertId);
                if (rowsAffected > 0) {
                    logger.info("Email status updated successfully for alert ID " +  pendingAlertId);
                } else {
                    logger.warning("No rows affected. Email status update failed for alert ID " +  pendingAlertId);
                }
            } catch (Exception e) {
                logger.severe("Error updating email status: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            logger.severe("Failed to get pending alert ID.");
        }
    }


    public boolean sendEmails(String[] to, String subject, String body) {
        if (to == null || to.length == 0) {
            logger.severe("No email addresses provided.");
            return false;
        }

        int maxRetries = 3; // Define the maximum number of retries

        for (String email : to) {
            boolean success = false;
            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                boolean emailSent = sendEmail(email, subject, body);
                if (emailSent) {
                    success = true;
                    break; // Exit retry loop if the email is sent successfully
                } else {
                    logger.warning("Attempt " + attempt + " failed to send email to: " + email);
                }
                try {
                    Thread.sleep(1000); // Wait for 1 second before retrying (you can adjust the delay)
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt(); // Restore the interrupted status
                }
            }
            if (!success) {
                logger.severe("Failed to send email to: " + email + " after " + maxRetries + " attempts.");
                return false;
            }
        }
        return true;
    }

    public boolean sendEmail(String to, String subject, String body) {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        String sender_email = senderEmail();
        String sender_pass = senderPass();
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender_email, sender_pass);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender_email));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            logger.info("Email sent successfully to: " + to);
            return true;
        } catch (MessagingException e) {
            logger.severe("Failed to send email to: " + to + " - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public String emailSubject(String initialAlert){
        String sql = "SELECT alert FROM vehicle_logs WHERE id = ?";
        try {
            String gps_alert = jdbcTemplate.queryForObject(sql, String.class, initialAlert);
            String subject = "EZTRACKPH " + gps_alert + " Alert";
            return subject;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public String emailBody(String imei, int initialAlert) {
        String platenum = platenum(imei);
        String model = model(imei);
        String driver = driver();
        String bodynum = bodyNum(imei);
        String chasisnum = chassisNum(imei);
        String enginenum = engineNum(imei);
        String lat = lat(initialAlert);
        String lng = lng(initialAlert);
        String address = address(initialAlert);
        String datetime = dateTime(initialAlert);
        String status = status(initialAlert);
        String speed = speed(initialAlert);

        StringBuilder body = new StringBuilder();
        body.append("EzTrackph Email Alert System\n\n");
        body.append("Vehicle Information:\n");
        body.append("Plate Number: ").append(platenum).append("\n");
        body.append("Model: ").append(model).append("\n");
        body.append("Driver: ").append(driver != null ? driver : "N/A").append("\n");
        body.append("Body Number: ").append(bodynum).append("\n");
        body.append("Chassis Number: ").append(chasisnum).append("\n");
        body.append("Engine Number: ").append(enginenum).append("\n");
        body.append("Latitude: ").append(lat).append("\n");
        body.append("Longitude: ").append(lng).append("\n");
        body.append("Address: ").append(address).append("\n");
        body.append("Datetime: ").append(datetime).append("\n");
        body.append("Status: ").append(status).append("\n");
        body.append("Speed: ").append(speed).append("\n");

        return body.toString();
    }

    public String[] idEmailFromUserFound(String imei, String selectAlertFromLogs) {
        String sql = "SELECT user_id FROM alerts WHERE contact_type = ? AND FIND_IN_SET(?, vehicle_id) AND FIND_IN_SET(?, alert_id)";
        try {
            String selectVehicleId = selectVehicleId(imei);
            String selectAlertID = String.valueOf(selectAlertId(selectAlertFromLogs));
            List<String> userIds = jdbcTemplate.queryForList(sql, String.class, 0, selectVehicleId, selectAlertID);
            return userIds.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Get email from existing user
    public String[] emailsFound(int idEmailFromUserFound, int selectCompanyIdByImei) {
        String sql = "SELECT email FROM users WHERE id = ? AND company_id = ?";
        try {
            List<String> emails = jdbcTemplate.queryForList(sql, String.class, idEmailFromUserFound, selectCompanyIdByImei);
            return emails.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Get email from not existing user
    public String[] otherEmails(int selectCompanyIdByImei, String selectVehicleId, String selectAlertFromLogs) {
        String sql = "SELECT others FROM alerts WHERE FIND_IN_SET(?, vehicle_id) AND FIND_IN_SET(?, alert_id) AND contact_type = ?";
        try {
            String selectAlertID = String.valueOf(selectAlertId(selectAlertFromLogs));
            List<String> rawEmails = jdbcTemplate.queryForList(sql, String.class, selectVehicleId, selectAlertID, 0);
            Pattern pattern = Pattern.compile("\\(" + selectCompanyIdByImei + "\\)([^,]+)");
            List<String> emails = new ArrayList<>();

            for (String rawEmail : rawEmails) {
                if (rawEmail != null) {
                    Matcher matcher = pattern.matcher(rawEmail);
                    while (matcher.find()) {
                        emails.add(matcher.group(1).trim());
                    }
                }
            }

            return emails.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }

    // SMS Alert
    public String smsBody(String imei, int initialAlert) {
        String platenum = platenum(imei);
        String model = model(imei);
        String driver = driver();
        String bodynum = bodyNum(imei);
        String chasisnum = chassisNum(imei);
        String enginenum = engineNum(imei);
        String lat = lat(initialAlert);
        String lng = lng(initialAlert);
        String address = address(initialAlert);
        String datetime = dateTime(initialAlert);
        String status = status(initialAlert);
        String speed = speed(initialAlert);

        StringBuilder body = new StringBuilder();
        body.append("EzTrackph SMS Alert System\n\n");
        body.append("Vehicle Information:\n");
        body.append("Plate Number: ").append(platenum).append("\n");
        body.append("Model: ").append(model).append("\n");
        body.append("Driver: ").append(driver != null ? driver : "N/A").append("\n");
        body.append("Body Number: ").append(bodynum).append("\n");
        body.append("Chassis Number: ").append(chasisnum).append("\n");
        body.append("Engine Number: ").append(enginenum).append("\n");
        body.append("Latitude: ").append(lat).append("\n");
        body.append("Longitude: ").append(lng).append("\n");
        body.append("Address: ").append(address).append("\n");
        body.append("Datetime: ").append(datetime).append("\n");
        body.append("Status: ").append(status).append("\n");
        body.append("Speed: ").append(speed).append("\n");

        return body.toString();
    }
    public String[] idSmsFromUserFound(String imei, String selectAlertFromLogs) {
        String sql = "SELECT user_id FROM alerts WHERE contact_type = ? AND FIND_IN_SET(?, vehicle_id) AND FIND_IN_SET(?, alert_id)";
        try {
            String selectVehicleId = selectVehicleId(imei);
            String selectAlertID = String.valueOf(selectAlertId(selectAlertFromLogs));
            List<String> userIds = jdbcTemplate.queryForList(sql, String.class, 1, selectVehicleId, selectAlertID);
            return userIds.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Get email from existing user
    public String[] numberFound(int idSmsFromUserFound, int selectCompanyIdByImei) {
        String sql = "SELECT contact_no FROM users WHERE id = ? AND company_id = ?";
        try {
            List<String> emails = jdbcTemplate.queryForList(sql, String.class, idSmsFromUserFound, selectCompanyIdByImei);
            return emails.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // Get email from not existing user
    public String[] otherNumbers(int selectCompanyIdByImei, String selectVehicleId, String selectAlertFromLogs) {
        String sql = "SELECT others FROM alerts WHERE FIND_IN_SET(?, vehicle_id) AND FIND_IN_SET(?, alert_id) AND contact_type = ?";
        try {
            String selectAlertID = String.valueOf(selectAlertId(selectAlertFromLogs));
            List<String> rawEmails = jdbcTemplate.queryForList(sql, String.class, selectVehicleId, selectAlertID, 1);
            Pattern pattern = Pattern.compile("\\(" + selectCompanyIdByImei + "\\)([^,]+)");
            List<String> emails = new ArrayList<>();
            for (String rawEmail : rawEmails) {
                if (rawEmail != null) {
                    Matcher matcher = pattern.matcher(rawEmail);
                    while (matcher.find()) {
                        emails.add(matcher.group(1).trim());
                    }
                }
            }

            return emails.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }
    public String senderSms() {
        String sql = "SELECT gsm_ip FROM email_sms_configs ORDER BY id ASC LIMIT 1";
        try {
            return jdbcTemplate.queryForObject(sql, String.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean sendSMS(String message, List<String> phoneNumbers) {
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        String ipConfig = senderSms();
        int maxRetries = 3; // Define the maximum number of retries
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            for (String number : phoneNumbers) {
                boolean success = false;
                for (int attempt = 1; attempt <= maxRetries; attempt++) {
                    String url = String.format(ipConfig+"/SendSMS?username=apsys&password=apsys123&phone=%s&message=%s", number, encodedMessage);
                    HttpGet request = new HttpGet(url);
                    try (CloseableHttpResponse response = httpClient.execute(request)) {
                        if (response.getStatusLine().getStatusCode() == 200) {
                            success = true;
                            break; // Exit retry loop if the message is sent successfully
                        } else {
                            System.err.println("Error sending message to " + number + ": " + response.getStatusLine());
                        }
                    } catch (IOException e) {
                        System.err.println("Attempt " + attempt + " failed for number " + number + ": " + e.getMessage());
                    }
                    try {
                        Thread.sleep(1000); // Wait for 1 second before retrying (you can adjust the delay)
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt(); // Restore the interrupted status
                    }
                }
                if (!success) {
                    System.err.println("Failed to send message to " + number + " after " + maxRetries + " attempts.");
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public void successSms() {
        int  pendingAlertId =  pendingAlertId();

        if ( pendingAlertId != -1) {
            String sql = "UPDATE pending_alerts SET sms = 1 WHERE id = ?";
            try {
                int rowsAffected = jdbcTemplate.update(sql,  pendingAlertId);
                if (rowsAffected > 0) {
                    logger.info("Sms status updated successfully for alert ID " +  pendingAlertId);
                } else {
                    logger.warning("No rows affected. Sms status update failed for alert ID " +  pendingAlertId);
                }
            } catch (Exception e) {
                logger.severe("Error updating email status: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            logger.severe("Failed to get pending alert ID.");
        }
    }

    // Update pending alert history to 1
    public void updateHistory() {
        int  pendingAlertId =  pendingAlertId();

        if ( pendingAlertId != -1) {
            String sql = "UPDATE pending_alerts SET history = 1 WHERE id = ? AND email = 1 AND sms = 1";
            try {
                int rowsAffected = jdbcTemplate.update(sql,  pendingAlertId);
                if (rowsAffected > 0) {
                    logger.info("History status updated successfully for alert ID " +  pendingAlertId);
                } else {
                    logger.warning("No rows affected. History  status update failed for alert ID " +  pendingAlertId);
                }
            } catch (Exception e) {
                logger.severe("Error updating email status: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            logger.severe("Failed to get pending alert ID.");
        }
    }
    public int checkHistoryStatus() {
        String sql = "SELECT COUNT(*) FROM pending_alerts WHERE history = 0";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        return count > 0 ? 0 : 1;
    }
}
