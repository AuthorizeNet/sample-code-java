package net.authorize.sample.TransactionReporting;

import java.util.Calendar;
import java.util.GregorianCalendar;
import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.GetSettledBatchListController;
import net.authorize.api.controller.base.ApiOperationBase;
import javax.xml.datatype.DatatypeFactory;

//author @krgupta modified @kikmak42
public class GetSettledBatchList {

    public static ANetApiResponse run(String apiLoginId, String transactionKey) {

        ApiOperationBase.setEnvironment(Environment.SANDBOX);

        MerchantAuthenticationType merchantAuthenticationType = new MerchantAuthenticationType();
        merchantAuthenticationType.setName(apiLoginId);
        merchantAuthenticationType.setTransactionKey(transactionKey);
        ApiOperationBase.setMerchantAuthentication(merchantAuthenticationType);

        GetSettledBatchListRequest getRequest = new GetSettledBatchListRequest();
        getRequest.setMerchantAuthentication(merchantAuthenticationType);

        try {
            // Set first settlement date in format (year, month, day)(should not be less that 31 days since last settlement date)
            GregorianCalendar pastDate = new GregorianCalendar();
            pastDate.add(Calendar.DAY_OF_YEAR, -7);
            getRequest.setFirstSettlementDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(pastDate));

            // Set last settlement date in format (year, month, day) (should not be greater that 31 days since first settlement date)
            GregorianCalendar currentDate = new GregorianCalendar();
            getRequest.setLastSettlementDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(currentDate));

        } catch (Exception ex) {
            System.out.println("Error : while setting dates");
            ex.printStackTrace();
        }

        GetSettledBatchListController controller = new GetSettledBatchListController(getRequest);
        controller.execute();
        GetSettledBatchListResponse getResponse = controller.getApiResponse();
        if (getResponse != null) {

            if (getResponse.getMessages().getResultCode() == MessageTypeEnum.OK) {

                System.out.println(getResponse.getMessages().getMessage().get(0).getCode());
                System.out.println(getResponse.getMessages().getMessage().get(0).getText());

                ArrayOfBatchDetailsType batchList = getResponse.getBatchList();
                if (batchList != null) {
                    System.out.println("List of Settled Transaction :");
                    for (BatchDetailsType batch : batchList.getBatch()) {
                        System.out.println(batch.getBatchId() + " - " + batch.getMarketType() + " - " + batch.getPaymentMethod() + " - " + batch.getProduct() + " - " + batch.getSettlementState());
                    }
                }
            } else {
                System.out.println("Failed to get settled batch list:  " + getResponse.getMessages().getResultCode());
                System.out.println(getResponse.getMessages().getMessage().get(0).getText());
            }
        }
		return getResponse;
    }
}
