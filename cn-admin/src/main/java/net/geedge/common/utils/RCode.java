package net.geedge.common.utils;

public enum RCode {

    /**
     * 第一位对应页面菜单项
     * 1：dashboard
     * 2：project
     * 3：assets
     * 4：alerts
     * 5：settings
     * 6: webshell
     * 7: license
     *
     * <p>
     * 第二位对应页面左侧菜单
     * 从上到下顺序
     * 1-~
     * <p>
     *
     * 第三位
     * 1：参数不能为空
     * 2：参数重复
     * 3：参数值与要求不符合
     * 4：参数值不在枚举类型内
     * 5：数值类型参数 不在合理范围内
     * 6：内容不存在
     * 7：权限相关错误
     * 8: 登陆相关错误
     * 9：文件相关错误
     * 0: 第三方服务相关错误
     */


    SUCCESS(200, "success"), //成功

    /**
     * dashboard菜单项相关
     */
    PANEL_NAME_ISNULL(121000, "Panel name is required"),
    PANEL_ID_ISNULL(121001, "Panel ID is required"),
    PANEL_NAME_DUPLICATE(122002, "Panel name already exists"),
    PANEL_TYPE_ISNULL(121003, "Panel type is required"),
    PANEL_TYPE_INVALIDE(124004, "Panel type error"),
    PANEL_LINK_ISNULL(121005,"Panel link is required when the type is not dashboard"),
    PANEL_BUILDIN_CAN_NOT_REMOVE(127006, "This panel is built-in and cannot be deleted"),
    CHART_NAME_ISNULL(121007, "Chart name is required"),
    CHART_SPAN_ISNULL(121008, "Chart span is required"),
    CHART_HEIGHT_ISNULL(121009, "Chart height is required"),
    CHART_TYPE_ISNULL(121010, "Chart type is required"),
    CHART_CREATEAT_ISNULL(121011, "Chart create time is required"),
    CHART_ELEMENT_ISNULL(121012, "Chart element is required"),
    CHART_TYPE_INVALIDE(123013, "Chart type is invalid"),
    CHART_PANEL_ISNULL(126014, "Panel does not exist"),
    CHART_NOTEXSITS(126015, "Chart does not exist"),
    CHART_ID_ISNULL(121016, "Chart ID is required"),
    CHART_UNIT_ISNULL(121017, "Chart unit is required"),
    CHART_PREV_ISNULL(121018,"Chart prev is required"),
    CHART_NEXT_ISNULL(121019,"Chart next is required"),
    CHART_CANNOT_MODIFY(126020,"Chart cannot modify"),
    CHART_PREVNEXT_INCORRECT(123021,"Prev and next is incorrect"),
    CHART_TITLE_DUPLICATE(122022,"Chart title already exists"),            // 同一个panel 下 chart title重复
    CHART_URLPARAM_ISNULL(121023,"Chart param is required when type is url"), // 当type为url时，param不能为空
    CHART_PARAM_FORMAT(123024,"Chart param must be JSON format"),               // param类型必须为json格式
    CHART_PARAMURL_ISNULL(121025,"Chart param url not found when type is url"),              // param 中未找到url key值
    CHART_PARAMURL_FORMAT(123026,"Chart param url value must be URL format"),  // param 中url的值必须为网址类型
    CHART_PARAMSINGLESTAT_ISNULL(121027,"Chart param statistics not found when type is singleStat"),
    CHART_PARAMSINGLESTAT_INVALIDE(124028,"Chart param statistics must be min, max, average, total, first, last, range  or different"),
    CHART_SINGLESTATPARAM_ISNULL(121029,"Chart param is required when type is singleStat"),
    CHART_BUILDIN_CAN_NOT_REMOVE(127030, "This chart is built-in and cannot be deleted"),
    CHARTELEMENT_EXPRESSION_ISNULL(121031, "Chart element expression is required"),
    CHARTELEMENT_TYPE_ISNULL(121032, "Chart element type is required"),
    CHARTELEMENT_TYPE_INVALIDE(123033, "Chart element type is invalid"),
    CHARTELEMENT_NOTEXSITS(126034, "chart element is not exists"),
    PANEL_PREV_ISNULL(121035,"Panel prev is required"),
    PANEL_NEXT_ISNULL(121036,"Panel next is required"),
    PANEL_NOT_EXIST(126037,"Panel does not exist"),
    PANEL_NOTFOUND_BYTYPE(126038,"There is no panel under this type"),
    PANEL_CHARTS_EXISTS(123039, "These panel contain charts and cannot be deleted"),
    PANEL_EXPORT_HEADER_LANGUAGE_ISNULL(121040,"Export header language is required"),
    PANEL_EXPORT_HEADER_LANGUAGE_ERROR(124041,"Export header language must be en, cn and ru"),
    PANEL_IMPORT_FILE_ISNULL(121042, "Panel chart import file is required"),
    PANEL_IMPORTFILE_TYPE(129043,"Only support import XLS or XLSX file"),
    PANEL_CANCELIMPORTSEQ_ISNULL(121044, "Panel chart cancel import seq is required"),
    PANEL_IMPORTFILE_FAILED(119045,"Import file resolution failed"),     // 导入文件解析失败
    PANEL_EXPORT_HEADER_TEMPLATE_ERROR(114046,"The header row of the excel import template is inconsistent with the system template"),
    CHART_WIDTH_FORMAT(123047, "Chart width format should be a integer"),
    CHART_WIDTH_INVALIDE(123048, "Chart width should be between 1 and 12"),
    CHART_HEIGHT_FORMAT(123049, "Chart height format should be a integer"),
    CHART_UNIT_NOTFOUND(126050, "Chart unit not found"),
    CHART_ELEMENTS_FORMAT(123051,"Chart elements must be JSON format"),
    PANEL_CHART_TEMPLATE_TYPE_INCORRECT(123052, "Panel charts template type must be dashboard, model and asset"),
    PANEL_CHART_IMPORT_LINKID_ISNULL(121053, "When the type is model or asset, link Id is required"),
    PANEL_CHART_TEMPLATE_TYPE_ISNULL(121054, "Panel charts template type is required"),
    CHART_TEXTPARAM_ISNULL(121055,"Chart param is required when type is text"),
    CHART_PARAMTEXT_ISNULL(121056,"Chart param text not found when type is text"),
    CHART_WEIGHT_ISNULL(121057,"Chart weight is required"),
    PANEL_PID_ISNULL(121058,"Panel pid is required"),
    PANEL_WEIGHT_ISNULL(121059,"Panel weight is required"),
    PANEL_NAME_FORMAT_ERROR(123060,"Panel name format error, cannot include '/'"),
    CHART_GROUPID_ISNULL(121061,"Chart group id is required"),
    CHART_VARTYPE_ISNULL(121062,"Chart var type is required when panel id is empty"),
    CHART_PID_ISNULL(121063,"pid is required"),
    CHART_VARID_ISNULL(121064,"varid is required"),
    CHART_SYNCTMPL_ERROR(121065,"Chart sync param is required"),
    CHART_VARID_ERROR(121066,"varid param error"),
    CHART_GROUP_NAME_DUPLICATE(121067,"chart type is group name already exists"),
    CHART_HEIGHT_INVALIDE(123068, "Chart height should be between 1 and 12"),
    CHART_X_ISNULL(121016, "Chart x is required"),
    CHART_Y_ISNULL(121016, "Chart y is required"),
    CHART_W_ISNULL(121016, "Chart weight is required"),
    CHART_H_ISNULL(121016, "Chart height is required"),
    DATASOURCE_TYPE_ISNULL(121069, "Datasource type is required"),
    DATASOURCE_ID_ISNULL(1210070, "Datasource ID is required"),
    DATASOURCE_NAME_ISNULL(121071, "Datasource name is required"),
    DATASOURCE_TYPE_INVALIDE(123072, "Datasource type is invalid"),
    DATASOURCE_NAME_DUPLICATE(122073, "Datasource name already exists"),
    DATASOURCE_BUILDIN_CAN_NOT_REMOVE(127074, "This datasource is built-in and cannot be deleted"),
    DATASET_ID_ISNULL(1210075, "Dataset ID is required"),
    DATASET_NAME_ISNULL(121076, "Dataset name is required"),
    DATASET_TYPE_ISNULL(121077, "Dataset type is required"),
    DATASET_TYPE_INVALIDE(123078, "Dataset type is invalid"),
    DATASET_NAME_DUPLICATE(122079, "Dataset name already exists"),
    DATASET_BUILDIN_CAN_NOT_REMOVE(127080, "This dataset is built-in and cannot be deleted"),
    DATASET_FIELD_ID_ISNULL(1210081, "Dataset field ID is required"),
    DATASET_FIELD_NAME_ISNULL(121082, "Dataset field name is required"),
    DATASET_FIELD_TYPE_ISNULL(121083, "Dataset field type is required"),
    DATASET_FIELD_TYPE_INVALIDE(123084, "Dataset field type is invalid"),
    DATASET_FIELD_STYPE_ISNULL(121085, "Dataset field stype is required"),
    DATASET_FIELD_STYPE_INVALIDE(123086, "Dataset field stype is invalid"),
    DATASET_FIELD_NAME_DUPLICATE(122087, "Dataset field name already exists"),
    DATASET_FIELD_BUILDIN_CAN_NOT_REMOVE(127088, "This dataset field is built-in and cannot be deleted"),
    DATASET_FIELD_DSETID_ISNULL(121089, "Dataset field dsetid is required"),
    DATASET_FIELD_IDX_ISNULL(121090, "Dataset field idx is required"),
    DATASET_FIELD_EXPR_ISNULL(121091, "Dataset field expr is required"),
    DATASET_DSID_ISNULL(121092, "Dataset dsid is required"),

