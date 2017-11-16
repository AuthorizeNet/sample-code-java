package net.authorize.sample.FraudManagement;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.authorize.Environment;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.AfdsTransactionEnum;
import net.authorize.api.contract.v1.CreateTransactionRequest;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.CreditCardType;
import net.authorize.api.contract.v1.HeldTransactionRequestType;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.PaymentType;
import net.authorize.api.contract.v1.TransactionRequestType;
import net.authorize.api.contract.v1.TransactionResponse;
import net.authorize.api.contract.v1.TransactionTypeEnum;
import net.authorize.api.contract.v1.UpdateHeldTransactionRequest;
import net.authorize.api.contract.v1.UpdateHeldTransactionResponse;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.UpdateHeldTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

public class ApproveOrDeclineHeldTransaction {

    public static ANetApiResponse run(String apiLoginId, String transactionKey, String transactionId) {

        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);


        // Create the payment transaction request
        HeldTransactionRequestType txnRequest = new HeldTransactionRequestType();
        txnRequest.setAction(AfdsTransactionEnum.APPROVE);
        txnRequest.setRefTransId("60012148613");

        // Make the API Request
        UpdateHeldTransactionRequest apiRequest = new UpdateHeldTransactionRequest();
        apiRequest.setHeldTransactionRequest(txnRequest);
        UpdateHeldTransactionController controller = new UpdateHeldTransactionController(apiRequest);
        controller.execute();


        UpdateHeldTransactionResponse response = controller.getApiResponse();

        if (response!=null) {
            // If API Response is ok, go ahead and check the transaction response
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                TransactionResponse result = response.getTransactionResponse();
                if (result.getMessages() != null) {
                    System.out.println("Successfully updated transaction with Transaction ID: " + result.getTransId());
                    System.out.println("Response Code: " + result.getResponseCode());
                    System.out.println("Message Code: " + result.getMessages().getMessage().get(0).getCode());
                    System.out.println("Description: " + result.getMessages().getMessage().get(0).getDescription());
                    System.out.println("Auth Code: " + result.getAuthCode());
                } else {
                    System.out.println("Failed while updating transaction.");
                    if (response.getTransactionResponse().getErrors() != null) {
                        System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
                        System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
                    }
                }
            } else {
                System.out.println("Failed while updating transaction.");
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
