package net.geedge.modules.sys.controller;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.TypeEnum;
import net.geedge.modules.sys.entity.Link;
import net.geedge.modules.sys.service.LinkService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/link")
public class LinkController extends AbstractController{
	@Autowired
	private LinkService linkService;
	
	@GetMapping
	@SysLog(operation = OperationEnum.QUERY,type = TypeEnum.LINK)
	public R list(@RequestParam Map<String, Object> params){
		List<Link> result = linkService.queryList(params);
		return R.ok().putData("list",result);
	}
	
	@PostMapping
	@SysLog(operation = OperationEnum.ADD,type = TypeEnum.LINK)
	/*@RequiresPermissions({"sys:link:save"})*/
	public R save(@RequestBody Link link) {
		ValidateUtils.is(link.getName()).notNull(RCode.LINK_NAME_ISNULL);
		ValidateUtils.is(link.getUrl()).notNull(RCode.LINK_URL_ISNULL);
		linkService.saveLink(link);
		return R.ok().putData("id", link.getId());
	}
	
	@PutMapping
	@SysLog(operation = OperationEnum.UPDATE,type = TypeEnum.LINK)
	/*@RequiresPermissions({"sys:link:update"})*/
	public R update(@RequestBody Link link) {
		ValidateUtils.is(link.getId()).notNull(RCode.LINK_ID_ISNULL);
		ValidateUtils.is(link.getName()).notNull(RCode.LINK_NAME_ISNULL);
		ValidateUtils.is(link.getUrl()).notNull(RCode.LINK_URL_ISNULL);
		linkService.updateById(link);
		return R.ok().putData("id", link.getId());
	}
	
	@DeleteMapping
	@SysLog(operation = OperationEnum.DELETE,type = TypeEnum.LINK)
	/*@RequiresPermissions({"sys:link:delete"})*/
	public R remove(@RequestParam String ids) {
		ValidateUtils.is(ids).notNull(RCode.LINK_ID_ISNULL);
		String[] split = ids.split(",");
		linkService.removeLinks(Arrays.asList(split));
		return R.ok();
	}
	
	@PutMapping("/weights")
	@SysLog(operation = OperationEnum.UPDATE,type = TypeEnum.LINK)
	public R modify(@RequestBody List<Link> links){
		linkService.saveWeights(links);
		return R.ok();
	}
}
