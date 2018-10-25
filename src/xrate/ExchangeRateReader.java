package xrate;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Provide access to basic currency exchange rate services.
 * 
 * @author PUT YOUR TEAM NAME HERE
 */
public class ExchangeRateReader {

    public String baseURL;

    /**
     * Construct an exchange rate reader using the given base URL. All requests
     * will then be relative to that URL. If, for example, your source is Xavier
     * Finance, the base URL is http://api.finance.xaviermedia.com/api/ Rates
     * for specific days will be constructed from that URL by appending the
     * year, month, and day; the URL for 25 June 2010, for example, would be
     * http://api.finance.xaviermedia.com/api/2010/06/25.xml
     * 
     * @param url
     *            the base URL for requests
     */
    public ExchangeRateReader(String url) throws MalformedURLException {
        // TODO Your code here
        /*
         * DON'T DO MUCH HERE!
         * People often try to do a lot here, but the action is actually in
         * the two methods below. All you need to do here is store the
         * provided `baseURL` in a field so it will be accessible later.
         */

        this.baseURL = url;
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
        // TODO Your code here
        String toMonth = String.valueOf(month);
        String toDay = String.valueOf(day);

        if (month < 10) toMonth = '0' + toMonth;
        if (day < 10) toDay = '0' + toDay;

        baseURL = baseURL + year + '-' + toMonth + '-' + toDay;
        URL url = new URL(baseURL);
        InputStream inputStream = url.openStream();
        //Reader reader = new BufferedReader(inputStream);
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = (JsonObject)parser.parse(new InputStreamReader(inputStream));
        JsonObject rates = jsonObject.getAsJsonObject("rates");
        float rate = rates.get(currencyCode).getAsFloat();


        return rate;
        //throw new UnsupportedOperationException();
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
    public float getExchangeRate(
            String fromCurrency, String toCurrency,
            int year, int month, int day) throws IOException {
        // TODO Your code here
        throw new UnsupportedOperationException();
    }
}