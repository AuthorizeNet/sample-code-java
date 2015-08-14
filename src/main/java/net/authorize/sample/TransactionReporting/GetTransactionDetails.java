package net.authorize.sample.TransactionDetails;


import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.contract.v1.GetTransactionDetailsRequest;
import net.authorize.api.contract.v1.GetTransactionDetailsResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.TransactionDetailsType;
import net.authorize.api.controller.GetTransactionDetailsController;
import net.authorize.api.controller.base.ApiOperationBase;

//author @krgupta
public class GetTransactionDetails {

    public static void run(String apiLoginId, String transactionKey) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName("5KP3u95bQpv");
        merchantAuthenticationType.setTransactionKey("4Ktq966gC55GAX7S");
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
    
        //need valid transacaction Id to run  
        String transId = "2238248926";
    
        GetTransactionDetailsRequest getRequest = new GetTransactionDetailsRequest();
        getRequest.setMerchantAuthentication(merchantAuthenticationType);
        getRequest.setTransId(transId);
       
        GetTransactionDetailsController controller = new GetTransactionDetailsController(getRequest);
        controller.execute();
        GetTransactionDetailsResponse getResponse = controller.getApiResponse();

        System.out.println(" TransactionDetails : " + getResponse.getTransaction());
  
}
}




