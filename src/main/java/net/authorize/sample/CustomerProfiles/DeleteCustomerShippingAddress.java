package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;

import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.DeleteCustomerShippingAddressController;
import net.authorize.api.controller.base.ApiOperationBase;

public class DeleteCustomerShippingAddress {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey, String customerProfileId,
			String customerAddressId) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        DeleteCustomerShippingAddressRequest apiRequest = new DeleteCustomerShippingAddressRequest();
        apiRequest.setCustomerProfileId(customerProfileId);
        apiRequest.setCustomerAddressId(customerAddressId);

        DeleteCustomerShippingAddressController controller = new DeleteCustomerShippingAddressController(apiRequest);
        controller.execute();
       
		DeleteCustomerShippingAddressResponse response = new DeleteCustomerShippingAddressResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
            }
            else
            {
                System.out.println("Failed to delete customer shipping address:  " + response.getMessages().getResultCode());
            }
        }
		return response;
    }
}