    GALAXY_PROXY_ID_ISNULL(210001,"galaxy proxy id is required"),
    GALAXY_PROXY_NAME_ISNULL(210002,"galaxy proxy name is required"),
    GALAXY_PROXY_PATH_ISNULL(210003,"galaxy proxy path is required"),
    GALAXY_PROXY_METHOD_ISNULL(210004,"galaxy proxy method is required"),
    GALAXY_PROXY_VERSION_ISNULL(210005,"galaxy proxy version is required"),
    GALAXY_PROXY_TARGET_URL_ISNULL(210006,"galaxy proxy target url is required"),
    GALAXY_PROXY_TARGET_PARAM_ISNULL(210007,"galaxy proxy target param is required"),
    GALAXY_PROXY_TARGET_HEADER_ISNULL(210008,"galaxy proxy target header is required"),
    GALAXY_PROXY_PRE_HANDLE_ISNULL(210009,"galaxy proxy pre handle is required"),
    GALAXY_PROXY_POST_HANDLE_ISNULL(210010,"galaxy proxy post handle is required"),
    GALAXY_PROXY_CONFIG_URL_ISNULL(21001,"galaxy proxy config url is empty"),
    /**
     * setting菜单项相关
     */
    SYS_ERROR(517000, "system error"), //10xxxx，系统类（业务系统、用户组、权限等）通用错误以及其他系统内部错误

