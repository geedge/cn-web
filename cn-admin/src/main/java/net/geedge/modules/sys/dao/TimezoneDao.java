package net.geedge.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.geedge.modules.sys.entity.Timezone;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TimezoneDao extends BaseMapper<Timezone> {
}
