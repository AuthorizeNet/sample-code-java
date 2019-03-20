package net.authorize.sample.MobileInAppTransactions;

import java.math.BigDecimal;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class CreateAnAcceptTransaction
{
    public static ANetApiResponse run(String apiLoginId, String transactionKey)
    {
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        
        // Giving the merchant authentication information
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
        // Setting the payment
        OpaqueDataType op = new OpaqueDataType();
        op.setDataDescriptor("COMMON.ACCEPT.INAPP.PAYMENT");
        op.setDataValue("9471471570959063005001");
        PaymentType paymentOne = new PaymentType();
        paymentOne.setOpaqueData(op);
        // Setting the transaction
        TransactionRequestType transactionRequest = new TransactionRequestType();
        transactionRequest.setAmount(new BigDecimal("131"));
        transactionRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        transactionRequest.setPayment(paymentOne);
        // Making the api request
        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(transactionRequest);
        // Creating the controller
        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();
        // Getting the response
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
        			System.out.println("Auth code : " + result.getAuthCode());
                } else {
        			System.out.println("Failed Transaction.");
        			if (response.getTransactionResponse().getErrors() != null) {
        				System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
        				System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
        			}
        		}
            } else {
        		System.out.println("Failed Transaction.");
        		if (response.getTransactionResponse() != null && response.getTransactionResponse().getErrors() != null) {
        			System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
        			System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
                } else {
        			System.out.println("Error Code: " + response.getMessages().getMessage().get(0).getCode());
        			System.out.println("Error message: " + response.getMessages().getMessage().get(0).getText());
        		}
        	}
        } else {
        	System.out.println("Null Response.");
        }
        
		return response;
    }
}
