package viet.sn.regret.eventListener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketConnectEventListener implements ApplicationListener<SessionConnectEvent> {

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        String sessionId = event.toString();
        System.out.println(":D kết nối WebSocket: sessionId = " + sessionId);

        // Nếu bạn đang lưu username <-> sessionId, có thể xóa ở đây
        // Ví dụ: onlineUserRegistry.removeBySessionId(sessionId);
    }
}

