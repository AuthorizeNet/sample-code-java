package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;

import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.GetCustomerProfileIdsController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetCustomerProfileIds {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        GetCustomerProfileIdsRequest apiRequest = new GetCustomerProfileIdsRequest();

        GetCustomerProfileIdsController controller = new GetCustomerProfileIdsController(apiRequest);
        controller.execute();
       
		GetCustomerProfileIdsResponse response = new GetCustomerProfileIdsResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());

                for (int i =0; i < response.getIds().getNumericString().size(); i++) {

                	System.out.println(response.getIds().getNumericString().get(i));
                }
            }
            else
            {
                System.out.println("Failed to get customer payment profile:  " + response.getMessages().getResultCode());
            }
        }
		return response;
    }
}
