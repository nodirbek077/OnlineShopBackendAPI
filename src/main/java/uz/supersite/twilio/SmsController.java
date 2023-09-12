package uz.supersite.twilio;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/v1/sms")
public class SmsController {

    private final SmsService smsService;
    @Autowired
    public SmsController(SmsService smsService) {
        this.smsService = smsService;
    }
    @PostMapping
    public void sendSms(@Valid @RequestBody SmsRequest smsRequest) {
        smsService.sendSms(smsRequest);
    }
}
