package net.authorize.sample.PaymentTransactions;

import java.math.BigDecimal;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.CreateTransactionController;

public class VoidTransaction {

    //
    // Run this sample from command line with:
    //                 java -jar target/ChargeCreditCard-jar-with-dependencies.jar
    //
    public static void run(String apiLoginId, String transactionKey) {

        // Required: 
        /*
         * Merchant Authentication
         * * name
         * * transaction key
         * RefTransId 
         */

        // Create Transaction Request. 
        // Fill in Merchant authentication field. 
        // Ref id
        // Transation Reuest
        //   TransactionType = voidTransaction
        //   refTransId

        
        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName("5KP3u95bQpv");
        merchantAuthenticationType.setTransactionKey("4Ktq966gC55GAX7S");
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        // Create the payment transaction request
        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.VOID_TRANSACTION.value());
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
                    System.out.println("Successfully Voided Transaction");
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
