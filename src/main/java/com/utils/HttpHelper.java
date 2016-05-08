/*
 * Copyright (c) 2011 Shanda Corporation. All rights reserved.
 *
 * Created on 2011-9-8.
 */

package com.utils;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.protocol.ProtocolSocketFactory;
import org.apache.commons.lang.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Http工具类.
 *
 * @author James Shen
 */
public final class HttpHelper {

    public static final String METHOD_GET = "GET";
    public static final String METHOD_POST = "POST";

    private static MultiThreadedHttpConnectionManager manager = null;

    private static int DEFAULT_CONNECTION_TIMEOUT = 5000;
    private static int DEFAULT_SOCKET_TIMEOUT = 10000;
    private static int DEFAULT_MAX_CONNECTION_PER_HOST = 5;
    private static int DEFAULT_MAX_TOTAL_CONNECTIONS = 50;

    private static final String DEFAULT_CHARSET = "UTF-8";

    private static boolean initialed = false;

    private HttpHelper() {
    }

    public static void init() {
        if (manager == null) {
            manager = new MultiThreadedHttpConnectionManager();
            manager.getParams().setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
            manager.getParams().setSoTimeout(DEFAULT_SOCKET_TIMEOUT);
            manager.getParams().setDefaultMaxConnectionsPerHost(DEFAULT_MAX_CONNECTION_PER_HOST);
            manager.getParams().setMaxTotalConnections(DEFAULT_MAX_TOTAL_CONNECTIONS);
            initialed = true;
        }
    }

    public static void init(int connectionTimeout, int SocketTimeout, int maxConnectionPerHost, int maxTotalConnections) {
        DEFAULT_CONNECTION_TIMEOUT = connectionTimeout;
        DEFAULT_SOCKET_TIMEOUT = SocketTimeout;
        DEFAULT_MAX_CONNECTION_PER_HOST = maxConnectionPerHost;
        DEFAULT_MAX_TOTAL_CONNECTIONS = maxTotalConnections;

        init();
    }

    public static void close() {
        if (manager != null) {
            manager.shutdown();
            manager = null;
            initialed = false;
        }
    }

    public static String fetchStringByGet(String url) {
        return getBody(get(url, null, null, null, null, null, 0, 0, 0));
    }

    public static String fetchStringByGet(String url, int connectionTimeOut, int socketTimeOut) {
        return getBody(get(url, null, null, null, null, null, connectionTimeOut, socketTimeOut, 0));
    }

    public static String fetchStringByGet(String url, Map<String, Object> params, String charset, int connectionTimeOut, int socketTimeOut) {
        return getBody(get(url, null, null, parseMap(params), charset, charset, connectionTimeOut, socketTimeOut, 0));
    }

    public static String fetchGBKStringByGet(String url, Map<String, Object> params, String charset, int connectionTimeOut, int socketTimeOut) {
        return getBody(get(url, null, null, parseMap(params), charset, "GBK", connectionTimeOut, socketTimeOut, 0));
    }

    public static String fetchUTF8StringByGet(String url, Map<String, Object> params, String charset, int connectionTimeOut, int socketTimeOut) {
        return getBody(get(url, null, null, parseMap(params), charset, "UTF-8", connectionTimeOut, socketTimeOut, 0));
    }

    public static HttpResponse get(String url) {
        return get(url, null, null, null, null, null, 0, 0, 0);
    }

    public static HttpResponse get(String url, int connectionTimeOut, int socketTimeOut) {
        return get(url, null, null, null, null, null, connectionTimeOut, socketTimeOut, 0);
    }

    public static HttpResponse get(String url, Map<String, Object> params, String charset, int connectionTimeOut, int socketTimeOut) {
        return get(url, null, null, parseMap(params), charset, charset, connectionTimeOut, socketTimeOut, 0);
    }

    public static HttpResponse get(String url, Cookie[] cookies, NameValuePair[] heads, NameValuePair[] params, String reqCharset, String resCharset, int connectionTimeOut, int socketTimeOut, int retryCount) {
        return get(url, null, 0, cookies, heads, params, reqCharset, resCharset, connectionTimeOut, socketTimeOut, retryCount);
    }

