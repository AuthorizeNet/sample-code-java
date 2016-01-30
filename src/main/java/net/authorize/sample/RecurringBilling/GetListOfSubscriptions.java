package net.authorize.sample.RecurringBilling;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.ARBGetSubscriptionListController;

public class GetListOfSubscriptions {

    public static ANetApiResponse run(String apiLoginId, String transactionKey) {
        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        ARBGetSubscriptionListSorting sorting = new ARBGetSubscriptionListSorting();
        sorting.setOrderBy(ARBGetSubscriptionListOrderFieldEnum.ID);
        sorting.setOrderDescending(false);

        Paging paging = new Paging();
        paging.setLimit(1000);
        paging.setOffset(1);

        // Make the API Request
        ARBGetSubscriptionListRequest apiRequest = new ARBGetSubscriptionListRequest();
        apiRequest.setSearchType(ARBGetSubscriptionListSearchTypeEnum.SUBSCRIPTION_INACTIVE);
        apiRequest.setSorting(sorting);
        apiRequest.setPaging(paging);
        ARBGetSubscriptionListController controller = new ARBGetSubscriptionListController(apiRequest);
        controller.execute();
        ARBGetSubscriptionListResponse response = controller.getApiResponse();

        if (response!=null) {

            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

                System.out.println(response.getTotalNumInResultSet());
                System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
            }
            else
            {
                System.out.println("Failed to get Subscription list:  " + response.getMessages().getResultCode());
            }
        }
        
        return response;
    }
}
