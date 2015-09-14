package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;

import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.GetCustomerShippingAddressController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetCustomerShippingAddress {
	
	public static void run(String apiLoginId, String transactionKey) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        GetCustomerShippingAddressRequest apiRequest = new GetCustomerShippingAddressRequest();
        apiRequest.setCustomerProfileId("10000");
        apiRequest.setCustomerAddressId("30000");

        GetCustomerShippingAddressController controller = new GetCustomerShippingAddressController(apiRequest);
        controller.execute();
       
		GetCustomerShippingAddressResponse response = new GetCustomerShippingAddressResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());

                System.out.println(response.getAddress().getFirstName());
                System.out.println(response.getAddress().getLastName());
                System.out.println(response.getAddress().getCompany());
                System.out.println(response.getAddress().getAddress());
                System.out.println(response.getAddress().getCity());
                System.out.println(response.getAddress().getState());
                System.out.println(response.getAddress().getZip());
                System.out.println(response.getAddress().getCountry());
                System.out.println(response.getAddress().getPhoneNumber());
                System.out.println(response.getAddress().getFaxNumber());

                System.out.println(response.getAddress().getCustomerAddressId());
            }
            else
            {
                System.out.println("Failed to get customer shipping address:  " + response.getMessages().getResultCode());
            }
        }
    }
}