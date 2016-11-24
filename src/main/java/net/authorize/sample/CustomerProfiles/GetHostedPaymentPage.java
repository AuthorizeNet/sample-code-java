package net.authorize.sample.CustomerProfiles;

import java.math.BigDecimal;
import java.math.RoundingMode;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.GetHostedPaymentPageController;
import net.authorize.api.controller.GetHostedProfilePageController;
import net.authorize.api.controller.base.ApiOperationBase;

public class GetHostedPaymentPage {
	
	public static ANetApiResponse run(String apiLoginId, String transactionKey, Double amount) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
        
        PaymentType paymentType = new PaymentType();
        CreditCardType creditCard = new CreditCardType();
        creditCard.setCardNumber("4242424242424242");
        creditCard.setExpirationDate("0822");
        paymentType.setCreditCard(creditCard);

        // Create the payment transaction request
        TransactionRequestType txnRequest = new TransactionRequestType();
        txnRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        txnRequest.setPayment(paymentType);
        txnRequest.setAmount(new BigDecimal(amount).setScale(2, RoundingMode.CEILING));

        SettingType setting = new SettingType();
        setting.setSettingName("hostedPaymentReturnOptions");
        setting.setSettingValue("https://returnurl.com/return/");

        ArrayOfSetting alist = new ArrayOfSetting();
        alist.getSetting().add(setting);

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