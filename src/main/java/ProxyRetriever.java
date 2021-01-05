import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProxyRetriever {
    private List<String> proxyList = new ArrayList<>();
    private String URL_ONE = "https://api.proxyscrape.com/?request=getproxies&proxytype=http&timeout=9000&ssl=yes";
    private String URL_TWO = "https://www.proxy-list.download/api/v1/get?type=http";


    public List<String> getProxyList() throws IOException {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("message", "Your message")
                .build();
        Request request = new Request.Builder()
                .url(URL_ONE)
                .post(formBody)
                .build();
        Response response = client.newCall(request).execute();
        String tempProxyStringValue = response.body().string();
        proxyList = Arrays.asList(tempProxyStringValue.split("\r\n"));
        return proxyList;
    }
}
