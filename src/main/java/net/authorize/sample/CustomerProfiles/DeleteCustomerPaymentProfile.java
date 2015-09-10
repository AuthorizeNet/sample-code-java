package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;

import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.DeleteCustomerPaymentProfileController;
import net.authorize.api.controller.base.ApiOperationBase;

public class DeleteCustomerPaymentProfile {
	
	public static void run(String apiLoginId, String transactionKey) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        DeleteCustomerPaymentProfileRequest apiRequest = new DeleteCustomerPaymentProfileRequest();
        apiRequest.setCustomerProfileId("10000");
        apiRequest.setCustomerPaymentProfileId("20000");

        DeleteCustomerPaymentProfileController controller = new DeleteCustomerPaymentProfileController(apiRequest);
        controller.execute();
       
		DeleteCustomerPaymentProfileResponse response = new DeleteCustomerPaymentProfileResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
            }
            else
            {
                System.out.println("Failed to delete customer payment profile:  " + response.getMessages().getResultCode());
            }
        }
    }
}