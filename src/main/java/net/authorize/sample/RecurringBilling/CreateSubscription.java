/*package net.authorize.sample.RecurringBilling;
import net.authorize.data.arb.*;
import java.math.BigDecimal;
import net.authorize.Environment;
import net.authorize.data.Customer;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.ARBCreateSubscriptionController;
public class CreateSubscription {
    //
    // Run this sample from command line with:
    //                 java -jar target/ChargeCreditCard-jar-with-dependencies.jar
    //
    public static void run(String apiLoginId, String transactionKey) {
        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
        // Set up payment schedule
        PaymentSchedule schedule = PaymentSchedule.createPaymentSchedule();
        schedule.setIntervalLength(1);
        schedule.setSubscriptionUnit(SubscriptionUnitType.MONTHS);
        schedule.setStartDate("2020-08-30"); //2020-08-30
        schedule.setTotalOccurrences(12);
        schedule.setTrialOccurrences(1);
        // Set up customer information
        net.authorize.data.xml.Customer customer = net.authorize.data.xml.Customer.createCustomer();
        //customer.setFirstName("John");
        //customer.setLastName("Smith");
        // Populate subscription
        Subscription subscription = Subscription.createSubscription();
        subscription.setSchedule(schedule);
        subscription.setAmount(new BigDecimal(10.29));
        subscription.setTrialAmount(new BigDecimal(0.00));
        subscription.setCustomer(customer);
        // Populate the payment data
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber("4111111111111111");
        creditCard.setExpirationDate("1220");
        paymentType.setCreditCard(creditCard);
        ARBSubscriptionType arbSubscriptionType = new ARBSubscriptionType();
        // Make the API Request
        ARBCreateSubscriptionRequest apiRequest = new ARBCreateSubscriptionRequest();
        //apiRequest.setTransactionRequest(txnRequest);
        ARBCreateSubscriptionController controller = new ARBCreateSubscriptionController(apiRequest);
        controller.execute();
        ARBCreateSubscriptionResponse response = controller.getApiResponse();
        if (response!=null) {
            // If API Response is ok, go ahead and check the transaction response
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                ARBSubscriptionResponse result = response.getSubscriptionResponse();
                if (result.getResponseCode().equals("1")) {
                    System.out.println(result.getResponseCode());
                    System.out.println("Success");
                    System.out.println(result.getAuthCode());
                    System.out.println(result.getTransId());
                }
                else
                {
                    System.out.println("Failed Transaction"+result.getResponseCode());
                }
            }
            else
            {
                System.out.println("Failed Transaction:  "+response.getMessages().getResultCode());
            }
        }
    }
}*/