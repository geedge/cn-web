/**

 *

 *

 */

package net.geedge.common.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.log.Log;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.poi.util.TempFile;
import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 常量
 *
 * 
 */
public class Constant {
	private final static Log logger = Log.get();
	/** 超级管理员ID */
	public static final int SUPER_ADMIN = 1;

	// 系统 普通用户角色
	public static final int SYSTEM_COMMON_ROLE = 2;
	/** 数据权限过滤 */
	public static final String SQL_FILTER = "sql_filter";
	/**
	 * 当前页码
	 */
	public static final String PAGE = "pageNo";
	/**
	 * 每页显示记录数
	 */
	public static final String LIMIT = "pageSize";
	/**
	 * 每页显示条数
	 */
	public static final long PAGESIZE = 10;

	/**
	 * 排序方式
	 */
	public static final String ORDER = "orderBy";
	/**
	 * 升序
	 */
	public static final String ASC = "asc";

	/**
	 * mysql text 最大长度 65535
	 */
	public static final Integer MYSQL_TEXT_MAXLENGTH = 65535;

	/**
	 * 统一utf-8字符编码
	 */
	public static final String DEFAULT_CHARTSET_NAME = "UTF-8";

	/**
	 * 菜单类型
	 */
	public enum MenuType {
		/**
		 * 目录
		 */
		CATALOG(0),
		/**
		 * 菜单
		 */
		MENU(1),
		/**
		 * 按钮
		 */
		BUTTON(2),

		/**
		 * tab标签
		 */
		TAB(3);

		private int value;

		MenuType(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}
	}

	/**
	 * 当前数据库类型
	 */
	//public static final String DB_TYPE;

	public static final String ORACLE = "oracle";
	public static final String MYSQL = "mysql";
	public static final String POSTGRESQL = "postgresql";
	/**
	 * 临时目录
	 */
	public static final String TEMP_PATH = System.getProperty("user.dir") + File.separator + "tmp";

	/**
	 * 工具目录
	 */
	public static final String TOOL_PATH = System.getProperty("user.dir") + File.separator + "tools";
	
	/**
	 * 工具目录
	 */
	public static final String CONFIG_PATH = System.getProperty("user.dir") + File.separator + "config";

	// 图表 singleStat statistics可选值列表
	public static final List<String> CHART_SINGLESTAT_STATISTICS;
	
	/**
	 * 表名 和 排序字段对应关系 KEY: tablename
	 */
	public static final Map<String, Map<String, String>> TABLE_NAME_ORDER_FIELD_MAPPING = Tool.MapUtil.newHashMap();

	static {
		/*DatabaseIdProvider databaseIdProvider = SpringContextUtils.getBean("getDatabaseIdProvider",
				DatabaseIdProvider.class);
		DataSource dataSource = SpringContextUtils.getBean("dataSource", DataSource.class);
		String databaseId = null;
		try {
			databaseId = databaseIdProvider.getDatabaseId(dataSource);
		} catch (SQLException e) {
			logger.error(e);
		}
		DB_TYPE = databaseId;*/

		File tempPath = FileUtil.file(TEMP_PATH);
		if (!tempPath.isDirectory()) {
			boolean mkdir = tempPath.mkdir();
			if (mkdir) {
				logger.debug("临时目录tmp创建成功");
			} else {
				logger.debug("临时目录tmp创建失败");
			}
		}

		// 设置 POI 临时目录为项目 临时目录
		TempFile.setTempFileCreationStrategy(new TempFile.DefaultTempFileCreationStrategy(tempPath));

		CHART_SINGLESTAT_STATISTICS = new ArrayList<>();
		CHART_SINGLESTAT_STATISTICS.add("min");
		CHART_SINGLESTAT_STATISTICS.add("max");
		CHART_SINGLESTAT_STATISTICS.add("average");
		CHART_SINGLESTAT_STATISTICS.add("total");
		CHART_SINGLESTAT_STATISTICS.add("first");
		CHART_SINGLESTAT_STATISTICS.add("last");
		CHART_SINGLESTAT_STATISTICS.add("range");
		CHART_SINGLESTAT_STATISTICS.add("different");
		
		Map<String, String> reportOrderFieldMap = new HashMap<>();
		reportOrderFieldMap.put("name", "rj.name");
		reportOrderFieldMap.put("type", "rt.id");
		
		Map<String, String> knowledgeOrderFieldMap = new HashMap<>();
		knowledgeOrderFieldMap.put("name", "tag_name");
		TABLE_NAME_ORDER_FIELD_MAPPING.put("knowledge_base", knowledgeOrderFieldMap);
	}

	/**
	 * 字典中语言的名称和类型
	 */
	public static final String DICT_NAME_LANG = "lang";

	public static final String AUTH_TOKEN_CODE = "Cn-Authorization";

	// cn-web 新增常量

	// 字典表中 chart 类型的 type 值
	public static final String DICT_CHART_TYPE = "chartType";

	// 字典表中 chart element 表达式类型的 type 值
	public static final String DICT_EXPRESSION_TYPE = "exprType";

	// ip 校验正则
	public static final String REGEX_IP = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d|\\*)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d|\\*)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d|\\*)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d|\\*)";

	// 二级域名
	public static final String REGEX_DOMAIN = "(\\w*\\.?){2}\\.(com.cn|net.cn|gov.cn|org\\.nz|org.cn|com|net|org|gov|cc|biz|info|cn|co)$";

	// panel chart link 链表结构 首位 prev 值
	public static final Integer START_PREV_VALUE = 0;

	// panel chart link 链表结构 末尾 next 值
	public static final Integer END_NEXT_VALUE = -1;

	// 高可用
	public static final String SERVER_ID = Tool.IdUtil.fastUUID();
	public static final String SYS_HA_LOCK = "sys_ha_lock";
	
	public static final String CN_ROOT_PATH = StrUtil.subBefore(System.getProperty("user.dir"), StrUtil.C_SLASH,
			true);
	public static final String CNWEB_ROOT_PATH = CN_ROOT_PATH + "/cn-web";
	public static final String TMP_ROOT_PATH = CN_ROOT_PATH + "/cn-web/tmp";
	public static final String GUI_ROOT_PATH = CN_ROOT_PATH + "/cn-web/webRoot";
	public static final String RESOURCE_ROOT_PATH = CN_ROOT_PATH + "/cn-web/resource";// 
	public static final String BIN_ROOT_PATH = CN_ROOT_PATH + "/cn-web/bin";// promtool,generator
	public static final String SETUP_FILE_NAME = "setup.json";
	public static final String SYSCONFIG_SESSION_TIMEOUT_KEY = "session_timeout";

	//AES密钥
	public static final byte[] AES_SECRET_KEY = Tool.HexUtil.decodeHex("bde5430614b21baf1c53bd6f616d1a39");
	
	public final static String CN_REPORT_CRON_GROUP="cn_report_cron_group";
	
	public final static String CN_REPORT_SINGLE_GROUP="cn_report_single_group";
}
