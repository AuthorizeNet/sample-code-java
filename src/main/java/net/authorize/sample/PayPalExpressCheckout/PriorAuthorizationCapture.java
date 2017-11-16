/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.authorize.sample.PayPalExpressCheckout;

import java.math.BigDecimal;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.Environment;

/**
 *
 * @author gnongsie
 */
public class PriorAuthorizationCapture {
    public static ANetApiResponse run(String apiLoginId, String apiTransactionKey, String transactionId) {
        System.out.println("PayPal Prior Authorization Transaction");
        
        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        
        // Define the merchant information (Authentication / Transaction ID)
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
            merchantAuthenticationType.setName(apiLoginId);
            merchantAuthenticationType.setTransactionKey(apiTransactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        // Populate the payment data
        PayPalType payPalType = new PayPalType();
            payPalType.setCancelUrl("http://www.merchanteCommerceSite.com/Success/TC25262");
            payPalType.setSuccessUrl("http://www.merchanteCommerceSite.com/Success/TC25262");

        // Standard api call to retrieve response
        PaymentType paymentType = new PaymentType();
            paymentType.setPayPal(payPalType);
        
        // Create the payment transaction request
        TransactionRequestType transactionRequest = new TransactionRequestType();
            transactionRequest.setTransactionType(TransactionTypeEnum.PRIOR_AUTH_CAPTURE_TRANSACTION.value());
            transactionRequest.setPayment(paymentType);
            transactionRequest.setAmount(BigDecimal.valueOf(19.99));
            transactionRequest.setRefTransId(transactionId);

        // Make the API Request
        CreateTransactionRequest request = new CreateTransactionRequest();
            request.setTransactionRequest(transactionRequest);
        
        // Instantiate the contoller that will call the service
        CreateTransactionController controller = new CreateTransactionController(request);
            controller.execute();
            
        CreateTransactionResponse response = controller.getApiResponse();
        
        if (response!=null) {
        	// If API Response is ok, go ahead and check the transaction response
        	if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
        		TransactionResponse result = response.getTransactionResponse();
        		if (result.getMessages() != null) {
        			System.out.println("Successfully created transaction with Transaction ID: " + result.getTransId());
        			System.out.println("Response Code: " + result.getResponseCode());
        			System.out.println("Message Code: " + result.getMessages().getMessage().get(0).getCode());
        			System.out.println("Description: " + result.getMessages().getMessage().get(0).getDescription());
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
