package net.authorize.sample.SampleCodeTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.SecureRandom;
import java.text.DecimalFormat;

import org.junit.Test;

import junit.framework.Assert;
import net.authorize.api.contract.v1.ANetApiResponse;
import net.authorize.api.contract.v1.ARBCreateSubscriptionResponse;
import net.authorize.api.contract.v1.ARBGetSubscriptionResponse;
import net.authorize.api.contract.v1.ARBGetSubscriptionStatusResponse;
import net.authorize.api.contract.v1.ARBUpdateSubscriptionResponse;
import net.authorize.api.contract.v1.CreateCustomerPaymentProfileResponse;
import net.authorize.api.contract.v1.CreateCustomerProfileResponse;
import net.authorize.api.contract.v1.CreateCustomerShippingAddressResponse;
import net.authorize.api.contract.v1.CreateTransactionResponse;
import net.authorize.api.contract.v1.DeleteCustomerPaymentProfileResponse;
import net.authorize.api.contract.v1.DeleteCustomerShippingAddressResponse;
import net.authorize.api.contract.v1.GetCustomerPaymentProfileResponse;
import net.authorize.api.contract.v1.GetCustomerProfileResponse;
import net.authorize.api.contract.v1.GetCustomerShippingAddressResponse;
import net.authorize.api.contract.v1.GetHostedProfilePageResponse;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.UpdateCustomerPaymentProfileResponse;
import net.authorize.api.contract.v1.UpdateCustomerProfileResponse;
import net.authorize.api.contract.v1.UpdateCustomerShippingAddressResponse;
import net.authorize.api.contract.v1.ValidateCustomerPaymentProfileResponse;
import net.authorize.sample.AcceptSuite.GetAcceptCustomerProfilePage;
import net.authorize.sample.AcceptSuite.GetAnAcceptPaymentPage;
import net.authorize.sample.CustomerProfiles.CreateCustomerPaymentProfile;
import net.authorize.sample.CustomerProfiles.CreateCustomerProfile;
import net.authorize.sample.CustomerProfiles.CreateCustomerProfileFromTransaction;
import net.authorize.sample.CustomerProfiles.CreateCustomerShippingAddress;
import net.authorize.sample.CustomerProfiles.DeleteCustomerPaymentProfile;
import net.authorize.sample.CustomerProfiles.DeleteCustomerProfile;
import net.authorize.sample.CustomerProfiles.DeleteCustomerShippingAddress;
import net.authorize.sample.CustomerProfiles.GetCustomerPaymentProfile;
import net.authorize.sample.CustomerProfiles.GetCustomerProfile;
import net.authorize.sample.CustomerProfiles.GetCustomerShippingAddress;
import net.authorize.sample.CustomerProfiles.UpdateCustomerPaymentProfile;
import net.authorize.sample.CustomerProfiles.UpdateCustomerProfile;
import net.authorize.sample.CustomerProfiles.UpdateCustomerShippingAddress;
import net.authorize.sample.CustomerProfiles.ValidateCustomerPaymentProfile;
import net.authorize.sample.PaymentTransactions.AuthorizeCreditCard;
import net.authorize.sample.PaymentTransactions.CaptureFundsAuthorizedThroughAnotherChannel;
import net.authorize.sample.PaymentTransactions.CapturePreviouslyAuthorizedAmount;
import net.authorize.sample.PaymentTransactions.ChargeCreditCard;
import net.authorize.sample.PaymentTransactions.ChargeCustomerProfile;
import net.authorize.sample.PaymentTransactions.ChargeTokenizedCreditCard;
import net.authorize.sample.PaymentTransactions.CreditBankAccount;
import net.authorize.sample.PaymentTransactions.DebitBankAccount;
import net.authorize.sample.PaymentTransactions.RefundTransaction;
import net.authorize.sample.PaymentTransactions.VoidTransaction;
import net.authorize.sample.PayPalExpressCheckout.AuthorizationAndCapture;
import net.authorize.sample.PayPalExpressCheckout.AuthorizationAndCaptureContinued;
import net.authorize.sample.PayPalExpressCheckout.AuthorizationOnly;
import net.authorize.sample.PayPalExpressCheckout.AuthorizationOnlyContinued;
import net.authorize.sample.PayPalExpressCheckout.Credit;
import net.authorize.sample.PayPalExpressCheckout.GetDetails;
import net.authorize.sample.PayPalExpressCheckout.PriorAuthorizationCapture;
import net.authorize.sample.RecurringBilling.CancelSubscription;
import net.authorize.sample.RecurringBilling.CreateSubscription;
import net.authorize.sample.RecurringBilling.CreateSubscriptionFromCustomerProfile;
import net.authorize.sample.RecurringBilling.GetSubscription;
import net.authorize.sample.RecurringBilling.GetSubscriptionStatus;
import net.authorize.sample.RecurringBilling.UpdateSubscription;
import net.authorize.sample.TransactionReporting.GetTransactionDetails;