    SYS_DB_DUPLICATERECORD(512001, "Duplicate record"), //数据库中已存在该记录
    SYS_DB_AUTH(517002, "Permission denied"),            //没有权限
    SYS_LOGIN_CAPTCHA(518003, "Captcha verification failed"),    //登录验证码错误
    SYS_LOGIN_UNKNOWNACCOUNT(518004, "Unknown error"),            //未知校验错误
    SYS_LOGIN_USERPWD(518005, "Incorrect username or password"), //账号或密码错误
    SYS_LOGIN_LOCK(518006, "Locked account"),                      //账号被锁定
    SYS_LOGIN_ACCOUNTAUTH(518007, "Incorrect username or password"), //账号验证失败
    SYS_LOGIN_REQUIRED(518008, "Please log in to the system first"), //账号验证失败
    SYS_MENU_SYSMENUDEL(517009, "Delete system menu is not allowed"), //系统菜单，不能删除
    SYS_MENU_DELSUB(517010, "Please delete the submenu first"),       //请先删除子菜单或按钮
    SYS_USER_OLDPWD(518011, "Incorrect old pwd"), //原密码不正确
    SYS_USER_DELADMIN(517012, "'Admin' cannot be deleted"), //系统管理员不能删除
    SYS_USER_CANNOTDEL(517013, "The currently logged in user cannot be deleted"),          //当前登录用户不能删除
    SYS_USER_DUPLICATENAME(517014, "Duplicate user name duplicate"), //业务系统选择错误
    SYS_USER_NOT_EXIST(516015, "System user does not exist"),    //系统用户不存在
    SYS_USER_USERNAME_ISNULL(511016, "System user login name is required"),
    SYS_USER_STATUS_ISNULL(511017, "System user status is required"),
    SYS_USER_PASSWORD_ISNULL(511018, "System user password is required"),
    SYS_USER_ID_ISNULL(511019, "System user id is required"),
    SYS_USER_SCRIPT_ACCOUNT_REPEATED(512020, "System user script id and account cannot be repeated"),
    SYS_USER_STATUS_INVALIDE(514021, "System user status must be 0 or 1"),
    SYS_USER_EMAIL_FORMAT(513022, "System user email format is incorrect"),
    SYS_MENU_ISNULL(516023, "Menu does not exist"),
    SYS_MENU_ID_ISNULL(511024, "Menu id is required"),
    SYS_MENU_NAME_ISNULL(511025, "Menu name is required"),
    SYS_MENU_CODE_ISNULL(511026, "Menu code is required"),
    SYS_MENU_I18N_ISNULL(511027, "Menu i18n is required"),
    SYS_MENU_TYPE_ISNULL(511028, "Menu type is required"),
    SYS_MENU_ORDERNUM_ISNULL(511029, "Menu order num is required"),
    SYS_MENU_CODE_DUPLICATE(512030, "Menu code duplicate"),
    SYS_MENU_TYPE_INVALIDE(513031,"Menu type not found"),
    SYS_MENU_BUTTON_PARENTID_ISNULL(511032,"Button must have a parent id"),
    SYS_MENU_BUTTON_PARENTTYPE_INVALIDE(513033,"Button cannot be used as a parent menu"),
    SYS_TOKEN_ISNULL(511034,"Cannot get token information from request header"),
    SYS_USER_NAME_ISNULL(511035, "System user name is required"),
    SYS_USER_NAME_DUPLICATE(511036, "System user name duplicate"),
    SYS_MENU_TAB_PARENTTYPE_INVALIDE(513037,"Tab cannot be used as a parent menu"),
    
