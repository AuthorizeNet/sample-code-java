package net.authorize.sample.RecurringBilling;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.ARBGetSubscriptionStatusController;

public class GetSubscriptionStatus {

	public static ANetApiResponse run(String apiLoginId, String transactionKey, String subscriptionId) {
		//Common code to set for all requests
		ApiOperationBase.setEnvironment(Environment.SANDBOX);
		MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
		merchantAuthenticationType.setName(apiLoginId);
		merchantAuthenticationType.setTransactionKey(transactionKey);
		ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		ARBGetSubscriptionStatusRequest apiRequest = new ARBGetSubscriptionStatusRequest();
		apiRequest.setSubscriptionId(subscriptionId);
		ARBGetSubscriptionStatusController controller = new ARBGetSubscriptionStatusController(apiRequest);
		controller.execute();
		ARBGetSubscriptionStatusResponse response = controller.getApiResponse();
		if (response!=null) {

			if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

				System.out.println(response.getStatus());
				System.out.println(response.getMessages().getMessage().get(0).getCode());
				System.out.println(response.getMessages().getMessage().get(0).getText());
			}
			else
			{
				System.out.println("Failed to update Subscription:  " + response.getMessages().getResultCode());
			}
		}
		return response;
	}
}