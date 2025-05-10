package vn.com.notification.api.handler;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import vn.com.notification.api.annotation.CifNumber;
import vn.com.notification.core.contextholder.PartyContext;
import vn.com.notification.core.contextholder.PartyContextHolder;

public class HeaderCifNumberArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.getParameterAnnotation(CifNumber.class) != null;
    }

    @Override
    public Object resolveArgument(
            MethodParameter methodParameter,
            ModelAndViewContainer modelAndViewContainer,
            NativeWebRequest nativeWebRequest,
            WebDataBinderFactory webDataBinderFactory) {

        HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();
        return getCifNumberFromToken(request.getHeader("GTW-Authorization"));
    }

    private Long getCifNumberFromToken(String authToken) {
        String token = authToken.replace("Bearer ", "");
        DecodedJWT decodedJWT = JWT.decode(token);
        Long cifNumber = Long.valueOf(decodedJWT.getClaim("username").asString());
        PartyContextHolder.setContext(PartyContext.builder().cifNumber(cifNumber).build());
        return cifNumber;
    }
}
