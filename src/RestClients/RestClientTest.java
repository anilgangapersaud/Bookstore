package RestClients;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Client samples to test the REST web services
 * When testing credentials must match a Partner role.
 * @author agang
 *
 */
public class RestClientTest {

	private final CloseableHttpClient httpClient = HttpClients.createDefault();
	
	public static void main(String[] args) throws Exception {
		
		RestClientTest obj = new RestClientTest();
		
		try {
			System.out.println("Testing 1 - Send Http GET request for getOrders");
			obj.restOrders();
			System.out.println("Testing 2 - Send Http GET request for Product Catalog");
			obj.restCatalog();
		} finally {
			obj.close();
		}
	}
	
	private void close() throws IOException {
		httpClient.close();
	}
	
	/** A test for project spec C
	 * 
	 * @author Anil
	 * @throws Exception
	 */
	private void restOrders() throws Exception {
		
		HttpGet request = new HttpGet("https://bookstore--56226.mybluemix.net/rest/getOrdersByPartNumber?bid=b004&username=Partner&password=destruction");
		
		try(CloseableHttpResponse response = httpClient.execute(request)) {
			
			System.out.println(response.getStatusLine().toString());
			
			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println(headers);
			
			if (entity != null) {
				
				String result = EntityUtils.toString(entity);
				System.out.println(result);
			}
		}
	}
	
	/** A test for project spec B
	 * 
	 * @author Anil
	 * @throws Exception
	 */
	private void restCatalog() throws Exception {
	HttpGet request = new HttpGet("https://bookstore--56226.mybluemix.net/rest/getProductInfo?bid=b004&username=Partner&password=destruction");
	
		try(CloseableHttpResponse response = httpClient.execute(request)) {
			
			System.out.println(response.getStatusLine().toString());
			
			HttpEntity entity = response.getEntity();
			Header headers = entity.getContentType();
			System.out.println(headers);
			
			if (entity != null) {
				
				String result = EntityUtils.toString(entity);
				System.out.println(result);
			}
		}
		
		
		
	}
}
