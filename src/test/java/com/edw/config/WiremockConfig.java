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
        return new HashMap<>();
    }

    @Override
    public void stop() {
        if (server != null) {
            server.stop();
        }
    }
}
