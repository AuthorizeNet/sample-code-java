package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.UpdateCustomerPaymentProfileController;

public class UpdateCustomerPaymentProfile {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey, String customerProfileId, String customerPaymentProfileId) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		//customer address
		CustomerAddressType customerAddress = new CustomerAddressType();
		customerAddress.setFirstName("John");
		customerAddress.setLastName("Doe");
		customerAddress.setAddress("123 Main Street");
		customerAddress.setCity("Bellevue");
		customerAddress.setState("WA");
		customerAddress.setZip("98004");
		customerAddress.setCountry("USA");
		customerAddress.setPhoneNumber("000-000-0000");
		
		//credit card details
		CreditCardType creditCard = new CreditCardType();
		creditCard.setCardNumber("4111111111111111");
		creditCard.setExpirationDate("2023-12");

		PaymentType paymentType = new PaymentType();
		paymentType.setCreditCard(creditCard);

		CustomerPaymentProfileExType customer = new CustomerPaymentProfileExType();
		customer.setPayment(paymentType);
		customer.setCustomerPaymentProfileId(customerPaymentProfileId);
		customer.setBillTo(customerAddress);

		UpdateCustomerPaymentProfileRequest apiRequest = new UpdateCustomerPaymentProfileRequest();
		apiRequest.setCustomerProfileId(customerProfileId);	
		apiRequest.setPaymentProfile(customer);
		apiRequest.setValidationMode(ValidationModeEnum.LIVE_MODE);
	
        UpdateCustomerPaymentProfileController controller = new UpdateCustomerPaymentProfileController(apiRequest);
        controller.execute();
       
		UpdateCustomerPaymentProfileResponse response = new UpdateCustomerPaymentProfileResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
            }
            else
            {
                System.out.println("Failed to update customer payment profile:  " + response.getMessages().getResultCode());
            }
        }
		return response;
	}	
}