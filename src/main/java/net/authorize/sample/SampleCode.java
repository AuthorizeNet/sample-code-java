package net.authorize.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import net.authorize.sample.ApplePayTransactions.CreateAnApplePayTransaction;

import net.authorize.sample.VisaCheckout.*;
import net.authorize.sample.PaymentTransactions.*;
import net.authorize.sample.PaypalExpressCheckout.*;
import net.authorize.sample.PaypalExpressCheckout.Void;
import net.authorize.sample.RecurringBilling.*;
import net.authorize.sample.TransactionReporting.*;
import net.authorize.sample.CustomerProfiles.*;
import net.authorize.sample.ApplePayTransactions.*;
/**
 * Created by anetdeveloper on 8/5/15.
 */
public class SampleCode {

    public static void main( String[] args )
    {

        if (args.length == 0)
        {
            SelectMethod();
        }
        else if (args.length == 1)
        {
            RunMethod(args[0]);
            return;
        }
        else
        {
            ShowUsage();
        }

        System.out.println("");
        System.out.print("Press <Return> to finish ...");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            int i = Integer.parseInt(br.readLine());
        }catch(Exception ex){
        }

    }

    private static void ShowUsage()
    {
        System.out.println("Usage : java -jar SampleCode.jar [CodeSampleName]");
        System.out.println("");
        System.out.println("Run with no parameter to select a method.  Otherwise pass a method name.");
        System.out.println("");
        System.out.println("Code Sample Names: ");
        ShowMethods();


    }

    private static void SelectMethod()
    {
        System.out.println("Code Sample Names: ");
        System.out.println("");
        ShowMethods();
        System.out.println("");
        System.out.print("Type a sample name & then press <Return> : ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try{
            RunMethod(br.readLine());
        }catch(Exception ex){
            System.out.print("Error no such method");
        }
    }

    private static void ShowMethods()
    {
        System.out.println("    DecryptVisaCheckoutData");
        System.out.println("    CreateVisaCheckoutTransaction");
        System.out.println("    ChargeCreditCard");
        System.out.println("    AuthorizeCreditCard");
        System.out.println("    RefundTransaction");
        System.out.println("    VoidTransaction");
        System.out.println("    CreateCustomerProfileFromTransaction");
        System.out.println("    CaptureOnly");
        System.out.println("    CapturePreviouslyAuthorizedAmount");
        System.out.println("    DebitBankAccount");
        System.out.println("    CreditBankAccount");
        System.out.println("    ChargeTokenizedCreditCard");
        System.out.println("    CreateAnApplePayTransaction");
        System.out.println("    ChargeCustomerProfile");
        System.out.println("    CreateSubscription");
        System.out.println("    GetSubscription");
        System.out.println("    GetSubscriptionStatus");
        System.out.println("    CancelSubscription");
        System.out.println("    UpdateSubscription");
        System.out.println("    GetListOfSubscriptions");
        System.out.println("    GetBatchStatistics");
        System.out.println("    GetSettledBatchList");
        System.out.println("    GetTransactionList");
        System.out.println("    GetUnsettledTransactionList");
        System.out.println("    GetTransactionDetails");
        System.out.println("    CreateCustomerProfile");
        System.out.println("    CreateCustomerPaymentProfile");
        System.out.println("    CreateCustomerShippingAddress");
        System.out.println("    DeleteCustomerPaymentProfile");
        System.out.println("    DeleteCustomerProfile");
        System.out.println("    DeleteCustomerShippingAddress");
        System.out.println("    GetCustomerPaymentProfile");
        System.out.println("    GetCustomerPaymentProfileList");
        System.out.println("    GetCustomerProfile");
        System.out.println("    GetCustomerProfileIds");
        System.out.println("    GetCustomerShippingAddress");
        System.out.println("    GetHostedProfilePage");
        System.out.println("    UpdateCustomerPaymentProfile");
        System.out.println("    UpdateCustomerShippingAddress");
        System.out.println("    PayPalAuthorizeCapture");
        System.out.println("    PayPalVoid");
        System.out.println("    PayPalAuthorizationOnly");
        System.out.println("    PayPalAuthorizeCaptureContinue");
        System.out.println("    PayPalGetDetails");
        System.out.println("    PayPalPriorAuthorizationCapture");	
        System.out.println("    PayPalAuthorizeOnlyContinue");
        System.out.println("    PayPalCredit");
        System.out.println("    UpdateSplitTenderGroup");
    }

    private static void RunMethod(String methodName)
    {
        // These are default transaction keys.
        // You can create your own keys in seconds by signing up for a sandbox account here: https://developer.authorize.net/sandbox/
        String apiLoginId           = "5KP3u95bQpv";
        String transactionKey       = "4Ktq966gC55GAX7S";
        //Update the payedId with which you want to run the sample code
        String payerId 				= "";
        //Update the transactionId with which you want to run the sample code
        String transactionId 		= "";

        switch (methodName) {
            case "DecryptVisaCheckoutData":
                DecryptVisaCheckoutData.run(apiLoginId, transactionKey);
                break;
            case "CreateVisaCheckoutTransaction":
                CreateVisaCheckoutTransaction.run(apiLoginId, transactionKey);
                break;
            case "ChargeCreditCard":
                ChargeCreditCard.run(apiLoginId, transactionKey);
                break;
            case "VoidTransaction":
                VoidTransaction.run(apiLoginId, transactionKey);
                break;
            case "AuthorizeCreditCard":
                AuthorizeCreditCard.run(apiLoginId, transactionKey);
                break;
            case "RefundTransaction":
                RefundTransaction.run(apiLoginId, transactionKey);
                break;
            case "CreateCustomerProfileFromTransaction":
                CreateCustomerProfileFromTransaction.run(apiLoginId, transactionKey);
                break;
            case "CaptureOnly":
                CaptureOnly.run(apiLoginId, transactionKey);
                break;
            case "CapturePreviouslyAuthorizedAmount":
                CapturePreviouslyAuthorizedAmount.run(apiLoginId, transactionKey);
                break;
            case "DebitBankAccount":
                DebitBankAccount.run(apiLoginId, transactionKey);
                break;
            case "CreditBankAccount":
                CreditBankAccount.run(apiLoginId, transactionKey);
                break;
            case "ChargeTokenizedCreditCard":
                ChargeTokenizedCreditCard.run(apiLoginId, transactionKey);
                break;
            case "CreateAnApplePayTransaction":
                CreateAnApplePayTransaction.run(apiLoginId, transactionKey);
                break;
            case "ChargeCustomerProfile":
                ChargeCustomerProfile.run(apiLoginId, transactionKey);
                break;
            case "CreateSubscription":
                CreateSubscription.run(apiLoginId, transactionKey);
                break;
            case "GetSubscription":
                GetSubscription.run(apiLoginId, transactionKey);
                break; 
            case "GetSubscriptionStatus":
                GetSubscriptionStatus.run(apiLoginId, transactionKey);
                break; 
            case "CancelSubscription":
                CancelSubscription.run(apiLoginId, transactionKey);
                break;
            case "UpdateSubscription":
                UpdateSubscription.run(apiLoginId, transactionKey);
                break;
            case "GetListOfSubscriptions":
                GetListOfSubscriptions.run(apiLoginId, transactionKey);
                break;
            case "GetBatchStatistics":
                GetBatchStatistics.run(apiLoginId, transactionKey);
                break;
            case "GetSettledBatchList":
                GetSettledBatchList.run(apiLoginId, transactionKey);
                break;
            case "GetTransactionList":
                GetTransactionList.run(apiLoginId, transactionKey);
                break;
            case "GetUnsettledTransactionList":
                GetUnsettledTransactionList.run(apiLoginId, transactionKey);
                break;
            case "GetTransactionDetails":
                GetTransactionDetails.run(apiLoginId, transactionKey);
                break;
            case "CreateCustomerProfile":
                CreateCustomerProfile.run(apiLoginId, transactionKey);
                break;
            case "CreateCustomerPaymentProfile":
                CreateCustomerPaymentProfile.run(apiLoginId, transactionKey);
                break;
            case "CreateCustomerShippingAddress":
                CreateCustomerShippingAddress.run(apiLoginId, transactionKey);
                break;
            case "DeleteCustomerPaymentProfile":
                DeleteCustomerPaymentProfile.run(apiLoginId, transactionKey);
                break;
            case "DeleteCustomerProfile":
                DeleteCustomerProfile.run(apiLoginId, transactionKey);
                break;
            case "DeleteCustomerShippingAddress":
                DeleteCustomerShippingAddress.run(apiLoginId, transactionKey);
                break;
            case "GetCustomerPaymentProfile":
                GetCustomerPaymentProfile.run(apiLoginId, transactionKey);
                break;
            case "GetCustomerPaymentProfileList":
                GetCustomerPaymentProfileList.run(apiLoginId, transactionKey);
                break;
            case "GetCustomerProfile":
                GetCustomerProfile.run(apiLoginId, transactionKey);
                break;
            case "GetCustomerProfileIds":
                GetCustomerProfileIds.run(apiLoginId, transactionKey);
                break;
            case "GetCustomerShippingAddress":
                GetCustomerShippingAddress.run(apiLoginId, transactionKey);
                break;
            case "GetHostedProfilePage":
                GetHostedProfilePage.run(apiLoginId, transactionKey);
                break;
            case "UpdateCustomerPaymentProfile":
                UpdateCustomerPaymentProfile.run(apiLoginId, transactionKey);
                break;
            case "UpdateCustomerShippingAddress":
                UpdateCustomerShippingAddress.run(apiLoginId, transactionKey);
                break;
            case "PayPalAuthorizeCapture":
            	AuthorizationAndCapture.run(apiLoginId, transactionKey);
                break;
            case "PayPalVoid":
            	Void.run(apiLoginId, transactionKey);
                break;
            case "PayPalAuthorizationOnly":
                AuthorizationOnly.run(apiLoginId, transactionKey);
                break;
            case "PayPalAuthorizeCaptureContinue":
            	AuthorizationAndCaptureContinue.run(apiLoginId, transactionKey, transactionId, payerId);
            	break;
            case "PayPalAuthorizeOnlyContinue":
            	AuthorizationOnlyContinued.run(apiLoginId, transactionKey, transactionId, payerId);
            	break;	
            case "PayPalCredit":
                Credit.run(apiLoginId, transactionKey, transactionId);
                break;
            case "PayPalGetDetails":
            	GetDetails.run(apiLoginId, transactionKey);
            case "PaypalPriorAuthorizationCapture":
                transactionId = "2241801682"; // Use a valid transaction ID here        
                PriorAuthorizationCapture.run(apiLoginId, transactionKey, transactionId);
                break;
            case "UpdateSplitTenderGroup":
                UpdateSplitTenderGroup.run(apiLoginId, transactionKey);
                break;
            default:
                ShowUsage();
                break;
        }
    }
}
