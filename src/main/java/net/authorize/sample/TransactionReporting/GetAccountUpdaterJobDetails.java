package net.authorize.sample.TransactionReporting;

import java.util.ArrayList;
import net.authorize.Environment;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.AUJobTypeEnum;
import net.authorize.api.contract.v1.AuDeleteType;
import net.authorize.api.contract.v1.AuDetailsType;
import net.authorize.api.contract.v1.AuUpdateType;
import net.authorize.api.contract.v1.CreditCardMaskedType;
import net.authorize.api.contract.v1.GetAUJobDetailsRequest;
import net.authorize.api.contract.v1.GetAUJobDetailsResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.Paging;
import net.authorize.api.controller.GetAUJobDetailsController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetAccountUpdaterJobDetails {

	public static ANetApiResponse run(String apiLoginId, String transactionKey) {

		// Set the request to operate in either the sandbox or production environment
		ApiOperationBase.setEnvironment(Environment.SANDBOX);

		// Create object with merchant authentication details
		MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
		merchantAuthenticationType.setName(apiLoginId);
		merchantAuthenticationType.setTransactionKey(transactionKey);

		String month = "2018-08";
		String reFid = "123456";
		Paging paging = new Paging();
		paging.setLimit(100);
		paging.setOffset(2);

		// Create the API request and set the parameters for this specific request
		GetAUJobDetailsRequest apiRequest = new GetAUJobDetailsRequest();
		apiRequest.setMerchantAuthentication(merchantAuthenticationType);
		apiRequest.setPaging(paging);
		apiRequest.setRefId(reFid);
		apiRequest.setMonth(month);
		apiRequest.setModifiedTypeFilter(AUJobTypeEnum.ALL);

		// Call the controller
		GetAUJobDetailsController controller = new GetAUJobDetailsController(apiRequest);
		controller.execute();

		GetAUJobDetailsResponse response = new GetAUJobDetailsResponse();
		response = controller.getApiResponse();

		// If API Response is OK, go ahead and check the transaction response
		if (response != null && response.getMessages().getResultCode() == MessageTypeEnum.OK) {

			System.out.println("SUCCESS: Get Account Updater job details for Month and year : " + month);

			if (response.getAuDetails() == null) {
				System.out.println("No GetAccountUpdaterjobdetails for this month and year.");
				return response;
			}

			ArrayList<AuUpdateType> updateTypeList = new ArrayList<AuUpdateType>();
			ArrayList<AuDeleteType> deleteTypeList = new ArrayList<AuDeleteType>();

			for (AuDetailsType details : response.getAuDetails().getAuUpdateOrAuDelete()) {

				System.out.println("---Customer profile details Start---");
				System.out.println("customerProfileID:" + details.getCustomerProfileID());
				System.out.println("customerPaymentProfileID:" + details.getCustomerPaymentProfileID());
				System.out.println("customerFirstname:" + details.getFirstName());
				System.out.println("customerLastname:" + details.getLastName());
				System.out.println("updateTimeUTC:" + details.getUpdateTimeUTC());
				System.out.println("reasonCode:" + details.getAuReasonCode());
				System.out.println("reasonDescription:" + details.getReasonDescription());

				if (details.getClass().getTypeName().toString().contains("AuUpdateType")) {

					updateTypeList.add((AuUpdateType) details);

				} else if (details.getClass().getTypeName().toString().contains("AuDeleteType")) {

					deleteTypeList.add((AuDeleteType) details);

				}

				if (!(updateTypeList.isEmpty())) {
					for (int i = 0; i < updateTypeList.size(); i++) {

						System.out.println("---AU Update Start---");
						System.out.println("customerProfileID:" + details.getCustomerProfileID());
						System.out.println("customerPaymentProfileID:" + details.getCustomerPaymentProfileID());
						System.out.println("customerFirstname:" + details.getFirstName());
						System.out.println("customerLastname:" + details.getLastName());
						System.out.println("updateTimeUTC:" + details.getUpdateTimeUTC());
						System.out.println("reasonCode:" + details.getAuReasonCode());
						System.out.println("reasonDescription:" + details.getReasonDescription());

						if ((updateTypeList.get(i).getSubscriptionIdList() != null)
								&& (updateTypeList.get(i).getSubscriptionIdList().getSubscriptionId() != null)
								&& (!updateTypeList.get(i).getSubscriptionIdList().getSubscriptionId().isEmpty())) {
							System.out.println("SubscriptionIdlist");
							for (String subscriptionid : updateTypeList.get(i).getSubscriptionIdList()
									.getSubscriptionId())
								System.out.println("SubscriptionId:" + subscriptionid);

						}

						if (updateTypeList.get(i).getNewCreditCard() != null) {
							CreditCardMaskedType newCreditCard = updateTypeList.get(i).getNewCreditCard();
							System.out.println("---Fetching New Card Details---");
							System.out.println("cardNumber:" + newCreditCard.getCardNumber());
							System.out.println("expirationDate:" + newCreditCard.getExpirationDate());
							System.out.println("cardType:" + newCreditCard.getCardType());

						}

						if (updateTypeList.get(i).getOldCreditCard() != null) {
							CreditCardMaskedType oldCreditCard = updateTypeList.get(i).getOldCreditCard();
							System.out.println("---Fetching Old Card Details---");
							System.out.println("cardNumber:" + oldCreditCard.getCardNumber());
							System.out.println("expirationDate:" + oldCreditCard.getExpirationDate());
							System.out.println("cardType:" + oldCreditCard.getCardType());

						}
					}
				}
				
				if (!(deleteTypeList.isEmpty())) {
					for (int i = 0; i < deleteTypeList.size(); i++) {
						
						System.out.println("---AU Delete Start---");
						System.out.println("customerProfileID:" + details.getCustomerProfileID());
						System.out.println("customerPaymentProfileID:" + details.getCustomerPaymentProfileID());
						System.out.println("customerFirstname:" + details.getFirstName());
						System.out.println("customerLastname:" + details.getLastName());
						System.out.println("updateTimeUTC:" + details.getUpdateTimeUTC());
						System.out.println("reasonCode:" + details.getAuReasonCode());
						System.out.println("reasonDescription:" + details.getReasonDescription());

						
						if ((deleteTypeList.get(i).getSubscriptionIdList() != null)
								&& (deleteTypeList.get(i).getSubscriptionIdList().getSubscriptionId() != null)
								&& (!deleteTypeList.get(i).getSubscriptionIdList().getSubscriptionId().isEmpty())) {
							System.out.println("SubscriptionIdlist");
							for (String subscriptionid : deleteTypeList.get(i).getSubscriptionIdList()
									.getSubscriptionId())
								System.out.println("SubscriptionId:" + subscriptionid);

						}

						if (deleteTypeList.get(i).getCreditCard() != null) {

							CreditCardMaskedType creditCard = deleteTypeList.get(i).getCreditCard();
							System.out.println("cardNumber:" + creditCard.getCardNumber());
							System.out.println("expirationDate:" + creditCard.getExpirationDate());
							System.out.println("cardType:" + creditCard.getCardType());
						}

					}
				}

			}
		}

		else {
			// Display the error code and message when response is null
			ANetApiResponse errorResponse = controller.getErrorResponse();
			System.out.println("Failed to get response");
			if (!errorResponse.getMessages().getMessage().isEmpty()) {
				System.out.println("Error: " + errorResponse.getMessages().getMessage().get(0).getCode() + " \n"
						+ errorResponse.getMessages().getMessage().get(0).getText());
			}
		}

		return response;
	}
}
