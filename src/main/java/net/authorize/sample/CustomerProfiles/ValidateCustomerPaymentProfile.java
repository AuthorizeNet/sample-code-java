package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.ValidateCustomerPaymentProfileController;

public class ValidateCustomerPaymentProfile {
	
	public static void run(String apiLoginId, String transactionKey) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		ValidateCustomerPaymentProfileRequest apiRequest = new ValidateCustomerPaymentProfileRequest();
		apiRequest.setCustomerProfileId("10000");	
		apiRequest.setCustomerPaymentProfileId("20000");
		apiRequest.setValidationMode(ValidationModeEnum.LIVE_MODE);
	
        ValidateCustomerPaymentProfileController controller = new ValidateCustomerPaymentProfileController(apiRequest);
        controller.execute();
       
		ValidateCustomerPaymentProfileResponse response = new ValidateCustomerPaymentProfileResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
                System.out.println(response.getDirectResponse());
            }
            else
            {
                System.out.println("Failed to validate customer payment profile:  " + response.getMessages().getResultCode());
            }
        }
	}	
}