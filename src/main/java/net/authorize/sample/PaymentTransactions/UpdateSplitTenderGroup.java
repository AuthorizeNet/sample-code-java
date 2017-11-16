
package net.authorize.sample.PaymentTransactions;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.UpdateSplitTenderGroupController;

/**
 *
 * @author gnongsie
 */
public class UpdateSplitTenderGroup {
    public static ANetApiResponse run(String apiLoginId, String transactionKey) {
        System.out.println("Update Split Tender Group Sample Code");
        
        //Common code to set for all requests
        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        // Provide a split tender Id
        // To get a split Tender ID in sandbox, authorize any transaction with amount = 462.25 [if card present] and set allowPartialAuth = true
        String splitTenderId = "115901"; 
        
        // Create a request
        UpdateSplitTenderGroupRequest request = new UpdateSplitTenderGroupRequest();
        request.setMerchantAuthentication(merchantAuthenticationType);
        request.setSplitTenderId(splitTenderId);
        
        // Set the Split Tender Status (VOIDED or HELD or COMPLETED)
        request.setSplitTenderStatus(SplitTenderStatusEnum.VOIDED);
        
        UpdateSplitTenderGroupController controller = new UpdateSplitTenderGroupController(request);
        controller.execute();
        
        UpdateSplitTenderGroupResponse response = controller.getApiResponse();
        
        if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                System.out.println(response.getMessages().getMessage().get(0).getCode());
                System.out.println(response.getMessages().getMessage().get(0).getText());
        }
        
        else {
            System.out.println("Error: " + response.getMessages().getMessage().get(0).getCode() + 
                                    " " + response.getMessages().getMessage().get(0).getText());
        }
		return response;
    }
}
