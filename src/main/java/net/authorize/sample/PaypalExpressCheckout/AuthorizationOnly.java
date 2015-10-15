package net.authorize.sample.PaypalExpressCheckout;

import java.math.BigDecimal;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.CreateTransactionController;

public class AuthorizationOnly {

    public static void run(String apiLoginId, String transactionKey) {
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
        transactionRequest.setAmount(new BigDecimal("19.44"));

        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionRequest(transactionRequest);

       
        CreateTransactionController controller = new CreateTransactionController(request);
         
        // call the service
        controller.execute();

        // get the response from the service 
        CreateTransactionResponse response = controller.getApiResponse();
        MessagesType responseMessage = response.getMessages();
        
        //validate 
        if (responseMessage.getResultCode().equals(MessageTypeEnum.OK)) {
            System.out.println("Message Code : " + responseMessage.getMessage().get(0).getCode() + " | Message Text : " + responseMessage.getMessage().get(0).getText());
            if (response.getTransactionResponse() != null) {
                System.out.println("Success, Response Code : " + response.getTransactionResponse().getResponseCode() + " | Transaction ID : " + response.getTransactionResponse().getTransId());
            }
        } else {
            System.out.println("Error: " + response.getMessages().getMessage().get(0).getCode() + "  " + response.getMessages().getMessage().get(0).getText());
            if (response.getTransactionResponse() != null) {
                System.out.println("Transaction Error : " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode() + " : " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
            }
        }
    }
}