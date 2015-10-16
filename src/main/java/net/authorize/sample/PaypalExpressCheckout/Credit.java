package net.authorize.sample.PaypalExpressCheckout;

import java.math.BigDecimal;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.CreateTransactionController;

public class Credit {

	public static void run(String apiLoginId, String transactionKey, String transactionId) {

		System.out.println("PayPal Credit Transaction");
		//Common code to set for all requests
		ApiOperationBase.setEnvironment(Environment.SANDBOX);
		MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
		merchantAuthenticationType.setName(apiLoginId);
		merchantAuthenticationType.setTransactionKey(transactionKey);
		ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		// Populate PayPal Transaction Data
		PayPalType payPalType = new PayPalType();
		payPalType.setCancelUrl("http://www.merchanteCommerceSite.com/Success/TC25262");
		payPalType.setSuccessUrl("http://www.merchanteCommerceSite.com/Success/TC25262");

		// Populate the payment data
		PaymentType paymentType = new PaymentType();
		paymentType.setPayPal(payPalType);

		// Create the payment transaction request
		TransactionRequestType txnRequest = new TransactionRequestType();
		txnRequest.setTransactionType(TransactionTypeEnum.REFUND_TRANSACTION.value());
		txnRequest.setPayment(paymentType);
		txnRequest.setAmount(new BigDecimal(500.00));
		txnRequest.setRefTransId(transactionId);

		// Make the API Request
		CreateTransactionRequest apiRequest = new CreateTransactionRequest();
		apiRequest.setTransactionRequest(txnRequest);
		CreateTransactionController controller = new CreateTransactionController(apiRequest);
		controller.execute();


		CreateTransactionResponse response = controller.getApiResponse();

		if (response!=null) {

			// If API Response is ok, go ahead and check the transaction response
			if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

				TransactionResponse result = response.getTransactionResponse();
				if (result.getResponseCode().equals("1")) {
					System.out.println(result.getResponseCode());
					System.out.println("Successful PayPal Transaction");
					//System.out.println("Reference Transaction ID: " + result.getRefTransID());
					System.out.println(result.getMessages().getMessage().get(0).getDescription());
				}
				else
				{
					System.out.println("Transaction Error:  "+result.getErrors().getError().get(0).getErrorCode()+" "+result.getErrors().getError().get(0).getErrorText());

				}
			}
			else
			{
				System.out.println("Error: "+response.getMessages().getMessage().get(0).getCode()+ " " + response.getMessages().getMessage().get(0).getText());

			}
		}

	}



}
