package vn.com.notification.core.service;

import vn.com.notification.core.dto.params.CampaignCreationParams;
import vn.com.notification.core.dto.params.NotificationParams;
import vn.com.notification.core.dto.result.CampaignCreationResult;

public interface CampaignsService {
    CampaignCreationResult createCampaign(CampaignCreationParams params);

    void createNotificationCampaign(NotificationParams params);
}
