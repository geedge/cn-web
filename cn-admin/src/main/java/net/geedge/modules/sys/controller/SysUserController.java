package net.geedge.modules.sys.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import net.geedge.common.exception.CNException;
import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.TypeEnum;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.service.SysUserService;
import net.geedge.modules.sys.shiro.ShiroUtils;

@RestController
@RequestMapping("/sys/user")
public class SysUserController extends AbstractController {

	@Autowired
	private SysUserService sysUserService;

	/**
	 * 所有用户列表
	 */
	@RequestMapping
	@SysLog(operation = OperationEnum.QUERY,type = TypeEnum.ACCOUNT)
	public R list(@RequestParam Map<String, Object> params){
		PageUtils page = sysUserService.queryPage(params);
		return R.ok(page);
	}

	/**
	 * 修改登录用户密码
	 */
	@PutMapping("/pin")
	public R password(@RequestBody Map<String,Object> params){
		Long id = (Long)params.getOrDefault("id", this.getUserId());
		String oldPin = (String)params.get("oldPin");
		String newPin = (String)params.get("newPin");
		SysUserEntity user = sysUserService.getById(id);
		//原密码
		oldPin = ShiroUtils.sha256(oldPin, user.getSalt());
		//新密码
		newPin = ShiroUtils.sha256(newPin, user.getSalt());
		//更新密码
		boolean flag = sysUserService.updatePassword(id, oldPin, newPin);
		if(!flag){
			throw new CNException(RCode.SYS_USER_OLDPWD);
		}
		return R.ok();
	}

	/**
	 * 用户信息
	*/ 
	@GetMapping("/{id}")
	public R info(@PathVariable("id") Long id){
		ValidateUtils.is(id).notNull(RCode.SYS_USER_ID_ISNULL);
		SysUserEntity user = sysUserService.queryUserInfo(id);
		return R.ok().put("data", user);
	}

	/**
	 * 保存用户
	 */
	@PostMapping
	@SysLog(operation = OperationEnum.ADD,type = TypeEnum.ACCOUNT)
	@RequiresPermissions("sys:user:add")
	public R save(@RequestBody SysUserEntity user){
		Integer id = sysUserService.saveUser(user);
		return R.ok().putData("id", id);
	}

	/**
	 * 修改用户
	 */
	@PutMapping
	@SysLog(operation = OperationEnum.UPDATE,type = TypeEnum.ACCOUNT)
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUserEntity user){
		ValidateUtils.is(user).notNull(RCode.SYS_USER_ID_ISNULL)
			.and(user.getId()).notNull(RCode.SYS_USER_ID_ISNULL);
		Integer id = sysUserService.update(user);
		return R.ok().putData("id", id);
	}

	/**
	 * 删除用户
	 */
	@DeleteMapping
	@SysLog(operation = OperationEnum.DELETE,type = TypeEnum.ACCOUNT)
	@RequiresPermissions("sys:user:delete")
	public R delete(String ids) {
		sysUserService.deleteUsers(ids);
		return R.ok();
	}

	/**
	 * 用户权限查询接口
	 * @param request
	 * @return
	 */
	@PostMapping("/permissions")
	public R permissions(HttpServletRequest request){
		return R.ok(sysUserService.getUserPermissions(request));
	}
}
