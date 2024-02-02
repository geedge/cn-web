package net.geedge.common.config;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import cn.hutool.setting.dialect.Props;
import net.geedge.common.utils.Constant;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.entity.SysUserEntity;
import net.geedge.modules.sys.service.SysConfigService;
import net.geedge.modules.sys.service.SysUserService;
import net.geedge.modules.sys.shiro.ShiroUtils;
import net.geedge.setup.entity.SetupEntity;

/**
 * setup初始化操作
 * @author fang
 *
 */
@Component
public class SetupRunner implements CommandLineRunner{
	private static final Log log = Log.get();
	@Value("${cn.inited}")
    private int inited;
	
    @Value("${cn.adminId:1}")
    private String adminId;

    @Value("${server.tomcat.basedir}")
    private String tmpDir;
    @Value("${cn.config.path:./config/cn.properties}")
    private String cnConfigPath;
    
    @Autowired
    SysConfigService sysConfigService;

    @Autowired
    SysUserService sysUserService;
	
	@Override
	public void run(String... args) throws Exception {
		if( inited != 1) {
			// 创建设备16位数字的license token
			String licenseToken = sysConfigService.getValue("license_token");
			if(StrUtil.isEmpty(licenseToken)|| ! licenseToken.startsWith("GDNT-")||licenseToken.length()!=21) {
				int random = (int) (Math.random()*9+1);
		        String valueOf = String.valueOf(random);
		        //生成uuid的hashCode值
		        int hashCode = UUID.randomUUID().toString().hashCode();
		        //可能为负数
		        if(hashCode<0){
		            hashCode = -hashCode;
		        }
		        licenseToken = StrUtil.concat(true, "GDNT-",valueOf,String.format("%015d", hashCode));
				sysConfigService.updateValueByKey("license_token", licenseToken);
			}
			log.debug(" inited = {}	,skip init setup", inited);
			return ;
		}
		File file = Tool.FileUtil.file(tmpDir, Constant.SETUP_FILE_NAME);
		log.debug("file path: {}",file.getAbsoluteFile());
		if(file.exists()) {
			log.info("start inited setup");
			String jsonStr = Tool.FileUtil.readUtf8String(file);
	        SetupEntity setupEntity = Tool.JSONUtil.toBean(jsonStr, SetupEntity.class);
	        log.info("update db config");
	        SysUserEntity sysUser =  sysUserService.getById(adminId);
	        sysUser.setId(Long.parseLong(adminId));
	        sysUser.setUsername(setupEntity.getSystem().getUsername());
	        sysUser.setPin(ShiroUtils.sha256(setupEntity.getSystem().getPin(), sysUser.getSalt()));
	        log.info("update admin user");
	        sysUserService.updateById(sysUser);
	        Tool.FileUtil.del(file);//删除临时配置文件
		}
		//配置文件
    	boolean absolutePath = Tool.FileUtil.isAbsolutePath(cnConfigPath);
		File configFile = absolutePath ? Tool.FileUtil.file(cnConfigPath) : Tool.FileUtil.file(Tool.WebPathUtil.getRootPath(),cnConfigPath);
		//更改 cn.inited 为 2，下次启动时不再执行
		//cn.properties 配置文件路径：./config/cn.properties
		Props prop = new Props(configFile, Constant.DEFAULT_CHARTSET_NAME);
		prop.put("cn.inited", "2");
		prop.store(configFile.getAbsolutePath());
	}

}
