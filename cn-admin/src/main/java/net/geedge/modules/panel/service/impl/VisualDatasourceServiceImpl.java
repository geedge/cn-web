package net.geedge.modules.panel.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.modules.panel.dao.VisualDatasourceDao;
import net.geedge.modules.panel.entity.VisualDatasource;
import net.geedge.modules.panel.service.VisualDatasourceService;
import net.geedge.modules.sys.shiro.ShiroUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: lijinyang
 * @Date: 2021/12/01/16:00
 */
@Service("visualDatasourceService")
public class VisualDatasourceServiceImpl extends ServiceImpl<VisualDatasourceDao, VisualDatasource> implements VisualDatasourceService {

    @Autowired
    private VisualDatasourceService visualDatasourceService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if (ObjectUtil.isNotEmpty(params.get("ids"))) {
            params.put("ids", Arrays.asList(String.valueOf(params.get("ids")).split(StringPool.COMMA)));
        }
        IPage<VisualDatasource> page=this.page(new Query(VisualDatasource.class).getPage(params));

        List<VisualDatasource> list=this.baseMapper.queryPage(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public VisualDatasource queryInfo(Integer id) {
        return this.baseMapper.selectInfo(id);
    }

    @Override
    public Integer saveOrUpdateDatasource(VisualDatasource datasource) {
        validateDatasource(datasource);
        if(ObjectUtil.isEmpty(datasource.getId())) {
            datasource.setCby(ShiroUtils.getUserId());
            datasource.setCtime(new Date());
        }
        datasource.setUby(ShiroUtils.getUserId());
        datasource.setUtime(new Date());
        this.saveOrUpdate(datasource);
        return datasource.getId();
    }

    @Override
    public void removeDatasource(String ids) {
        // 内置datasource不删除
        validateBuildInDatasources(Arrays.asList(ids.split(",")));
        // 删除datasource
        this.removeByIds(Arrays.asList(ids.split(",")));
    }

    /**
     * 校验 datasource 相关参数是否正确
     *
     * @param datasource
     */
    public void validateDatasource(VisualDatasource datasource) {
        String name = datasource.getName();
        ValidateUtils.is(name).notNull(RCode.DATASOURCE_NAME_ISNULL)
                .and(datasource.getType()).notNull(RCode.DATASOURCE_TYPE_ISNULL).max(2, RCode.DATASOURCE_TYPE_INVALIDE).min(1, RCode.DATASOURCE_TYPE_INVALIDE);

        // 校验name是否重复
        List<VisualDatasource> list = this.list(new QueryWrapper<VisualDatasource>().lambda().eq(VisualDatasource::getName, datasource.getName()).ne(ObjectUtil.isNotEmpty(datasource.getId()),VisualDatasource::getId,datasource.getId()));
        boolean flag = false;
        //当只有一个结果并且这个结果是自身时，或没有结果时，说明名称不重复
        if (ObjectUtil.isNotEmpty(list)) {
            flag = true;
        }
        if (flag) {
            throw new CNException(RCode.DATASOURCE_NAME_DUPLICATE);
        }
    }

    /**
     * 校验内置 datasource 不可删除
     *
     * @param removeIdList
     */
    private void validateBuildInDatasources(List<String> removeIdList) {
        // 内置 datasource 不能删除
        List<VisualDatasource> list = this.list(new QueryWrapper<VisualDatasource>().lambda().in(VisualDatasource::getId, removeIdList).eq(VisualDatasource::getBuildIn, 1));
        if (CollectionUtils.isNotEmpty(list)) {
            List<String> datasourceNames = list.stream().map(VisualDatasource::getName).collect(Collectors.toList());
            throw new CNException("These panel is built-in and cannot be deleted, panels info: " + datasourceNames.toString(), RCode.DATASOURCE_BUILDIN_CAN_NOT_REMOVE.getCode());
        }
    }
}
