package net.geedge.modules.sys.controller;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;

import cn.hutool.core.util.StrUtil;
import net.geedge.common.annotation.SysLog;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.HttpContextUtils;
import net.geedge.common.utils.IPUtils;
import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.R;
import net.geedge.common.utils.RCode;
import net.geedge.common.utils.Tool;
import net.geedge.common.utils.TypeEnum;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.entity.Timezone;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.service.SysUserService;
import net.geedge.modules.sys.service.TimezoneService;
import net.geedge.modules.sys.shiro.ShiroUtils;

/**
 * 登录相关
 *

 */
@Controller
public class SysLoginController extends AbstractController {
	@Autowired
	private Producer producer;
	@Autowired
	private RedisTemplate<String,String> redisTemplate;
	@Autowired
	private SysConfigService configService;
	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private TimezoneService timezoneService;
	
	@RequestMapping("captcha.jpg")
	public void captcha(HttpServletResponse response)throws IOException {
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
	}

	/**
	 * 登录
	 */
	@ResponseBody
	@RequestMapping(value = "/sys/login", method = RequestMethod.POST)
	@SysLog(operation = OperationEnum.LOGIN,type = TypeEnum.SYSTEM)
	public R login(@RequestBody SysUserEntity sysUser) {
		String username = sysUser.getUsername();
		String pin = StrUtil.str(sysUser.getPin());
		try{
			Subject subject = ShiroUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(username, pin);
			subject.login(token); //登录
			String loginToken= Tool.IdUtil.simpleUUID();
			subject.getSession().setAttribute("token",loginToken);
			Map<String, Object> obj = new HashMap<>();
			obj.put("1", "1");
			obj.put("2", "1");
			obj.put("3", "1");
			obj.put("4", "1");
			obj.put("token", subject.getSession().getId().toString());
			// 查询系统配置信息
			String[] paramkeys = {"system_name", "system_logo", "timezone", "default_cabinet_usize", "map_center_config", "unsaved_change","prometheus_federation_enabled"};
			List<SysConfigEntity> configEntityList = configService.list(new LambdaUpdateWrapper<SysConfigEntity>().in(SysConfigEntity::getCkey, paramkeys));
			for (SysConfigEntity configEntity : configEntityList) {
				if ("timezone".equals(configEntity.getCkey())) {
					Timezone timezoneEntity = timezoneService.getOne(new LambdaUpdateWrapper<Timezone>().eq(Timezone::getName, configEntity.getCvalue()));
					obj.put("offset", timezoneEntity.getOffset());
				}
				obj.put(Tool.StrUtil.toCamelCase(configEntity.getCkey()), configEntity.getCvalue());
			}

            // 获取request
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();            
            SysUserEntity userEntity = ShiroUtils.getUserEntity();
            userEntity.setLastLoginTime(new Date());
            // 设置IP地址
            userEntity.setLastLoginIp(IPUtils.getIpAddr(request));
            sysUserService.updateById(userEntity);
            return R.ok(obj);
		}catch (UnknownAccountException e) {
			throw new CNException(RCode.SYS_LOGIN_UNKNOWNACCOUNT);
		}catch (IncorrectCredentialsException e) {
			throw new CNException(RCode.SYS_LOGIN_USERPWD);
		}catch (LockedAccountException e) {
			throw new CNException(RCode.SYS_LOGIN_LOCK);
		}catch (AuthenticationException e) {
			throw new CNException(RCode.SYS_LOGIN_ACCOUNTAUTH);
		}
	}


	/**
	 * 退出
	 */
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	@ResponseBody
	@SysLog(operation = OperationEnum.LOGOUT,type = TypeEnum.SYSTEM)
	public R logout(HttpServletRequest request) {
		ShiroUtils.logout();
		String requestToken=request.getHeader(Constant.AUTH_TOKEN_CODE);
		if(StringUtils.isBlank(requestToken)){
			requestToken=request.getParameter(Constant.AUTH_TOKEN_CODE);
		}
		try {
			redisTemplate.delete(requestToken);
		}catch (RuntimeException e){
				logger.debug("登出操作删除redis信息失败，失败key：{}",requestToken);
		}
		return R.ok();
	}

}
