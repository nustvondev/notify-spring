package vn.com.notification.api.dto.mapper;

import org.mapstruct.Mapper;
import vn.com.notification.api.dto.request.CampaignCreationRequest;
import vn.com.notification.core.dto.params.CampaignCreationParams;

@Mapper(componentModel = "spring")
public interface CampaignsMapper {
    CampaignCreationParams mapFrom(CampaignCreationRequest request);
}
