package net.authorize.sample.RecurringBilling;

import net.authorize.Environment;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.ARBGetSubscriptionRequest;
import net.authorize.api.contract.v1.ARBGetSubscriptionResponse;
import net.authorize.api.contract.v1.ErrorResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.controller.ARBGetSubscriptionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetSubscription {
    public static ANetApiResponse run(String apiLoginId,String transactionKey, String subscriptionId) {

        // Set the request to operate in either the sandbox or production environment
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        
        // Create object with merchant authentication details
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);

        // Create the API request and set the parameters for this specific request
        ARBGetSubscriptionRequest apiRequest = new ARBGetSubscriptionRequest();
        apiRequest.setMerchantAuthentication(merchantAuthenticationType);
        apiRequest.setRefId("Sample");
        apiRequest.setSubscriptionId(subscriptionId);

        // Call the controller
        ARBGetSubscriptionController controller = new ARBGetSubscriptionController(apiRequest);
        controller.execute();

        // Get the response
        ARBGetSubscriptionResponse response = new ARBGetSubscriptionResponse();
        response = controller.getApiResponse();
        
        // Parse the response to determine results
        if (response!=null) {
            // If API Response is OK, go ahead and check the transaction response
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                if (response.getSubscription() != null) {
                    // Displaying the subscription details 
                    System.out.println("Successfully got ARB Subscription");
                    System.out.println("Subscription Details:");
                    System.out.println("Subscription Name: " + response.getSubscription().getName());
                    System.out.println("Subscription start date: " + response.getSubscription().getPaymentSchedule().getStartDate());
                    System.out.println("Subscription amount: " + response.getSubscription().getAmount());
                    System.out.println("Subscription status: " + response.getSubscription().getStatus());
                    System.out.println("Subscription Description: "+response.getSubscription().getProfile().getDescription());
                    System.out.println("Customer Profile ID: "+ response.getSubscription().getProfile().getCustomerProfileId());
                }
            } else {
                // Displaying the error code and message when error occurs
                System.out.println("Failed to get ARB Subscription");
                if (!response.getMessages().getMessage().isEmpty()) {
                    System.out.println("Error: "+response.getMessages().getMessage().get(0).getCode()+" "+ response.getMessages().getMessage().get(0).getText());
                }
            }
        } else {
            // Display the error code and message when response is null 
            ANetApiResponse errorResponse = controller.getErrorResponse();
            System.out.println("Failed to get response");
            if (!errorResponse.getMessages().getMessage().isEmpty()) {
                System.out.println("Error: "+errorResponse.getMessages().getMessage().get(0).getCode()+" \n"+ errorResponse.getMessages().getMessage().get(0).getText());
            }
        }

        return response;
    }
}
