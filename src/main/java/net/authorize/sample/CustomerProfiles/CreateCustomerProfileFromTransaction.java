package net.authorize.sample.CustomerProfiles;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.authorize.Environment;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.CreateCustomerProfileFromTransactionRequest;
import net.authorize.api.contract.v1.CreateCustomerProfileResponse;
import net.authorize.api.contract.v1.CreateTransactionRequest;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.CreditCardType;
import net.authorize.api.contract.v1.CustomerDataType;
import net.authorize.api.contract.v1.CustomerProfileBaseType;
import net.authorize.api.contract.v1.ErrorResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.PaymentType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.controller.CreateCustomerProfileFromTransactionController;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class CreateCustomerProfileFromTransaction {

	public static ANetApiResponse run(String apiLoginId, String transactionKey, Double amount, String email) {

		ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
		
		CreditCardType creditCard = new CreditCardType();
		creditCard.setCardNumber("4111111111111111");
	    creditCard.setExpirationDate("0621");
				
		PaymentType paymentType = new PaymentType();
		paymentType.setCreditCard(creditCard);
				
		TransactionRequestType requestInternal = new TransactionRequestType();
		requestInternal.setTransactionType("authOnlyTransaction");
		requestInternal.setPayment(paymentType);
		requestInternal.setAmount(new BigDecimal(amount).setScale(2, RoundingMode.CEILING));
		
		CustomerDataType customer = new CustomerDataType();
		customer.setEmail(email);
		requestInternal.setCustomer(customer);
				
		CreateTransactionRequest transactionRequest = new CreateTransactionRequest();
		transactionRequest.setTransactionRequest(requestInternal);
				
		CreateTransactionController controller = new CreateTransactionController(transactionRequest);
		controller.execute();
				
		ANetApiResponse apiResponseForTransaction = controller.getApiResponse();
		
		if (apiResponseForTransaction != null) {
			 if (!(apiResponseForTransaction instanceof CreateTransactionResponse) && !(apiResponseForTransaction instanceof ErrorResponse)) {
				 System.out.println(apiResponseForTransaction.getMessages().getMessage().get(0).getCode());
                 System.out.println(apiResponseForTransaction.getMessages().getMessage().get(0).getText());
                 System.out.println("Failed to create transaction:  " + apiResponseForTransaction.getMessages().getResultCode());
                 
                 return apiResponseForTransaction;
			 }
		}
		
		CreateTransactionResponse transactionResponse = (CreateTransactionResponse) apiResponseForTransaction;

		CustomerProfileBaseType customerProfile = new CustomerProfileBaseType();
		customerProfile.setMerchantCustomerId("123213");
		customerProfile.setEmail("johnsnow@castleblack.com");
		customerProfile.setDescription("This is a sample customer profile");		
		
		CreateCustomerProfileFromTransactionRequest request = new CreateCustomerProfileFromTransactionRequest();
		request.setTransId(transactionResponse.getTransactionResponse().getTransId());
		// You can either specify the customer information in form of customerProfileBaseType object
		request.setCustomer(customerProfile);
		//  OR   
		// You can just provide the customer Profile ID
		// transaction_request.setCustomerProfileId("1232132");
		
		CreateCustomerProfileFromTransactionController createProfileController = new CreateCustomerProfileFromTransactionController(request);
		createProfileController.execute();
		ANetApiResponse apiResponse = createProfileController.getApiResponse();

		if (apiResponse != null) {
			 if (apiResponse instanceof CreateCustomerProfileResponse) {
				 CreateCustomerProfileResponse response = (CreateCustomerProfileResponse) apiResponse;
			
	             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
	            	System.out.println("Transaction ID : " + transactionResponse.getTransactionResponse().getTransId());
	                System.out.println("Customer Profile Created : " + response.getCustomerProfileId());
	                if (!response.getCustomerPaymentProfileIdList().getNumericString().isEmpty()) {
	                    System.out.println(response.getCustomerPaymentProfileIdList().getNumericString().get(0));
	                }
	                if (!response.getCustomerShippingAddressIdList().getNumericString().isEmpty()) {
	                    System.out.println(response.getCustomerShippingAddressIdList().getNumericString().get(0));
	                }
	                if (!response.getValidationDirectResponseList().getString().isEmpty()) {
	                    System.out.println(response.getValidationDirectResponseList().getString().get(0));
	                }
	             } 
	             else if (response.getMessages().getResultCode() == MessageTypeEnum.ERROR) {
	            	 System.out.println(response.getMessages().getMessage().get(0).getCode());
	                 System.out.println(response.getMessages().getMessage().get(0).getText());
	             }	
           }
		   else if (apiResponse instanceof ErrorResponse) {
		 		System.out.println(apiResponse.getMessages().getMessage().get(0).getCode());
               System.out.println(apiResponse.getMessages().getMessage().get(0).getText());
               System.out.println("Failed to create customer payment profile:  " + apiResponse.getMessages().getResultCode());
           }
       }

		return apiResponse;
	}
}
