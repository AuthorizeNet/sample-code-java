package net.authorize.sample.TransactionReporting;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.GetBatchStatisticsController;
import net.authorize.api.controller.base.ApiOperationBase;

//author @krgupta
public class GetBatchStatistics {
	
		public static ANetApiResponse run(String apiLoginId, String transactionKey) {
			ApiOperationBase.setEnvironment(Environment.SANDBOX);

	        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
	        merchantAuthenticationType.setName(apiLoginId);
	        merchantAuthenticationType.setTransactionKey(transactionKey);
	        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

			GetBatchStatisticsRequest getRequest = new GetBatchStatisticsRequest();
            getRequest.setMerchantAuthentication(merchantAuthenticationType);
            String batchId = "12345";
            getRequest.setBatchId(batchId);

            GetBatchStatisticsController controller = new GetBatchStatisticsController(getRequest);
            controller.execute();
            GetBatchStatisticsResponse getResponse = controller.getApiResponse();
			
			if (getResponse!=null) {

            	 if (getResponse.getMessages().getResultCode() == MessageTypeEnum.OK) {

                	System.out.println(getResponse.getMessages().getMessage().get(0).getCode());
                	System.out.println(getResponse.getMessages().getMessage().get(0).getText());
            	}
            	else
            	{
                	System.out.println("Failed to get batch statistics:  " + getResponse.getMessages().getResultCode());
            	}
       	 	}
			return getResponse;
    	}
}