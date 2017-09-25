package net.authorize.sample.FraudManagement;


import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.GetSettledBatchListController;
import net.authorize.api.controller.GetUnsettledTransactionListController;
import net.authorize.api.controller.base.ApiOperationBase;

//author @krgupta
public class GetHeldTransactionList{
	
		public static ANetApiResponse run(String apiLoginId, String transactionKey) {
			ApiOperationBase.setEnvironment(Environment.SANDBOX);

	        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
	        merchantAuthenticationType.setName(apiLoginId);
	        merchantAuthenticationType.setTransactionKey(transactionKey);
	        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
	
			GetUnsettledTransactionListRequest getRequest = new GetUnsettledTransactionListRequest();
			getRequest.setMerchantAuthentication(merchantAuthenticationType);
			getRequest.setStatus(TransactionGroupStatusEnum.PENDING_APPROVAL);
			
	        Paging paging = new Paging();
	        paging.setLimit(100);
	        paging.setOffset(1);
	        
			getRequest.setPaging(paging);
			
			TransactionListSorting sorting = new TransactionListSorting();
			sorting.setOrderBy(TransactionListOrderFieldEnum.ID);
			sorting.setOrderDescending(true);
			
			getRequest.setSorting(sorting);

  			GetUnsettledTransactionListController controller = new GetUnsettledTransactionListController(getRequest);
            controller.execute();
            GetUnsettledTransactionListResponse getResponse = controller.getApiResponse();

            if (getResponse!=null) {

            	 if (getResponse.getMessages().getResultCode() == MessageTypeEnum.OK) {

                	System.out.println(getResponse.getMessages().getMessage().get(0).getCode());
                	System.out.println(getResponse.getMessages().getMessage().get(0).getText());
                	getResponse.getTransactions();
                	
                	ArrayOfTransactionSummaryType txnList = getResponse.getTransactions();
	                if (txnList != null) {
	                    System.out.println("List of Suspicious Transactions :");
	                    for (TransactionSummaryType txn : txnList.getTransaction()) {
	                        System.out.println(txn.getTransId());
	                    }
	                }
            	}
            	else
            	{
                	System.out.println("Failed to get unsettled transaction list:  " + getResponse.getMessages().getResultCode());
            	}
       	 	}
			return getResponse;

	}
}
