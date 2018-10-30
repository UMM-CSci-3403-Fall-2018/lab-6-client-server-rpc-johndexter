package xrate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;


/**
 * Provide access to basic currency exchange rate services.
 * 
 * @author john schonebaum, zihan an
 */
public class ExchangeRateReader {

    public String baseURL;

    //https://github.com/UMM-CSci-Systems/Currency-exchange-RPC/blob/master/src/xrate/ExchangeRateReader.java
    private String accessKey;


    public ExchangeRateReader(String url) throws IOException {
        // TODO Your code here
        /*
         * DON'T DO MUCH HERE!
         * People often try to do a lot here, but the action is actually in
         * the two methods below. All you need to do here is store the
         * provided `baseURL` in a field so it will be accessible later.
         */

        this.baseURL = url;
        readAccessKeys();
    }
    /**
     * This reads the `fixer_io` access key from `etc/access_keys.properties`
     * and assigns it to the field `accessKey`.
     *
     * @throws IOException if there is a problem reading the properties file
     */
    private void readAccessKeys() throws IOException {
        Properties properties = new Properties();
        FileInputStream in = null;
        try {
            // Don't ch85ac0c3f21231a61ccc8acccac77b422ange this filename unless you know what you're doing.
            // It's crucial that we don't commit the file that contains the
            // (private) access keys. This file is listed in `.gitignore` so
            // it's safe to put keys there as we won't accidentally commit them.
            in = new FileInputStream("etc/access_keys.properties");
        } catch (FileNotFoundException e) {
            /*
             * If this error gets generated, make sure that you have the desired
             * properties file in your project's `etc` directory. You may need
             * to rename the file ending in `.sample` by removing that suffix.
             */
            System.err.println("Couldn't open etc/access_keys.properties; have you renamed the sample file?");
            throw (e);
        }
        properties.load(in);
        // This assumes we're using Fixer.io and that the desired access key is
        // in the properties file in the key labelled `fixer_io`.
        accessKey = properties.getProperty("fixer_io");
    }

    /**
     * Get the exchange rate for the specified currency against the base
     * currency (the Euro) on the specified date.
     * 
     * @param currencyCode
     *            the currency code for the desired currency
     * @param year
     *            the year as a four digit integer
     * @param month
     *            the month as an integer (1=Jan, 12=Dec)
     * @param day
     *            the day of the month as an integer
     * @return the desired exchange rate
     * @throws IOException
     */
    public float getExchangeRate(String currencyCode, int year, int month, int day) throws IOException {

        String toMonth = String.valueOf(month);
        String toDay = String.valueOf(day);

        if (month < 10) toMonth = '0' + toMonth;
        if (day < 10) toDay = '0' + toDay;

        baseURL = baseURL + year + '-' + toMonth + '-' + toDay + "?access_key=" + accessKey;
        URL url = new URL(baseURL);
        InputStream inputStream = url.openStream();
        //Reader reader = new BufferedReader(inputStream);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject)parser.parse(new InputStreamReader(inputStream));
        JsonObject rates = jsonObject.getAsJsonObject("rates");
        float rate = rates.get(currencyCode).getAsFloat();

        return rate;

    }

    /**
     * Get the exchange rate of the first specified currency against the second
     * on the specified date.
     * 
     * @param fromCurrency
     *            the currency code we're exchanging *from*
     * @param toCurrency
     *            the currency code we're exchanging *to*
     * @param year
     *            the year as a four digit integer
     * @param month
     *            the month as an integer (1=Jan, 12=Dec)
     * @param day
     *            the day of the month as an integer
     * @return the desired exchange rate
     * @throws IOException
     */
    public float getExchangeRate(String fromCurrency, String toCurrency, int year, int month, int day) throws IOException {
        // TODO Your code here
        String toMonth = String.valueOf(month);
        String toDay = String.valueOf(day);

        if (month < 10) toMonth = '0' + toMonth;
        if (day < 10) toDay = '0' + toDay;

        baseURL = baseURL + year + '-' + toMonth + '-' + toDay + "?access_key=" + accessKey;
        URL url = new URL(baseURL);
        InputStream inputStream = url.openStream();
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject)parser.parse(new InputStreamReader(inputStream));
        JsonObject rates = jsonObject.getAsJsonObject("rates");
        float rate01 = rates.get(fromCurrency).getAsFloat();
        float rate02 = rates.get(toCurrency).getAsFloat();

        return rate01/rate02;

    }
}