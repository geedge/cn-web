package net.geedge.modules.location.service.impl;

import net.geedge.common.utils.GeoUtil;
import net.geedge.common.utils.R;
import net.geedge.modules.location.service.LocationIntelligenceService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/05/21/18:02
 */
@Service
public class LocationIntelligenceServiceImpl implements LocationIntelligenceService {
    @Override
    public R geolocation(Double longitude, Double latitude) {
        Map<String, Object> map = GeoUtil.reverseGeocode(longitude, latitude);
        return R.ok(map);
    }
}
