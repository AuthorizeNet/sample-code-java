package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.GetHostedProfilePageController;

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
               
        ANetApiResponse apiResponse = controller.getApiResponse();
		if (apiResponse != null) {
			 if (apiResponse instanceof GetHostedProfilePageResponse) {
				 GetHostedProfilePageResponse response = (GetHostedProfilePageResponse) apiResponse;
			
	             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {	            	
	                System.out.println(response.getToken());
	 				System.out.println(response.getMessages().getMessage().get(0).getCode());
	                System.out.println(response.getMessages().getMessage().get(0).getText());
	             } 
	             else if (response.getMessages().getResultCode() == MessageTypeEnum.ERROR) {
	            	 System.out.println(response.getMessages().getMessage().get(0).getCode());
	                 System.out.println(response.getMessages().getMessage().get(0).getText());
	             }	
            }
		 	else if (apiResponse instanceof ErrorResponse) {
		 		System.out.println(apiResponse.getMessages().getMessage().get(0).getCode());
                System.out.println(apiResponse.getMessages().getMessage().get(0).getText());
                System.out.println("Failed to get hosted profile page:  " + apiResponse.getMessages().getResultCode());
            }
        }

		return apiResponse;
    }
}