    LINK_ID_ISNULL(581001,"Link id is required"),
    LINK_NAME_ISNULL(581002,"Link name is required"),
    LINK_URL_ISNULL(581003,"Link url is required"),
    LINK_PREV_ISNULL(581004,"Link prev is required"),
	LINK_NEXT_ISNULL(581005,"Link next is required"),
	LINK_BUILDIN_CAN_NOT_REMOVE(587006,"This link is built-in and cannot be deleted"),
	LINK_WEIGHT_PARAM_INVALID(587007,"Link weight param is invalid"),

    SYS_I18N_DUPLICATE(542000, "duplicate error (code + lang)"), //国际化配置重复（code+lang）
    SYS_I18N_NOSUCHLANG(543001, "no such lang"),                  //不合法的lang
    SYS_I18N_ID_ISNULL(543002," i18n id is required"),
    SYS_I18N_PARAM_ISNULL(543003," param is required"),
    SYS_I18N_NAME_ISNULL(543004,"name is required"),
    SYS_I18N_CODE_ISNULL(543005,"code is required"),
    SYS_I18N_LANG_ISNULL(543006,"lang is required"),
    SYS_I18N_VALUE_ISNULL(543007,"value is required"),

    SYS_DICT_NAME_ISNULL(551000, "Field 'name' is required"),        // 字典名称不能为空
    SYS_DICT_TYPE_ISNULL(551001, "Field 'type' is required"),        // 字典类型不能为空
    SYS_DICT_VALUE_ISNULL(551002, "Field 'value' is required"),       // 字典值不能为空
    SYS_DICT_CODE_ISNULL(551003, "Field 'code' is required"),        // 字典值不能为空
    SYS_DICT_VALUE_DUPLICATE(552004, "Field 'type' already exists"),  //  字典值重复
    SYS_DICT_ID_INVALIDE(553005, "Invalid system dictionary ID"),
    SYS_DICT_ID_ISNULL(551006, "System dictionary ID is required"),
    SYS_DICT_REMOVE_ERROR(553007,"Those dict params contain model cannot remove"),
    SYS_DICT_CODE_EXISTS(551008, "Field 'code' already exists"),        // 字典值code已存在

