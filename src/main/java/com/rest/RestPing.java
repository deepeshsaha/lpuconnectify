package com.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Created by Deepesh on 3/27/2015.
 */


@Path("/ping")
public class RestPing {

    @GET
    public String ping(){
        return "pong";
    }

}
