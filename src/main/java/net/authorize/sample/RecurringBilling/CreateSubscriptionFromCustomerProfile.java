package net.authorize.sample.RecurringBilling;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import net.authorize.Environment;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.ARBCreateSubscriptionRequest;
import net.authorize.api.contract.v1.ARBCreateSubscriptionResponse;
import net.authorize.api.contract.v1.ARBSubscriptionType;
import net.authorize.api.contract.v1.ARBSubscriptionUnitEnum;
import net.authorize.api.contract.v1.CustomerProfileIdType;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.PaymentScheduleType;
import net.authorize.api.controller.ARBCreateSubscriptionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class CreateSubscriptionFromCustomerProfile {

    public static ANetApiResponse run(String apiLoginId, String transactionKey, short intervalLength, Double amount,
    		String profileId, String paymentProfileId, String customerAddressId) {
        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        // Set up payment schedule
        PaymentScheduleType schedule = new PaymentScheduleType();
        PaymentScheduleType.Interval interval = new PaymentScheduleType.Interval();
        interval.setLength(intervalLength);
        interval.setUnit(ARBSubscriptionUnitEnum.DAYS);
        schedule.setInterval(interval);
        
        try {
          XMLGregorianCalendar startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
          startDate.setDay(30);
          startDate.setMonth(8);
          startDate.setYear(2020);
          schedule.setStartDate(startDate); //2020-08-30 
        }
        catch(Exception e) { }
        
        schedule.setTotalOccurrences((short)12);
        schedule.setTrialOccurrences((short)1);    
        
        CustomerProfileIdType profile = new CustomerProfileIdType();
		profile.setCustomerProfileId(profileId);
		profile.setCustomerPaymentProfileId(paymentProfileId);
		profile.setCustomerAddressId(customerAddressId);

		ARBSubscriptionType arbSubscriptionType = new ARBSubscriptionType();
		arbSubscriptionType.setPaymentSchedule(schedule);
		arbSubscriptionType.setAmount(new BigDecimal(amount).setScale(2, RoundingMode.CEILING));
		arbSubscriptionType.setTrialAmount(new BigDecimal(0.0).setScale(2, RoundingMode.CEILING));
		arbSubscriptionType.setProfile(profile);
        
        // Make the API Request
        ARBCreateSubscriptionRequest apiRequest = new ARBCreateSubscriptionRequest();
        apiRequest.setSubscription(arbSubscriptionType);
        ARBCreateSubscriptionController controller = new ARBCreateSubscriptionController(apiRequest);
        controller.execute();
        ARBCreateSubscriptionResponse response = controller.getApiResponse();
        if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

                System.out.println(response.getSubscriptionId());
                System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
            }
            else
            {
                System.out.println("Failed to create Subscription:  " + response.getMessages().getResultCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
            }
        }
        
        return response;
    }
}