    SYS_CONFIG_TYPE_ISNULL(571000,"System config type is required"), // sysConfig 查询type参数不能为空
    SYS_CONFIG_TYPE_INVALIDE(574001,"System config type is invalid"),   // type 不在枚举值类
    SYS_CONFIG_SAVEENTITY_ISNULL(571002,"System config save entity is required"),// 保存实体数据不能为空
    SYS_CONFIG_MAXTERMINALNUM_ISNULL(571003, "System config max terminal num is required"),// 每个用户最大打开窗口数量不能为空
    SYS_CONFIG_MAXTERMINALNUM_INVALIDE(575004, "System config max terminal num is invalid, Its minimum value is integer 1"),// 参数值不正确
    SYS_CONFIG_ALERTAPI_ISNULL(571005,"System config alert api is required"),    //  alert api 不能为空
    SYS_CONFIG_SWITCHVAUE_INVALIDE(574006,"System config switch value is invalid, must be on or off"), // 开关值不在枚举范围内
    SYS_CONFIG_ASSETPINGSWITCH_ISNULL(571007,"System config asset pings witch is required"), // assetpingswitch不能为空
    SYS_CONFIG_NODETARGETPATH_ISNULL(571008,"System config node export target path is required"), // node exporter targer path不能为空
    SYS_CONFIG_ASSETPINGINTERVAL_ISNULL(571009,"System config asset ping interval is required"),  // asset_ping_interval不能为空
    SYS_CONFIG_ASSETPINGINTERVAL_INVALIDE(573010,"System config asset ping interval should be an integer"),    // asset_ping_interval参数与要求不符合
    SYS_CONFIG_PORT_INVALID(573011,"port must be between 1 and 65535"),
    SYS_CONFIG_EMAILENABLE_ISNULL(571011,"System config email enable is required"),               // email_enable 不能为空
    SYS_CONFIG_EMAILSMTPHOST_ISNULL(571012,"System config email smtp host is required"),         // email_smtp_host 不能为空
    SYS_CONFIG_EMAILSMTPHOST_FORMAT(573013,"System config email smtp host format error"), // email_smtp_host 格式不正确
    SYS_CONFIG_EMAILSMTPPORT_ISNULL(571014,"System config email smtp port is required"), // email_smtp_port 不能为空
    SYS_CONFIG_EMAILSMTPPORT_ERROR(575015,"System config email smtp port is invalid"),        // email_smtp_port 端口错误
    SYS_CONFIG_EMAILSMTPACCOUNT_ISNULL(571016,"System config email smtp account is required"), // email_smtp_account 不能为空
    SYS_CONFIG_EMAILSMTPPASSWORD_ISNULL(571017,"System config email smtp password is required"), // email_smtp_password 不能为空
    SYS_CONFIG_EMAILSENDACCOUNT_ISNULL(571018,"System config email send account is required"), // email_send_account 不能为空
    SYS_CONFIG_EMAILTESTRECIVER_ISNULL(571019,"System config email test receiver is required"), // email_test_reciver 不能为空
    SYS_CONFIG_EMAILTESTRECIVER_FORMAT(573020,"System config email test receiver format error, format such as xxx@xxx.com"), // email_test_reciver 邮箱格式错误
    SYS_CONFIG_EMAILSSLFLAG_FORMAT(571021,"System config email ssl flag is required"),         // email_ssl_flag 不能为空
    SYS_CONFIG_EMAILTLSFLAG_FORMAT(571022,"System config email tls flag is required"),         // email_tls_flag 不能为空
    SYS_CONFIG_EMAILSSLFLAG_INVALIDE(573023,"System config email ssl flag is invalid"),            // email_ssl_flag 参数与要求不符合
    SYS_CONFIG_EMAILTLSFLAG_INVALIDE(573024,"System config email tls flag is invalid"),            // email_tls_flag 参数与要求不符合
    SYS_CONFIG_EMAILPROTOCOL_INVALIDE(573025,"System config email security type is invalid"),            // email_tls_flag 参数与要求不符合
    SYS_CONFIG_LDAPENABLE_ISNULL(571025,"System config ldap enable is required"),              // ldap_enable 不能为空
    SYS_CONFIG_LDAPADDRESS_ISNULL(571026,"System config ldap address is required"),            // ldap_address 不能为空
    SYS_CONFIG_LDAPDN_ISNULL(571027,"System config ldap dn is required"),                      // ldap_dn 不能为空
    SYS_CONFIG_LDAPPASSWORD_ISNULL(571028,"System config ldap password is required"),         // ldap_password 不能为空
    SYS_CONFIG_LDAPUSERFILTER_ISNULL(571029,"System config ldap user filter is required"),    // ldap_user_filter 不能为空
    SYS_CONFIG_LDAPMAPPING_ISNULL(571030,"System config ldap mapping is required"),            // ldap_mapping 不能为空
    SYS_CONFIG_PASSWORDORTYPE_INVALID(571031,"The password or type is invalid when reset"),
    SYS_CONFIG_PASSWORD_ERROR(571032,"The password is different from the original one when reset"),
    SYS_CONFIG_QUERYPARAMS_ISNULL(571033,"Type and paramKey is required at the same time when sysconfig queries"),// 查询sysConfig时， type 和 paramKey不能同时为空
    SYS_CONFIG_SCRAPEINTERVAL_ISNULL(571034,"System config scrape interval is required"),            // scrape_interval 不能为空
    SYS_CONFIG_LOCALRETENTION_ISNULL(571035,"System config storage local retention is required"),   // storage_local_retention 不能为空
    SYS_CONFIG_SYSTEMNAME_ISNULL(571036,"System config system name is required"),                    // system_name 不能为空
    SYS_CONFIG_SCRAPEINTERVAL_INVALIDE(573037,"System config scrape interval should be an integer"),    // scrape_interval参数与要求不符合
    SYS_CONFIG_LOCALRETENTION_INVALIDE(573038,"System config storage local retention should be an integer"),    // storage_local_retention参数与要求不符合
    SYS_CONFIG_EMAILSMTPPORT_INVALIDE(573039,"System config email smtp port should be an integer"),     // email_smtp_port参数与要求不符合
    SYS_CONFIG_EMAILSMTPACCOUNT_FORMAT(573040,"System config email smtp account format error, format such as xxx@xxx.com"), //email_smtp_account应为邮件格式
    SYS_CONFIG_LDAPOU_ISNULL(571041,"System config ldap ou is required"),                            // ldap_ou 不能为空
    SYS_CONFIG_TEST_ISNULL(571042,"System config test is required"),                                 // test 不能为空
    SYS_CONFIG_EMAIL_AUTHENTICATIONFAIL(570043,"Failed to connect to SMTP service: username/password wrong"),
    SYS_CONFIG_EMAIL_CONNECTFAIL(570044,"Unable to establish a connection with the SMTP service. Please check whether the SMTP service is running normally and can connect externally, such as host and port information."),
    SYS_CONFIG_EMAIL_OTHERFAILURES(570045,"The connection to the SMTP service timed out. Please check the network condition"),
    SYS_CONFIG_LDAP_AUTHENTICATIONFAIL(570046,"ldap service config connection failed : username/password wrong"),
    SYS_CONFIG_LDAP_CONNECTIONFAILED(570047,"Unable to establish a connection with the ldap service. Please check whether the LDAP service is running normally and can connect externally."),
    SYS_CONFIG_TIMEZONE_ISNULL(571048,"System config timezone is required"),                    // timezone 不能为空
    SYS_CONFIG_TIMEZONE_INVALIDE(573049,"System config timezone should be an integer"),             // timezone参数与要求不符合
    SYS_CONFIG_TIMEZONE_INTERVAL(575050,"The interval value of timezone is between -12 and +12"),  // timezone 区间值为-12至+12之间
    SYS_CONFIG_DEFALUTCABINETU_INTERVAL(573051,"System config default cabinet usize is invalid"), // 配置表中机柜默认值有误
    SYS_CONFIG_LDAP_OU_NOT_EXIST(570052,"Ldap ou not exist"),                                      // ldap连接ou不存在
    SYS_CONFIG_LDAPMAPPING_FORMAT_ERROR(571053,"System config ldap mapping format error"),         // ldap_mapping格式错误 非正常json格式
    SYS_CONFIG_TESTEMAIL_TEMPLATE_NOTFOUND(576054,"System config test email sending template not found"),// 发送测试邮件模板未找到
    SYS_CONFIG_TESTEMAIL_TEMPLATE_FAILED(570055,"Test email template generation failed"),                // freemarker生成发送邮件模板内容失败
    SYS_CONFIG_EMAILSENDCCOUNT_FORMAT(573056,"System config email send account format error"),           //email_send_account应为邮件格式
    SYS_CONFIG_RESET_FAIL(573057,"System reset fail, check config and try again later"),
    SYS_CONFIG_MODELCHARTTPL_NOTFOUND(576058,"System config model chart tpl not found"),
    SYS_CONFIG_PROJECTCHARTTPL_NOTFOUND(576059,"System config project chart tpl not found"),
    SYS_CONFIG_DEFAULTCABINETUSIZE_INVALIDE(573060,"System config default cabinet usize should be an integer"),
    SYS_CONFIG_QUERYMAXSERIES_ISNULL(571061, "System config Query max series is required"),
    SYS_CONFIG_QUERYMAXSERIES_INVALIDE(573062, "System config Query max series should be an integer"),
    SYS_CONFIG_QUERYMAXSERIES_INTERVAL(575063, "The interval value of Query max series is between -1 and +1000"),
    SYS_CONFIG_EXPORT_MAX_LINE(579064,"The number of exported data than the configured max export line"),
    SYS_CONFIG_UNSAVEDCHANGE_ISNULL(571065, "System config unsaved change is required"),
    SYS_CONFIG_CODE_FILE_ISNULL(574001,"The validate file is not exist"), //不存在校验使用的文件
    SYS_CONFIG_CODE_INVALID(574002,"The code is invalid"),//校验文件中的code与传递的值不符
    SYS_CONFIG_DB_PARAM_INVALID(574003,"The DB connect params is invalid"),//校验文件中的code与传递的值不符
    SYS_CONFIG_REDIS_PASSWORD_INVALID(574004,"The redis password is invalid"),
    SYS_CONFIG_REDIS_PASSWORD_REQUIRED(574005,"The redis password is required"),
    SYS_CONFIG_REDIS_PARAM_INVALID(574006,"The redis params is invalid"),
    SYS_CONFIG_DB_URL_ISNULL(574007,"The DB host is required"),
    SYS_CONFIG_DB_USERNAME_ISNULL(574008,"The DB username is required"),
    SYS_CONFIG_DB_PASSWORD_ISNULL(574009,"The DB password is required"),
    SYS_CONFIG_REDIS_HOST_ISNULL(574010,"The redis host is required"),
    SYS_CONFIG_REDIS_PORT_ISNULL(574011,"The redis host is required"),
    SYS_CONFIG_HAD_CONFIG(574012,"Someone has started to configure the system"),
    SYS_CONFIG_SESSION_TIMEOUT_INVALID(573013,"Session timeout is not less than 15"),
    SYS_CONFIGI_PROMEFEDEENABLED_NCORRECT(574014, "Prometheus federation enabled must be 0 or 1"),
    SYS_CONFIGI_ASSETPINGFROM_NCORRECT(574015, "Asset ping from must be 1 or 2"),

