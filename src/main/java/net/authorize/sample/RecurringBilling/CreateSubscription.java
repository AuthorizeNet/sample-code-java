package net.authorize.sample.RecurringBilling;

import net.authorize.data.arb.*;
import java.math.BigDecimal;
import net.authorize.Environment;
import net.authorize.data.Customer;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.ARBCreateSubscriptionController;
import javax.xml.datatype.*;
import java.lang.Exception.*;

public class CreateSubscription {

    public static void run(String apiLoginId, String transactionKey) {
        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        // Set up payment schedule
        PaymentScheduleType schedule = new PaymentScheduleType();
        PaymentScheduleType.Interval interval = new PaymentScheduleType.Interval();
        interval.setLength( (short)1);
        interval.setUnit(ARBSubscriptionUnitEnum.MONTHS);
        schedule.setInterval(interval);
        
        try {
          XMLGregorianCalendar startDate = DatatypeFactory.newInstance().newXMLGregorianCalendar();
          startDate.setDay(30);
          startDate.setMonth(8);
          startDate.setYear(2020);
          schedule.setStartDate(startDate); //2020-08-30 
        }
        catch(Exception e) {

        }
        
        schedule.setTotalOccurrences((short)12);
        schedule.setTrialOccurrences((short)1);

        // Populate the payment data
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber("4111111111111111");
        creditCard.setExpirationDate("1220");
        paymentType.setCreditCard(creditCard);

        ARBSubscriptionType arbSubscriptionType = new ARBSubscriptionType();
        arbSubscriptionType.setPaymentSchedule(schedule);
        arbSubscriptionType.setAmount(new BigDecimal("10.29"));
        arbSubscriptionType.setTrialAmount(new BigDecimal("0.00"));
        arbSubscriptionType.setPayment(paymentType);

        NameAndAddressType name = new NameAndAddressType();
        name.setFirstName("John");
        name.setLastName("Smith");

        arbSubscriptionType.setBillTo(name);

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
            }
        }
    }
}
