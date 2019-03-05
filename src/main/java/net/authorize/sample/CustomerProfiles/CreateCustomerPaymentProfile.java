package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;

import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.controller.CreateCustomerPaymentProfileController;
import net.authorize.api.controller.base.ApiOperationBase;

//author @krgupta
public class CreateCustomerPaymentProfile {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey, String customerProfileId) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
		
	//private String getPaymentDetails(MerchantAuthenticationType merchantAuthentication, String customerprofileId, ValidationModeEnum validationMode) {
		CreateCustomerPaymentProfileRequest apiRequest = new CreateCustomerPaymentProfileRequest();
		apiRequest.setMerchantAuthentication(merchantAuthenticationType);
		apiRequest.setCustomerProfileId(customerProfileId);	

		//customer address
		CustomerAddressType customerAddress = new CustomerAddressType();
		customerAddress.setFirstName("test");
		customerAddress.setLastName("scenario");
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
		creditCard.setCardCode("122");

		CustomerPaymentProfileType profile = new CustomerPaymentProfileType();
		profile.setBillTo(customerAddress);

		PaymentType payment = new PaymentType();
		payment.setCreditCard(creditCard);
		profile.setPayment(payment);

		apiRequest.setPaymentProfile(profile);
		
		CreateCustomerPaymentProfileController controller = new CreateCustomerPaymentProfileController(apiRequest);
		controller.execute();
       
		CreateCustomerPaymentProfileResponse response = new CreateCustomerPaymentProfileResponse();
		response = controller.getApiResponse();
		if (response!=null) {
             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
            	
                System.out.println(response.getCustomerPaymentProfileId());
 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
                if (response.getValidationDirectResponse() != null)
                	System.out.println(response.getValidationDirectResponse());
            }
            else
            {
                System.out.println("Failed to create customer payment profile:  " + response.getMessages().getResultCode());
            }
        }

		return response;
	}	
}
