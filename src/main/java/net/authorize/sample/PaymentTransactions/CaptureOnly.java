package net.authorize.sample.PaymentTransactions;

import java.math.BigDecimal;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.CreateTransactionController;

public class CaptureOnly {

    //
    // Run this sample from command line with:
    //                 java -jar target/ChargeCreditCard-jar-with-dependencies.jar
    //
    public static void run(String apiLoginId, String transactionKey) {
        
        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        // Create the payment transaction request
        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.CAPTURE_ONLY_TRANSACTION.value());
        txnRequest.setRefTransId("2238786428");

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
                    System.out.println("Successfully Captured Transaction");
                    System.out.println(result.getAuthCode());
                    System.out.println(result.getTransId());
                }
                else
                {
                    System.out.println("Failed Transaction"+result.getResponseCode());
                }
            }
            else
            {
                System.out.println("Failed Transaction:  "+response.getMessages().getResultCode());
            }
        }

    }

}
