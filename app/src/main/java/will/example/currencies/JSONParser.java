package will.example.currencies;

import android.annotation.SuppressLint;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;


public class JSONParser {

    static InputStream sInputStream = null;
    static JSONObject sReturnJSONObject = null;
    static String sRawJSONString = "";

    public JSONParser() {}

    @SuppressLint("LongLogTag")
    public JSONObject getJSONFromURL (String url) {

        //attempt to get response form server
        try{

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);

            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            sInputStream = httpEntity.getContent();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Read stream into string-builder
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    sInputStream, "iso-8859-1"), 8);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            sInputStream.close();
            sRawJSONString = stringBuilder.toString();
            } catch (Exception e) {
            Log.e("Error reading from Buffer: " + e.toString(),
            this.getClass().getSimpleName());
        }

        try {
            sReturnJSONObject = new JSONObject(sRawJSONString);
        } catch (JSONException e) {
            Log.e("Parser", "Error when parsing data " + e.toString());
        }
        //return json object
        return sReturnJSONObject;
        }
    }

