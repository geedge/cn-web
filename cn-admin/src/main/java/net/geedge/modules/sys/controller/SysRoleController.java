package net.geedge.modules.sys.controller;

import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.annotation.SysLog;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.TypeEnum;
import net.geedge.modules.sys.entity.SysRoleEntity;
import net.geedge.modules.sys.service.SysRoleService;

/**
 * 角色管理
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleController extends AbstractController {

	@Autowired
	private SysRoleService sysRoleService;

	/**
	 * 角色列表查询
	 * @param params
	 * @return
	 */
	@GetMapping
	@SysLog(operation = OperationEnum.QUERY,type = TypeEnum.SYSTEM)
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysRoleService.queryPage(params);
		return R.ok(page);
	}

    /**
     * 角色菜单查询
     * @param roleId
     * @return
     */
    @GetMapping("/menu/{id}")
    @SysLog(operation = OperationEnum.QUERY,type = TypeEnum.SYSTEM)
    public R list(@PathVariable("id") Integer roleId) {
        ValidateUtils.is(roleId).notNull(RCode.SYS_ROLE_ID_ISNULL);
        return R.ok(sysRoleService.getMenuListByRoleId(roleId));
    }


    /**
     * 角色保存
     * @param role
     * @return
     */
	@PostMapping
	@SysLog(operation = OperationEnum.ADD,type = TypeEnum.SYSTEM)
	/*@RequiresPermissions("sys:role:save")*/
	public R save(@RequestBody SysRoleEntity role){
		sysRoleService.saveRole(role);
		return R.ok().put("id", role.getId());
	}

	/**
	 * 修改角色
	 * @param role
	 * @return
	 */
	@PutMapping
	@SysLog(operation = OperationEnum.UPDATE,type = TypeEnum.SYSTEM)
	/*@RequiresPermissions("sys:role:update")*/
	public R update(@RequestBody SysRoleEntity role){
		ValidateUtils.is(role.getId()).notNull(RCode.SYS_ROLE_ID_ISNULL);
		sysRoleService.update(role);
		return R.ok().put("id", role.getId());
	}

	/**
	 * 删除角色
	 * @param ids,params
	 * @return
	 */
	@DeleteMapping
	@SysLog(operation = OperationEnum.DELETE,type = TypeEnum.SYSTEM)
	/*@RequiresPermissions("sys:role:delete")*/
	public R delete(@RequestParam Integer[] ids, @RequestParam Map<String, Object> params) {
		ValidateUtils.is(ids).notNull(RCode.SYS_ROLE_ID_ISNULL);

		return sysRoleService.deleteBatch(ids,params);
	}
}
