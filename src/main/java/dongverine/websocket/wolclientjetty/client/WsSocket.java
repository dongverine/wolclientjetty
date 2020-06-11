package dongverine.websocket.wolclientjetty.client;


import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

@WebSocket(maxTextMessageSize = 64 * 1024)
public class WsSocket{

    private final CountDownLatch closeLatch = new CountDownLatch(1);

    @SuppressWarnings("unused")
    private Session session;

//    public SimpleEchoSocket(){
//        this.closeLatch = new CountDownLatch(1);
//    }

    public boolean awaitClose(int duration, TimeUnit unit) throws InterruptedException{
        return this.closeLatch.await(duration,unit);
    }

    @OnWebSocketClose
    public void onClose(int statusCode, String reason){
        System.out.printf("Connection closed: %d - %s%n",statusCode,reason);
        this.session = null;
        this.closeLatch.countDown(); // trigger latch
    }

    @OnWebSocketConnect
    public void onConnect(Session session){
        System.out.printf("Got connect: %s%n",session);
        this.session = session;
        try{
            //CommonGlobalVariable.webSocketSession = session;
            Future<Void> fut;
            fut = session.getRemote().sendStringByFuture("Hello");
            fut.get(2,TimeUnit.SECONDS); // wait for send to complete.
        }catch (Throwable t){
            t.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(String msg){
        System.out.printf("Got msg: %s%n",msg);
    }
}