package net.authorize.sample.PaypalExpressCheckout;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.authorize.Environment;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.CreateTransactionRequest;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.PayPalType;
import net.authorize.api.contract.v1.PaymentType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.contract.v1.TransactionTypeEnum;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;


public class AuthorizationAndCaptureContinue
{
	public static ANetApiResponse run(String apiLoginId, String transactionKey, String TransactionID, String payerID , Double amount){
		
		System.out.println("PayPal Authorize Capture-Continue Transaction");
		
		//Common code to set for all requests
		ApiOperationBase.setEnvironment(Environment.SANDBOX);
		
		// define the merchant information (authentication / transaction id)
		MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
        
        // Get Transaction Code and Payer ID from the User
        
        
        // Set PayPal type and attributes
        PayPalType payPalType = new PayPalType();
        payPalType.setCancelUrl("http://www.merchanteCommerceSite.com/Success/TC25262");
        payPalType.setSuccessUrl("http://www.merchanteCommerceSite.com/Success/TC25262");
        payPalType.setPayerID(payerID);
        
        //standard api call to retrieve response
        PaymentType paymentType = new PaymentType();
        paymentType.setPayPal(payPalType);
        
        TransactionRequestType transactionRequest = new TransactionRequestType();
        transactionRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_CONTINUE_TRANSACTION.value().toString());
        transactionRequest.setPayment(paymentType);
        transactionRequest.setAmount(new BigDecimal(amount).setScale(2, RoundingMode.CEILING));
        transactionRequest.setRefTransId(TransactionID);
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionRequest(transactionRequest);
        
        // instantiate the controller that will call the service
        CreateTransactionController controller = new CreateTransactionController(request);        
        controller.execute();
        
        // get the response from the service (errors contained if any)
        CreateTransactionResponse response = controller.getApiResponse();
        
        //validate
        if(response != null){
            if(response.getMessages().getResultCode() == MessageTypeEnum.OK){
            	if(response.getTransactionResponse() != null){
            		if((response.getTransactionResponse().getMessages() != null) && 
            				(!response.getTransactionResponse().getMessages().getMessage().isEmpty())) {
            			System.out.println("Success, \nMessage : "+response.getTransactionResponse().getMessages().getMessage().get(0).getDescription() );
            			// Get Auth Code By :  response.getTransactionResponse().getAuthCode()
            		}
            	}
            }
            else {
    			System.out.println("Error: " + response.getMessages().getMessage().get(0).getCode()+ "   " + response.getMessages().getMessage().get(0).getText() ); 
    			if(response.getTransactionResponse() != null){
    				System.out.println("Transaction Error : "+ response.getTransactionResponse().getErrors().getError().get(0).getErrorCode() + "    "  +  response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
    			}
    		}
        }

		return response; 
	}
}
