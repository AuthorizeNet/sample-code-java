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

			System.out.println("SUCCESS: Get Account Updater job details for Month: " + month);

			if (response.getAuDetails() == null) {
				System.out.println("No GetAccountUpdaterjobdetails for Month.");
				return response;
			}

			ArrayList<AuUpdateType> updateTypeList = new ArrayList<AuUpdateType>();
			ArrayList<AuDeleteType> deleteTypeList = new ArrayList<AuDeleteType>();

			for (AuDetailsType details : response.getAuDetails().getAuUpdateOrAuDelete()) {

				System.out.println("---Customer Profile Details Start---");
				System.out.println("Profile ID:" + details.getCustomerProfileID());
				System.out.println("Payment Profile ID:" + details.getCustomerPaymentProfileID());
				System.out.println("First Name:" + details.getFirstName());
				System.out.println("Last Name:" + details.getLastName());
				System.out.println("Update Time UTC:" + details.getUpdateTimeUTC());
				System.out.println("Reason Code:" + details.getAuReasonCode());
				System.out.println("Reason Description:" + details.getReasonDescription());

				if (details.getClass().getTypeName().toString().contains("AuUpdateType")) {

					updateTypeList.add((AuUpdateType) details);

				} else if (details.getClass().getTypeName().toString().contains("AuDeleteType")) {

					deleteTypeList.add((AuDeleteType) details);

				}

				if (!(updateTypeList.isEmpty())) {
					for (int i = 0; i < updateTypeList.size(); i++) {

						System.out.println("---AU Update Start---");
						System.out.println("Profile ID:" + details.getCustomerProfileID());
						System.out.println("Payment Profile ID:" + details.getCustomerPaymentProfileID());
						System.out.println("First Name:" + details.getFirstName());
						System.out.println("Last Name:" + details.getLastName());
						System.out.println("Update Time UTC:" + details.getUpdateTimeUTC());
						System.out.println("Reason Code:" + details.getAuReasonCode());
						System.out.println("Reason Description:" + details.getReasonDescription());

						if (updateTypeList.get(i).getNewCreditCard() != null) {
							CreditCardMaskedType newCreditCard = updateTypeList.get(i).getNewCreditCard();
							System.out.println("---Fetching New Card Details---");
							System.out.println("Card Number:" + newCreditCard.getCardNumber());
							System.out.println("Expiration Date:" + newCreditCard.getExpirationDate());
							System.out.println("Card Type:" + newCreditCard.getCardType());
						}

						if (updateTypeList.get(i).getOldCreditCard() != null) {
							CreditCardMaskedType oldCreditCard = updateTypeList.get(i).getOldCreditCard();
							System.out.println("---Fetching Old Card Details---");
							System.out.println("Card Number:" + oldCreditCard.getCardNumber());
							System.out.println("Expiration Date:" + oldCreditCard.getExpirationDate());
							System.out.println("Card Type:" + oldCreditCard.getCardType());
						}
					}
				}
				
				if (!(deleteTypeList.isEmpty())) {
					for (int i = 0; i < deleteTypeList.size(); i++) {
						
						System.out.println("---AU Delete Start---");
						System.out.println("Profile ID:" + details.getCustomerProfileID());
						System.out.println("Payment Profile ID:" + details.getCustomerPaymentProfileID());
						System.out.println("First Name:" + details.getFirstName());
						System.out.println("Last Name:" + details.getLastName());
						System.out.println("Update Time UTC:" + details.getUpdateTimeUTC());
						System.out.println("Reason Code:" + details.getAuReasonCode());
						System.out.println("Reason Description:" + details.getReasonDescription());

						if (deleteTypeList.get(i).getCreditCard() != null) {

							CreditCardMaskedType creditCard = deleteTypeList.get(i).getCreditCard();
							System.out.println("Card Number:" + creditCard.getCardNumber());
							System.out.println("Expiration Date:" + creditCard.getExpirationDate());
							System.out.println("Card Type:" + creditCard.getCardType());
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
