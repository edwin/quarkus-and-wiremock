# Integrating Wiremock for External API Mocking to Quarkus

## Concept
Hitting `/call` endpoint directly will gives below result,
```
$ curl -kv http://localhost:8080/call
*   Trying ::1:8080...
* TCP_NODELAY set
*   Trying 127.0.0.1:8080...
* TCP_NODELAY set
* Connected to localhost (127.0.0.1) port 8080 (#0)
> GET /call HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/7.65.0
> Accept: */*
>
* Mark bundle as not supporting multiuse
< HTTP/1.1 200 OK
< Content-Type: application/json
< content-length: 17
<
* Connection #0 to host localhost left intact
{"hello":"world"}                                     
```

while running the test-case will gives below result,
```
2023-11-22 12:22:35,367 DEBUG [org.apa.htt.imp.con.DefaultHttpClientConnectionOperator] (executor-thread-1) Connecting to localhost/127.0.0.1:8082
2023-11-22 12:22:35,369 DEBUG [org.apa.htt.imp.con.DefaultHttpClientConnectionOperator] (executor-thread-1) Connection established 127.0.0.1:58792<->127.0.0.1:8082
2023-11-22 12:22:35,369 DEBUG [org.apa.htt.imp.con.DefaultManagedHttpClientConnection] (executor-thread-1) http-outgoing-0: set socket timeout to 30000
2023-11-22 12:22:35,370 DEBUG [org.apa.htt.imp.exe.MainClientExec] (executor-thread-1) Executing request GET / HTTP/1.1
2023-11-22 12:22:35,370 DEBUG [org.apa.htt.imp.exe.MainClientExec] (executor-thread-1) Target auth state: UNCHALLENGED
2023-11-22 12:22:35,370 DEBUG [org.apa.htt.imp.exe.MainClientExec] (executor-thread-1) Proxy auth state: UNCHALLENGED
2023-11-22 12:22:35,371 DEBUG [org.apa.htt.headers] (executor-thread-1) http-outgoing-0 >> GET / HTTP/1.1
2023-11-22 12:22:35,371 DEBUG [org.apa.htt.headers] (executor-thread-1) http-outgoing-0 >> Accept: application/json
2023-11-22 12:22:35,371 DEBUG [org.apa.htt.headers] (executor-thread-1) http-outgoing-0 >> Host: localhost:8082
2023-11-22 12:22:35,371 DEBUG [org.apa.htt.headers] (executor-thread-1) http-outgoing-0 >> Connection: Keep-Alive
2023-11-22 12:22:35,371 DEBUG [org.apa.htt.headers] (executor-thread-1) http-outgoing-0 >> User-Agent: Apache-HttpClient/4.5.14.redhat-00007 (Java/17.0.6)
2023-11-22 12:22:35,371 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 >> "GET / HTTP/1.1[\r][\n]"
2023-11-22 12:22:35,371 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 >> "Accept: application/json[\r][\n]"
2023-11-22 12:22:35,372 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 >> "Host: localhost:8082[\r][\n]"
2023-11-22 12:22:35,372 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 >> "Connection: Keep-Alive[\r][\n]"
2023-11-22 12:22:35,372 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 >> "User-Agent: Apache-HttpClient/4.5.14.redhat-00007 (Java/17.0.6)[\r][\n]"
2023-11-22 12:22:35,372 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 >> "[\r][\n]"
2023-11-22 12:22:35,572 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 << "HTTP/1.1 200 OK[\r][\n]"
2023-11-22 12:22:35,572 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 << "Content-Type: application/json[\r][\n]"
2023-11-22 12:22:35,572 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 << "Matched-Stub-Id: a1e44621-eaf0-423f-9ae1-e383608b7e46[\r][\n]"
2023-11-22 12:22:35,572 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 << "Transfer-Encoding: chunked[\r][\n]"
2023-11-22 12:22:35,572 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 << "[\r][\n]"
2023-11-22 12:22:35,573 DEBUG [org.apa.htt.headers] (executor-thread-1) http-outgoing-0 << HTTP/1.1 200 OK
2023-11-22 12:22:35,573 DEBUG [org.apa.htt.headers] (executor-thread-1) http-outgoing-0 << Content-Type: application/json
2023-11-22 12:22:35,573 DEBUG [org.apa.htt.headers] (executor-thread-1) http-outgoing-0 << Matched-Stub-Id: a1e44621-eaf0-423f-9ae1-e383608b7e46
2023-11-22 12:22:35,573 DEBUG [org.apa.htt.headers] (executor-thread-1) http-outgoing-0 << Transfer-Encoding: chunked
2023-11-22 12:22:35,577 DEBUG [org.apa.htt.imp.exe.MainClientExec] (executor-thread-1) Connection can be kept alive indefinitely
2023-11-22 12:22:35,598 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 << "11[\r][\n]"
2023-11-22 12:22:35,598 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 << "{"hello": "mock"}[\r][\n]"
2023-11-22 12:22:35,598 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 << "0[\r][\n]"
2023-11-22 12:22:35,599 DEBUG [org.apa.htt.wire] (executor-thread-1) http-outgoing-0 << "[\r][\n]"
```

## Configuration
when running app directly, it will point to external API whih is hosted in `mocky` 
```
https://run.mocky.io/v3/99687692-4390-4ca2-816a-35c015fd72d0/
```

when running test cases, it will go to a `wiremock` endpoint
```
http://localhost:8082
```

## Wiremock Configuration
```java
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
}
```