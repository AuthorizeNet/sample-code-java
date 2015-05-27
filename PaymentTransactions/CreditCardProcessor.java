import java.math.BigDecimal;

/**
 * Created by michael on 5/27/15.
 */
public class CreditCardProcessor {

/* Fields */

    private String appId;
    private String transKey;
    private Merchant merchant;

/* Singleton */

    private static CreditCardProcessor _instance;
    public static CreditCardProcessor instance() {
        if (_instance == null) _instance = new CreditCardProcessor();
        return _instance;
    }

    public void config(String appLoginId, String transactionKey) {
        this.instance().appId = appLoginId;
        this.transKey = transactionKey;
        this.merchant = Merchant.createMerchant(Environment.SANDBOX,
              apiLoginID, transactionKey);
    }

    public void runCreditCardTransaction(String cardNumber, String expMonth, String expYear, BigDecimal amt) {

        CreditCard card = new CreditCard();
        card.setCreditCardNumber(cardNumber);
        card.setExpirationMonth(expMonth);
        card.setExpirationYear(expYear);

        Transaction trans = merchant.createAIMTransaction(
                TransactionType.AUTH_CAPTURE, amt);
        trans.setCreditCard(card);

        Result<Transaction> result = (Result<Transaction>)merchant
                .postTransaction(trans);

        if(result.isApproved()) {
            System.out.println("Approved!");
            System.out.println(" Id: " + result.getTarget().getTransactionId());
        } else if (result.isDeclined()) {
            System.out.println("Declined by issuing bank");
            System.out.println(result.getReasonResponseCode() + " : " + result.getResponseText());
        } else {
            System.out.println("Unable to process transaction");
            Sytem.out.println(result.getReasonResponseCode() + " : " + result.getResponseText());
        }
    }

}
