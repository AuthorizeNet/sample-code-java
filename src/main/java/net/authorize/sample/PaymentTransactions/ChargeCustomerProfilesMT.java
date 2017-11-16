package net.authorize.sample.PaymentTransactions;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import net.authorize.Environment;
import net.authorize.api.contract.v1.*;
import net.authorize.api.controller.CreateTransactionController;
import net.authorize.api.controller.base.ApiOperationBase;
import net.authorize.sample.CustomerProfiles.CreateCustomerPaymentProfile;
import net.authorize.sample.CustomerProfiles.CreateCustomerProfile;

public class ChargeCustomerProfilesMT {

    private static final int MYTHREADS = 120;
    private static String apiLoginId           = "5KP3u95bQpv";
    private static String transactionKey       = "346HZ32z3fP4hTG2";

    static SecureRandom rgenerator = new SecureRandom();

    private static Double getAmount()
    {
        double d = (double)(1.05 + (450.0 * rgenerator.nextDouble()));
        DecimalFormat df = new DecimalFormat("#.##");
        d = Double.valueOf(df.format(d));
        return d;
    }

    private static String getEmail()
    {
        return rgenerator.nextInt(10000000) + "@test.com";
    }

    //
    // Run this sample from command line with:
    //                 java -jar target/ChargeCreditCard-jar-with-dependencies.jar
    //
    public static Boolean run() {


        ExecutorService executor = Executors.newFixedThreadPool(MYTHREADS);

        Date dt1 = new Date();
        for (int i = 0; i < MYTHREADS; i++) {
            Runnable worker = new MyRunnable();
            executor.execute(worker);
        }
        executor.shutdown();

        // Wait until all threads are finish
        while (!executor.isTerminated()) {

        }
        Date dt2 = new Date();
        long difference = dt2.getTime() - dt1.getTime();

        System.out.println("\nFinished all threads : " +  difference/1000 + " sec");

        return true;
    }

    public static class MyRunnable implements Runnable {

        @Override
        public void run() {
            CreateCustomerProfileResponse response = null;
            CreateCustomerPaymentProfileResponse paymentProfileResponse = null;

            try{
                response = (CreateCustomerProfileResponse) CreateCustomerProfile.run(apiLoginId, transactionKey, getEmail());
                paymentProfileResponse = (CreateCustomerPaymentProfileResponse) CreateCustomerPaymentProfile.run(apiLoginId, transactionKey, response.getCustomerProfileId());
            }
            catch (Exception e) {
                System.out.println("Failure in CIM calls.");
                System.out.println(e.getMessage());
                return;
            }

            try {
                Date dt1 = new Date();
                System.out.println("============================================================================");
                System.out.println("chargeResponse Dt1: + " + dt1.toString());

                if ((response.getCustomerProfileId() != null) && (paymentProfileResponse.getCustomerPaymentProfileId() != null))
                {
                    CreateTransactionResponse chargeResponse = (CreateTransactionResponse) ChargeCustomerProfile.run(apiLoginId, transactionKey,
                            response.getCustomerProfileId(), paymentProfileResponse.getCustomerPaymentProfileId(), getAmount());
                }
                else
                {
                    System.out.println("NULL PROFILE IDS.");
                }

                Date dt2 = new Date();
                System.out.println("chargeResponse Dt2: + " + dt2.toString());

                long difference = dt2.getTime() - dt1.getTime();
                System.out.println("chargeResponse Diff: " + difference/1000);
                System.out.println("============================================================================");
            } catch (Exception e) {
                System.out.println("Failure in charge customer profile call:");
                System.out.println(e.getMessage());
            }
        }
    }
}
