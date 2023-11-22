package com.edw.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.HashMap;

/**
 * <pre>
 *     com.edw.client.MockyService
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 22 Nov 2023 12:06
 */
@Path("/")
@RegisterRestClient
public interface MockyService {
    @GET
    @Path("/")
    HashMap getDefaultMockData();
}
