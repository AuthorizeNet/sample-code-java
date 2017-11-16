package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;

import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.GetCustomerProfileController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetCustomerProfile {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey, String customerProfileId) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        GetCustomerProfileRequest apiRequest = new GetCustomerProfileRequest();
        apiRequest.setCustomerProfileId(customerProfileId);

        GetCustomerProfileController controller = new GetCustomerProfileController(apiRequest);
        controller.execute();
       
		GetCustomerProfileResponse response = new GetCustomerProfileResponse();
		response = controller.getApiResponse();

		if (response!=null) {

            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
                
                System.out.println(response.getProfile().getMerchantCustomerId());
                System.out.println(response.getProfile().getDescription());
                System.out.println(response.getProfile().getEmail());
                System.out.println(response.getProfile().getCustomerProfileId());

                if ((!response.getProfile().getPaymentProfiles().isEmpty()) &&
                		(response.getProfile().getPaymentProfiles().get(0).getBillTo() != null)) {
                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getBillTo().getFirstName());
                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getBillTo().getLastName());
                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getBillTo().getCompany());
                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getBillTo().getAddress());
                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getBillTo().getCity());
                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getBillTo().getState());
                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getBillTo().getZip());
                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getBillTo().getCountry());
                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getBillTo().getPhoneNumber());
                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getBillTo().getFaxNumber());

                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getCustomerPaymentProfileId());

                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getPayment().getCreditCard().getCardNumber());
                    System.out.println(response.getProfile().getPaymentProfiles().get(0).getPayment().getCreditCard().getExpirationDate());
                }

                if (!response.getProfile().getShipToList().isEmpty()) {
                    System.out.println(response.getProfile().getShipToList().get(0).getFirstName());
                    System.out.println(response.getProfile().getShipToList().get(0).getLastName());
                    System.out.println(response.getProfile().getShipToList().get(0).getCompany());
                    System.out.println(response.getProfile().getShipToList().get(0).getAddress());
                    System.out.println(response.getProfile().getShipToList().get(0).getCity());
                    System.out.println(response.getProfile().getShipToList().get(0).getState());
                    System.out.println(response.getProfile().getShipToList().get(0).getZip());
                    System.out.println(response.getProfile().getShipToList().get(0).getCountry());
                    System.out.println(response.getProfile().getShipToList().get(0).getPhoneNumber());
                    System.out.println(response.getProfile().getShipToList().get(0).getFaxNumber());
                }
                
                if ((response.getSubscriptionIds() != null) && (response.getSubscriptionIds().getSubscriptionId() != null) && 
                		(!response.getSubscriptionIds().getSubscriptionId().isEmpty())) {
                	System.out.println("List of subscriptions:");
                	for (String subscriptionid : response.getSubscriptionIds().getSubscriptionId())
                		System.out.println(subscriptionid);
                }

            } else {
                System.out.println("Failed to get customer profile:  " + response.getMessages().getResultCode());
            }
        }
		return response;
    }
}