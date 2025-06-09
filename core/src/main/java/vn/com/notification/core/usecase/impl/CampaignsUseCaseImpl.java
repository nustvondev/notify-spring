package vn.com.notification.core.usecase.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import vn.com.notification.core.dto.params.CampaignCreationParams;
import vn.com.notification.core.dto.params.NotificationParams;
import vn.com.notification.core.dto.result.CampaignCreationResult;
import vn.com.notification.core.service.CampaignsService;
import vn.com.notification.core.usecase.CampaignsUseCase;

@RequiredArgsConstructor
@Slf4j
@Component
public class CampaignsUseCaseImpl implements CampaignsUseCase {
    private final CampaignsService campaignsService;

    @Override
    public CampaignCreationResult createCampaign(CampaignCreationParams params) {
        return campaignsService.createCampaign(params);
    }

    @Override
    public void createNotifications(NotificationParams params) {
        campaignsService.createNotificationCampaign(params);
    }
}
