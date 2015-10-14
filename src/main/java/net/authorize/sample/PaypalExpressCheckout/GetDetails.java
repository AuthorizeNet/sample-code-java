package net.authorize.sample.PaypalExpressCheckout;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetDetails {
	
	public static void run(String apiLoginId, String transactionKey) {
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
        transactionRequestType.setRefTransId("2241936095");
        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(transactionRequestType);
        
        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();
        
        
        CreateTransactionResponse response = controller.getApiResponse();
        
        if (response!=null && response.getMessages().getResultCode() == MessageTypeEnum.OK) {

            TransactionResponse result = response.getTransactionResponse();
            if (result.getResponseCode().equals("1")) {
                System.out.println("Successful PayPal Get Details Transaction");
                System.out.println("Account Type : "+ result.getAccountType());
                System.out.println("PayPal PayerID : "+ result.getSecureAcceptance().getPayerID());
                System.out.println("Shipping Address : "+ result.getShipTo().getFirstName());
                System.out.println(result.getShipTo().getAddress() +" " + result.getShipTo().getCity());
                System.out.println(result.getShipTo().getState() +" "+ result.getShipTo().getZip());
                System.out.println(result.getShipTo().getCountry());
                System.out.println("Transaction ID : " + result.getTransId());
            }
        }
        else
        {
            System.out.println("Failed Transaction:  "+response.getMessages().getMessage().get(0).getCode()+ "   " + response.getMessages().getMessage().get(0).getText());
        }

	}

}
