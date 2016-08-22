package net.authorize.sample.MobileInAppTransactions;

import java.math.BigDecimal;
import net.authorize.Environment;
import net.authorize.TransactionType;
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
        if(response!=null)
        {
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK)
            {
                if (response.getTransactionResponse() != null)
                {
                        TransactionResponse result = response.getTransactionResponse();
                        System.out.println("Successful: Create an Accept Transaction");
                        System.out.println("Response Code : " + result.getResponseCode());
                        System.out.println("Transaction ID : " + result.getTransId());
                        System.out.println("Auth code : " + result.getAuthCode());
                }
            }
            else
            {
                
                System.out.println("Failed: Create Accept Transaction");
                if(!response.getMessages().getMessage().isEmpty())
                        System.out.println("Error: " + response.getMessages().getMessage().get(0).getCode() + "  " + response.getMessages().getMessage().get(0).getText());

                if (response.getTransactionResponse() != null)
                    if(response.getTransactionResponse().getErrors() != null && !response.getTransactionResponse().getErrors().getError().isEmpty())
                        System.out.println("Transaction Error : " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode() + " " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
            }
        }
		return response;
    }
}
