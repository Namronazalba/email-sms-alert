package EmailSms.Alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class AlertApplication implements CommandLineRunner {
	@Autowired
	private Sender sender;

	public static void main(String[] args) {
		SpringApplication.run(AlertApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		while (true) {
			if (sender.checkHistoryStatus() == 1) {
				// If all data history is 1, wait and then check again
				System.out.println("All data history is 1. Waiting for new data...");
				Thread.sleep(30000); // Wait for 1 minute before checking again
				continue;
			}

			int initialAlert = sender.initialAlert();
			String selectAlertFromLogs = sender.selectAlertFromLogs(initialAlert);
			String imei = sender.selectImeiFromLogs(initialAlert);
			String vehicle_id = sender.selectVehicleId(imei);
			int company_id = sender.selectCompanyIdByImei(imei);

			System.out.println("--------------------------------find emails------------------------------");
			// Find emails to recieve alert
			List<String> allEmails = new ArrayList<>();
			String[] userIDs = sender.idEmailFromUserFound(imei, selectAlertFromLogs);
			for (String userID : userIDs) {
				try {
					int userIdInt = Integer.parseInt(userID);
					String[] emails = sender.emailsFound(userIdInt, company_id);
					if (emails != null && emails.length > 0) {
						allEmails.addAll(Arrays.asList(emails));
					}
				} catch (NumberFormatException e) {
				}
			}
			String[] userEmailsArray = allEmails.toArray(new String[0]);
			String[] otherEmailsArray = sender.otherEmails(company_id, vehicle_id, selectAlertFromLogs);

			int mergedLength = userEmailsArray.length + otherEmailsArray.length;
			String[] mergedEmails = new String[mergedLength];
			System.arraycopy(userEmailsArray, 0, mergedEmails, 0, userEmailsArray.length);
			System.arraycopy(otherEmailsArray, 0, mergedEmails, userEmailsArray.length, otherEmailsArray.length);
			System.out.println(Arrays.toString(mergedEmails));

			// Find email content
			String emailSubject = sender.emailSubject(String.valueOf(initialAlert));
			String emailBody = sender.emailBody(imei, initialAlert);

			// Send emails to all recipients
			if(mergedEmails.length > 0){
				boolean emailSent = sender.sendEmails(mergedEmails, emailSubject, emailBody);
				if (emailSent) {
					sender.successEmail();
					System.out.println("Emails sent successfully!");
				} else {
					System.out.println("Failed to send emails.");
				}
			}else {
				sender.successEmail();
				System.out.println("No emails found.");
			}



			System.out.println("--------------------------------find sms------------------------------");
			// Find numbers to recieve alert
			List<String> allNumbers = new ArrayList<>();
			String[] userSmsIDs = sender.idSmsFromUserFound(imei, selectAlertFromLogs);
			for (String userSmsID : userSmsIDs) {
				try {
					int userSmsIdInt = Integer.parseInt(userSmsID);
					String[] numbers = sender.numberFound(userSmsIdInt, company_id);
					if (numbers != null && numbers.length > 0) {
						allNumbers.addAll(Arrays.asList(numbers));
					}
				} catch (NumberFormatException e) {
				}
			}
			String[] userSmsArray = allNumbers.toArray(new String[0]);
			String[] otherSmsArray = sender.otherNumbers(company_id, vehicle_id, selectAlertFromLogs);

			int mergedNumberLength = userSmsArray.length + otherSmsArray.length;
			String[] mergedNumbers = new String[mergedNumberLength];
			System.arraycopy(userSmsArray, 0, mergedNumbers, 0, userSmsArray.length);
			System.arraycopy(otherSmsArray, 0, mergedNumbers, userSmsArray.length, otherSmsArray.length);
			System.out.println(Arrays.toString(mergedNumbers));

			// Send emails to all recipients
			if (mergedNumbers.length > 0) {
				String message = sender.smsBody(imei, initialAlert);
				boolean result = sender.sendSMS(message, List.of(mergedNumbers));
				if (result) {
					sender.successSms();
					System.out.println("Message sent successfully.");
				} else {
					System.out.println("Failed to send message.");
				}
			} else {
				sender.successSms();
				System.out.println("No mobile numbers found.");
			}
			sender.updateHistory();
		}
	}
}
