package com.kony;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.TimerTask;

import org.apache.http.HttpHeaders;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ScheduledTasks extends TimerTask {

	Date now; // to display current time

	// Add your task here
	public void run() {
		now = new Date(); // initialize date
		System.out.println("Time is :" + now); // Display current time
	}

	public static String getClaimsToken() throws ClientProtocolException, IOException {
		String baseUri = "https://accounts.auth.konycloud.com/login";//"https://accounts.auth.qa-konycloud.com/login";
		String userId = "piyush.mittal@kony.com";
		String password = "Krsna@29";
		String claimsToken = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(baseUri +  "");///authService/accounts/login");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("userid", userId));
		nvps.add(new BasicNameValuePair("password", password));
		post.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse httpResponse = httpclient.execute(post);
		String response = EntityUtils.toString(httpResponse.getEntity());
		ObjectMapper objectMapper = new ObjectMapper();
		//System.out.println(response);
		JsonNode jsonRootNode = objectMapper.readTree(response);
		
		Iterator<Entry<String, JsonNode>> jsonFieldsIter = jsonRootNode.fields();
		while (jsonFieldsIter.hasNext()) {
			Entry<String, JsonNode> field = jsonFieldsIter.next();
			if (field.getKey().equals("claims_token")) {
				claimsToken = field.getValue().get("value").asText();
			}
		}
		return claimsToken;
	}

	public static void triggerPush(ArrayList<String> ksids, String message) throws ClientProtocolException, IOException {
		//https://mobilefabric-demo1.messaging.konycloud.com/api/v1
		//https://marketplaceassets.messaging.qa-konycloud.com
		String baseUri = "https://corpconnect.messaging.konycloud.com/api/v1/messages/push";
		String claimsToken = getClaimsToken();
		for (int i = 0; i < ksids.size(); i++) {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			String pushPayload = "{\"messageRequest\":{\"appId\":\"4144295a-040f-47ff-a51e-a1e5ba8d2759\",\"global\":{},\"messages\":{\"message\":{\"expiryTimestamp\":\"0\",\"overrideMessageId\":\"0\",\"startTimestamp\":\"0\",\"type\":\"PUSH\",\"subscribers\":{\"subscriber\":{\"ksid\":"+ksids.get(i)+"}},\"platformSpecificProps\":{},\"content\":{\"mimeType\":\"text/plain\",\"priorityService\":\"false\",\"data\":\""+message+"\"}}}}}";
			HttpPost post = new HttpPost(baseUri + "");// "/kpns/api/v1/messages/push");
			post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			post.setHeader("X-Kony-Authorization", claimsToken);
			post.setEntity(new StringEntity(pushPayload));
			CloseableHttpResponse httpResponse = httpclient.execute(post);

			String response = EntityUtils.toString(httpResponse.getEntity());
			//System.out.println(response);
		}

	}

	public static void main(String args[]) {
		try {
			ArrayList<String> ksids = new ArrayList<String>();
			//8966137396409634755
			ksids.add("8285657837679045502");
			ksids.add("8285658679695539674");
			ksids.add("8285629191625397758");
			
			triggerPush(ksids,"Testing");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
