package viet.sn.regret.eventListener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

@Component
public class WebSocketSubventListener implements ApplicationListener<SessionSubscribeEvent> {

    @Override
    public void onApplicationEvent(SessionSubscribeEvent event) {
        String sessionId = event.toString();
        System.out.println(":> SubWebSocket: sessionId = " + sessionId);
    }
}

