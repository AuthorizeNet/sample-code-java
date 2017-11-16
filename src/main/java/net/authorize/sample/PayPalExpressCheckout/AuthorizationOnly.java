package net.authorize.sample.PayPalExpressCheckout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.authorize.Environment;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.CreateTransactionRequest;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.MessagesType;
import net.authorize.api.contract.v1.PayPalType;
import net.authorize.api.contract.v1.PaymentType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.contract.v1.TransactionResponse;
import net.authorize.api.contract.v1.TransactionTypeEnum;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class AuthorizationOnly {

    public static ANetApiResponse run(String apiLoginId, String transactionKey, Double amount) {
        System.out.println("PayPal Authorize Only Transaction");
        
        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        PayPalType payPal = new PayPalType();
        payPal.setCancelUrl("http://www.merchanteCommerceSite.com/Success/TC25262");
        payPal.setSuccessUrl("http://www.merchanteCommerceSite.com/Success/TC25262");

        //standard api call to retrieve response
        PaymentType paymentType = new PaymentType();
        paymentType.setPayPal(payPal);

        TransactionRequestType transactionRequest = new TransactionRequestType();
        transactionRequest.setTransactionType(TransactionTypeEnum.AUTH_ONLY_TRANSACTION.value());
        transactionRequest.setPayment(paymentType);
        transactionRequest.setAmount(new BigDecimal(amount).setScale(2, RoundingMode.CEILING));

        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionRequest(transactionRequest);

       
        CreateTransactionController controller = new CreateTransactionController(request);
         
        // call the service
        controller.execute();

        // get the response from the service 
        CreateTransactionResponse response = controller.getApiResponse();
        MessagesType responseMessage = response.getMessages();
        
        //validate 
        if (response!=null) {
        	// If API Response is ok, go ahead and check the transaction response
        	if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
        		TransactionResponse result = response.getTransactionResponse();
        		if (result.getMessages() != null) {
        			System.out.println("Successfully created transaction with Transaction ID: " + result.getTransId());
        			System.out.println("Response Code: " + result.getResponseCode());
        			System.out.println("Message Code: " + result.getMessages().getMessage().get(0).getCode());
        			System.out.println("Description: " + result.getMessages().getMessage().get(0).getDescription());
        			System.out.println("Secure Acceptance URL : " + result.getSecureAcceptance().getSecureAcceptanceUrl());
        		}
        		else {
        			System.out.println("Failed Transaction.");
        			if (response.getTransactionResponse().getErrors() != null) {
        				System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
        				System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
        			}
        		}
        	}
        	else {
        		System.out.println("Failed Transaction.");
        		if (response.getTransactionResponse() != null && response.getTransactionResponse().getErrors() != null) {
        			System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
        			System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
        		}
        		else {
        			System.out.println("Error Code: " + response.getMessages().getMessage().get(0).getCode());
        			System.out.println("Error message: " + response.getMessages().getMessage().get(0).getText());
        		}
        	}
        }
        else {
        	System.out.println("Null Response.");
        }
        
		return response;
    }
}