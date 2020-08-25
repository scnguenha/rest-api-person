package mz.co.scn.restapi.service;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.config.RequestConfig;
import cz.msebera.android.httpclient.client.methods.HttpDelete;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.client.methods.HttpPut;
import cz.msebera.android.httpclient.impl.client.HttpClients;

/**
 * Created by Sidónio Goenha on 06/08/2020.
 */
public class PersonService {
    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(8 * 1000).setConnectTimeout(15 * 1000).build();
    private static HttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(requestConfig).build();

    public static HttpResponse get(String url, String contentType) throws IOException {
        HttpGet httpGet = new HttpGet(getAbsoluteUrl(url));
        httpGet.addHeader("Host", "192.168.43.163");
        httpGet.addHeader("Content-Type", contentType);

        HttpResponse response = httpClient.execute(httpGet);

        return response;
    }

    public static HttpResponse post(String url, HttpEntity httpEntity, String contentType) throws IOException {
        HttpPost httpPost = new HttpPost(getAbsoluteUrl(url));
        httpPost.addHeader("Host", "192.168.43.163:8180");
        httpPost.addHeader("Content-Type", contentType);
        httpPost.setEntity(httpEntity);

        HttpResponse response = httpClient.execute(httpPost);

        return response;
    }

    public static HttpResponse put(String url, HttpEntity httpEntity, String contentType) throws IOException {
        HttpPut httpPut = new HttpPut(getAbsoluteUrl(url));
        httpPut.addHeader("Host", "192.168.43.163:8180");
        httpPut.addHeader("Content-Type", contentType);
        httpPut.setEntity(httpEntity);

        HttpResponse response = httpClient.execute(httpPut);

        return response;
    }

    public static HttpResponse delete(String url, String contentType) throws IOException {
        HttpDelete httpDelete = new HttpDelete(getAbsoluteUrl(url));
        httpDelete.addHeader("Host", "192.168.43.163:8180");
        httpDelete.addHeader("Content-Type", contentType);

        HttpResponse response = httpClient.execute(httpDelete);

        return response;
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return "http://192.168.43.163:8180/api/" + relativeUrl;
    }
}
