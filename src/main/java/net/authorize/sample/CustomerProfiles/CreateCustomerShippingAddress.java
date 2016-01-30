package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import java.math.BigDecimal;
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

        CreateCustomerShippingAddressResponse response = controller.getApiResponse();
        if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

                System.out.println(response.getCustomerAddressId());
                System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
            }
            else
            {
                System.out.println("Failed to create customer shipping address:  " + response.getMessages().getResultCode());
            }
        }
		return response;
 
    }
}