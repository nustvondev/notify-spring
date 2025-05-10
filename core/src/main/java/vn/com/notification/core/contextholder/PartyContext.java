package vn.com.notification.core.contextholder;

import lombok.Builder;
import vn.com.notification.core.enumeration.Modifier;

@Builder
public record PartyContext(Long cifNumber, Modifier modifier) {}
