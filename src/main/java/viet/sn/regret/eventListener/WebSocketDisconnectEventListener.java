package viet.sn.regret.eventListener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketDisconnectEventListener implements ApplicationListener<SessionDisconnectEvent> {

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        String sessionId = event.getSessionId();
        System.out.println("❌ Ngắt kết nối WebSocket: sessionId = " + sessionId);

        // Nếu bạn đang lưu username <-> sessionId, có thể xóa ở đây
        // Ví dụ: onlineUserRegistry.removeBySessionId(sessionId);
    }
}

