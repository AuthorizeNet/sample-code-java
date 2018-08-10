package net.authorize.sample.AcceptSuite;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.GetHostedPaymentPageController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetAnAcceptPaymentPage {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey, Double amount) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
        
        // Create the payment transaction request
        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        txnRequest.setAmount(new BigDecimal(amount).setScale(2, RoundingMode.CEILING));

        SettingType setting1 = new SettingType();
        setting1.setSettingName("hostedPaymentButtonOptions");
        setting1.setSettingValue("{\"text\": \"Pay\"}");
        
        SettingType setting2 = new SettingType();
        setting2.setSettingName("hostedPaymentOrderOptions");
        setting2.setSettingValue("{\"show\": false}");

        ArrayOfSetting alist = new ArrayOfSetting();
        alist.getSetting().add(setting1);
        alist.getSetting().add(setting2);

        GetHostedPaymentPageRequest apiRequest = new GetHostedPaymentPageRequest();
        apiRequest.setTransactionRequest(txnRequest);
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
