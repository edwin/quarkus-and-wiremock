package com.edw.controller;

import com.edw.client.MockyService;
import com.edw.client.TypicodeService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.HashMap;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>
 *     com.edw.controller.IndexController
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 22 Nov 2023 11:54
 */
@Path("/")
public class IndexController {

    private Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Inject
    @RestClient
    MockyService mockyService;

    @Inject
    @RestClient
    TypicodeService typicodeService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response index(@QueryParam("name") String name) {
        logger.debug("calling {}", name);
        return Response
                .status(200)
                .entity(new HashMap(){{
                    put("hello", name);
                }})
                .build();
    }

    @GET
    @Path("/call")
    @Produces(MediaType.APPLICATION_JSON)
    public Response callExternalUrl() {
        logger.debug("calling external-service");
        return Response
                .status(200)
                .entity(mockyService.getDefaultMockData())
                .build();
    }

    @GET
    @Path("/post")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPost(@QueryParam("id") String id) {
        logger.debug("calling typicode");
        return typicodeService.getOnePost(id);
    }
}
