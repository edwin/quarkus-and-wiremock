package com.edw.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.HashMap;

/**
 * <pre>
 *     com.edw.client.TypicodeService
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 22 Nov 2023 18:13
 */
@RegisterRestClient
public interface TypicodeService {
    @GET
    @Path("/posts/{id}")
    Response getOnePost(@PathParam("id") String id);
}
