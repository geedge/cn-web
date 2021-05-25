package net.geedge.modules.sys.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.geedge.common.smartvalidate.ValidateUtils;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.modules.sys.entity.SysMenuEntity;
import net.geedge.modules.sys.service.SysMenuService;

@RestController
@RequestMapping("/sys/menu")
public class SysMenuController extends AbstractController {

	@Autowired
	private SysMenuService sysMenuService;

	/**
	 * 所有菜单列表
	 * @param id
	 * @return
	 */
	@GetMapping
	public R list(Integer id) {
		List<SysMenuEntity> menuList = sysMenuService.getMenuList(id);
		return R.ok().putData("list", menuList);
	}

	/**
	 * 保存
	 * @param menu
	 * @return
	 */
	@PostMapping
	public R save(@RequestBody SysMenuEntity menu) {
		sysMenuService.saveMenu(menu);
		return R.ok().put("id", menu.getId());
	}

	/**
	 * 修改
	 * @param menu
	 * @return
	 */
	@PutMapping
	public R update(@RequestBody SysMenuEntity menu) {
		ValidateUtils.is(menu.getId()).notNull(RCode.SYS_MENU_ID_ISNULL);
		sysMenuService.updateMenu(menu);
		return R.ok().put("id", menu.getId());
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	@DeleteMapping
	public R delete(@RequestParam Integer... ids) {
		ValidateUtils.is(ids).notNull(RCode.SYS_MENU_ID_ISNULL);
		sysMenuService.delete(ids);
		return R.ok();
	}
}