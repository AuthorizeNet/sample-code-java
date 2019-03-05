package net.authorize.sample.RecurringBilling;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.ARBUpdateSubscriptionController;

public class UpdateSubscription {

    public static ANetApiResponse run(String apiLoginId, String transactionKey, String subscriptionId) {
        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        // Populate the payment data
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber("4111111111111111");
        creditCard.setExpirationDate("1220");
        paymentType.setCreditCard(creditCard);
        
        //set profile information
		CustomerProfileIdType profile = new CustomerProfileIdType();
		profile.setCustomerProfileId("121212");
		profile.setCustomerPaymentProfileId("131313");
		profile.setCustomerAddressId("141414");

        ARBSubscriptionType arbSubscriptionType = new ARBSubscriptionType();
        arbSubscriptionType.setPayment(paymentType);
        //to update profile
        //arbSubscriptionType.setProfile(profile);

        // Make the API Request
        ARBUpdateSubscriptionRequest apiRequest = new ARBUpdateSubscriptionRequest();
        apiRequest.setSubscriptionId(subscriptionId);
        apiRequest.setSubscription(arbSubscriptionType);
        ARBUpdateSubscriptionController controller = new ARBUpdateSubscriptionController(apiRequest);
        controller.execute();
        ARBUpdateSubscriptionResponse response = controller.getApiResponse();
        if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                
                System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
            }
            else
            {
                System.out.println("Failed to update Subscription:  " + response.getMessages().getResultCode());
            }
        }
		return response;
    }
}