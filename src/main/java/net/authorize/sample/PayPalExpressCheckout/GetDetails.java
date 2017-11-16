package net.authorize.sample.PayPalExpressCheckout;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetDetails {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey, String transactionID) {
        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
                
        PayPalType payPalType = new PayPalType();
        payPalType.setCancelUrl("http://www.merchanteCommerceSite.com/Success/TC25262");
        payPalType.setSuccessUrl("http://www.merchanteCommerceSite.com/Success/TC25262");
        // set payment type as PayPal
        PaymentType paymentType = new PaymentType();
        paymentType.setPayPal(payPalType);
        
        //create GetDetailsTransaction for any valid PayPal transaction Id for the Merchant
        TransactionRequestType transactionRequestType = new TransactionRequestType();
        transactionRequestType.setTransactionType(TransactionTypeEnum.GET_DETAILS_TRANSACTION.value());
        transactionRequestType.setPayment(paymentType);
        transactionRequestType.setRefTransId(transactionID);
        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(transactionRequestType);
        
        CreateTransactionController controller = new CreateTransactionController(apiRequest);
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
        			
        			if (result.getSecureAcceptance() != null)
                    	System.out.println("PayPal PayerID : "+ result.getSecureAcceptance().getPayerID());
                    
                    if (result.getShipTo() != null) {
                    	System.out.println("Shipping Address : "+ result.getShipTo().getFirstName());
    	                System.out.println(result.getShipTo().getAddress() +" " + result.getShipTo().getCity());
    	                System.out.println(result.getShipTo().getState() +" "+ result.getShipTo().getZip());
    	                System.out.println(result.getShipTo().getCountry());
                    }
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
