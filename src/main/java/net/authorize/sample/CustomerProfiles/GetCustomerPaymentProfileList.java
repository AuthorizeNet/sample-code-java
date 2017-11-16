package net.authorize.sample.CustomerProfiles;

import java.util.ArrayList;
import java.util.List;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.*;

public class GetCustomerPaymentProfileList {
    public static ANetApiResponse run(String apiLoginId,String transactionKey) {

        // Set the request to operate in either the sandbox or production environment
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        
        // Create object with merchant authentication details
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        
        // Setting the paging
        Paging paging = new Paging();
        paging.setLimit(100);
        paging.setOffset(1);
        
        // Setting the sorting order
        CustomerPaymentProfileSorting sorting = new CustomerPaymentProfileSorting();
        CustomerPaymentProfileOrderFieldEnum orderByEnum = CustomerPaymentProfileOrderFieldEnum.ID;
        sorting.setOrderBy(orderByEnum);
        sorting.setOrderDescending(false);
        
        // Setting the searchType
        CustomerPaymentProfileSearchTypeEnum searchType = CustomerPaymentProfileSearchTypeEnum.CARDS_EXPIRING_IN_MONTH;
        
        // Create the API request and set the parameters for this specific request
        GetCustomerPaymentProfileListRequest apiRequest = new GetCustomerPaymentProfileListRequest();
        apiRequest.setMerchantAuthentication(merchantAuthenticationType);
        apiRequest.setPaging(paging);
        apiRequest.setSearchType(searchType);
        apiRequest.setSorting(sorting);
        apiRequest.setMonth("2020-12");
        
        // Call the controller
        GetCustomerPaymentProfileListController controller = new GetCustomerPaymentProfileListController(apiRequest);
        controller.execute();

        // Get the response
        GetCustomerPaymentProfileListResponse response = new GetCustomerPaymentProfileListResponse();
        response = controller.getApiResponse();

        // Parse the response to determine results
        if (response != null) {
            // If API Response is OK, go ahead and check the transaction response
            if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {
                System.out.println("Successfully got customer payment profile list");
                System.out.println("Message Code:" + response.getMessages().getMessage().get(0).getCode());
                System.out.println("Message Text:" + response.getMessages().getMessage().get(0).getText());
                System.out.println("Total Number of Results in the Results Set:" + response.getTotalNumInResultSet());
                List <CustomerPaymentProfileListItemType> arr = response.getPaymentProfiles().getPaymentProfile();
                
                for (CustomerPaymentProfileListItemType element : arr) {
                    System.out.println();
                    if (element.getBillTo()!=null) {
                        System.out.println("Customer name : "+element.getBillTo().getFirstName()+" "+element.getBillTo().getLastName());
                    }
                    System.out.println("Credit card number: "+element.getPayment().getCreditCard().getCardNumber());
                    System.out.println("Customer Profile ID : "+element.getCustomerProfileId());
                    System.out.println("Customer Payment Profile ID : "+element.getCustomerPaymentProfileId());
                }
            } else {
                // Displaying the error code and message when error occurs
                System.out.println("Failed to get customer payment profile list");
                if (!response.getMessages().getMessage().isEmpty()) {
                    System.out.println("Error: "+response.getMessages().getMessage().get(0).getCode()+" "+ response.getMessages().getMessage().get(0).getText());
                }
            }
        } else {
            // Display the error code and message when response is null 
            ANetApiResponse errorResponse = controller.getErrorResponse();
            System.out.println("Failed to get response");
            if (!errorResponse.getMessages().getMessage().isEmpty()) {
                System.out.println("Error: "+errorResponse.getMessages().getMessage().get(0).getCode()+" \n"+ errorResponse.getMessages().getMessage().get(0).getText());
            }
        }

        return response;
    }
}
