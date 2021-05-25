package net.geedge.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.TimezoneDao;
import net.geedge.modules.sys.entity.Timezone;
import net.geedge.modules.sys.service.TimezoneService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TimezoneServiceImpl extends ServiceImpl<TimezoneDao, Timezone> implements TimezoneService{
    @Override
    public List<Timezone> queryTimezone(String name) {
        List<Timezone> list = this.list(new QueryWrapper<Timezone>().lambda().like(Tool.StrUtil.isNotBlank(name), Timezone::getName, name));
        return list;
    }
}
