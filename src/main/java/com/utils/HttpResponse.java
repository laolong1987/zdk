/*
 * Copyright (c) 2011 Shanda Corporation. All rights reserved.
 *
 * Created on 2011-9-8.
 */

package com.utils;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Http工具响应对象.
 *
 * @author James Shen
 */
public class HttpResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private int statusCode;

    private String statusText;

    private Cookie[] cookies;

    private Map<String, String> heads = new HashMap<String, String>();

    private String body;

    private String URI;

    public HttpResponse() {
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public Cookie[] getCookies() {
        return cookies;
    }

    public void setCookies(Cookie[] cookies) {
        this.cookies = cookies;
    }

    public void addCookies(Cookie[] cookies) {
        Cookie[] newCookies;
        if (this.cookies != null && cookies != null) {
            newCookies = new Cookie[this.cookies.length + cookies.length];
            System.arraycopy(this.cookies, 0, newCookies, 0, this.cookies.length);
            System.arraycopy(cookies, 0, newCookies, this.cookies.length, cookies.length);
        } else if (cookies != null) {
            newCookies = cookies;
        } else {
            newCookies = this.cookies;
        }
        this.cookies = newCookies;
    }

    public Map<String, String> getHeads() {
        return heads;
    }

    public void setHeads(Map<String, String> heads) {
        this.heads = heads;
    }

    public String getHead(String name) {
        return this.heads.get(name);
    }

    public void setHead(String name, String value) {
        this.heads.put(name, value);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }
}
