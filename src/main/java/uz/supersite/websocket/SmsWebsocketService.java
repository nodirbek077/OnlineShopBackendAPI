package uz.supersite.websocket;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
@Component
public class SmsWebsocketService {

    public void send(SmsPojo sms) {
        String ACCOUNT_SID = "ACe26a3946834a2fee21f0ef712ff464bb";
        String AUTH_TOKEN = "be7dc2fc69645a2ed714fe075aa0686f";
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        String FROM_NUMBER = "+19254783780";
        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage())
                .create();
        System.out.println("here is my id:"+message.getSid());// Unique resource ID created to manage this transaction

    }

    public void receive(MultiValueMap<String, String> smscallback) {
    }
}
