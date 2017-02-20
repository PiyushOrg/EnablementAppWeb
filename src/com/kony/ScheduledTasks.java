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
		String baseUri = "https://accounts.auth.konycloud.com/login";
		String userId = "mukul.mudgil@kony.com";
		String password = "Parm@2802";
		String claimsToken = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = new HttpPost(baseUri + "");// "/authService/accounts/login");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("userid", userId));
		nvps.add(new BasicNameValuePair("password", password));
		post.setEntity(new UrlEncodedFormEntity(nvps));
		CloseableHttpResponse httpResponse = httpclient.execute(post);
		String response = EntityUtils.toString(httpResponse.getEntity());
		ObjectMapper objectMapper = new ObjectMapper();
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

	public static void triggerPush(ArrayList<String> ksids) throws ClientProtocolException, IOException {
		String baseUri = "https://mobilefabric-demo.messaging.konycloud.com/api/v1/messages/push";
		String claimsToken = getClaimsToken();
		for (int i = 0; i < ksids.size(); i++) {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			String pushPayload = "{\"messageRequest\":{\"appId\":\"47be03c7-840b-44a6-a161-a29e8903bdbd\",\"global\":{},\"messages\":{\"message\":{\"expiryTimestamp\":\"0\",\"overrideMessageId\":\"0\",\"startTimestamp\":\"0\",\"type\":\"PUSH\",\"subscribers\":{\"subscriber\":{\"ksid\":"+ksids.get(i)+"}},\"platformSpecificProps\":{},\"content\":{\"mimeType\":\"text/plain\",\"priorityService\":\"false\",\"data\":\"Test Message\"}}}}}";
			HttpPost post = new HttpPost(baseUri + "");// "/kpns/api/v1/messages/push");
			post.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");
			post.setHeader("X-Kony-Authorization", claimsToken);
			post.setEntity(new StringEntity(pushPayload));
			CloseableHttpResponse httpResponse = httpclient.execute(post);

			String response = EntityUtils.toString(httpResponse.getEntity());
			System.out.println(response);
		}

	}

	public static void main(String args[]) {
		try {
			ArrayList<String> ksids = new ArrayList<String>();
			ksids.add("6860722726240578234");
			triggerPush(ksids);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
