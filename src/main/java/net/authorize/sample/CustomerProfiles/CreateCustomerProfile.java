package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import java.math.BigDecimal;
import net.authorize.api.controller.CreateCustomerProfileController;
import net.authorize.api.controller.base.ApiOperationBase;

public class CreateCustomerProfile {

	public static void run(String apiLoginId, String transactionKey) {

		ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        CreditCardType creditCard = new CreditCardType();
		creditCard.setCardNumber("4111111111111111");
	    creditCard.setExpirationDate("1220");
				
		PaymentType paymentType = new PaymentType();
		paymentType.setCreditCard(creditCard);

		CustomerPaymentProfileType customerPaymentProfileType = new CustomerPaymentProfileType();
		customerPaymentProfileType.setCustomerType(CustomerTypeEnum.INDIVIDUAL);
		customerPaymentProfileType.setPayment(paymentType);

        CustomerProfileType customerProfileType = new CustomerProfileType();
        customerProfileType.setMerchantCustomerId("Merchant_Customer_ID");
        customerProfileType.setDescription("Profile description here");
        customerProfileType.setEmail("customer-profile-email@here.com");
        customerProfileType.getPaymentProfiles().add(customerPaymentProfileType);

        CreateCustomerProfileRequest apiRequest = new CreateCustomerProfileRequest();
        apiRequest.setProfile(customerProfileType);
        apiRequest.setValidationMode(ValidationModeEnum.TEST_MODE);
        CreateCustomerProfileController controller = new CreateCustomerProfileController(apiRequest);
        controller.execute();
        CreateCustomerProfileResponse response = controller.getApiResponse();
        if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

                System.out.println(response.getCustomerProfileId());
                System.out.println(response.getCustomerPaymentProfileIdList().getNumericString().get(0));
                System.out.println(response.getCustomerShippingAddressIdList().getNumericString().get(0));
                System.out.println(response.getValidationDirectResponseList().getString().get(0));
            }
            else
            {
                System.out.println("Failed to create customer profile:  " + response.getMessages().getResultCode());
            }
        }

    }
}