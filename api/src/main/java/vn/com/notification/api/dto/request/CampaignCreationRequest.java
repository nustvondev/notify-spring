package vn.com.notification.api.dto.request;

import jakarta.validation.constraints.NotEmpty;

public record CampaignCreationRequest(@NotEmpty String code) {}
