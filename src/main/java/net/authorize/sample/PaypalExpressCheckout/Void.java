package net.authorize.sample.PaypalExpressCheckout;

import net.authorize.Environment;
import net.authorize.api.contract.v1.CreateTransactionRequest;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.PayPalType;
import net.authorize.api.contract.v1.PaymentType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.contract.v1.TransactionResponse;
import net.authorize.api.contract.v1.TransactionTypeEnum;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class Void {
	public static void run(String apiLoginId, String transactionKey) {

		System.out.println("PayPal Void Transaction");

		//Common code to set for all requests
		ApiOperationBase.setEnvironment(Environment.SANDBOX);

		MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType();
		merchantAuthenticationType.setName(apiLoginId);
		merchantAuthenticationType.setTransactionKey(transactionKey);
		ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		PayPalType payPalType = new PayPalType();
		payPalType.setSuccessUrl("");
		payPalType.setCancelUrl("");

		//standard api call to retrieve response
		PaymentType paymentType = new PaymentType();
		paymentType.setPayPal(payPalType);

		// Create the payment transaction request
		TransactionRequestType transactionRequest = new TransactionRequestType();

		transactionRequest.setTransactionType(TransactionTypeEnum.VOID_TRANSACTION.value());
		transactionRequest.setPayment(paymentType);
		transactionRequest.setRefTransId("2241708879");

		// Make the API Request
		CreateTransactionRequest apiRequest = new CreateTransactionRequest();
		apiRequest.setTransactionRequest(transactionRequest);
		CreateTransactionController controller = new CreateTransactionController(apiRequest);
		controller.execute();

		CreateTransactionResponse response = controller.getApiResponse();

		// If API Response is ok, go ahead and check the transaction response
		if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

			if (response.getTransactionResponse() != null)
			{
				TransactionResponse result = response.getTransactionResponse();
				System.out.println("Successful Paypal Void Transaction");
				System.out.println("Response Code : " + result.getResponseCode());
			}
		}
		else
		{
			System.out.println("Failed Paypal Void Transaction");
			if(!response.getMessages().getMessage().isEmpty())
				System.out.println("Error: " + response.getMessages().getMessage().get(0).getCode() + "  " + response.getMessages().getMessage().get(0).getText());

			if (response.getTransactionResponse() != null)
				if(!response.getTransactionResponse().getErrors().getError().isEmpty())
					System.out.println("Transaction Error : " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode() + " " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
		}
	}

}
