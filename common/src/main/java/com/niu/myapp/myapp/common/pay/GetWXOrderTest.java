//package com.niu.myapp.myapp.common.pay;//package com.sdu.didi.util.pay;
//
//import android.app.Activity;
//
//import org.apache.http.HttpResponse;
//import org.apache.http.HttpStatus;
//import org.apache.http.HttpVersion;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.conn.ClientConnectionManager;
//import org.apache.http.conn.scheme.PlainSocketFactory;
//import org.apache.http.conn.scheme.Scheme;
//import org.apache.http.conn.scheme.SchemeRegistry;
//import org.apache.http.conn.ssl.SSLSocketFactory;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.HttpParams;
//import org.apache.http.params.HttpProtocolParams;
//import org.apache.http.protocol.HTTP;
//import org.apache.http.util.EntityUtils;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.IOException;
//import java.net.Socket;
//import java.net.UnknownHostException;
//import java.security.KeyManagementException;
//import java.security.KeyStore;
//import java.security.KeyStoreException;
//import java.security.NoSuchAlgorithmException;
//import java.security.UnrecoverableKeyException;
//import java.security.cert.X509Certificate;
//
//import javax.net.ssl.SSLContext;
//import javax.net.ssl.TrustManager;
//import javax.net.ssl.X509TrustManager;
//
///**
// * 微信支付之前获取  订单id的测试类
// * Created by niuxiaowei on 2015/12/24.
// */
//public class GetWXOrderTest {
//
//    public static interface Listener{
//        void onJson(JSONObject json);
//    }
//
//    public static void getOrder(final Activity activity){
//        GetWXOrderTest.getOrder(new Listener() {
//            @Override
//            public void onJson(final JSONObject json) {
//                activity.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (json == null) {
//                            return;
//                        }
//                        WXPay pay = new WXPay(activity);
//                        WXPay.WXPayRequest req = new WXPay.WXPayRequest();
//                        try {
//                            req.appId = json.getString("appid");
//                            req.partnerId = json.getString("partnerid");
//                            req.prepayId = json.getString("prepayid");
//                            req.nonceStr = json.getString("noncestr");
//                            req.timeStamp = json.getString("timestamp");
//                            req.packageValue = json.getString("package");
//                            req.sign = json.getString("sign");
//                            req.extData = "app data"; // optional
//                            pay.pay(req, null);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                });
//            }
//        });
//    }
//
//    public static void getOrder(final Listener listener) {
//
//        new Thread(){
//
//            @Override
//            public void run() {
//                super.run();
//                String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
//
//                HttpClient httpClient = getNewHttpClient();
//                HttpGet httpGet = new HttpGet(url);
//
//                try {
//                    HttpResponse resp = httpClient.execute(httpGet);
//                    if (resp.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//                        return ;
//                    }
//
//                    byte[] buf = EntityUtils.toByteArray(resp.getEntity());
//                    if (buf != null && buf.length > 0) {
//                        String content = new String(buf);
//                        JSONObject json = new JSONObject(content);
//                        if(listener != null){
//                            listener.onJson(json);
//                        }
//                        return ;
//                    }
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//
//        }.start();
//
//    }
//
//    private static HttpClient getNewHttpClient() {
//        try {
//            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
//            trustStore.load(null, null);
//
//            SSLSocketFactory sf = new SSLSocketFactoryEx(trustStore);
//            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
//
//            HttpParams params = new BasicHttpParams();
//            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
//            HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
//
//            SchemeRegistry registry = new SchemeRegistry();
//            registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
//            registry.register(new Scheme("https", sf, 443));
//
//            ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
//
//            return new DefaultHttpClient(ccm, params);
//        } catch (Exception e) {
//            return new DefaultHttpClient();
//        }
//    }
//
//    private static class SSLSocketFactoryEx extends SSLSocketFactory {
//
//        SSLContext sslContext = SSLContext.getInstance("TLS");
//
//        public SSLSocketFactoryEx(KeyStore truststore) throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException {
//            super(truststore);
//
//            TrustManager tm = new X509TrustManager() {
//
//                public X509Certificate[] getAcceptedIssuers() {
//                    return null;
//                }
//
//                @Override
//                public void checkClientTrusted(X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
//                }
//
//                @Override
//                public void checkServerTrusted(X509Certificate[] chain,	String authType) throws java.security.cert.CertificateException {
//                }
//            };
//
//            sslContext.init(null, new TrustManager[] { tm }, null);
//        }
//
//        @Override
//        public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException, UnknownHostException {
//            return sslContext.getSocketFactory().createSocket(socket, host,	port, autoClose);
//        }
//
//        @Override
//        public Socket createSocket() throws IOException {
//            return sslContext.getSocketFactory().createSocket();
//        }
//    }
//}
