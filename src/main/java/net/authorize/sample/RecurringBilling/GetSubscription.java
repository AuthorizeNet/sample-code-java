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

public class GetSubscription 
{
        public static void run(String apiLoginId,String transactionKey)
    {
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        // Giving the merchant authentication information
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
        // Making the API request
        ARBGetSubscriptionRequest apiRequest = new ARBGetSubscriptionRequest();
        apiRequest.setRefId("Sample");
        apiRequest.setSubscriptionId("2930242");
        // Calling the controller
        ARBGetSubscriptionController controller = new ARBGetSubscriptionController(apiRequest);
        controller.execute();
        // Getting the response
        ARBGetSubscriptionResponse response = controller.getApiResponse();
        
        if(response!=null)
        {
            if(response.getMessages().getResultCode() == MessageTypeEnum.OK)
            {
                if(response.getSubscription() != null)
                {
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
            }
            else
            {
                // Displaying the error code and message when error occurs
                System.out.println("Failed to get ARB Subscription");
                if(!response.getMessages().getMessage().isEmpty())
                    System.out.println("Error: "+response.getMessages().getMessage().get(0).getCode()+" "+ response.getMessages().getMessage().get(0).getText());
            }
        }
        else
        {
            // Displaying the error code and message when response is null 
            ANetApiResponse errorResponse = controller.getErrorResponse();
            
            System.out.println("Failed to get response");
            if(!errorResponse.getMessages().getMessage().isEmpty())
                System.out.println("Error: "+errorResponse.getMessages().getMessage().get(0).getCode()+" \n"+ errorResponse.getMessages().getMessage().get(0).getText());
        }
    }
}