    public static HttpResponse get(String url, String proxyHost, int proxyPort, Cookie[] cookies, NameValuePair[] heads, NameValuePair[] params, String reqCharset, String resCharset, int connectionTimeOut, int socketTimeOut, int retryCount) {
        HttpResponse httpResponse = new HttpResponse();
        HttpMethod method = null;
        BufferedReader br = null;
        try {
            if (!initialed) {
                init();
            }

            // https
            if (url.length() > 5 && url.substring(0, 5).equalsIgnoreCase("https")) {
                ProtocolSocketFactory psf = new EasySSLProtocolSocketFactory();
                Protocol.registerProtocol("https", new Protocol("https", psf, 443));
            }

            HttpClient client = new HttpClient(manager);
            method = new GetMethod(url);

            // proxy
            if (proxyHost != null) {
                client.getHostConfiguration().setProxy(proxyHost, proxyPort);
            }

            // retry
            if (retryCount > 0) {
                method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(retryCount, false));
            }

            method.setFollowRedirects(true);

            // timeout
            if (connectionTimeOut > 0) {
                client.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut);
            }
            if (socketTimeOut > 0) {
                method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, socketTimeOut);
            }

            // cookies
            if (cookies != null) {
                client.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
                HttpState initialState = new HttpState();
                initialState.addCookies(cookies);
                client.setState(initialState);
            }

            // headers
            if (heads != null) {
                for (NameValuePair head : heads) {
                    method.addRequestHeader(head.getName(), head.getValue());
                }
            }

            // params
            String queryString = getQueryString(params, reqCharset);
            if (StringUtils.isNotBlank(queryString)) {
                method.setQueryString(queryString);
            }

            // executeMethod
            int statusCode = client.executeMethod(method);

            httpResponse.setStatusCode(statusCode);
            httpResponse.setStatusText(method.getStatusText());

            // cookies
            httpResponse.setCookies(client.getState().getCookies());

            // headers
            Header[] headers = method.getResponseHeaders();
            for (Header header : headers) {
                String oldHeadValue = httpResponse.getHead(header.getName());
                if (oldHeadValue != null) {
                    httpResponse.setHead(header.getName(), oldHeadValue + "; " + header.getValue());
                } else {
                    httpResponse.setHead(header.getName(), header.getValue());
                }
            }

            // body
            if (StringUtils.isBlank(resCharset)) {
                resCharset = DEFAULT_CHARSET;
            }
            br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), resCharset));
            StringBuilder sb = new StringBuilder();
            String inputLine = br.readLine();
            if (inputLine != null) {
                sb.append(inputLine);
                while ((inputLine = br.readLine()) != null) {
                    sb.append("\n");
                    sb.append(inputLine);
                }
            }
            httpResponse.setBody(sb.toString());

            // URI
            httpResponse.setURI(method.getURI().toString());
        } catch (Exception e) {
            httpResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            httpResponse.setStatusText(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                } finally {
                    br = null;
                }
            }
            if (connectionTimeOut > 0) {
                resetConnectionTimeout();
            }
            if (method != null) {
                method.releaseConnection();
            }
        }
        return httpResponse;
    }

    public static String fetchStringByPost(String url) {
        return getBody(post(url, null, null, null, null, null, 0, 0, 0));
    }

    public static String fetchStringByPost(String url, int connectionTimeOut, int socketTimeOut) {
        return getBody(post(url, null, null, null, null, null, connectionTimeOut, socketTimeOut, 0));
    }

    public static String fetchStringByPost(String url, Map<String, Object> params, String charset, int connectionTimeOut, int socketTimeOut) {
        return getBody(post(url, null, null, parseMap(params), charset, charset, connectionTimeOut, socketTimeOut, 0));
    }

    public static String fetchGBKStringByPost(String url, Map<String, Object> params, String charset, int connectionTimeOut, int socketTimeOut) {
        return getBody(post(url, null, null, parseMap(params), charset, "GBK", connectionTimeOut, socketTimeOut, 0));
    }

    public static String fetchUTF8StringByPost(String url, Map<String, Object> params, String charset, int connectionTimeOut, int socketTimeOut) {
        return getBody(post(url, null, null, parseMap(params), charset, "UTF-8", connectionTimeOut, socketTimeOut, 0));
    }

    public static HttpResponse post(String url) {
        return post(url, null, null, null, null, null, 0, 0, 0);
    }

    public static HttpResponse post(String url, int connectionTimeOut, int socketTimeOut) {
        return post(url, null, null, null, null, null, connectionTimeOut, socketTimeOut, 0);
    }

    public static HttpResponse post(String url, Map<String, Object> params, String charset, int connectionTimeOut, int socketTimeOut) {
        return post(url, null, null, parseMap(params), charset, charset, connectionTimeOut, socketTimeOut, 0);
    }

    public static HttpResponse post(String url, Cookie[] cookies, NameValuePair[] heads, NameValuePair[] params, String reqCharset, String resCharset, int connectionTimeOut, int socketTimeOut, int retryCount) {
        return post(url, null, 0, cookies, heads, params, reqCharset, resCharset, connectionTimeOut, socketTimeOut, retryCount);
    }

    public static HttpResponse post(String url, String proxyHost, int proxyPort, Cookie[] cookies, NameValuePair[] heads, NameValuePair[] params, String reqCharset, String resCharset, int connectionTimeOut, int socketTimeOut, int retryCount) {
        HttpResponse httpResponse = new HttpResponse();
        PostMethod method = null;
        BufferedReader br = null;
        try {
            if (!initialed) {
                init();
            }

            // https
            if (url.length() > 5 && url.substring(0, 5).equalsIgnoreCase("https")) {
                ProtocolSocketFactory psf = new EasySSLProtocolSocketFactory();
                Protocol.registerProtocol("https", new Protocol("https", psf, 443));
            }

            HttpClient client = new HttpClient(manager);
            method = new PostMethod(url);

            // proxy
            if (proxyHost != null) {
                client.getHostConfiguration().setProxy(proxyHost, proxyPort);
            }

            if (StringUtils.isBlank(reqCharset)) {
                reqCharset = DEFAULT_CHARSET;
            }
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, reqCharset);

            // retry
            if (retryCount > 0) {
                method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(retryCount, false));
            }

            // timeout
            if (connectionTimeOut > 0) {
                client.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut);
            }
            if (socketTimeOut > 0) {
                method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, socketTimeOut);
            }

            // cookies
            if (cookies != null) {
                client.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
                HttpState initialState = new HttpState();
                initialState.addCookies(cookies);
                client.setState(initialState);
            }

            // headers
            if (heads != null) {
                for (NameValuePair head : heads) {
                    method.addRequestHeader(head.getName(), head.getValue());
                }
            }

            // params
            if (params != null) {
                method.setRequestBody(params);
            }

            // executeMethod
            int statusCode = client.executeMethod(method);

            // redirect
            if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                Header locationHeader = method.getResponseHeader("location");
                if (locationHeader != null) {
                    return post(locationHeader.getValue(), cookies, heads, params, reqCharset, resCharset, connectionTimeOut, socketTimeOut, retryCount);
                }
            }

            httpResponse.setStatusCode(statusCode);
            httpResponse.setStatusText(method.getStatusText());

            // cookies
            httpResponse.setCookies(client.getState().getCookies());

            // headers
            Header[] headers = method.getResponseHeaders();
            for (Header header : headers) {
                String oldHeadValue = httpResponse.getHead(header.getName());
                if (oldHeadValue != null) {
                    httpResponse.setHead(header.getName(), oldHeadValue + "; " + header.getValue());
                } else {
                    httpResponse.setHead(header.getName(), header.getValue());
                }
            }

            // body
            if (StringUtils.isBlank(resCharset)) {
                resCharset = method.getResponseCharSet();
            }
            br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), resCharset));
            StringBuilder sb = new StringBuilder();
            String inputLine = br.readLine();
            if (inputLine != null) {
                sb.append(inputLine);
                while ((inputLine = br.readLine()) != null) {
                    sb.append("\n");
                    sb.append(inputLine);
                }
            }
            httpResponse.setBody(sb.toString());

            // URI
            httpResponse.setURI(method.getURI().toString());
        } catch (Exception e) {
            httpResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            httpResponse.setStatusText(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                } finally {
                    br = null;
                }
            }
            if (connectionTimeOut > 0) {
                resetConnectionTimeout();
            }
            if (method != null) {
                method.releaseConnection();
            }
        }
        return httpResponse;
    }

    public static HttpResponse postEntity(String url, Cookie[] cookies, NameValuePair[] heads, String entity, String reqCharset, String resCharset, int connectionTimeOut, int socketTimeOut, int retryCount) {
        return postEntity(url, cookies, heads, entity, PostMethod.FORM_URL_ENCODED_CONTENT_TYPE, reqCharset, resCharset, connectionTimeOut, socketTimeOut, retryCount);
    }

    public static HttpResponse postEntity(String url, Cookie[] cookies, NameValuePair[] heads, String entity, String contentType, String reqCharset, String resCharset, int connectionTimeOut, int socketTimeOut, int retryCount) {
        return postEntity(url, null, 0, cookies, heads, entity, contentType, reqCharset, resCharset, connectionTimeOut, socketTimeOut, retryCount);
    }

    public static HttpResponse postEntity(String url, String proxyHost, int proxyPort, Cookie[] cookies, NameValuePair[] heads, String entity, String contentType, String reqCharset, String resCharset, int connectionTimeOut, int socketTimeOut, int retryCount) {
        HttpResponse httpResponse = new HttpResponse();
        PostMethod method = null;
        BufferedReader br = null;
        try {
            if (!initialed) {
                init();
            }

            // https
            if (url.length() > 5 && url.substring(0, 5).equalsIgnoreCase("https")) {
                ProtocolSocketFactory psf = new EasySSLProtocolSocketFactory();
                Protocol.registerProtocol("https", new Protocol("https", psf, 443));
            }

            HttpClient client = new HttpClient(manager);
            method = new PostMethod(url);

            // proxy
            if (proxyHost != null) {
                client.getHostConfiguration().setProxy(proxyHost, proxyPort);
            }

            if (StringUtils.isBlank(reqCharset)) {
                reqCharset = DEFAULT_CHARSET;
            }
            method.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, reqCharset);

            // retry
            if (retryCount > 0) {
                method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(retryCount, false));
            }

            // timeout
            if (connectionTimeOut > 0) {
                client.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut);
            }
            if (socketTimeOut > 0) {
                method.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, socketTimeOut);
            }

            // cookies
            if (cookies != null) {
                client.getParams().setCookiePolicy(CookiePolicy.RFC_2109);
                HttpState initialState = new HttpState();
                initialState.addCookies(cookies);
                client.setState(initialState);
            }

            // headers
            if (heads != null) {
                for (NameValuePair head : heads) {
                    method.addRequestHeader(head.getName(), head.getValue());
                }
            }

            // entity
            RequestEntity requestEntity = new StringRequestEntity(entity, contentType, reqCharset);
            method.setRequestEntity(requestEntity);
            method.setContentChunked(false);

            // executeMethod
            int statusCode = client.executeMethod(method);

            // redirect
            if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY) {
                Header locationHeader = method.getResponseHeader("location");
                if (locationHeader != null) {
                    return postEntity(locationHeader.getValue(), cookies, heads, entity, contentType, reqCharset, resCharset, connectionTimeOut, socketTimeOut, retryCount);
                }
            }

            httpResponse.setStatusCode(statusCode);
            httpResponse.setStatusText(method.getStatusText());

            // cookies
            httpResponse.setCookies(client.getState().getCookies());

            // headers
            Header[] headers = method.getResponseHeaders();
            for (Header header : headers) {
                String oldHeadValue = httpResponse.getHead(header.getName());
                if (oldHeadValue != null) {
                    httpResponse.setHead(header.getName(), oldHeadValue + "; " + header.getValue());
                } else {
                    httpResponse.setHead(header.getName(), header.getValue());
                }
            }

            // body
            if (StringUtils.isBlank(resCharset)) {
                resCharset = method.getResponseCharSet();
            }
            br = new BufferedReader(new InputStreamReader(method.getResponseBodyAsStream(), resCharset));
            StringBuilder sb = new StringBuilder();
            String inputLine = br.readLine();
            if (inputLine != null) {
                sb.append(inputLine);
                while ((inputLine = br.readLine()) != null) {
                    sb.append("\n");
                    sb.append(inputLine);
                }
            }
            httpResponse.setBody(sb.toString());

            // URI
            httpResponse.setURI(method.getURI().toString());
        } catch (Exception e) {
            httpResponse.setStatusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            httpResponse.setStatusText(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                } finally {
                    br = null;
                }
            }
            if (connectionTimeOut > 0) {
                resetConnectionTimeout();
            }
            if (method != null) {
                method.releaseConnection();
            }
        }
        return httpResponse;
    }

    public static HttpResponse process(String method, String url, NameValuePair[] params, String charset, int connectionTimeOut, int socketTimeOut, int retryCount) {
        return process(method, url, null, null, params, charset, connectionTimeOut, socketTimeOut, retryCount);
    }

    public static HttpResponse process(String method, String url, Cookie[] cookies, NameValuePair[] heads, NameValuePair[] params, String charset, int connectionTimeOut, int socketTimeOut, int retryCount) {
        if (METHOD_GET.equalsIgnoreCase(method)) {
            return get(url, cookies, heads, params, charset, charset, connectionTimeOut, socketTimeOut, retryCount);
        } else if (METHOD_POST.equalsIgnoreCase(method)) {
            return post(url, cookies, heads, params, charset, charset, connectionTimeOut, socketTimeOut, retryCount);
        }
        return null;
    }

    /**
     * 从url中获取queryString.
     *
     * @param url
     * @return
     */
    public static String getQueryString(String url) {
        if (StringUtils.isNotBlank(url)) {
            int begin = url.indexOf("?");
            if (begin != -1) {
                return url.substring(begin + 1);
            }
        }
        return "";
    }

    /**
     * 用params参数对象生成queryString, 并用charset编码.
     *
     * @param params
     * @param charset
     * @return
     */
    public static String getQueryString(NameValuePair[] params, String charset) {
        if (params != null) {
            StringBuilder sb = new StringBuilder();
            for (NameValuePair param : params) {
                if (param != null) {
                    sb.append("&").append(encode(param.getName(), charset)).append("=").append(encode(param.getValue(), charset));
                }
            }
            if (sb.length() > 0) {
                sb.deleteCharAt(0);
            }
            return sb.toString();
        }
        return "";
    }

    /**
     * 用queryString生成params参数对象, 并用charset解码.
     *
     * @param queryString
     * @param charset
     * @return
     */
    public static NameValuePair[] getNameValuePair(String queryString, String charset) {
        NameValuePair[] params = null;
        if (StringUtils.isNotBlank(queryString)) {
            String[] parameters = queryString.split("&");
            params = new NameValuePair[parameters.length];
            int i = 0;
            for (String parameter : parameters) {
                String[] keyAndValue = parameter.split("=");
                if (keyAndValue.length != 2) {
                    continue;
                }
                params[i++] = new NameValuePair(decode(keyAndValue[0], charset), decode(keyAndValue[1], charset));
            }
        }
        return params;
    }

    /**
     * 参数对象转换.
     *
     * @param map
     * @return
     */
    public static NameValuePair[] parseMap(Map<String, Object> map) {
        NameValuePair[] params = null;
        if (map != null) {
            params = new NameValuePair[map.size()];
            int i = 0;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                params[i++] = new NameValuePair(entry.getKey(), String.valueOf(entry.getValue()));
            }
        }
        return params;
    }

    /**
     * 参数对象转换(会丢失name重复的value).
     *
     * @param params
     * @return
     */
    public static Map<String, Object> parseNameValuePair(NameValuePair[] params) {
        Map<String, Object> map = null;
        if (params != null) {
            map = new HashMap<String, Object>();
            for (NameValuePair param : params) {
                if (param != null) {
                    map.put(param.getName(), param.getValue());
                }
            }
        }
        return map;
    }

    /**
     * http编码.
     *
     * @param source
     * @param enc
     * @return
     */
    public static String encode(String source, String enc) {
        if (StringUtils.isNotBlank(source)) {
            if (StringUtils.isNotBlank(enc)) {
                try {
                    return URLEncoder.encode(source, enc);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                return source;
            }
        }
        return "";
    }

    /**
     * http解码.
     *
     * @param source
     * @param enc
     * @return
     */
    public static String decode(String source, String enc) {
        if (StringUtils.isNotBlank(source)) {
            if (StringUtils.isNotBlank(enc)) {
                try {
                    return URLDecoder.decode(source, enc);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            } else {
                return source;
            }
        }
        return "";
    }

    private static String getBody(HttpResponse httpResponse) {
        if (httpResponse.getStatusCode() == HttpStatus.SC_OK) {
            return httpResponse.getBody();
        } else {
            return null;
        }
    }

    private static void resetConnectionTimeout() {
        if (manager != null) {
            manager.getParams().setConnectionTimeout(DEFAULT_CONNECTION_TIMEOUT);
        }
    }
}
