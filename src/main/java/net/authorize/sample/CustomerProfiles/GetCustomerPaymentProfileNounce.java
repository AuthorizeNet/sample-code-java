package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.GetCustomerPaymentProfileNonceRequest;
import net.authorize.api.contract.v1.GetCustomerPaymentProfileNonceResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.controller.GetCustomerPaymentProfileNonceController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetCustomerPaymentProfileNounce {
	public static ANetApiResponse run(String apiLoginId, String transactionKey, String customerProfileId,
			String customerPaymentProfileId) {

		ApiOperationBase.setEnvironment(Environment.SANDBOX);

		MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
		merchantAuthenticationType.setName(apiLoginId);
		merchantAuthenticationType.setTransactionKey(transactionKey);
		ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		GetCustomerPaymentProfileNonceRequest apiRequest = new GetCustomerPaymentProfileNonceRequest();
		apiRequest.setMerchantAuthentication(merchantAuthenticationType);

		String refId = "eyJraWQiOiJlOTQ0MDI3Zjg2YWIwMDkxMDAwMDAwMDA1NGNlYTM0ZiIsImFsZyI6IlJTMjU2In0.eyJqdGkiOiIzMjkzMmQwYy1mMzY5LTQ5OWMtOGE1OS0yZWQ1MGNmMDM3NDUiLCJzY29wZXMiOlsicmVhZCIsIndyaXRlIl0sImlhdCI6MTU0MDI0NzY4NTg1MiwiYXNzb2NpYXRlZF9pZCI6IjI0MDY4IiwiY2xpZW50X2lkIjoiVlJsVjZZZXhpRCIsIm1lcmNoYW50X2lkIjoiNzA1ODU4IiwiYWRkaXRpb25hbEluZm8iOiJ7XCJhcGlMb2dpbklkXCI6XCI5MnRrQ0w3cyAgICAgICAgICAgIFwiLFwicm91dGluZ0lkXCI6XCIkJDkydGtDTDdzJCRcIn0iLCJleHBpcmVzX2luIjoxNTQwMjc2NDg1ODU0LCJncmFudF90eXBlIjoiYXV0aG9yaXphdGlvbl9jb2RlIn0.I6KrX2YYF_qhY75cxE84dGuhlwxbLo-AnzNj-k3SdWx74xVT4ptB1S4wzMlf94zJoPt-RxCyzu2ZH20bWJOa-wCd-xmBjT6qDOMzxYz6hmiqzOf7WbaOi5ByaYWcqcU-37z3OMHVwajU2EvHpkK_g38eka17jLajI1YCv41B8suGoxYuSGzKhOWVq0JKAgo9x-Ne7rLiILDPJjqJsoSMsqlGDHbLQ4lVD1YKOila6hlntmnN80g7TBbV_f_ODgwbUXTMhynBB94Nu2SCGWhuOB8y97lRptuT00o9V8JuO_nwZRApHAnMtzihl9pSaXHiDsu5lN36Xxqos-x7BILRgA";
		apiRequest.setConnectedAccessToken("refId");
		apiRequest.setCustomerProfileId(customerProfileId);
		apiRequest.setCustomerPaymentProfileId(customerPaymentProfileId);
		GetCustomerPaymentProfileNonceController controller = new GetCustomerPaymentProfileNonceController(apiRequest);
		controller.execute();
		GetCustomerPaymentProfileNonceResponse response = new GetCustomerPaymentProfileNonceResponse();
		response = controller.getApiResponse();

		if (response != null) {

			if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

				System.out.println("Data Descriptor: " + response.getOpaqueData().getDataDescriptor());
				System.out.println("Data Key: " + response.getOpaqueData().getDataKey());
				System.out.println("Expiration Time Stamp: " + response.getOpaqueData().getExpirationTimeStamp());
			} else {
				System.out.println("Invalid Response:  " + response.getMessages().getMessage().get(0).getText());
			}
		}

		return response;
	}

}
