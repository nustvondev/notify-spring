package vn.com.notification.api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.notification.api.dto.mapper.CampaignsMapper;
import vn.com.notification.api.dto.request.CampaignCreationRequest;
import vn.com.notification.common.api.ResponseApi;
import vn.com.notification.core.dto.result.CampaignCreationResult;
import vn.com.notification.core.usecase.CampaignsUseCase;

@RestController
@RequestMapping("/v1/internal/campaigns")
// @RequiredArgsConstructor
@Slf4j
@ConditionalOnExpression("${application.campaigns-app-notification-enable}")
public class CampaignsController {
    private final CampaignsUseCase campaignsUseCase;
    private final CampaignsMapper campaignsMapper;

    public CampaignsController(CampaignsUseCase campaignsUseCase, CampaignsMapper campaignsMapper) {
        this.campaignsUseCase = campaignsUseCase;
        this.campaignsMapper = campaignsMapper;
    }

    @PostMapping
    public ResponseApi<CampaignCreationResult> createCampaign(
            @RequestBody CampaignCreationRequest request) {
        log.info("Creating campaign with code [{}]", request.code());
        CampaignCreationResult result =
                campaignsUseCase.createCampaign(campaignsMapper.mapFrom(request));
        log.info("Campaign created successfully");
        return ResponseApi.success(result);
    }
}