public class TestRunner {

	String apiLoginId = Constants.API_LOGIN_ID;
	String transactionKey = Constants.TRANSACTION_KEY;
	String TransactionID = Constants.TRANSACTION_ID;
	String payerID = Constants.PAYER_ID;

	static SecureRandom rgenerator = new SecureRandom();

	private static String getEmail()
	{
		return rgenerator.nextInt(1000000) + "@test.com";
	}

	private static Double getAmount()
	{
		double d = (double)(1.05 + (450.0 * rgenerator.nextDouble()));
		DecimalFormat df = new DecimalFormat("#.##");      
		d = Double.valueOf(df.format(d));
		return d;
	}

	private static short getDays()
	{
		return (short) (rgenerator.nextInt(358) + 7);
	}

	@Test
	public void TestAllSampleCodes()
	{
		String fileName = Constants.CONFIG_FILE;
		int numRetries = 3;

		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(fileName));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		TestRunner tr = new TestRunner();
		int cnt = 0;
		String line;
		try {

			while ((line = reader.readLine()) != null)
			{
				String[] items = line.split("\t");

				String apiName = items[0];
				String isDependent = items[1];
				String shouldApiRun = items[2];

				System.out.println(apiName);

				if (!shouldApiRun.equals("1"))
					continue;
				
				
				System.out.println("-------------------");
				System.out.println("Running test case for :: " + apiName);
				System.out.println("-------------------");
				 
				ANetApiResponse response = null;

				cnt++;
				
				for (int i = 0;i<numRetries;++i)
				{
					try
					{
						if (isDependent.equals("0"))
						{
							response = InvokeRunMethod(apiName); 
						}
						else
						{
							String[] namespace = apiName.split("\\.");
							
							String className = namespace[1];
							Class classType = this.getClass();
							response = (ANetApiResponse)classType.getMethod("Test" + className).invoke(tr);
						}

						if ((response != null) && (response.getMessages().getResultCode() == MessageTypeEnum.OK))
							break;
					}
					catch (Exception e)
					{
						System.out.println("Exception in " + apiName + " " + e.toString());
						System.out.println(e.getMessage());
					}
				}
				Assert.assertNotNull(response);
				Assert.assertEquals(response.getMessages().getResultCode(), MessageTypeEnum.OK);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Total Sample Runs: " + cnt);
	}

	public ANetApiResponse InvokeRunMethod(String className)
	{
		String fqClassName = "net.authorize.sample." + className;

		Class classType = null;
		try {
			classType = Class.forName(fqClassName);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Method runMethod = null;
		try {
			runMethod = classType.getMethod("run",String.class, String.class);
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			return (ANetApiResponse)runMethod.invoke(null, Constants.API_LOGIN_ID, Constants.TRANSACTION_KEY);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ANetApiResponse TestValidateCustomerPaymentProfile()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse) CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		CreateCustomerPaymentProfileResponse customerPaymentProfile = (CreateCustomerPaymentProfileResponse)CreateCustomerPaymentProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		System.out.println(response.getCustomerProfileId());
		System.out.println(customerPaymentProfile.getCustomerPaymentProfileId());
		ValidateCustomerPaymentProfileResponse validateResponse = (ValidateCustomerPaymentProfileResponse) ValidateCustomerPaymentProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId(), customerPaymentProfile.getCustomerPaymentProfileId());
		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());

		return validateResponse;
	}


