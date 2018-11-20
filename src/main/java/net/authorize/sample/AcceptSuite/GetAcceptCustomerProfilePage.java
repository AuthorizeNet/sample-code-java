package net.authorize.sample.AcceptSuite;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;

import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.GetHostedProfilePageController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetAcceptCustomerProfilePage {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey, String customerProfileId) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        SettingType setting = new SettingType();
        setting.setSettingName("hostedProfileReturnUrl");
        setting.setSettingValue("https://returnurl.com/return/");

        ArrayOfSetting alist = new ArrayOfSetting();
        alist.getSetting().add(setting);

        GetHostedProfilePageRequest apiRequest = new GetHostedProfilePageRequest();
        apiRequest.setCustomerProfileId(customerProfileId);
        apiRequest.setHostedProfileSettings(alist);

        GetHostedProfilePageController controller = new GetHostedProfilePageController(apiRequest);
        controller.execute();
       
		GetHostedProfilePageResponse response = new GetHostedProfilePageResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());

                System.out.println(response.getToken());
            }
            else
            {
                System.out.println("Failed to get hosted profile page:  " + response.getMessages().getResultCode());
            }
        }
		return response;
    }
}
