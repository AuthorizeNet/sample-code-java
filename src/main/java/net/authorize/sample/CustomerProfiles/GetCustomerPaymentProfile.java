package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;

import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.GetCustomerPaymentProfileController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetCustomerPaymentProfile {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey, String customerProfileId,
			String customerPaymentProfileId) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        GetCustomerPaymentProfileRequest apiRequest = new GetCustomerPaymentProfileRequest();
        apiRequest.setCustomerProfileId(customerProfileId);
        apiRequest.setCustomerPaymentProfileId(customerPaymentProfileId);

        GetCustomerPaymentProfileController controller = new GetCustomerPaymentProfileController(apiRequest);
        controller.execute();
       
		GetCustomerPaymentProfileResponse response = new GetCustomerPaymentProfileResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());

                System.out.println(response.getPaymentProfile().getBillTo().getFirstName());
                System.out.println(response.getPaymentProfile().getBillTo().getLastName());
                System.out.println(response.getPaymentProfile().getBillTo().getCompany());
                System.out.println(response.getPaymentProfile().getBillTo().getAddress());
                System.out.println(response.getPaymentProfile().getBillTo().getCity());
                System.out.println(response.getPaymentProfile().getBillTo().getState());
                System.out.println(response.getPaymentProfile().getBillTo().getZip());
                System.out.println(response.getPaymentProfile().getBillTo().getCountry());
                System.out.println(response.getPaymentProfile().getBillTo().getPhoneNumber());
                System.out.println(response.getPaymentProfile().getBillTo().getFaxNumber());

                System.out.println(response.getPaymentProfile().getCustomerPaymentProfileId());

                System.out.println(response.getPaymentProfile().getPayment().getCreditCard().getCardNumber());
                System.out.println(response.getPaymentProfile().getPayment().getCreditCard().getExpirationDate());
                
                if ((response.getPaymentProfile().getSubscriptionIds() != null) && (response.getPaymentProfile().getSubscriptionIds().getSubscriptionId() != null) && 
                		(!response.getPaymentProfile().getSubscriptionIds().getSubscriptionId().isEmpty())) {
                	System.out.println("List of subscriptions:");
                	for (String subscriptionid : response.getPaymentProfile().getSubscriptionIds().getSubscriptionId())
                		System.out.println(subscriptionid);
                }
            } else {
                System.out.println("Failed to get customer payment profile:  " + response.getMessages().getResultCode());
            }
        }
		return response;
    }
}