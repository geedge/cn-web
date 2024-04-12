package net.geedge.modules.knowledge.service;

import com.baomidou.mybatisplus.extension.service.IService;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.modules.knowledge.entity.Tag;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @Auther: lijinyang
 * @Date: 2024/04/03/11:30
 */
public interface TagService extends IService<Tag> {

    PageUtils queryTagList(Map<String,Object> params);

    String addOrModifyTag(Map<String,Object> params);

    R importData(String indicatorType, MultipartFile file);

    List<String> remove(String ids);

    Integer importBatch(MultipartFile file,String action);

    Map<String,Object> queryIndicators(String id, Map<String, Object> params);
}
