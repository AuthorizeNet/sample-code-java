package net.authorize.sample.TransactionReporting;


import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.GetMerchantDetailsController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetMerchantDetails {

    public static ANetApiResponse run(String apiLoginId, String transactionKey) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
    
        GetMerchantDetailsRequest getRequest = new GetMerchantDetailsRequest();
        getRequest.setMerchantAuthentication(merchantAuthenticationType);
       
        GetMerchantDetailsController controller = new GetMerchantDetailsController(getRequest);
        controller.execute();
        GetMerchantDetailsResponse getResponse = controller.getApiResponse();

       if (getResponse!=null) {

                 if (getResponse.getMessages().getResultCode() == MessageTypeEnum.OK) {

                	System.out.println("Merchant Name : " + getResponse.getMerchantName());
                	System.out.println("Gateway Id : " + getResponse.getGatewayId());

                    System.out.println(getResponse.getMessages().getMessage().get(0).getCode());
                    System.out.println(getResponse.getMessages().getMessage().get(0).getText());
                }
                else
                {
                    System.out.println("Failed to get merchant details:  " + getResponse.getMessages().getResultCode());
                }
            }
	return getResponse;
}
}




