package net.authorize.sample.TransactionReporting;

import java.util.List;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.GetTransactionListForCustomerController;
import net.authorize.api.controller.base.ApiOperationBase;

//author @akashah
public class GetCustomerProfileTransactionList{
	
		public static ANetApiResponse run(String apiLoginId, String transactionKey, String customerProfileId) {
			ApiOperationBase.setEnvironment(Environment.SANDBOX);

	        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
	        merchantAuthenticationType.setName(apiLoginId);
	        merchantAuthenticationType.setTransactionKey(transactionKey);
	        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

	
			GetTransactionListForCustomerRequest getRequest = new GetTransactionListForCustomerRequest();
			getRequest.setMerchantAuthentication(merchantAuthenticationType);
			getRequest.setCustomerProfileId(customerProfileId);
			
	        Paging paging = new Paging();
	        paging.setLimit(100);
	        paging.setOffset(1);
	        
			getRequest.setPaging(paging);
			
			TransactionListSorting sorting = new TransactionListSorting();
			sorting.setOrderBy(TransactionListOrderFieldEnum.ID);
			sorting.setOrderDescending(true);
			
			getRequest.setSorting(sorting);

			GetTransactionListForCustomerController controller = new GetTransactionListForCustomerController(getRequest);
            controller.execute();

			GetTransactionListResponse getResponse = controller.getApiResponse();
			if (getResponse!=null) {
				ArrayOfTransactionSummaryType transactions = getResponse.getTransactions();
				List<TransactionSummaryType> list = transactions.getTransaction();
				
				for (TransactionSummaryType summary : list) {
					System.out.println(summary.getFirstName());
					System.out.println(summary.getTransId());
					System.out.println(summary.getSettleAmount());
					System.out.println(summary.getSubmitTimeLocal());
				}
				
			    if (getResponse.getMessages().getResultCode() == MessageTypeEnum.OK) {
			        System.out.println(getResponse.getMessages().getMessage().get(0).getCode());
					System.out.println(getResponse.getMessages().getMessage().get(0).getText());
				} else {
					System.out.println("Failed to get transaction list:  " + getResponse.getMessages().getResultCode());
				}
			}
			return getResponse;
		
	}	
}
