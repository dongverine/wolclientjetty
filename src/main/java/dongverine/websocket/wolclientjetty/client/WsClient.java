package dongverine.websocket.wolclientjetty.client;

import org.eclipse.jetty.websocket.client.ClientUpgradeRequest;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
public class WsClient {

    private String destUri = "ws://127.0.0.1:13701/events/";
    public void start(){
        /*Redis 로 올라온 연계 데이터를 웹소켓 클라이언트로 중국에서 개발한 2차센터로 전달*/
        WebSocketClient client = new WebSocketClient();
        WsSocket socket = new WsSocket();

        try{
            client.start();
            URI echoUri = new URI(destUri);
            ClientUpgradeRequest request = new ClientUpgradeRequest();
            client.connect(socket,echoUri,request);
            System.out.printf("Connecting to : %s%n",echoUri);

            socket.awaitClose(5, TimeUnit.SECONDS);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
