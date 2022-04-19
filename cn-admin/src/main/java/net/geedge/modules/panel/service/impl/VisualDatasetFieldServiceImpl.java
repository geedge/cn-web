package net.geedge.modules.panel.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.panel.dao.VisualDatasetFieldDao;
import net.geedge.modules.panel.entity.VisualDatasetField;
import net.geedge.modules.panel.service.VisualDatasetFieldService;
import net.geedge.modules.sys.shiro.ShiroUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Auther: lijinyang
 * @Date: 2021/12/02/16:57
 */
@Service("visualDatasetFieldService")
public class VisualDatasetFieldServiceImpl extends ServiceImpl<VisualDatasetFieldDao, VisualDatasetField> implements VisualDatasetFieldService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if (ObjectUtil.isNotEmpty(params.get("ids"))) {
            params.put("ids", Arrays.asList(String.valueOf(params.get("ids")).split(StringPool.COMMA)));
        }
        IPage<VisualDatasetField> page=this.page(new Query(VisualDatasetField.class).getPage(params));
        
        List<VisualDatasetField> list=this.baseMapper.queryPage(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public VisualDatasetField queryInfo(Integer id) {
        return this.baseMapper.selectInfo(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveOrUpdateDatasetField(VisualDatasetField datasetField) {
        validateDatasetField(datasetField);
        if (ObjectUtil.isEmpty(datasetField.getId())) {
            datasetField.setCby(ShiroUtils.getUserId());
            datasetField.setCtime(new Date());
        }
        datasetField.setUby(ShiroUtils.getUserId());
        datasetField.setUtime(new Date());
        this.saveOrUpdate(datasetField);
        return datasetField.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeDatasetField(String ids) {
        // 内置datasetField不删除
        validateBuildInDatasetFields(Arrays.asList(ids.split(",")));
        // 删除datasetField
        this.removeByIds(Arrays.asList(ids.split(",")));
    }

    /**
     * 校验 datasetField 相关参数是否正确
     *
     * @param datasetField
     */
    public void validateDatasetField(VisualDatasetField datasetField) {
        String name = datasetField.getName();
        ValidateUtils.is(name).notNull(RCode.DATASET_FIELD_NAME_ISNULL)
                .and(datasetField.getType()).notNull(RCode.DATASET_FIELD_TYPE_ISNULL).max(4, RCode.DATASET_FIELD_TYPE_INVALIDE).min(1,RCode.DATASET_FIELD_TYPE_INVALIDE)
                .and(datasetField.getStype()).notNull(RCode.DATASET_FIELD_STYPE_ISNULL).max(2, RCode.DATASET_FIELD_STYPE_INVALIDE).min(1, RCode.DATASET_FIELD_STYPE_INVALIDE)
                .and(datasetField.getDsetid()).notNull(RCode.DATASET_FIELD_DSETID_ISNULL)
                .and(datasetField.getIdx()).notNull(RCode.DATASET_FIELD_IDX_ISNULL)
                .and(datasetField.getExpr()).notNull(RCode.DATASET_FIELD_EXPR_ISNULL)
                ;

        //校验name是否重复
        VisualDatasetField visualDatasetField = this.getOne(new LambdaQueryWrapper<VisualDatasetField>().eq(VisualDatasetField::getName,datasetField.getName()));
        //id为null 新增
        if (Tool.ObjectUtil.isEmpty(datasetField.getId()) && Tool.ObjectUtil.isNotEmpty(visualDatasetField)){
            throw new CNException(RCode.DATASET_FIELD_NAME_DUPLICATE);
        }
        //id not null 修改
        if (Tool.ObjectUtil.isNotEmpty(datasetField.getId()) && Tool.ObjectUtil.isNotEmpty(visualDatasetField) && !visualDatasetField.getId().equals(datasetField.getId())){
            throw new CNException(RCode.DATASET_FIELD_NAME_DUPLICATE);
        }
    }

    /**
     * 校验内置 datasetField 不可删除
     *
     * @param removeIdList
     */
    private void validateBuildInDatasetFields(List<String> removeIdList) {
        // 内置 datasetField 不能删除
        List<VisualDatasetField> list = this.list(new QueryWrapper<VisualDatasetField>().lambda().in(VisualDatasetField::getId, removeIdList).eq(VisualDatasetField::getBuildIn,1));
        if(CollectionUtils.isNotEmpty(list)) {
            List<String> datasetFieldNames = list.stream().map(VisualDatasetField::getName).collect(Collectors.toList());
            throw new CNException("These dataset field is built-in and cannot be deleted, dataset fields info: " + datasetFieldNames.toString(),RCode.DATASET_FIELD_BUILDIN_CAN_NOT_REMOVE.getCode());
        }
    }
}
