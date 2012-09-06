package jgroove;

import jgroove.json.JsonPost;
import jgroove.json.JsonToken;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.security.MessageDigest;

import java.net.URL;
import java.net.URLConnection;

import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.MalformedURLException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import com.google.gson.Gson;

/**
 * Abstract class to interact with Grooveshark using some simple methods.
 * All methods can be used independetly.
 */
public abstract class JGroove {
    public static final String domain = "grooveshark.com";
    public static final String methodphp = "more.php"; //service.php
    public static final String streamphp = "stream.php";
    public static String protocol = "https"; //https
    public static String homeurl = protocol + "://"  + domain;
    public static String nosslhomeurl = "http" + "://"  + domain;
    public static  String apiurl = protocol + "://" +  domain;
    public static  String methodurl = apiurl + "/" + methodphp;
    public static  String nosslapiurl = "https" + "://" +  domain;
    public static  String nosslmethodurl = apiurl + "/" + methodphp;
    public static final String nameHTML = "htmlshark"; 
    public static final String nameJS = "jsqueue"; 
    public static final String versionHTML = "20120220"; //20110906 20120206
    public static final String versionJS = "20120220.01"; //20110906.02   20120220.02
    public static final String swfVersion = "20120124.01"; //20110912.02
    public static String password = new String();
    private static boolean needNewToken = false;
    private static Timer timer = new Timer();
    public static int newTokenTime = 960; //16mins
    public static final String uuid = UUID.randomUUID().toString();

    private static String sessionid = new String();
    private static String token = new String();

    /**
     * Returns the needed Grooveshark's SessionID to communicate with the
     * services and generate the secret key, it is also stored as attribute to
     * be used by the other methods.
     * @return Grooveshark's SessionID
     * @throws MalformedURLException
     * @throws IOException
     */
    public static String getSessionID() throws IOException {
        try {
        	
            URL url = new URL(nosslhomeurl);
        	
            URLConnection conn = url.openConnection();
            System.out.println(conn.getHeaderField("Set-Cookie"));
            JGroove.sessionid = conn.getHeaderField("Set-Cookie").split("=")[1].split(";")[0];
            return JGroove.sessionid;
        } catch (MalformedURLException badurl){
            System.out.println(badurl);
            System.out.println("Please, change home url definition");
            return null;
        }
    }

     /**
     * Returns the current Session ID without getting a new one
     * @return Session ID attribute
     */
    public static String getCurrentSessionID(){
        return JGroove.sessionid;
    }

    /**
     * Generates the SecretKey from the SessionID needed to get the
     * communication Token and returns it. If getSessionID() hasn't been called
     * already it will do it automagically
     * @return SessionID's SecretKey
     * @throws NoSuchAlgorithmException
     * @throws IOException
     */
    public static String getSecretKey() throws IOException {
        if (JGroove.sessionid.isEmpty()){
    		timer.schedule(new TimerTask() { //renew token every 16 mins
    			public void run() {
    				System.out.println("Starting timer");
    				needNewToken = true;
    			}
    		}, newTokenTime * 1000);
        	JGroove.getSessionID();
        }

        try {

            MessageDigest md5 = MessageDigest.getInstance("MD5");

            byte hash[] = md5.digest(JGroove.sessionid.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String digits = Integer.toHexString(0xFF & hash[i]);
                if (digits.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException badalgorithm) {
            System.out.println(badalgorithm);
            System.out.println("Please, change encryption algorithm in getSecretKey");
            return null;
        }
    }

    /**
     * Returns the needed Grooveshark's communication Token value to communicate
     * with the services, it is also stored as attribute to be used by the other
     * methods. It will automatically call getSecretKey().
     * @return Token's value
     * @throws NoSuchAlgorithmException
     * @throws MalformedURLException
     * @throws IOException
     */
    public static String getToken() throws IOException {
        HashMap<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("secretKey", JGroove.getSecretKey());
        //parameters.put("country", jgroove.json.JsonPost.country);
        String response = JGroove.callMethod(parameters, "getCommunicationToken");
        
        //System.out.println(response);
        JGroove.token = (new Gson().fromJson(response, JsonToken.class).result);
        return JGroove.token;
    }

     /**
     * Returns the current Token value without getting a new one
     * @return Token value attribute
     */
    public static String getCurrentToken(){
        return JGroove.sessionid;
    }

    /**
     * Generates the TokenKey using Grooveshark's hacked password and the method
     * to call to finally call a service correctly. If the Token hasn't been
     * already retrieved, then it will do it automagically.
     * @param method Service to call
     * @return The Token Key
     * @throws NoSuchAlgorithmException
     * @throws MalformedURLException
     * @throws IOException
     */
    public static String getTokenKey(String method) throws IOException {
        if (JGroove.token.isEmpty()||needNewToken) {
        	JGroove.getToken();
        	needNewToken = false;
        }

        StringBuilder randomhex = new StringBuilder();
        Random rand = new Random();
        while (6 > randomhex.length()) {
            randomhex.append("0123456789abcdef".charAt(rand.nextInt(16)));
        }
        String pass = method + ":" + JGroove.token + ":" + JGroove.password + ":" + randomhex.toString();

        try {

            MessageDigest sha1 = MessageDigest.getInstance("SHA1");
            byte hash[] = sha1.digest(pass.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hash.length; i++) {
                String digits = Integer.toHexString(0xFF & hash[i]);
                if (digits.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
            return (randomhex.toString() + hexString.toString());

        } catch (NoSuchAlgorithmException badalgorithm){
            System.out.println(badalgorithm);
            System.out.println("Please, change encryption algorithm in getTokenKey");
            return null;
        }
    }

    /**
     * Uses the token key and the session ID to get a response from Grooveshark
     * from the desired method in Json format. The parameters have to be
     * specified too. There's no need to retrieve a Session ID or Token before
     * calling this method.
     * @param method The Service to call
     * @param parameters Parameters need by the method in format (Kay:Value)
     * @return Json data in String format, interesting data use to be in the
     * "result" field
     * @throws MalformedURLException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     */
    public static String callMethod(HashMap<String, Object> parameters, String method)
            throws IOException {
    	
        JsonPost json = new JsonPost(parameters, method);
        if (!method.equalsIgnoreCase("getCommunicationToken")) {
            json.header.put("token", JGroove.getTokenKey(method));
        }
 
       
        String data = new Gson().toJson(json);
        System.out.println(data);
        try {
        	String mProtocol = "https";
        	URL url = null;
        	//if(method == "getSearchResultsEx" || method == "popularGetSongs" || method == "markSongDownloadedEx" || method == "getStreamKeyFromSongIDEx" || method == "getCommunicationToken")
        		url = new URL(nosslmethodurl);
        	//else
        	//	url = new URL(methodurl);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("Content-Type", "application/json");
            //if(method == "getSearchResultsEx" || method == "popularGetSongs" || method == "getCommunicationToken" || method == "initiateQueue" || method == "markSongComplete" || method == "getStreamKeyFromSongIDEx")
            //	conn.setRequestProperty("Referer", "http://grooveshark.com/");
            //else
            	conn.setRequestProperty("Referer", "http://grooveshark.com/");
            conn.setDoOutput(true);
            System.out.println("sending request to :" +url);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(data);
            wr.flush();
            wr.close();

            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            String response = "";
            while ((line = rd.readLine()) != null) {
                response += line;
            }
            rd.close();
           
            System.out.println("RESPONSE: "+response);
            return response;
        } catch (MalformedURLException badurl) {
            System.out.println(badurl);
            System.out.println("Please, change method url definition in JGroove");
            return null;
        }
    }
}
