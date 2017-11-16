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
import net.authorize.api.contract.v1.MerchantAuthenticationType;
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
				
		CreateTransactionRequest request = new CreateTransactionRequest();
		request.setTransactionRequest(requestInternal);
				
		CreateTransactionController controller = new CreateTransactionController(request);
		controller.execute();
				
		CreateTransactionResponse response = controller.getApiResponse();

		CustomerProfileBaseType customerProfile = new CustomerProfileBaseType();
		customerProfile.setMerchantCustomerId("123213");
		customerProfile.setEmail("johnsnow@castleblack.com");
		customerProfile.setDescription("This is a sample customer profile");		
		
		CreateCustomerProfileFromTransactionRequest transaction_request = new CreateCustomerProfileFromTransactionRequest();
		transaction_request.setTransId(response.getTransactionResponse().getTransId());
		// You can either specify the customer information in form of customerProfileBaseType object
		transaction_request.setCustomer(customerProfile);
		//  OR   
		// You can just provide the customer Profile ID
		// transaction_request.setCustomerProfileId("1232132");
		
		CreateCustomerProfileFromTransactionController createProfileController = new CreateCustomerProfileFromTransactionController(transaction_request);
		createProfileController.execute();
		CreateCustomerProfileResponse customer_response = createProfileController.getApiResponse();

		if (customer_response != null) {
			System.out.println(transaction_request.getTransId());
		}
		return customer_response;
	}
}
