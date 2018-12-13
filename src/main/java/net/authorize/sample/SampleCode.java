package net.authorize.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.authorize.sample.VisaCheckout.*;
import net.authorize.sample.PaymentTransactions.*;
import net.authorize.sample.PayPalExpressCheckout.*;
import net.authorize.sample.PayPalExpressCheckout.Void;
import net.authorize.sample.RecurringBilling.*;
import net.authorize.sample.TransactionReporting.*;
import net.authorize.sample.CustomerProfiles.*;
import net.authorize.sample.MobileInAppTransactions.*;
import net.authorize.sample.FraudManagement.*;
import net.authorize.sample.AcceptSuite.GetAcceptCustomerProfilePage;
import net.authorize.sample.AcceptSuite.GetAnAcceptPaymentPage;

/**
 * Created by anetdeveloper on 8/5/15.
 */
public class SampleCode {

	public static void main(String[] args) {

		if (args.length == 0) {
			SelectMethod();
		} else if (args.length == 1) {
			RunMethod(args[0]);
			return;
		} else {
			ShowUsage();
		}

		System.out.println("");
		System.out.print("Press <Return> to finish ...");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			int i = Integer.parseInt(br.readLine());
		} catch (Exception ex) {
		}

	}

	private static void ShowUsage() {
		System.out.println("Usage : java -jar SampleCode.jar [CodeSampleName]");
		System.out.println("");
		System.out.println("Run with no parameter to select a method.  Otherwise pass a method name.");
		System.out.println("");
		System.out.println("Code Sample Names: ");
		ShowMethods();

	}

	private static void SelectMethod() {
		System.out.println("Code Sample Names: ");
		System.out.println("");
		ShowMethods();
		System.out.println("");
		System.out.print("Type a sample name & then press <Return> : ");
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		try {
			RunMethod(br.readLine());
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}

	private static void ShowMethods() {
		System.out.println("    DecryptVisaCheckoutData");
		System.out.println("    CreateVisaCheckoutTransaction");
		System.out.println("    ChargeCreditCard");
		System.out.println("    AuthorizeCreditCard");
		System.out.println("    RefundTransaction");
		System.out.println("    VoidTransaction");
		System.out.println("    CreateCustomerProfileFromTransaction");
		System.out.println("    CaptureFundsAuthorizedThroughAnotherChannel");
		System.out.println("    CapturePreviouslyAuthorizedAmount");
		System.out.println("    DebitBankAccount");
		System.out.println("    CreditBankAccount");
		System.out.println("    ChargeTokenizedCreditCard");
		System.out.println("    CreateAnApplePayTransaction");
		System.out.println("    CreateAnAndroidPayTransaction");
		System.out.println("    CreateAnAcceptTransaction");
		System.out.println("    ChargeCustomerProfile");
		System.out.println("    CreateSubscription");
		System.out.println("    CreateSubscriptionFromCustomerProfile");
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
		System.out.println("    GetCustomerProfileTransactionList");
		System.out.println("    GetCustomerShippingAddress");
		System.out.println("    GetAcceptCustomerProfilePage");
		System.out.println("    UpdateCustomerProfile");
		System.out.println("    UpdateCustomerPaymentProfile");
		System.out.println("    UpdateCustomerShippingAddress");
		System.out.println("    ValidateCustomerPaymentProfile");
		System.out.println("    PayPalAuthorizeCapture");
		System.out.println("    PayPalVoid");
		System.out.println("    PayPalAuthorizationOnly");
		System.out.println("    PayPalAuthorizeCaptureContinued");
		System.out.println("    PayPalGetDetails");
		System.out.println("    PayPalPriorAuthorizationCapture");
		System.out.println("    PayPalAuthorizeOnlyContinued");
		System.out.println("    PayPalCredit");
		System.out.println("    UpdateSplitTenderGroup");
		System.out.println("    GetMerchantDetails");
		System.out.println("    GetHeldTransactionList");
		System.out.println("    ApproveOrDeclineHeldTransaction");
		System.out.println("    GetAnAcceptPaymentPage");
		System.out.println("    CreateChasePayTransaction");
	}

	private static void RunMethod(String methodName) {
		// These are default transaction keys.
		// You can create your own keys in seconds by signing up for a sandbox account
		// here: https://developer.authorize.net/sandbox/
		String apiLoginId = "5KP3u95bQpv";
		String transactionKey = "346HZ32z3fP4hTG2";
		// Update the payedId with which you want to run the sample code
		String payerId = "6ZSCSYG33VP8Q";
		// Update the transactionId with which you want to run the sample code
		String transactionId = "123456";

		String customerProfileId = "36596285";
		String customerPaymentProfileId = "33086593";
		String customerAddressId = "1873761911";

		String emailId = "test@test.com";

		String subscriptionId = "2925606";

		Double amount = 123.45;

		// Proxy server settings.
		// Enable these lines for using Sample Codes behind a proxy server

		// System.setProperty("https.proxyUse", "true");
		// System.setProperty("https.proxyHost", "example.proxy.server");
		// System.setProperty("https.proxyPort", "portNumber");
		// System.setProperty("https.proxyUserName", "exampleUsername");
		// System.setProperty("https.proxyPassword", "examplePassword");

		switch (methodName) {
		case "DecryptVisaCheckoutData":
			DecryptVisaCheckoutData.run(apiLoginId, transactionKey);
			break;
		case "CreateVisaCheckoutTransaction":
			CreateVisaCheckoutTransaction.run(apiLoginId, transactionKey);
			break;
		case "ChargeCreditCard":
			ChargeCreditCard.run(apiLoginId, transactionKey, amount);
			break;
		case "VoidTransaction":
			VoidTransaction.run(apiLoginId, transactionKey, transactionId);
			break;
		case "AuthorizeCreditCard":
			AuthorizeCreditCard.run(apiLoginId, transactionKey, amount);
			break;
		case "RefundTransaction":
			RefundTransaction.run(apiLoginId, transactionKey, amount, transactionId);
			break;
		case "CreateCustomerProfileFromTransaction":
			CreateCustomerProfileFromTransaction.run(apiLoginId, transactionKey, amount, emailId);
			break;
		case "CaptureFundsAuthorizedThroughAnotherChannel":
			CaptureFundsAuthorizedThroughAnotherChannel.run(apiLoginId, transactionKey, amount);
			break;
		case "CapturePreviouslyAuthorizedAmount":
			CapturePreviouslyAuthorizedAmount.run(apiLoginId, transactionKey, transactionId);
			break;
		case "DebitBankAccount":
			DebitBankAccount.run(apiLoginId, transactionKey, amount);
			break;
		case "CreditBankAccount":
			CreditBankAccount.run(apiLoginId, transactionKey, transactionId, amount);
			break;
		case "ChargeTokenizedCreditCard":
			ChargeTokenizedCreditCard.run(apiLoginId, transactionKey, amount);
			break;
		case "CreateAnApplePayTransaction":
			CreateAnApplePayTransaction.run(apiLoginId, transactionKey);
			break;
		case "CreateAnAndroidPayTransaction":
			CreateAnAndroidPayTransaction.run(apiLoginId, transactionKey);
			break;
		case "CreateAnAcceptTransaction":
			CreateAnAcceptTransaction.run(apiLoginId, transactionKey);
			break;
		case "ChargeCustomerProfile":
			ChargeCustomerProfile.run(apiLoginId, transactionKey, customerProfileId, customerPaymentProfileId, amount);
			break;
		case "ChargeCustomerProfilesMT":
			ChargeCustomerProfilesMT.run();
			break;
		case "CreateSubscription":
			CreateSubscription.run(apiLoginId, transactionKey, (short) 12, amount);
			break;
		case "CreateSubscriptionFromCustomerProfile":
			CreateSubscriptionFromCustomerProfile.run(apiLoginId, transactionKey, (short) 12, amount, customerProfileId,
					customerPaymentProfileId, customerAddressId);
			break;
		case "GetSubscription":
			GetSubscription.run(apiLoginId, transactionKey, subscriptionId);
			break;
		case "GetSubscriptionStatus":
			GetSubscriptionStatus.run(apiLoginId, transactionKey, subscriptionId);
			break;
		case "CancelSubscription":
			CancelSubscription.run(apiLoginId, transactionKey, subscriptionId);
			break;
		case "UpdateSubscription":
			UpdateSubscription.run(apiLoginId, transactionKey, subscriptionId);
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
			GetTransactionDetails.run(apiLoginId, transactionKey, transactionId);
			break;
		case "CreateCustomerProfile":
			CreateCustomerProfile.run(apiLoginId, transactionKey, emailId);
			break;
		case "CreateCustomerPaymentProfile":
			CreateCustomerPaymentProfile.run(apiLoginId, transactionKey, customerProfileId);
			break;
		case "CreateCustomerShippingAddress":
			CreateCustomerShippingAddress.run(apiLoginId, transactionKey, customerProfileId);
			break;
		case "DeleteCustomerPaymentProfile":
			DeleteCustomerPaymentProfile.run(apiLoginId, transactionKey, customerProfileId, customerPaymentProfileId);
			break;
		case "DeleteCustomerProfile":
			DeleteCustomerProfile.run(apiLoginId, transactionKey, customerProfileId);
			break;
		case "DeleteCustomerShippingAddress":
			DeleteCustomerShippingAddress.run(apiLoginId, transactionKey, customerProfileId, customerAddressId);
			break;
		case "GetCustomerPaymentProfile":
			GetCustomerPaymentProfile.run(apiLoginId, transactionKey, customerProfileId, customerPaymentProfileId);
			break;
		case "GetCustomerPaymentProfileList":
			GetCustomerPaymentProfileList.run(apiLoginId, transactionKey);
			break;
		case "GetCustomerProfile":
			GetCustomerProfile.run(apiLoginId, transactionKey, customerProfileId);
			break;
		case "GetCustomerProfileIds":
			GetCustomerProfileIds.run(apiLoginId, transactionKey);
			break;
		case "GetCustomerProfileTransactionList":
			customerProfileId = "1811474252";
			GetCustomerProfileTransactionList.run(apiLoginId, transactionKey, customerProfileId);
			break;
		case "GetCustomerShippingAddress":
			GetCustomerShippingAddress.run(apiLoginId, transactionKey, customerProfileId, customerAddressId);
			break;
		case "GetAcceptCustomerProfilePage":
			GetAcceptCustomerProfilePage.run(apiLoginId, transactionKey, customerProfileId);
			break;
		case "UpdateCustomerProfile":
			UpdateCustomerProfile.run(apiLoginId, transactionKey, customerProfileId);
			break;
		case "UpdateCustomerPaymentProfile":
			UpdateCustomerPaymentProfile.run(apiLoginId, transactionKey, customerProfileId, customerPaymentProfileId);
			break;
		case "UpdateCustomerShippingAddress":
			UpdateCustomerShippingAddress.run(apiLoginId, transactionKey, customerProfileId, customerAddressId);
			break;
		case "ValidateCustomerPaymentProfile":
			ValidateCustomerPaymentProfile.run(apiLoginId, transactionKey, customerProfileId, customerPaymentProfileId);
			break;
		case "PayPalAuthorizeCapture":
			AuthorizationAndCapture.run(apiLoginId, transactionKey, amount);
			break;
		case "PayPalVoid":
			Void.run(apiLoginId, transactionKey, transactionId);
			break;
		case "PayPalAuthorizationOnly":
			AuthorizationOnly.run(apiLoginId, transactionKey, amount);
			break;
		case "PayPalAuthorizeCaptureContinued":
			AuthorizationAndCaptureContinued.run(apiLoginId, transactionKey, transactionId, payerId, amount);
			break;
		case "PayPalAuthorizeOnlyContinued":
			AuthorizationOnlyContinued.run(apiLoginId, transactionKey, transactionId, payerId, amount);
			break;
		case "PayPalCredit":
			Credit.run(apiLoginId, transactionKey, transactionId);
			break;
		case "PayPalGetDetails":
			GetDetails.run(apiLoginId, transactionKey, transactionId);
		case "PayPalPriorAuthorizationCapture":
			PriorAuthorizationCapture.run(apiLoginId, transactionKey, transactionId);
			break;
		case "UpdateSplitTenderGroup":
			UpdateSplitTenderGroup.run(apiLoginId, transactionKey);
			break;
		case "GetMerchantDetails":
			GetMerchantDetails.run(apiLoginId, transactionKey);
			break;
		case "GetHeldTransactionList":
			GetHeldTransactionList.run(apiLoginId, transactionKey);
			break;
		case "ApproveOrDeclineHeldTransaction":
			ApproveOrDeclineHeldTransaction.run(apiLoginId, transactionKey, transactionId);
			break;
		case "GetAnAcceptPaymentPage":
			GetAnAcceptPaymentPage.run(apiLoginId, transactionKey, amount);
			break;
		case "CreateChasePayTransaction":
			CreateChasePayTransaction.run(apiLoginId, transactionKey, amount);
			break;
		default:
			ShowUsage();
			break;
		}
	}
}
