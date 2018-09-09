package testing;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpPostRequest {
    public static void post(String json, String url) throws Exception {
      
        StringEntity entity = new StringEntity(json,
                ContentType.APPLICATION_FORM_URLENCODED);

        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost request = new HttpPost(url);
        request.setEntity(entity);

        HttpResponse response = httpClient.execute(request);
        System.out.println(response.getStatusLine().getStatusCode());
    }
}