package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import java.math.BigDecimal;

import net.authorize.api.controller.CreateCustomerProfileFromTransactionController;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class CreateCustomerProfileFromTransaction {

	public static void run(String apiLoginId, String transactionKey) {

		ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
		
		String customerprofileId = "36374423" ;

		CreditCardType creditCard = new CreditCardType();
		creditCard.setCardNumber("4111111111111111");
	    creditCard.setExpirationDate("0616");
				
		PaymentType paymentType = new PaymentType();
		paymentType.setCreditCard(creditCard);
				
		TransactionRequestType requestInternal = new TransactionRequestType();
		requestInternal.setTransactionType("authOnlyTransaction");
		requestInternal.setPayment(paymentType);
		requestInternal.setAmount(new BigDecimal(System.currentTimeMillis() % 100));
		
		CustomerDataType customer = new CustomerDataType();
		customer.setEmail(System.currentTimeMillis()+"@b.bla");
		requestInternal.setCustomer(customer);
				
		CreateTransactionRequest request = new CreateTransactionRequest();
		request.setTransactionRequest(requestInternal);
				
		CreateTransactionController controller = new CreateTransactionController(request);
		controller.execute();
				
		CreateTransactionResponse response = controller.getApiResponse();

		CreateCustomerProfileFromTransactionRequest transaction_request = new CreateCustomerProfileFromTransactionRequest();
		transaction_request.setTransId(response.getTransactionResponse().getTransId());
		CreateCustomerProfileFromTransactionController createProfileController = new CreateCustomerProfileFromTransactionController(transaction_request);
		createProfileController.execute();
		CreateCustomerProfileResponse customer_response = createProfileController.getApiResponse();

		if(customer_response != null) {
			System.out.println(transaction_request.getTransId());
		}
	}
}
