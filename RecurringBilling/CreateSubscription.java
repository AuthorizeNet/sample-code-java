import net.authorize.arb;
public class CreateSubscription {
  private String appId;
  private String transKey;
  /* Singleton */
  private static CreateSubscription helper;
  public static CreateSubscription SharedHelper() {
    if (helper == null) helper = new CreateSubscription();
    return helper;
  }

  public void scheduleSubscription(BigDecimal amt, String startDate, CreditCard card) {
    PaymentSchedule schedule = PaymentSchedule.createPaymentSchedule();
    schedule.setIntervalLength(1);
    schedule.setSubscriptionUnit(SubscriptionUnitType.MONTHS);
    schedule.setStartDate(startDate);
    schedule.setTotalOccurrences(12); // assuming an annual sub here.
    schedule.setTrialOccurrences(0);
    Address info = Address.createAddress();
    info.setFirstName("Jack " + System.currentTimeMillis());
    info.setLastName("Sparrow");
    Customer customer = Customer.createCustomer();
    customer.setBillTo(info);
    Subscription subscription = Subscription.createSubscription();
    subscription.setPayment(Payment.createPayment(credit_card));
    subscription.setSchedule(new_schedule);
    subscription.setCustomer(customer);
    subscription.setAmount(new BigDecimal(6.00));
    subscription.setTrialAmount(Transaction.ZERO_AMOUNT);
    subscription.setRefId("REF:" + System.currentTimeMillis());
    subscription.setName("Demo Subscription " + System.currentTimeMillis());

    Transaction transaction = merchant.createARBTransaction(TransactionType.CREATE_SUBSCRIPTION,
        subscription);
      Result<Transaction> result = (Result<Transaction>)merchant.postTransaction(transaction);

      System.out.println("Result Code: " + (result.getResultCode() != null ? result.getResultCode() : "No result code") + " ");
      System.out.println("Result Subscription Id: " + result.getResultSubscriptionId() + " ");
      for(int i = 0; i < result.getMessages().size(); i++){
        Message message = (Message)result.getMessages().get(i);
        System.out.println("Message: " + message.getCode() + " - " + message.getText() + " ");
      }
    }

}
