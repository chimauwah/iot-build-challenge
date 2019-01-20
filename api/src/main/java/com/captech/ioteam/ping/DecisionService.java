package com.captech.ioteam.ping;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DecisionService {

    @Autowired
    @Setter
    private DecisionRepository decisionRepository;

    public static final String ACCOUNT_SID = "AC1b1b60d4784c70eb073239cd16c4477c\n";
    public static final String AUTH_TOKEN = "6059a8af658007ad1afc384e5a01e46a";


    void execute(String fromLevel, String toLevel) {
        List<DecisionTable> decisions = decisionRepository.findAlertActionAndRecipientsByFromLevelAndToLevel(fromLevel, toLevel);

        for (DecisionTable decision : decisions) {
            takeAction(decision.getAlertAction(), decision.getRecipients());
        }

    }

    private void takeAction(AlertAction alertAction, String recipients) {
        switch (alertAction) {
            case DO_NOTHING:
                break;
            case SMS:
                sendSms(recipients);
                break;
            case EMAIL:
                sendEmail(recipients);
                break;
        }
    }

    /**
     * Sends email to all recipients
     *
     * @param recipients
     */
    private void sendEmail(String recipients) {
        // TODO: write code to send email

        // TODO: A message sent with an email client can be simultaneously addressed to multiple mobile telephones

        List recipientsList = splitCsv(recipients);

    }

    /**
     * Sends sms message to all recipients.
     * <p>free carrier lookup: https://freecarrierlookup.com/</p>
     *
     * @param recipients
     */
    private void sendSms(String recipients) {
        // TODO: write code to send sms

        // TODO: perform character count before sneding to ensure it is within 160 char limit
        List recipientsList = splitCsv(recipients);
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber("+18434259774"),
                new com.twilio.type.PhoneNumber("+18548886707"), //15017122661
                "This is the ship that made the Kessel Run in fourteen parsecs?")
                .create();

        System.out.println(message.getSid());

    }

    /**
     * Splits comma separted values into list
     *
     * @param csvString
     * @return
     */
    private List splitCsv(String csvString) {
        String[] split = csvString.split(",");
        return Arrays.asList(split);
    }
}
