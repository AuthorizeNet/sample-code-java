package net.authorize.sample.TransactionReporting;


import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.GetTransactionDetailsController;
import net.authorize.api.controller.base.ApiOperationBase;

//author @krgupta
public class GetTransactionDetails {

    public static ANetApiResponse run(String apiLoginId, String transactionKey, String transactionId) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
    
        GetTransactionDetailsRequest getRequest = new GetTransactionDetailsRequest();
        getRequest.setMerchantAuthentication(merchantAuthenticationType);
        getRequest.setTransId(transactionId);
       
        GetTransactionDetailsController controller = new GetTransactionDetailsController(getRequest);
        controller.execute();
        GetTransactionDetailsResponse getResponse = controller.getApiResponse();

       if (getResponse!=null) {

                 if (getResponse.getMessages().getResultCode() == MessageTypeEnum.OK) {

                    System.out.println(getResponse.getMessages().getMessage().get(0).getCode());
                    System.out.println(getResponse.getMessages().getMessage().get(0).getText());
                }
                else
                {
                    System.out.println("Failed to get transaction details:  " + getResponse.getMessages().getResultCode());
                }
            }
	return getResponse;
}
}




