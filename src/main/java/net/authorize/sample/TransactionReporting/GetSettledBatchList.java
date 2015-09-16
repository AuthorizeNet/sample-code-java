package net.authorize.sample.TransactionReporting;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.GetSettledBatchListController;
import net.authorize.api.controller.base.ApiOperationBase;

//author @krgupta
public class GetSettledBatchList {

     public static void run(String apiLoginId, String transactionKey) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
            
        GetSettledBatchListRequest getRequest = new GetSettledBatchListRequest();
        getRequest.setMerchantAuthentication(merchantAuthenticationType);
        GetSettledBatchListController controller = new GetSettledBatchListController(getRequest);
        controller.execute();
        GetSettledBatchListResponse getResponse = new GetSettledBatchListResponse();
         if (getResponse!=null) {

             if (getResponse.getMessages().getResultCode() == MessageTypeEnum.OK) {

                System.out.println(getResponse.getMessages().getMessage().get(0).getCode());
                System.out.println(getResponse.getMessages().getMessage().get(0).getText());
            }
            else
            {
                System.out.println("Failed to get settled batch list:  " + getResponse.getMessages().getResultCode());
            }
        }
}
}


