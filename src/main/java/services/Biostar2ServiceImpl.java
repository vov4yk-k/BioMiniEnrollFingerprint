package services;

import com.google.gson.Gson;
import models.biostarAPI.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by Користувач on 21.06.2017.
 */
@Service
public class Biostar2ServiceImpl implements Biostar2Service {

    private LoggedInUser loggedInUser;
    private String deviceId;

    public LoggedInUser getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(LoggedInUser loggedInUser) {
        this.loggedInUser = loggedInUser;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public LoggedInUser authClient(UserLoginRequestData userLogin) {

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("https://api.biostar2.com/v2/login");

        try {
            StringEntity input = new StringEntity(gson.toJson(userLogin));
            input.setContentType("application/json");
            post.setEntity(input);

            //cookie
            HttpClientContext context = HttpClientContext.create();
            CookieStore cookieStore = new BasicCookieStore();
            context.setCookieStore(cookieStore);

            //posting
            HttpResponse response = client.execute(post, context);

            HttpEntity entity = response.getEntity();
            String responseBody = EntityUtils.toString(entity);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                loggedInUser = gson.fromJson(responseBody, LoggedInUser.class);
                List<Cookie> cookies = context.getCookieStore().getCookies();
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("bs-cloud-session-id")) {
                        loggedInUser.setCookie(cookie);
                    }
                }
            }
        } catch (IOException e) {
            return loggedInUser;
        }
        return loggedInUser;
    }

    @Override
    public VerifyFingerprintResult verifyFingerprint(VerifyFingerprintOption fingerprint) {

        VerifyFingerprintResult verifyFingerprintResult = null;

        if (loggedInUser == null) return verifyFingerprintResult;

        String query = "https://api.biostar2.com/v2/devices/"+deviceId+"/verify_fingerprint";

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(query);


        //set query
        StringEntity input = null;
        try {
            input = new StringEntity(gson.toJson(fingerprint));
        } catch (UnsupportedEncodingException e) {
            return verifyFingerprintResult;
        }
        input.setContentType("application/json");

        post.setEntity(input);


        //cookie
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",
                loggedInUser.getCookie().getValue());
        cookie.setDomain(loggedInUser.getCookie().getDomain());
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        HttpResponse response = null;
        String responseBody = null;
        try {
            //posting
            response = client.execute(post, context);
            //response processing
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
        } catch (IOException e) {
            return verifyFingerprintResult;
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            verifyFingerprintResult = gson.fromJson(responseBody,VerifyFingerprintResult.class);
        }else {
            verifyFingerprintResult = new VerifyFingerprintResult();
            verifyFingerprintResult.setMessage(response.getStatusLine().toString());
            System.out.println(responseBody);
        }
        return verifyFingerprintResult;
    }

    @Override
    public UpdateResponse updateUserFingerprint(long user_id, String t0, String t1) {
        UpdateUserFingerprintTemplateList userFingerprintTemplateList = new UpdateUserFingerprintTemplateList();
        FingerprintTemplate fingerprintTemplate = new FingerprintTemplate(t0,t1);
        FingerprintTemplate[] fingerprintTemplateArr = new FingerprintTemplate[1];
        fingerprintTemplateArr[0]=fingerprintTemplate;
        userFingerprintTemplateList.setFingerprint_template_list(fingerprintTemplateArr);
        return updateUserFingerprint(user_id,userFingerprintTemplateList);
    }

    @Override
    public GetUserFingerprintTemplateList userFingerprintTemplateList(int user_id){

        GetUserFingerprintTemplateList getUserFingerprintTemplateList = null;

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();

        java.net.URI uri = null;
        try {
            uri = new URIBuilder("https://api.biostar2.com/v2/users/"+user_id+"/fingerprint_templates")
                    .addParameter("limit", "15").addParameter("offset", "0")
                    .build();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        HttpGet get = new HttpGet(uri);

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",
                loggedInUser.getCookie().getValue());
        cookie.setDomain(loggedInUser.getCookie().getDomain());
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);


        //getting
        HttpResponse response = null;
        String responseBody = null;
        try {
            response = client.execute(get, context);
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
        }catch (IOException e){
            e.printStackTrace();
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            getUserFingerprintTemplateList = gson.fromJson(responseBody,
                    GetUserFingerprintTemplateList.class);
        }else{
            getUserFingerprintTemplateList = new GetUserFingerprintTemplateList();
            FingerprintTemplate[] userTemplates = new FingerprintTemplate[1];
            userTemplates[0] = new FingerprintTemplate(responseBody);
            getUserFingerprintTemplateList.setFingerprint_template_list(userTemplates);
            System.out.println(responseBody);
        }

        return  getUserFingerprintTemplateList;
    }

    @Override
    public AddResponse createNewUser(CreateUser createUser) {

        AddResponse addResponse = null;

        if (loggedInUser == null || loggedInUser.getCookie() == null) return addResponse;

        String query = "https://api.biostar2.com/v2/users";

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost(query);


        //set query
        StringEntity input = null;
        try {
            input = new StringEntity(gson.toJson(createUser));
        } catch (UnsupportedEncodingException e) {
            return addResponse;
        }
        input.setContentType("application/json");

        post.setEntity(input);


        //cookie
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",
                loggedInUser.getCookie().getValue());
        cookie.setDomain(loggedInUser.getCookie().getDomain());
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        HttpResponse response = null;
        String responseBody = null;
        try {
            //posting
            response = client.execute(post, context);
            //response processing
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
        } catch (IOException e) {
            return addResponse;
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            addResponse = gson.fromJson(responseBody,AddResponse.class);
        }else {
            addResponse = new AddResponse();
            addResponse.setMessage(response.getStatusLine().toString());
            System.out.println(responseBody);
        }
        return addResponse;
    }

    @Override
    public String scanFingerprint(){

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();

        HttpPost post = new HttpPost("https://api.biostar2.com/v2/devices/"+deviceId+"/scan_fingerprint");
        String bod = "{ \"enroll_quality\": 50, \"retrieve_raw_image\": true}";
        String responseBody = "";
        try {
            StringEntity input = new StringEntity(bod);
            input.setContentType("application/json");
            post.setEntity(input);

            //cookie
            CookieStore cookieStore = new BasicCookieStore();
            BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",
                    loggedInUser.getCookie().getValue());
            cookie.setDomain(loggedInUser.getCookie().getDomain());
            cookie.setPath("/");
            cookieStore.addCookie(cookie);

            HttpClientContext context = HttpClientContext.create();
            context.setCookieStore(cookieStore);

            //posting
            HttpResponse response = client.execute(post, context);

            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);

        } catch (IOException e) {
            return responseBody;
        }
        return responseBody;
    }

    public UpdateResponse updateUserFingerprint(long user_id, UpdateUserFingerprintTemplateList updateUserFingerprintTemplateList) {

        UpdateResponse updateResponse = null;

        /*if(template.getMessage() != Messages.Success)
            return new UpdateResponse(Messages.GetImageFail.toString());*/
        if(loggedInUser == null) return new UpdateResponse("Not authorized!");

        String query = "https://api.biostar2.com/v2/users/"+user_id+"/fingerprint_templates";

        Gson gson = new Gson();
        HttpClient client = new DefaultHttpClient();
        HttpPut put = new HttpPut(query);

        //set query
        StringEntity input = null;

        try {
            input = new StringEntity(gson.toJson(updateUserFingerprintTemplateList));
        } catch (UnsupportedEncodingException e) {
            return updateResponse;
        }
        input.setContentType("application/json");

        put.setEntity(input);

        //cookie
        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie cookie = new BasicClientCookie("bs-cloud-session-id",
                loggedInUser.getCookie().getValue());
        cookie.setDomain(loggedInUser.getCookie().getDomain());
        cookie.setPath("/");
        cookieStore.addCookie(cookie);

        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);

        HttpResponse response = null;
        String responseBody = null;
        try {
            //posting
            response = client.execute(put, context);
            //response processing
            HttpEntity entity = response.getEntity();
            responseBody = EntityUtils.toString(entity);
        } catch (IOException e) {
            return updateResponse;
        }

        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
            updateResponse = gson.fromJson(responseBody,UpdateResponse.class);
        }else {
            updateResponse = new UpdateResponse();
            updateResponse.setMessage(response.getStatusLine().toString());
            updateResponse.setStatus_code(responseBody);
        }
        return updateResponse;
    }

}
