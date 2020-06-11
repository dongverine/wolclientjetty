package dongverine.websocket.wolclientjetty;

import dongverine.websocket.wolclientjetty.client.WsClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WolclientjettyApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(WolclientjettyApplication.class, args);
        WsClient wc = ctx.getBean(WsClient.class);
        wc.start();
    }

}
