package net.authorize.sample.RecurringBilling;

import net.authorize.data.arb.*;
import java.math.BigDecimal;

import net.authorize.Environment;
import net.authorize.data.*;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.CreateTransactionController;


public class CreateSubscription {

    //
    // Run this sample from command line with:
    //                 java -jar target/ChargeCreditCard-jar-with-dependencies.jar
    //
    public static void run(String apiLoginId, String transactionKey) {


        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName("5KP3u95bQpv");
        merchantAuthenticationType.setTransactionKey("4Ktq966gC55GAX7S");
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        // Set up payment schedule
        PaymentSchedule schedule = PaymentSchedule.createPaymentSchedule();
        schedule.setIntervalLength(1);
        schedule.setSubscriptionUnit(SubscriptionUnitType.MONTHS);
        schedule.setStartDate("2020-08-30"); //2020-08-30
        schedule.setTotalOccurrences(12);
        schedule.setTrialOccurrences(1);

        // Set up customer information
        net.authorize.data.xml.Customer customer =  net.authorize.data.xml.Customer.createCustomer();
        //customer.setFirstName("John");
        //customer.setLastName("Smith");

        // Populate subscription
        Subscription subscription = Subscription.createSubscription();
        subscription.setSchedule(schedule);
        subscription.setAmount(new BigDecimal(10.29));
        subscription.setTrialAmount(new BigDecimal(0.00));
        subscription.setCustomer(customer);
        subscription.setSubscriptionId("2790501");

        // Populate the payment data
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber("4111111111111111");
        creditCard.setExpirationDate("1220");
        paymentType.setCreditCard(creditCard);
        System.out.println(subscription.getSubscriptionId());
    }


}
