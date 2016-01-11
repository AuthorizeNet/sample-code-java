package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.UpdateCustomerShippingAddressController;

public class UpdateCustomerShippingAddress {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey, String customerProfileID, String customerAddressId) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		CustomerAddressExType customer = new CustomerAddressExType();
		customer.setFirstName("John");
        customer.setLastName("Doe");
        customer.setAddress("123 Main St.");
        customer.setCity("Bellevue");
        customer.setState("WA");
        customer.setZip("98004");
        customer.setCountry("USA");
        customer.setPhoneNumber("000-000-0000");
        customer.setCustomerAddressId(customerAddressId);

		UpdateCustomerShippingAddressRequest apiRequest = new UpdateCustomerShippingAddressRequest();
		apiRequest.setCustomerProfileId(customerProfileID);
		apiRequest.setAddress(customer);
	
        UpdateCustomerShippingAddressController controller = new UpdateCustomerShippingAddressController(apiRequest);
        controller.execute();
       
		UpdateCustomerShippingAddressResponse response = new UpdateCustomerShippingAddressResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
            }
            else
            {
                System.out.println("Failed to update customer shipping address:  " + response.getMessages().getResultCode());
            }
        }
		return response;
	}	
}