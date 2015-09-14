package net.authorize.sample.CustomerProfiles;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;

import net.authorize.api.contract.v1.MerchantAuthenticationType;
//import net.authorize.api.contract.v1.TransactionDetailsType;
//import net.authorize.api.controller.GetTransactionDetailsController;
import net.authorize.api.controller.base.ApiOperationBase;

import net.authorize.Transaction;
import net.authorize.api.contract.v1.CreateCustomerPaymentProfileRequest;
import net.authorize.api.contract.v1.CreateCustomerPaymentProfileResponse;
import net.authorize.api.contract.v1.CreateCustomerProfileFromTransactionRequest;
import net.authorize.api.contract.v1.CreateCustomerProfileRequest;
import net.authorize.api.contract.v1.CreateCustomerProfileResponse;
import net.authorize.api.contract.v1.CreditCardType;
import net.authorize.api.contract.v1.CustomerAddressType;
import net.authorize.api.contract.v1.CustomerPaymentProfileType;
import net.authorize.api.contract.v1.CustomerProfileType;
import net.authorize.api.contract.v1.GetCustomerPaymentProfileRequest;
import net.authorize.api.contract.v1.GetCustomerPaymentProfileResponse;
import net.authorize.api.contract.v1.GetTransactionDetailsRequest;
import net.authorize.api.contract.v1.GetTransactionDetailsResponse;
import net.authorize.api.contract.v1.MerchantAuthenticationType;
import net.authorize.api.contract.v1.MessageTypeEnum;
import net.authorize.api.contract.v1.TransactionDetailsType;
import net.authorize.api.contract.v1.ValidationModeEnum;
import net.authorize.api.controller.CreateCustomerProfileController;
import net.authorize.api.controller.CreateCustomerProfileFromTransactionController;
import net.authorize.api.controller.CreateCustomerPaymentProfileController;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.cim.Result;
import net.authorize.cim.TransactionType;
import net.authorize.cim.ValidationModeType;

//author @krgupta
public class CreateCustomerPaymentProfile {
	
	public static void run(String apiLoginId, String transactionKey) {

		ApiOperationBase.setEnvironment(Environment.SANDBOX);

		MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
		merchantAuthenticationType.setName(apiLoginId);
		merchantAuthenticationType.setTransactionKey(transactionKey);
		ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

		String customerprofileId = "36374423";


		//private String getPaymentDetails(MerchantAuthenticationType merchantAuthentication, String customerprofileId, ValidationModeEnum validationMode) {
		CreateCustomerPaymentProfileRequest getRequest = new CreateCustomerPaymentProfileRequest();
		getRequest.setMerchantAuthentication(merchantAuthenticationType);
		getRequest.setCustomerProfileId(customerprofileId);

		//customer address
		CustomerAddressType customerAddress = new CustomerAddressType();
		customerAddress.setFirstName("test");
		customerAddress.setLastName("scenario");
		customerAddress.setAddress("123 Main Street");
		customerAddress.setCity("Bellevue");
		customerAddress.setState("WA");
		customerAddress.setZip("98004");
		customerAddress.setCountry("USA");
		customerAddress.setPhoneNumber("000-000-0000");

		//credit card details
		CreditCardType creditCard = new CreditCardType();
		creditCard.setCardNumber("4111111111111111");
		creditCard.setExpirationDate("2023-12");
		creditCard.setCardCode("");

		CustomerPaymentProfileType profile = new CustomerPaymentProfileType();
		profile.setBillTo(customerAddress);

		PaymentType payment = new PaymentType();
		payment.setCreditCard(creditCard);
		profile.setPayment(payment);

		getRequest.setPaymentProfile(profile);

		CreateCustomerPaymentProfileController controller = new CreateCustomerPaymentProfileController(getRequest);
		controller.execute();

		CreateCustomerPaymentProfileResponse response = new CreateCustomerPaymentProfileResponse();
		response = controller.getApiResponse();

		if (response != null) {

			if (response.getMessages().getResultCode() == MessageTypeEnum.OK) {

				System.out.println(response.getCustomerPaymentProfileId());
				System.out.println(response.getMessages().getMessage().get(0).getCode());
				System.out.println(response.getMessages().getMessage().get(0).getText());
			} else {
				System.out.println("Failed to create Payment Profile:  " + response.getMessages().getResultCode());
			}
		}
		else
		{
			System.out.println("Failed to create Payment Profile:  Response is null");
		}
	}
	}