    SYS_ROLE_ID_ISNULL(581000, "Role id is required"),
    SYS_ROLE_ISNULL(586001, "Role does not exist"),
    SYS_ROLE_NAME_ISNULL(581002, "Role name is required"),
    SYS_ROLE_REMARK_ISNULL(581003, "Role remark is required"),
    SYS_ROLE_NAME_DUPLICATE(582004, "Role name duplicate"),
    SYS_ROLE_MENUID_ISNULL(581005, "Role related menu id is required"),
    SYS_ROLE_NAME_CANNOT_MODIFY(587006, "The name of the built-in role cannot be modified"),
    SYS_ROLE_CANNOT_DELETE(587007, "Built-in roles cannot be deleted"),
    
    KNOWLEDGE_BASE_PARAM_DUPLICATE(591001,"KnowledgeBase names and types are guaranteed to be unique"),
    KNOWLEDGE_BASE_PARAM_NOTNULL(591002,"KnowledgeBase data is required"),
    KNOWLEDGE_BASE_NOT_EXIST(591003,"KnowledgeBase does not exist"),
    KNOWLEDGE_NAME_ISNULL(591004,"KnowledgeBase name is required"),
    KNOWLEDGE_TYPE_ISNULL(591005,"KnowledgeBase type is required"),
    KNOWLEDGE_FILE_TYPE_ERROR(591006,"KnowledgeBase file type only supports csv"),
    KNOWLEDGE_FILE_ISNULL(591007,"KnowledgeBase file is required"),
    KNOWLEDGE_IDS_ISNULL(591008,"KnowledgeBase ids is required"),
    KNOWLEDGE_ID_ISNULL(591009,"KnowledgeBase id is required"),
    KNOWLEDGE_QGW_SERVICE_ERROR(591010,"Gateway service interface response error"),
    KNOWLEDGE_HOS_ERROR(591011,"HOS service response error"),
    KNOWLEDGE_BUILTIN_ERROR(591012,"KnowledgeBase built-in data can not be removed"),
    KNOWLEDGE_BASE_ITEM_NOT_EXIST(591013,"KnowledgeBase item not exist"),
    KNOWLEDGE_BASE_SYNC_VERSION_NOT_EXIST(591014,"KnowledgeBaseSyncVersion does not exist"),
    KNOWLEDGE_BASE_ENABLE_PARAMS_INVALID(591015,"KnowledgeBase enable params is invalid"),
    KNOWLEDGE_BASE_NAME_DUPLICATE(591016,"KnowledgeBase name already exists"),
    KNOWLEDGE_BASE_PARAM_FORMAT_ERROR(591017,"KnowledgeBase data format error, please check"),
    /**
     * stat相关
     */
    DIMENSION_IS_NULL(811001,"dimension param is required"),
    SYSAPIKEY_NAME_ISNULL(811002,"sys api key name is required"),
    SYSAPIKEY_ROLEID_ISNULL(811003,"sys api key role id is required"),
    SYSAPIKEY_ID_ISNULL(811004,"sys api key id is required"),
    SYSAPIKEY_NAME_DUPLICATE(811005,"sys api key name already exists"),
    
