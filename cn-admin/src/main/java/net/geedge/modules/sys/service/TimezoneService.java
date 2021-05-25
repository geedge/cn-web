package net.geedge.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.modules.sys.entity.Timezone;

import java.util.List;

public interface TimezoneService extends IService<Timezone> {
    List<Timezone> queryTimezone(String name);
}
