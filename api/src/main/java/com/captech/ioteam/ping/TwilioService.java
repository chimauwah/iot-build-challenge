package com.captech.ioteam.ping;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;

@Service
public class TwilioService {

    public static final String ACCOUNT_SID = "AC1b1b60d4784c70eb073239cd16c4477c";
    public static final String AUTH_TOKEN = "6059a8af658007ad1afc384e5a01e46a";
    public static final String TWILIO_PHONE_NUMBER = "+18438061051";

    /**
     * Send SMS using twilio SMS api
     *
     * @param toNumber
     * @param msg
     */
    public void sendTwilioSms(final String toNumber, String msg) {

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(toNumber),
                new com.twilio.type.PhoneNumber(TWILIO_PHONE_NUMBER),
                msg)
                .create();

        System.out.println(message.getSid());
    }
}