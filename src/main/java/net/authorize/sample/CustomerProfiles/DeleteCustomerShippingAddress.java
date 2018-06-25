package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.DeleteCustomerShippingAddressController;

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
       
        ANetApiResponse apiResponse = controller.getApiResponse();

		if (apiResponse != null) {
			if (apiResponse instanceof DeleteCustomerShippingAddressResponse) {
				DeleteCustomerShippingAddressResponse response = (DeleteCustomerShippingAddressResponse) apiResponse;

				if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
					System.out.println(response.getMessages().getMessage().get(0).getCode());
					System.out.println(response.getMessages().getMessage().get(0).getText());
				}
				else if (response.getMessages().getResultCode() == MessageTypeEnum.ERROR) {
	            	 System.out.println(response.getMessages().getMessage().get(0).getCode());
	                 System.out.println(response.getMessages().getMessage().get(0).getText());
	             }
			}
            else if (apiResponse instanceof ErrorResponse) {
		 		System.out.println(apiResponse.getMessages().getMessage().get(0).getCode());
                System.out.println(apiResponse.getMessages().getMessage().get(0).getText());
                System.out.println("Failed to delete customer shipping address:  " + apiResponse.getMessages().getResultCode());
            }
        }
		return apiResponse;
    }
}