package net.authorize.sample.MobileInAppTransactions;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;

import java.math.BigDecimal;

public class CreateGooglePayTransaction {
    public static ANetApiResponse run(String apiLoginId, String transactionKey, Double amount) {
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        OpaqueDataType opaqueData = new OpaqueDataType();
        opaqueData.setDataDescriptor("COMMON.GOOGLE.INAPP.PAYMENT");
        opaqueData.setDataValue("1234567890ABCDEF1111AAAA2222BBBB3333CCCC4444DDDD5555EEEE6666FFFF7777888899990000");

        PaymentType paymentType = new PaymentType();
        paymentType.setOpaqueData(opaqueData);

        LineItemType lineItem = new LineItemType();
        lineItem.setItemId("1");
        lineItem.setName("vase");
        lineItem.setDescription("Cannes logo");
        lineItem.setQuantity(BigDecimal.valueOf(18));
        lineItem.setUnitPrice(BigDecimal.valueOf(45.00));

        ArrayOfLineItem lineItems = new ArrayOfLineItem();
        lineItems.getLineItem().add(lineItem);

        ExtendedAmountType tax = new ExtendedAmountType();
        tax.setAmount(BigDecimal.valueOf(amount));
        tax.setName("level2 tax name");
        tax.setDescription("level2 tax");

        UserField userField = new UserField();
        TransactionRequestType.UserFields userFields = new TransactionRequestType.UserFields();

        userField.setName("UserDefinedFieldName1");
        userField.setValue("UserDefinedFieldValue1");
        userFields.getUserField().add(userField);

        userField.setName("UserDefinedFieldName2");
        userField.setValue("UserDefinedFieldValue2");
        userFields.getUserField().add(userField);

        TransactionRequestType transactionRequest = new TransactionRequestType();
        transactionRequest.setAmount(BigDecimal.valueOf(amount));
        transactionRequest.setTransactionType(TransactionTypeEnum.AUTH_CAPTURE_TRANSACTION.value());
        transactionRequest.setPayment(paymentType);
        transactionRequest.setLineItems(lineItems);
        transactionRequest.setTax(tax);
        transactionRequest.setUserFields(userFields);

        CreateTransactionRequest apiRequest = new CreateTransactionRequest();
        apiRequest.setTransactionRequest(transactionRequest);

        CreateTransactionController controller = new CreateTransactionController(apiRequest);
        controller.execute();

        CreateTransactionResponse response = controller.getApiResponse();

        if (response != null) {
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                TransactionResponse result = response.getTransactionResponse();
                if (result.getMessages() != null) {
                    System.out.println("Successfully created transaction with Transaction ID: " + result.getTransId());
                    System.out.println("Response Code: " + result.getResponseCode());
                    System.out.println("Message Code: " + result.getMessages().getMessage().get(0).getCode());
                    System.out.println("Description: " + result.getMessages().getMessage().get(0).getDescription());
                    System.out.println("Auth code : " + result.getAuthCode());
                } else {
                    System.out.println("Failed Transaction.");
                    if (response.getTransactionResponse().getErrors() != null) {
                        System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
                        System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
                    }
                }
            } else {
                System.out.println("Failed Transaction.");
                if (response.getTransactionResponse() != null && response.getTransactionResponse().getErrors() != null) {
                    System.out.println("Error Code: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorCode());
                    System.out.println("Error message: " + response.getTransactionResponse().getErrors().getError().get(0).getErrorText());
                } else {
                    System.out.println("Error Code: " + response.getMessages().getMessage().get(0).getCode());
                    System.out.println("Error message: " + response.getMessages().getMessage().get(0).getText());
                }
            }
        } else {
            System.out.println("Null Response.");
        }

        return response;
    }
}
