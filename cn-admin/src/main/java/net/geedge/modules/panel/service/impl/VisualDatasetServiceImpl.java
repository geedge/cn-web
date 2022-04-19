package net.geedge.modules.panel.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.panel.dao.VisualDatasetDao;
import net.geedge.modules.panel.entity.VisualDataset;
import net.geedge.modules.panel.service.VisualDatasetService;
import net.geedge.modules.sys.shiro.ShiroUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Auther: lijinyang
 * @Date: 2021/12/02/11:23
 */
@Service("visualDatasetService")
public class VisualDatasetServiceImpl extends ServiceImpl<VisualDatasetDao, VisualDataset> implements VisualDatasetService {
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        if (ObjectUtil.isNotEmpty(params.get("ids"))) {
            params.put("ids", Arrays.asList(String.valueOf(params.get("ids")).split(StringPool.COMMA)));
        }
        IPage<VisualDataset> page = this.page(new Query(VisualDataset.class).getPage(params));

        List<VisualDataset> list = this.baseMapper.queryPage(page,params);
        page.setRecords(list);
        return new PageUtils(page);
    }

    @Override
    public VisualDataset queryInfo(Integer id) {
        return this.baseMapper.selectInfo(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer saveOrUpdateDataset(VisualDataset dataset) {
        validateDataset(dataset);
        if(ObjectUtil.isEmpty(dataset.getId())) {
            dataset.setCby(ShiroUtils.getUserId());
            dataset.setCtime(new Date());
        }
        dataset.setUby(ShiroUtils.getUserId());
        dataset.setUtime(new Date());
        this.saveOrUpdate(dataset);
        return dataset.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeDataset(String ids) {
        // 内置dataset不删除
        validateBuildInDatasets(Arrays.asList(ids.split(",")));
        // 删除dataset
        this.removeByIds(Arrays.asList(ids.split(",")));
    }

    /**
     * 校验 dataset 相关参数是否正确
     *
     * @param dataset
     */
    public void validateDataset(VisualDataset dataset) {
        String name = dataset.getName();
        ValidateUtils.is(name).notNull(RCode.DATASET_NAME_ISNULL)
                .and(dataset.getType()).notNull(RCode.DATASET_TYPE_ISNULL).max(2, RCode.DATASET_TYPE_INVALIDE).min(1, RCode.DATASET_TYPE_INVALIDE)
                .and(dataset.getDsid()).notNull(RCode.DATASET_DSID_ISNULL);

        //校验name是否重复
        VisualDataset visualDataset = this.getOne(new LambdaQueryWrapper<VisualDataset>().eq(VisualDataset::getName,dataset.getName()));
        //id为null 新增
        if (Tool.ObjectUtil.isEmpty(dataset.getId()) && Tool.ObjectUtil.isNotEmpty(visualDataset)){
            throw new CNException(RCode.DATASET_NAME_DUPLICATE);
        }
        //id not null 修改
        if (Tool.ObjectUtil.isNotEmpty(dataset.getId()) && Tool.ObjectUtil.isNotEmpty(visualDataset) && !visualDataset.getId().equals(dataset.getId())){
            throw new CNException(RCode.DATASET_NAME_DUPLICATE);
        }
    }

    /**
     * 校验内置 dataset 不可删除
     *
     * @param removeIdList
     */
    private void validateBuildInDatasets(List<String> removeIdList) {
        // 内置 dataset 不能删除
        List<VisualDataset> list = this.list(new QueryWrapper<VisualDataset>().lambda().in(VisualDataset::getId, removeIdList).eq(VisualDataset::getBuildIn, 1));
        if(CollectionUtils.isNotEmpty(list)) {
            List<String> datasetNames = list.stream().map(VisualDataset::getName).collect(Collectors.toList());
            throw new CNException("These dataset is built-in and cannot be deleted, datasets info: " + datasetNames.toString(), RCode.DATASET_BUILDIN_CAN_NOT_REMOVE.getCode());
        }
    }
}
