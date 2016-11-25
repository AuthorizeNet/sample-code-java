package net.authorize.sample.TransactionReporting;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.GetTransactionListController;
import net.authorize.api.controller.base.ApiOperationBase;

//author @krgupta
public class GetTransactionList{
	
		public static ANetApiResponse run(String apiLoginId, String transactionKey) {
			ApiOperationBase.setEnvironment(Environment.SANDBOX);

	        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
	        merchantAuthenticationType.setName(apiLoginId);
	        merchantAuthenticationType.setTransactionKey(transactionKey);
	        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

			String batchId = "4594221";
	
			GetTransactionListRequest getRequest = new GetTransactionListRequest();
			getRequest.setMerchantAuthentication(merchantAuthenticationType);
			getRequest.setBatchId(batchId);
			
	        Paging paging = new Paging();
	        paging.setLimit(100);
	        paging.setOffset(1);
	        
			getRequest.setPaging(paging);
			
			TransactionListSorting sorting = new TransactionListSorting();
			sorting.setOrderBy(TransactionListOrderFieldEnum.ID);
			sorting.setOrderDescending(true);
			
			getRequest.setSorting(sorting);

			GetTransactionListController controller = new GetTransactionListController(getRequest);
            controller.execute();

			GetTransactionListResponse getResponse = controller.getApiResponse();
			if (getResponse!=null) {

			     if (getResponse.getMessages().getResultCode() == MessageTypeEnum.OK) {
			        System.out.println(getResponse.getMessages().getMessage().get(0).getCode());
			        System.out.println(getResponse.getMessages().getMessage().get(0).getText());
			        }
			        else
			        {
			            System.out.println("Failed to get transaction list:  " + getResponse.getMessages().getResultCode());
			        }
			}
			return getResponse;
		
	}	
}
