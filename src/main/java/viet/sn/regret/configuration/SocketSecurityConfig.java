//package viet.sn.regret.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.simp.SimpMessageType;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
//import org.springframework.security.messaging.access.intercept.MessageMatcherDelegatingAuthorizationManager;
//
//@Configuration // Đánh dấu đây là class cấu hình Spring
//@EnableWebSocketSecurity // Kích hoạt bảo mật WebSocket
//public class SocketSecurityConfig {
//    @Bean // Đăng ký bean quản lý phân quyền cho WebSocket message
//    AuthorizationManager<Message<?>> messageAuthorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
//        messages
//                .simpTypeMatchers(SimpMessageType.CONNECT)
//                .authenticated()
//                .anyMessage()
//                .permitAll();
////            // Chỉ cho phép người đã xác thực thực hiện CONNECT, MESSAGE, SUBSCRIBE
////            .simpTypeMatchers(SimpMessageType.CONNECT, SimpMessageType.MESSAGE, SimpMessageType.SUBSCRIBE).authenticated()
////            // Từ chối các message không xác định destination
////            .nullDestMatcher().denyAll()
////            // Từ chối tất cả các message còn lại
////            .anyMessage().denyAll();
//        return messages.build();
//    }
//}
