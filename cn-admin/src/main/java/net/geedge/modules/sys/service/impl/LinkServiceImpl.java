package net.geedge.modules.sys.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.geedge.common.exception.CNException;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.LinkDao;
import net.geedge.modules.sys.entity.Link;
import net.geedge.modules.sys.service.LinkService;

@Service
public class LinkServiceImpl extends ServiceImpl<LinkDao, Link> implements LinkService {

	@Autowired
	private LinkDao linkDao;

	@Override
	public List<Link> queryList(Map<String, Object> params) {
		List<Link> list = linkDao.queryList(params);
		return list;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void saveLink(Link link) {
        Link max = this.getOne(new QueryWrapper<Link>().select("id","weight").orderByDesc("id").last("limit 1"));
        if(Tool.ObjectUtil.isEmpty(max)){
            link.setWeight(0);
        }else{
            link.setWeight(max.getWeight()+1);
        }
        this.save(link);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void removeLinks(List<String> ids) {
		if (Tool.ObjectUtil.isEmpty(ids)) {
            return;
        }

        List<Link> links = this.list();


        // 内置chart不能删除
        this.validateBuildInLinks(ids);
        
        List<Integer> linkIdList = links.stream().map(Link::getId).collect(Collectors.toList());

        List<String> deleteIds = new ArrayList<>();
        for (String id : ids) {
            if (linkIdList.contains(Integer.valueOf(id))) {
                deleteIds.add(id);
            }
        }

        if (CollectionUtils.isNotEmpty(deleteIds)) {
            // 删除自身
            this.baseMapper.deleteBatchIds(deleteIds);
        }
		
	}

    @Override
    public void saveWeights(List<Link> links) {
        Link link = links.stream().filter(t -> Tool.ObjectUtil.isEmpty(t.getId()) || Tool.ObjectUtil.isEmpty(t.getWeight())).findAny().orElse(null);
        if(Tool.ObjectUtil.isNotEmpty(link)){
            throw  new CNException(RCode.LINK_WEIGHT_PARAM_INVALID);
        }

        this.saveOrUpdateBatch(links);
    }

	/**
     * 校验是否是内置link
     *
     * @param linkIds
     */
    private void validateBuildInLinks(List<String> linkIds) {
        // 内置charts
        List<Link> buildInLinks = this.list(new QueryWrapper<Link>().lambda().eq(Link::getBuildIn, 1));
        Map<Integer, String> buildInLinkIdAndNameMap = buildInLinks.stream().collect(Collectors.toMap(Link::getId, Link::getName));


        linkIds.forEach(id -> {
            if (Tool.ObjectUtil.isNotEmpty(buildInLinkIdAndNameMap.get(Integer.valueOf(id)))) {
                throw new CNException(buildInLinkIdAndNameMap.get(Integer.valueOf(id)) + " link is built-in and cannot be deleted", RCode.LINK_BUILDIN_CAN_NOT_REMOVE.getCode());
            }
        });
    }
	
}
