package com.captech.ioteam.ping;

import com.captech.ioteam.machine.PrintOSMachine;
import com.captech.ioteam.machine.ResourceLevel;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.List;

@Service
public class DecisionService {

    @Autowired
    @Setter
    private DecisionRepository decisionRepository;

    @Autowired
    @Setter
    private TwilioService twilioService;

    @Autowired
    @Setter
    private EmailService emailService;


    void execute(PrintOSMachine machine, ResourceLevel fromLevel, ResourceLevel toLevel) throws MessagingException {
        List<DecisionTable> decisions = decisionRepository.findByMachineIdAndFromLevelAndToLevel(machine.getId(), fromLevel, toLevel);

        for (DecisionTable decision : decisions) {
            takeAction(machine.getPressName(), fromLevel, toLevel, decision.getAlertAction(), decision.getRecipients());
        }

    }

    private void takeAction(String machineName, ResourceLevel fromLevel, ResourceLevel toLevel, AlertAction alertAction, String recipients) throws MessagingException {
        switch (alertAction) {
            case DO_NOTHING:
                break;
            case SMS:
                sendSms(machineName, fromLevel, toLevel, recipients);
                break;
            case EMAIL:
                sendEmail(machineName, fromLevel, toLevel, recipients);
                break;
        }
    }

    /**
     * Sends email to all recipients
     *
     * @param machineName
     * @param fromLevel
     * @param toLevel
     * @param recipients
     */
    private void sendEmail(String machineName, ResourceLevel fromLevel, ResourceLevel toLevel, String recipients) throws MessagingException {
        emailService.sendEmailMessage(recipients, toLevel.getAlertLevel(),
                String.format("Machine '%s' went from %s to %s.", machineName, fromLevel, toLevel));
    }

    /**
     * Sends sms message to all recipients.
     * <p>free carrier lookup: https://freecarrierlookup.com/</p>
     *
     * @param machineName
     * @param fromLevel
     * @param toLevel
     * @param recipients
     */
    private void sendSms(String machineName, ResourceLevel fromLevel, ResourceLevel toLevel, String recipients) {

        List<String> recipientsList = Arrays.asList(splitCsv(recipients));

        // if using twilio
//        recipientsList.forEach(r -> twilioService.sendTwilioSms(r, String.format("%s Machine [%s] went from %s to %s.", msgSubject, machineName, fromLevel, toLevel)));

        // if using email client to send sms
        recipientsList.forEach(r -> {
            try {
                emailService.sendSmsViaMailClient(
                        r, toLevel.getAlertLevel(),
                        String.format("Machine '%s' went from %s to %s.", machineName, fromLevel.name(), toLevel.name()));
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * Splits comma separted values into list
     *
     * @param csvString
     * @return
     */
    private String[] splitCsv(String csvString) {
        return csvString.split(",");
    }
}
