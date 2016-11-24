package net.authorize.sample.CustomerProfiles;

import java.math.BigDecimal;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.GetHostedPaymentPageController;
import net.authorize.api.controller.GetHostedProfilePageController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetHostedPaymentPage {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        SettingType setting = new SettingType();
        setting.setSettingName("hostedPaymentReturnOptions");
        setting.setSettingValue("https://returnurl.com/return/");

        ArrayOfSetting alist = new ArrayOfSetting();
        alist.getSetting().add(setting);
        
        TransactionRequestType reqType = new TransactionRequestType();
        reqType.setAmount(new BigDecimal("12.42"));
        reqType.setTransactionType(TransactionTypeEnum.AUTH_ONLY_TRANSACTION.value());

        GetHostedPaymentPageRequest apiRequest = new GetHostedPaymentPageRequest();
        apiRequest.setTransactionRequest(reqType);
        apiRequest.setHostedPaymentSettings(alist);

        GetHostedPaymentPageController controller = new GetHostedPaymentPageController(apiRequest);
        controller.execute();
       
        GetHostedPaymentPageResponse response = new GetHostedPaymentPageResponse();
		response = controller.getApiResponse();

		if (response!=null) {

             if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

 				System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());

                System.out.println(response.getToken());
            }
            else
            {
                System.out.println("Failed to get hosted payment page:  " + response.getMessages().getResultCode());
            }
        }
		return response;
    }
}