    NOT_NULL_ERROR(990, "not null error"), //not null错误
    NOT_NUMBER_ERROR(991, "not number error"), //not number错误
    READ_TIMED_OUT(992, "Response timeout"),
    CONNECTION_REFUSED(993, "Connection refused"),
    /**
     * 错误的时间格式
     */
    DateFormatWrong(40090018,"Incorrect Date Format"),
    
    /**
	 * License 相关
	 */
	LICENSE_FILE_IS_NULL(711001, "license file is required"),
	LICENSE_FILE_IS_EMPTY(716001, "The license file does not exist"), LICENSE_FILE_INVALID(715001, "Invalid license"),
	LICENSE_PARAM_IS_NULL(711002, "license parameter is required"),
	LICENSE_TOKEN_INVALID(715002, "Invalid license,token error"),
	LICENSE_MAXIMUMASSETS_INVALID(715003, "The number of assets exceeds the maximumAssets"),
	LICENSE_VERSION_INVALID(715004, "The version is not valid"),
	LICENSE_FILE_TYPE_ERROR(715005,"license upload file only suppose xml"),

	REPORT_JOB_ID_ISNULL(311001,"Report job id is required"),
	REPORT_TEMP_ID_ISNULL(311002,"Report temp id is required"),
	REPORT_TEMP_NAME_ISNULL(311003,"Report temp id is required"),
	REPORT_TEMP_HTML_CONTENT_ISNULL(311004,"Report temp html content is required"),
	REPORT_TEMP_CONFIG_ISNULL(311005,"Report temp id is required"),
	REPORT_NOT_FINSHED(311006,"Report not finshed yet please try again later"),
	REPORT_TEMP_CREAT_JOB_FAILED(311007,"Report create job failed"),
	REPORT_TEMP_NOT_EXISTS(311008,"Report temp not exists"),
	REPORT_ID_ISNULL(311009,"Param id is required"),
	REPORT_TYPE_ISNULL(311010,"Param type is required"),
	REPORT_CATEGORY_ID_ISNULL(311011,"Report category id is required"),
	REPORT_SCHEDULER_TYPE_IS_NULL(311012,"Report scheduler type is required"),
	REPORT_SCHEDULER_CRON_IS_NULL(311013,"Report scheduler cron is required"),
	REPORT_BUILD_IN_ERROR(311014,"Report build_in data cannot remove"),
	REPORT_CRON_MONTH_WEEK_DATE_ERROR(311015,"Report cron month week date error must in (1-5)"),
	
    ERROR(999, "error"); //通用错误/未知错误

	
	
	private RCode(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	private Integer code;
	private String msg;

	public Integer getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
}
