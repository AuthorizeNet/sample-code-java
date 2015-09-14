package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;

import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.controller.base.ApiOperationBase;

import net.authorize.Transaction;
import net.authorize.api.controller.CreateCustomerProfileController;
import net.authorize.api.controller.CreateCustomerProfileFromTransactionController;
import net.authorize.api.controller.CreateCustomerPaymentProfileController;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.cim.Result;
import net.authorize.cim.TransactionType;
import net.authorize.cim.ValidationModeType;

//author @krgupta
public class CreateCustomerPaymentProfile {
	
	public static void run(String apiLoginId, String transactionKey) {

<<<<<<< HEAD:src/main/java/net/authorize/sample/CustomerProfiles/CreateCustomerPaymentProfile.java
		ApiOperationBase.setEnvironment(Environment.SANDBOX);

		MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
		merchantAuthenticationType.setName(apiLoginId);
		merchantAuthenticationType.setTransactionKey(transactionKey);
		ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		String customerprofileId = "36374423";


		//private String getPaymentDetails(MerchantAuthenticationType merchantAuthentication, String customerprofileId, ValidationModeEnum validationMode) {
		CreateCustomerPaymentProfileRequest getRequest = new CreateCustomerPaymentProfileRequest();
		getRequest.setMerchantAuthentication(merchantAuthenticationType);
		getRequest.setCustomerProfileId(customerprofileId);
=======
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
		
		String customerprofileId = "36374423" ;
		
		
	//private String getPaymentDetails(MerchantAuthenticationType merchantAuthentication, String customerprofileId, ValidationModeEnum validationMode) {
		CreateCustomerPaymentProfileRequest apiRequest = new CreateCustomerPaymentProfileRequest();
		apiRequest.setMerchantAuthentication(merchantAuthenticationType);
		apiRequest.setCustomerProfileId(customerprofileId);	
>>>>>>> 907affc0b5af4a8b5009879bd21658213eee7bb4:src/main/java/net/authorize/sample/CustomerProfiles/CreateCustomerProfilePaymentProfile.java

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
		creditCard.setCardCode("");
<<<<<<< HEAD:src/main/java/net/authorize/sample/CustomerProfiles/CreateCustomerPaymentProfile.java

		CustomerPaymentProfileType profile = new CustomerPaymentProfileType();
		profile.setBillTo(customerAddress);

		PaymentType payment = new PaymentType();
		payment.setCreditCard(creditCard);
		profile.setPayment(payment);

		getRequest.setPaymentProfile(profile);

		CreateCustomerPaymentProfileController controller = new CreateCustomerPaymentProfileController(getRequest);
		controller.execute();

		CreateCustomerPaymentProfileResponse response = new CreateCustomerPaymentProfileResponse();
		response = controller.getApiResponse();

		if (response != null) {

			if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

				System.out.println(response.getCustomerPaymentProfileId());
				System.out.println(response.getMessages().getMessage().get(0).getCode());
				System.out.println(response.getMessages().getMessage().get(0).getText());
			} else {
				System.out.println("Failed to create Payment Profile:  " + response.getMessages().getResultCode());
			}
		}
		else
		{
			System.out.println("Failed to create Payment Profile:  Response is null");
		}
	}
	}
=======
	
        CreateCustomerPaymentProfileController controller = new CreateCustomerPaymentProfileController(apiRequest);
        controller.execute();
       
		CreateCustomerPaymentProfileResponse response = new CreateCustomerPaymentProfileResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

                System.out.println(response.getCustomerPaymentProfileId());
 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
                System.out.println(response.getValidationDirectResponse());
            }
            else
            {
                System.out.println("Failed to create customer payment profile:  " + response.getMessages().getResultCode());
            }
        }
	}	
}
>>>>>>> 907affc0b5af4a8b5009879bd21658213eee7bb4:src/main/java/net/authorize/sample/CustomerProfiles/CreateCustomerProfilePaymentProfile.java