	public ANetApiResponse TestUpdateCustomerShippingAddress()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		CreateCustomerShippingAddressResponse shippingResponse = (CreateCustomerShippingAddressResponse)CreateCustomerShippingAddress.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		UpdateCustomerShippingAddressResponse updateResponse = (UpdateCustomerShippingAddressResponse) UpdateCustomerShippingAddress.run(apiLoginId, transactionKey, response.getCustomerProfileId(), shippingResponse.getCustomerAddressId());
		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());

		return updateResponse;
	}

	public ANetApiResponse TestUpdateCustomerProfile()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		UpdateCustomerProfileResponse updateResponse = (UpdateCustomerProfileResponse) UpdateCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());

		return updateResponse;
	}

	public ANetApiResponse TestUpdateCustomerPaymentProfile()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		CreateCustomerPaymentProfileResponse paymentProfileResponse = (CreateCustomerPaymentProfileResponse)CreateCustomerPaymentProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		UpdateCustomerPaymentProfileResponse updateResponse = (UpdateCustomerPaymentProfileResponse) UpdateCustomerPaymentProfile.run(apiLoginId, transactionKey,
				response.getCustomerProfileId(), paymentProfileResponse.getCustomerPaymentProfileId());
		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());

		return updateResponse;
	}

	public ANetApiResponse TestGetCustomerShippingAddress()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		CreateCustomerShippingAddressResponse shippingResponse = (CreateCustomerShippingAddressResponse)CreateCustomerShippingAddress.run(apiLoginId, transactionKey, response.getCustomerProfileId());

		GetCustomerShippingAddressResponse getResponse = (GetCustomerShippingAddressResponse) GetCustomerShippingAddress.run(apiLoginId, transactionKey, 
				response.getCustomerProfileId(), shippingResponse.getCustomerAddressId());

		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		return getResponse;
	}

	public ANetApiResponse TestGetCustomerProfile()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		GetCustomerProfileResponse profileResponse = (GetCustomerProfileResponse) GetCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());

		return profileResponse;
	}

	public ANetApiResponse TestGetAcceptCustomerProfilePage()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		GetHostedProfilePageResponse profileResponse = (GetHostedProfilePageResponse) GetAcceptCustomerProfilePage.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());

		return profileResponse;
	}

	public ANetApiResponse TestGetCustomerPaymentProfile()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		CreateCustomerPaymentProfileResponse paymentProfileResponse = (CreateCustomerPaymentProfileResponse)CreateCustomerPaymentProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		GetCustomerPaymentProfileResponse getResponse = (GetCustomerPaymentProfileResponse) GetCustomerPaymentProfile.run(apiLoginId, transactionKey,
				response.getCustomerProfileId(), paymentProfileResponse.getCustomerPaymentProfileId());

		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		return getResponse;
	}

	public ANetApiResponse TestDeleteCustomerShippingAddress()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		CreateCustomerShippingAddressResponse shippingResponse = (CreateCustomerShippingAddressResponse)CreateCustomerShippingAddress.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		DeleteCustomerShippingAddressResponse deleteResponse = (DeleteCustomerShippingAddressResponse) DeleteCustomerShippingAddress.run(apiLoginId, transactionKey,
				response.getCustomerProfileId(), shippingResponse.getCustomerAddressId());
		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());

		return deleteResponse;
	}

	public ANetApiResponse TestDeleteCustomerProfile()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		return DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
	}

	public ANetApiResponse TestDeleteCustomerPaymentProfile()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		CreateCustomerPaymentProfileResponse paymentProfileResponse = (CreateCustomerPaymentProfileResponse)CreateCustomerPaymentProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		DeleteCustomerPaymentProfileResponse deleteResponse = (DeleteCustomerPaymentProfileResponse) DeleteCustomerPaymentProfile.run(apiLoginId, transactionKey,
				response.getCustomerProfileId(), paymentProfileResponse.getCustomerPaymentProfileId());
		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());

		return deleteResponse;
	}

	public ANetApiResponse TestCreateCustomerShippingAddress()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		CreateCustomerShippingAddressResponse shippingResponse = (CreateCustomerShippingAddressResponse)CreateCustomerShippingAddress.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());

		return shippingResponse;
	}

	public ANetApiResponse TestAuthorizeCreditCard()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)AuthorizeCreditCard.run(apiLoginId, transactionKey, getAmount());
		return response;
	}
	
	public ANetApiResponse TestDebitBankAccount()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)DebitBankAccount.run(apiLoginId, transactionKey, getAmount());
		return response;
	}
	
	public ANetApiResponse TestChargeTokenizedCreditCard()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)ChargeTokenizedCreditCard.run(apiLoginId, transactionKey, getAmount());
		return response;
	}

	public ANetApiResponse TestGetTransactionDetails()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)AuthorizeCreditCard.run(apiLoginId, transactionKey, getAmount());
		return GetTransactionDetails.run(apiLoginId, transactionKey, response.getTransactionResponse().getTransId());
	}

	public ANetApiResponse TestChargeCreditCard()
	{
		return ChargeCreditCard.run(apiLoginId, transactionKey, getAmount());
	}
	
	public ANetApiResponse TestCreateCustomerProfileFromTransaction()
	{
		return CreateCustomerProfileFromTransaction.run(apiLoginId, transactionKey, getAmount(), getEmail());
	}

	public ANetApiResponse TestCapturePreviouslyAuthorizedAmount()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)AuthorizeCreditCard.run(apiLoginId, transactionKey, getAmount());
		return CapturePreviouslyAuthorizedAmount.run(apiLoginId, transactionKey, response.getTransactionResponse().getTransId());
	}

	public ANetApiResponse TestRefundTransaction()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)AuthorizeCreditCard.run(apiLoginId, transactionKey, getAmount());
		response = (CreateTransactionResponse)CapturePreviouslyAuthorizedAmount.run(apiLoginId, transactionKey, response.getTransactionResponse().getTransId());
		return RefundTransaction.run(apiLoginId, transactionKey, getAmount(), response.getTransactionResponse().getTransId());
	}

	public ANetApiResponse TestVoidTransaction()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)AuthorizeCreditCard.run(apiLoginId, transactionKey, getAmount());
		return VoidTransaction.run(apiLoginId, transactionKey, response.getTransactionResponse().getTransId());
	}

	public ANetApiResponse TestCreditBankAccount()
	{
		return CreditBankAccount.run(apiLoginId, transactionKey, TransactionID, getAmount());
	}

	public ANetApiResponse TestChargeCustomerProfile()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		CreateCustomerPaymentProfileResponse paymentProfileResponse = (CreateCustomerPaymentProfileResponse)CreateCustomerPaymentProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
		CreateTransactionResponse chargeResponse = (CreateTransactionResponse) ChargeCustomerProfile.run(apiLoginId, transactionKey,
				response.getCustomerProfileId(), paymentProfileResponse.getCustomerPaymentProfileId(), getAmount());
		DeleteCustomerProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());

		return chargeResponse;
	}

	public ANetApiResponse TestPayPalVoid()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)AuthorizationAndCapture.run(apiLoginId, transactionKey, getAmount());
		return net.authorize.sample.PayPalExpressCheckout.Void.run(apiLoginId, transactionKey, response.getTransactionResponse().getTransId());
	}  

	public ANetApiResponse TestPayPalAuthorizationAndCapture()
	{
		return AuthorizationAndCapture.run(apiLoginId, transactionKey, getAmount());
	}

	public ANetApiResponse TestPayPalAuthorizationAndCaptureContinued()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)AuthorizationAndCapture.run(apiLoginId, transactionKey, getAmount());
		return AuthorizationAndCaptureContinued.run(apiLoginId, transactionKey, response.getTransactionResponse().getTransId(), payerID, getAmount());
	}
	
	public ANetApiResponse TestPayPalAuthorizationOnly()
	{
		return AuthorizationOnly.run(apiLoginId, transactionKey, getAmount());
	}

	public ANetApiResponse TestPayPalAuthorizationOnlyContinued()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)AuthorizationAndCapture.run(apiLoginId, transactionKey, getAmount());
		return AuthorizationOnlyContinued.run(apiLoginId, transactionKey, response.getTransactionResponse().getTransId(), payerID, getAmount());
	}

	public ANetApiResponse TestPayPalCredit()
	{
		return Credit.run(apiLoginId, transactionKey, TransactionID);
	}

	public ANetApiResponse TestPayPalGetDetails()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)AuthorizationAndCapture.run(apiLoginId, transactionKey, getAmount());
		return GetDetails.run(apiLoginId, transactionKey, response.getTransactionResponse()
				.getTransId());
	}

	public ANetApiResponse TestPayPalPriorAuthorizationCapture()
	{
		CreateTransactionResponse response = (CreateTransactionResponse)AuthorizationAndCapture.run(apiLoginId, transactionKey, getAmount());
		return PriorAuthorizationCapture.run(apiLoginId, transactionKey, response.getTransactionResponse().getTransId());
	}

	public ANetApiResponse TestCancelSubscription()
	{
		ARBCreateSubscriptionResponse response = (ARBCreateSubscriptionResponse)CreateSubscription.run(apiLoginId, transactionKey, getDays(), getAmount());
		return CancelSubscription.run(apiLoginId, transactionKey, response.getSubscriptionId());
	}

	public ANetApiResponse TestCreateSubscription()
	{
		ARBCreateSubscriptionResponse response = (ARBCreateSubscriptionResponse)CreateSubscription.run(apiLoginId, transactionKey,  getDays(), getAmount());
		CancelSubscription.run(apiLoginId, transactionKey, response.getSubscriptionId());

		return response;
	}
	
	public ANetApiResponse TestCreateSubscriptionFromCustomerProfile()
	{
		CreateCustomerProfileResponse profileResponse = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		CreateCustomerPaymentProfileResponse paymentResponse = (CreateCustomerPaymentProfileResponse) CreateCustomerPaymentProfile.
				run(apiLoginId, transactionKey, profileResponse.getCustomerProfileId());

		CreateCustomerShippingAddressResponse shippingResponse = (CreateCustomerShippingAddressResponse)CreateCustomerShippingAddress.
				run(apiLoginId, transactionKey, profileResponse.getCustomerProfileId());
		
		try
		{
			Thread.sleep(10000);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		
		ARBCreateSubscriptionResponse response = (ARBCreateSubscriptionResponse) CreateSubscriptionFromCustomerProfile.run(apiLoginId, transactionKey, getDays(), getAmount(), profileResponse.getCustomerProfileId(), 
				paymentResponse.getCustomerPaymentProfileId(), shippingResponse.getCustomerAddressId());

		CancelSubscription.run(apiLoginId, transactionKey, response.getSubscriptionId());
		DeleteCustomerProfile.run(apiLoginId, transactionKey, profileResponse.getCustomerProfileId());
		
		return response;
	}

	public ANetApiResponse TestGetSubscriptionStatus()
	{
		ARBCreateSubscriptionResponse response = (ARBCreateSubscriptionResponse)CreateSubscription.run(apiLoginId, transactionKey, getDays(), getAmount());
		ARBGetSubscriptionStatusResponse subscriptionResponse = (ARBGetSubscriptionStatusResponse) GetSubscriptionStatus.run(apiLoginId, transactionKey, response.getSubscriptionId());


		return subscriptionResponse;
	}

	public ANetApiResponse TestGetSubscription()
	{
		ARBCreateSubscriptionResponse response = (ARBCreateSubscriptionResponse)CreateSubscription.run(apiLoginId, transactionKey, getDays(), getAmount());
		ARBGetSubscriptionResponse getResponse = (ARBGetSubscriptionResponse) GetSubscription.run(apiLoginId, transactionKey, response.getSubscriptionId());
		CancelSubscription.run(apiLoginId, transactionKey, response.getSubscriptionId());
		return getResponse;
	}

	public ANetApiResponse TestUpdateSubscription()
	{
		ARBCreateSubscriptionResponse response = (ARBCreateSubscriptionResponse)CreateSubscription.run(apiLoginId, transactionKey, getDays(), getAmount());
		ARBUpdateSubscriptionResponse updateResponse = (ARBUpdateSubscriptionResponse) UpdateSubscription.run(apiLoginId, transactionKey, response.getSubscriptionId());
		CancelSubscription.run(apiLoginId, transactionKey, response.getSubscriptionId());

		return updateResponse;
	}

	public ANetApiResponse TestCreateCustomerProfile()
	{
		return CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
	}
	
	public ANetApiResponse TestCaptureFundsAuthorizedThroughAnotherChannel()
	{
		return CaptureFundsAuthorizedThroughAnotherChannel.run(apiLoginId, transactionKey, getAmount());
	}

	public ANetApiResponse TestCreateCustomerPaymentProfile()
	{
		CreateCustomerProfileResponse response = (CreateCustomerProfileResponse)CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
		return CreateCustomerPaymentProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
	}
	
	public ANetApiResponse TestGetAnAcceptPaymentPage()
	{
		return GetAnAcceptPaymentPage.run(apiLoginId, transactionKey, getAmount());
	}
}
