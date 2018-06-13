package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.UpdateCustomerProfileController;

public class UpdateCustomerProfile {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey, String customerProfileId) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		CustomerProfileInfoExType customer =  new CustomerProfileInfoExType();
		customer.setMerchantCustomerId("custId123");
		customer.setDescription("some description");
		customer.setEmail("newaddress@example.com");
		customer.setCustomerProfileId(customerProfileId);
		customer.setProfileType(CustomerProfileTypeEnum.REGULAR);

		UpdateCustomerProfileRequest apiRequest = new UpdateCustomerProfileRequest();
		apiRequest.setProfile(customer);
	
        UpdateCustomerProfileController controller = new UpdateCustomerProfileController(apiRequest);
        controller.execute();
       
		UpdateCustomerProfileResponse response = new UpdateCustomerProfileResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
            }
            else
            {
                System.out.println("Failed to update customer profile:  " + response.getMessages().getResultCode());
            }
        }
		return response;
	}	
}