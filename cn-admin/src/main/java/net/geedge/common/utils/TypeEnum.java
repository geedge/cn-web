package net.geedge.common.utils;

/**
 * 操作日志相关枚举类型
 */
public enum TypeEnum {
	// 类型type
	ACCOUNT("account"),PANEL("panel"),CHART("chart"), VISUALPANEL("visual panel"), VISUALCHART("visual chart"), VISUALDATASOURCE("visual datasource"),
	VISUALDATASET("visual dataset"),VISUALDATASETFIELD("visual dataset field"),
	UNKNOWN("unknown"),SYSTEM("system"), LINK("link"),LICENSE("license"), SYSTEMAPIKEY("sys api key"),SETTING("proxy setting"),
	INTERFACE("proxy interface"),I18N("i18n setting"),CTL("ctl"),REPORTJOB("report job"),REPORTTEMP("report temp"),
	KNOWLEDGEBASE("knowledge base");

	private String value;

	public String getValue() {
		return value;
	}

	private TypeEnum(String value) {
		this.value = value;
	}

}
