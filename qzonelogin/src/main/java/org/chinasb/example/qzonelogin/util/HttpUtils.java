package org.chinasb.example.qzonelogin.util;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
    
    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    public static byte[] request(HttpUriRequest httpUriRequest) throws Exception {
        // Create a custom response handler
        ResponseHandler<byte[]> responseHandler = new ResponseHandler<byte[]>() {
            @Override
            public byte[] handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                int status = httpResponse.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = httpResponse.getEntity();
                    return entity == null ? null : EntityUtils.toByteArray(entity);
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            }
        };
        return httpclient.execute(httpUriRequest, responseHandler);
    }
}
