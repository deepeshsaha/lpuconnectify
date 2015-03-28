package com.rest;

import com.mongodb.*;
import com.mongodb.util.JSON;

import javax.inject.Inject;
import javax.ws.rs.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deepesh on 3/27/2015.
 */
@Path("/users")
public class UserRestService {

    @Inject public DB db;

    DBCollection dbCollection=null;
    DBCursor dbCursor=null;


    @GET
    @Path("/view")
    @Produces("application/json")
    public String getUsers() {
        dbCollection =db.getCollection("user");
       dbCursor =dbCollection.find();
        List<String> list=new ArrayList<String>();
        while(dbCursor.hasNext())
        {
            list.add((String)dbCursor.next().toString());
        }

        return list.toString();
    }

    @GET
    @Path("/viewById")
    @Produces("application/json")
    public String getUsers1(@QueryParam("userid")String userid) {
        dbCollection =db.getCollection("user");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("firstname", userid);

        dbCursor =dbCollection.find(whereQuery);
        List<String> list=new ArrayList<String>();
        while(dbCursor.hasNext())
        {
            list.add((String)dbCursor.next().toString());
        }

        return list.toString();
    }


    @PUT
    @Path("/add")
    @Produces("application/json")
    public String addUsers(@QueryParam("user") String user) {
          dbCollection =db.getCollection("user");
       DBObject dbObject=(DBObject)JSON.parse(user);
        dbCollection.insert(dbObject);
        return "status:Success,user:"+user;
    }

    @PUT
    @Path("/update")
    @Produces("application/json")
    public String updateUsers(@QueryParam("userid") String userid,@QueryParam("updatekey")String updatekey,@QueryParam("updatevalue")String updatevalue) {
        dbCollection =db.getCollection("user");

        DBObject query = new BasicDBObject("lastname",userid);
        DBObject update = new BasicDBObject();
        update.put("$set", new BasicDBObject(updatekey,updatevalue));

        WriteResult result=dbCollection.updateMulti(query,update);
        return "status:updated"+result;
    }

}
