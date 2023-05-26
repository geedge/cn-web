package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.modules.knowledge.entity.IpTagUserDefined;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2023/05/18/11:47
 */
public interface IpTagUserDefinedService extends IService<IpTagUserDefined> {

    PageUtils queryListByKnowledgeId(Map<String,Object> params);
    
    void saveBatch(List<IpTagUserDefined> ipTagUserDefineds);
    
    void modifyBatch(List<IpTagUserDefined> ipTagUserDefineds);
}
