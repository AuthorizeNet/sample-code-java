package net.authorize.sample.CustomerProfiles;

import java.util.ArrayList;
import java.util.List;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.api.controller.*;

public class GetCustomerPaymentProfileList 
{
    public static void run(String apiLoginId,String transactionKey)
    {
        ApiOperationBase.setEnvironment(Environment.SANDBOX);
        
        // Giving the merchant authentication information
        MerchantAuthenticationType merchantAuthenticationType  = new MerchantAuthenticationType() ;
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);
        
        //Setting the paging
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
        
        // Making the API request
        GetCustomerPaymentProfileListRequest apiRequest = new GetCustomerPaymentProfileListRequest();
        apiRequest.setPaging(paging);
        apiRequest.setSearchType(searchType);
        apiRequest.setSorting(sorting);
        apiRequest.setMonth("2020-12");
        
        // Calling the controller
        GetCustomerPaymentProfileListController controller = new GetCustomerPaymentProfileListController(apiRequest);
        controller.execute();
        // Getting the response
        GetCustomerPaymentProfileListResponse response = new GetCustomerPaymentProfileListResponse();
        response = controller.getApiResponse();
        // Checking id the response is not null then printing the details
        if(response != null)
        {
            if(response.getMessages().getResultCode() == MessageTypeEnum.OK)
            {
                System.out.println("Successfully got customer payment profile list");
                System.out.println("Message Code:" + response.getMessages().getMessage().get(0).getCode());
                System.out.println("Message Text:" + response.getMessages().getMessage().get(0).getText());
                System.out.println("Total Number of Results in the Results Set:" + response.getTotalNumInResultSet());
                List <CustomerPaymentProfileListItemType> arr = response.getPaymentProfiles().getPaymentProfile();
                
                for(CustomerPaymentProfileListItemType element : arr)
                {
                    System.out.println();
                    if(element.getBillTo()!=null)
                        System.out.println("Customer name : "+element.getBillTo().getFirstName()+" "+element.getBillTo().getLastName());
                    System.out.println("Credit card number: "+element.getPayment().getCreditCard().getCardNumber());
                    System.out.println("Customer Profile ID : "+element.getCustomerProfileId());
                    System.out.println("Customer Payment Profile ID : "+element.getCustomerPaymentProfileId());
                }
            }
            else
            {
                // When the error occurs
                System.out.println("Failed to get customer payment profile list");
                if(!response.getMessages().getMessage().isEmpty())
                    System.out.println("Error: "+response.getMessages().getMessage().get(0).getCode()+" "+ response.getMessages().getMessage().get(0).getText());
            }
        }
        else
        {
            // Displaying the error code and message when response is null 
            ANetApiResponse errorResponse = controller.getErrorResponse();
            System.out.println("Failed to get response");
            if(!errorResponse.getMessages().getMessage().isEmpty())
                System.out.println("Error: "+errorResponse.getMessages().getMessage().get(0).getCode()+" \n"+ errorResponse.getMessages().getMessage().get(0).getText());
        }
    }
}
