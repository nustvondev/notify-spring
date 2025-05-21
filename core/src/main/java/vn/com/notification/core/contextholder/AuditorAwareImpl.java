package vn.com.notification.core.contextholder;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import org.springframework.data.domain.AuditorAware;
import vn.com.notification.core.enumeration.Modifier;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(PartyContextHolder.getContext()).map(getModifier());
    }

    private Function<PartyContext, String> getModifier() {
        return partyContext ->
                Optional.ofNullable(partyContext.cifNumber())
                        .map(String::valueOf)
                        .orElseGet(getSpecificModifier(partyContext));
    }

    private Supplier<String> getSpecificModifier(PartyContext partyContext) {
        return () ->
                Optional.ofNullable(partyContext.modifier()).map(Enum::name).orElse(Modifier.SYSTEM.name());
    }
}
