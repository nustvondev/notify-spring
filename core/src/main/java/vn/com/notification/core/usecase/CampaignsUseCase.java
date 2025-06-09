package vn.com.notification.core.usecase;

import vn.com.notification.core.dto.params.CampaignCreationParams;
import vn.com.notification.core.dto.params.NotificationParams;
import vn.com.notification.core.dto.result.CampaignCreationResult;

public interface CampaignsUseCase {
    CampaignCreationResult createCampaign(CampaignCreationParams params);

    void createNotifications(NotificationParams params);
}
