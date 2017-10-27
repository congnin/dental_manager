package com.prostage.dental_manage.core;

public interface IHttpResponse {
    void onHttpComplete(String response, int idRequest);

    void onHttpError(String response, int idRequest, int errorCode);
}
