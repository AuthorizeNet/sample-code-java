package net.authorize.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.authorize.sample.VisaCheckout.*;
import net.authorize.sample.PaymentTransactions.*;
import net.authorize.sample.RecurringBilling.*;
import net.authorize.sample.TransactionReporting.*;
import net.authorize.sample.CustomerProfiles.*;

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
        System.out.println("    VisaCheckoutDecrypt");
        System.out.println("    VisaCheckoutTransaction");
        System.out.println("    ChargeCreditCard");
        System.out.println("    CreateSubscription");
        System.out.println("    AuthorizeCreditCard");
        System.out.println("    CreateCustomerProfilePaymentProfile");
        System.out.println("    RefundTransaction");
        System.out.println("    VoidTransaction");
        System.out.println("    CreateCustomerProfileFromTransaction");
        System.out.println("    GetTransactionDetails");
        System.out.println("    CaptureOnly");
        System.out.println("    CapturePreviouslyAuthorizedAmount");
        System.out.println("    DebitBankAccount");
        System.out.println("    CreditBankAccount");
    }

    private static void RunMethod(String methodName)
    {
        // These are default transaction keys.
        // You can create your own keys in seconds by signing up for a sandbox account here: https://developer.authorize.net/sandbox/
        String apiLoginId           = "5KP3u95bQpv";
        String transactionKey       = "4Ktq966gC55GAX7S";
        

        switch (methodName) {
            case "VisaCheckoutDecrypt":
                VisaCheckoutDecrypt.run(apiLoginId, transactionKey);
                break;
            case "VisaCheckoutTransaction":
                VisaCheckoutTransaction.run(apiLoginId, transactionKey);
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
            case "CreateCustomerProfilePaymentProfile":
                CreateCustomerProfilePaymentProfile.run(apiLoginId, transactionKey);
                break;
            case "RefundTransaction":
                RefundTransaction.run(apiLoginId, transactionKey);
                break;
            case "CreateCustomerProfileFromTransaction":
                CreateCustomerProfileFromTransaction.run(apiLoginId, transactionKey);
                break;
            case "GetTransactionDetails":
                GetTransactionDetails.run(apiLoginId, transactionKey);
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
            default:
                ShowUsage();
                break;
        }
    }
}