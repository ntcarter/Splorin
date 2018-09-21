package com.andi.DungeonExplorer.Networking.Server;

/**
 * Created by Ntcarter on 11/20/2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.net.HttpParametersUtils;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.badlogic.gdx.net.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class LeaveLobby {

    public HttpRequest postReq;
    public HttpRequest getReq;
    public HttpRequestBuilder requestBuilder;
    public String posString;

    public LeaveLobby() {
        //create requests
        postReq = new HttpRequest(Net.HttpMethods.POST);
        getReq = new HttpRequest(Net.HttpMethods.POST);
        //set URLs
        postReq.setUrl("http://proj-309-sr-b-4.cs.iastate.edu/LeavingLobby.php");
    }

    //probably will want to make this return a string so the server can send a response that can be displayed to the user.
    public void SendLeaveInfo(String userName, String userPassword){
        Map<String,String> registerInfo = new HashMap<String, String>();
        registerInfo.put("N",userName);
        registerInfo.put("P",userPassword);

        postReq.setContent(HttpParametersUtils.convertHttpParameters(registerInfo));
        Gdx.net.sendHttpRequest(postReq, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(HttpResponse httpResponse) {
                HttpStatus status = httpResponse.getStatus();
                // System.out.println(httpResponse.getResultAsString());
                // System.out.println(status.getStatusCode());
                // System.out.println(postReq.getContent());
            }

            @Override
            public void failed(Throwable t) {
                System.out.println("send failed");
            }

            @Override
            public void cancelled() { System.out.println("send cancelled"); }
        });

        getStringResponse();
    }

    public String getPos(){
        return posString;
    }

    public void getStringResponse(){

        Gdx.net.sendHttpRequest(postReq, new HttpResponseListener() {
            @Override
            public void handleHttpResponse(HttpResponse httpResponse) {
                posString = httpResponse.getResultAsString();
                System.out.println("server response: "+ posString);
            }


            @Override
            public void failed(Throwable t) { }

            @Override
            public void cancelled() { }
        });
    }
}
