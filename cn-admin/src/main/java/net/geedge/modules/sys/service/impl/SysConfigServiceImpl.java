/**

 *

 *

 */

package net.geedge.modules.sys.service.impl;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.sun.mail.util.MailSSLSocketFactory;

import cn.hutool.json.JSONObject;
import net.geedge.common.exception.CNException;
import net.geedge.common.utils.PageUtils;
import net.geedge.common.utils.Query;
import net.geedge.common.utils.Tool;
import net.geedge.modules.sys.dao.SysConfigDao;
import net.geedge.modules.sys.entity.SysConfigEntity;
import net.geedge.modules.sys.service.SysConfigService;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

    public static final String[] BASIC_KEYS = {"system_name","lang","date_format","system_logo", "timezone","system_bgImg","system_favicon","s3_enable","delay_time","map_config"};

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		String paramKey = (String)params.get("ckey");

		IPage<SysConfigEntity> page = this.page(
			new Query(SysConfigEntity.class).getPage(params),
			new QueryWrapper<SysConfigEntity>()
				.like(StringUtils.isNotBlank(paramKey),"ckey", paramKey)
				.eq("state", 1)
		);

		return new PageUtils(page);
	}
	
	@Override
	public void saveConfig(SysConfigEntity config) {
		this.save(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysConfigEntity config) {
		this.updateById(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		baseMapper.updateValueByKey(key, value);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		this.removeByIds(Arrays.asList(ids));
	}

	@Override
	public String getValue(String key) {
		SysConfigEntity config = baseMapper.queryByKey(key);
		return config == null ? null : config.getCvalue();
	}

	/**
	 * 系统查询
	 *
	 * @param paramKey
	 * @return
	 */
	@Override
	public JSONObject queryConfig(String paramKey) {
		SysConfigEntity config = baseMapper.queryByKey(paramKey);
		String appearance = config.getCvalue();
		JSONObject jsonObject = Tool.JSONUtil.parseObj(appearance);
		return jsonObject;
	}
	
	@Override
	public SysConfigEntity queryByKey(String paramKey) {
		SysConfigEntity config = baseMapper.queryByKey(paramKey);
		return config;
	}
	
	@Override
    @Transactional(rollbackFor = Exception.class)
    public void saveBasic(Map<String, String> params) {     
        //过滤非法的key
        Map<String, String> result = this.validateKeys(BASIC_KEYS, params);
        //保存
        this.saveOrUpdateConfsBatch(result);
    }
	
	/**
    * @Description 校验参数是否存在，并返回过滤后的map
    * @Date   2021/4/19
    */
    private Map<String,String> validateKeys(String[] keys,Map<String,String> params){
        List<String> keyList = Arrays.asList(keys);
        Map<String,String> result = new HashMap<>();
        for (String key : params.keySet()){
            if(keyList.contains(key)){
               result.put(key,params.get(key));
            }
        }
        return result;
    }
	
	public Map<String,SysConfigEntity> saveOrUpdateConfsBatch(Map<String,String> params){

        List<SysConfigEntity> exists = this.list(new QueryWrapper<SysConfigEntity>().lambda().in(SysConfigEntity::getCkey,params.keySet()));

        Map<String,SysConfigEntity> map = new HashMap<>();

        for (SysConfigEntity conf : exists){
            map.put(conf.getCkey(),conf);
        }

        for (String key : params.keySet()){
            if(map.containsKey(key)){
                map.get(key).setCvalue(params.get(key));
            }else{
                SysConfigEntity sysConfigEntity = new SysConfigEntity();
                sysConfigEntity.setCkey(key);
                sysConfigEntity.setCvalue(params.get(key));
                map.put(key,sysConfigEntity);
            }
        }

        this.saveOrUpdateBatch(map.values());
        return map;
    }
	
	/**
	 * 系统更改
	 *
	 * @param paramKey
	 * @return
	 */
	@Override
	public int updateConfig(String paramKey, JSONObject paramValue) {
		String jsonStr = Tool.JSONUtil.toJsonStr(paramValue);
		int isUpdate = baseMapper.updateValueByKey(paramKey, jsonStr);
		return isUpdate;
	}

	/**
	 * 系统邮箱配置测试
	 *
	 * @param email
	 * @return
	 */
	@Override
	public boolean testEmail(JSONObject email){
		Properties prop = new Properties();
		//设置邮箱地址
		prop.setProperty("mail.host", email.getStr("host"));
		//设置SMTP端口
		prop.setProperty("mail.smtp.port", email.getStr("port"));
		prop.setProperty("mail.transport.protocol", "smtp");
		prop.setProperty("mail.smtp.auth", "true");
		//设置超时时间
		prop.setProperty("mail.smtp.timeout", email.getStr("timeout"));
		//判断SSL端口
		if("SSL".equals(email.getStr("securityType"))){
			MailSSLSocketFactory sf = null;
			try {
				sf = new MailSSLSocketFactory();
			} catch (GeneralSecurityException e) {
				e.printStackTrace();
			}
			sf.setTrustAllHosts(true);
			prop.put("mail.smtp.ssl.enable", "true");
			prop.put("mail.smtp.ssl.socketFactory", sf);
		}
		//判断TLS端口
		if("TLS".equals(email.getStr("securityType"))){
			prop.put("mail.smtp.starttls.enable","true");
		}
		//返回值声明
		boolean result = false;
		//1、创建session
		Session session = Session.getInstance(prop);
		//开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
		session.setDebug(true);
		//2、创建邮件的实例对象
		Message msg = new MimeMessage(session);
		//3、通过session得到transport对象
		Transport ts;
		try {
			ts = session.getTransport();
			//4、设置发件人地址
			msg.setFrom(new InternetAddress(email.getStr("sendAccount")));
			//5、设置收件人地址 TO表示发送
			msg.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(email.getStr("testAccount")));
			//6、设置邮件主题
			msg.setSubject("测试");
			//7、设置邮件正文
			msg.setContent("系统邮箱测试通过！", "text/html;charset=UTF-8");
			//8、设置邮件的发送时间,默认立即发送
			msg.setSentDate(new Date());
			//9、使用邮箱的用户名和密码连上邮件服务器，发送邮件时，发件人需要提交邮箱的用户名和密码给smtp服务器，用户名和密码都通过验证之后才能够正常发送邮件给收件人。
			//密码一般为邮箱授权码
			ts.connect(email.getStr("host"), email.getStr("authAccount"), email.getStr("authPin"));
			//10、发送邮件，并发送到所有收件人地址，message.getAllRecipients() 获取到的是在创建邮件对象时添加的所有收件人, 抄送人, 密送人
			ts.sendMessage(msg,msg.getAllRecipients());
			//关闭资源
			ts.close();
			result = true;
		} catch (NoSuchProviderException e1) {
			result = false;
		} catch (MessagingException e) {
			result = false;
		} finally {
			return result;
		}
	}

	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (IllegalAccessException e) {
            throw new CNException("获取参数失败");
        } catch (InstantiationException e) {
            throw new CNException("获取参数失败");
        }
    }

	@Override
	public Map<String, String> queryAppearance() {
		List<SysConfigEntity> list = this.baseMapper.selectList(new LambdaQueryWrapper<SysConfigEntity>().in(SysConfigEntity::getCkey, BASIC_KEYS));
        Map<String,String> map = new HashMap<>();
        for (SysConfigEntity entity : list){
        	map.put(entity.getCkey(),entity.getCvalue());
        }
        return map;
	}

	@Override
	public void updateByKey(Map<String,String> params) {
		List<SysConfigEntity> exists = this.list(new QueryWrapper<SysConfigEntity>().lambda().in(SysConfigEntity::getCkey,params.keySet()));

        Map<String,SysConfigEntity> map = new HashMap<>();

        for (SysConfigEntity conf : exists){
            map.put(conf.getCkey(),conf);
        }

        for (String key : params.keySet()){
            if(map.containsKey(key)){
                map.get(key).setCvalue(params.get(key));
            }else{
                SysConfigEntity sysConfigEntity = new SysConfigEntity();
                sysConfigEntity.setCkey(key);
                sysConfigEntity.setCvalue(params.get(key));
                map.put(key,sysConfigEntity);
            }
        }

        this.saveOrUpdateBatch(map.values());
	}
}
