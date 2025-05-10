package vn.com.notification.core.contextholder;

public class PartyContextHolder {

    public static final ThreadLocal<PartyContext> LOCAL_CONTEXT = new ThreadLocal<>();

    public static void setContext(PartyContext context) {
        LOCAL_CONTEXT.set(context);
    }

    public static PartyContext getContext() {
        return LOCAL_CONTEXT.get();
    }

    public static Long getCif() {
        return LOCAL_CONTEXT.get().cifNumber();
    }

    public static void clearContext() {
        LOCAL_CONTEXT.remove();
    }
}
