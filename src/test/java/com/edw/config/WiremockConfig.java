package com.edw.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * <pre>
 *     com.edw.config.WiremockConfig
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 * 22 Nov 2023 12:14
 */
public class WiremockConfig implements QuarkusTestResourceLifecycleManager {
    private WireMockServer server;

    @Override
    public Map<String, String> start() {
        server = new WireMockServer(8082);
        server.start();
        server.stubFor(
                get(urlEqualTo("/"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{\"hello\": \"mock\"}")));

        server.stubFor(
                get(urlPathTemplate("/posts/{id}"))
                        .withPathParam("id", equalTo("1"))
                            .willReturn(aResponse()
                                    .withStatus(200)
                                    .withHeader("Content-Type", "application/json")
                                    .withBody("{\n" +
                                                    "  \"userId\": 1,\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"title\": \"sunt aut facere repellat provident occaecati excepturi " +
                                                    "optio reprehenderit\",\n" +
                                                    "  \"body\": \"quia et suscipit\\nsuscipit recusandae consequuntur " +
                                                    "expedita et cum\\nreprehenderit molestiae ut ut quas totam\\nnostrum " +
                                                    "rerum est autem sunt rem eveniet architecto\"\n" +
                                                "}")));

        server.stubFor(
                get(urlPathTemplate("/posts/{id}"))
                        .withPathParam("id", not(equalTo("1")))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{\n" +
                                            "  \"userId\": 2,\n" +
                                            "  \"id\": 2,\n" +
                                            "  \"title\": \"post 2\",\n" +
                                            "  \"body\": \"est rerum tempore vitae\\nsequi sint nihil reprehenderit dolor" +
                                            " beatae ea dolores neque\\nfugiat blanditiis voluptate porro vel nihil " +
                                            "molestiae ut reiciendis\\nqui aperiam non debitis possimus qui neque nisi " +
                                            "nulla\"\n" +
                                        "}")));

        return new HashMap<>();
    }

    @Override
    public void stop() {
        if (server != null) {
            server.stop();
        }
    }
}
