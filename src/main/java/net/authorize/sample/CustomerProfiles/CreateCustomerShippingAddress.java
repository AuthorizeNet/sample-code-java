package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateCustomerShippingAddressController;
import net.authorize.api.controller.base.ApiOperationBase;

public class CreateCustomerShippingAddress {

	public static ANetApiResponse run(String apiLoginId, String transactionKey, String customerProfileId) {

		ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        CustomerAddressType customerAddressType = new CustomerAddressType();
        customerAddressType.setFirstName("John");
        customerAddressType.setLastName("Doe");
        customerAddressType.setAddress("123 Main St.");
        customerAddressType.setCity("Bellevue");
        customerAddressType.setState("WA");
        customerAddressType.setZip("98004");
        customerAddressType.setCountry("USA");
        customerAddressType.setPhoneNumber("000-000-0000");

        CreateCustomerShippingAddressRequest apiRequest = new CreateCustomerShippingAddressRequest();
        apiRequest.setCustomerProfileId(customerProfileId);
        apiRequest.setAddress(customerAddressType);

        CreateCustomerShippingAddressController controller = new CreateCustomerShippingAddressController(apiRequest);
        controller.execute();

        ANetApiResponse apiResponse = controller.getApiResponse();
        if (apiResponse != null) {
        	 if (apiResponse instanceof CreateCustomerShippingAddressResponse) {
        		 CreateCustomerShippingAddressResponse response = (CreateCustomerShippingAddressResponse) apiResponse;

	             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {	
	                System.out.println(response.getCustomerAddressId());
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
                System.out.println("Failed to create customer shipping address:  " + apiResponse.getMessages().getResultCode());
            }
        }
		return apiResponse;
 
    }
}