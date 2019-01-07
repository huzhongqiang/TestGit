package cn.grassinfo.wap.entity;

/**
 * 农业气象实况数据
 * @author 迪
 */
public class NyqxCurrentDataEx {

	private String title;
	private String type;
	private String value;
	
	/**
	 * @return 标题（要素名）
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * 设置要素名
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return 字段名
	 */
	public String getType() {
		return type;
	}
	/**
	 * 设置字段名
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return 要素值
	 */
	public String getValue() {
		return value;
	}
	/**
	 * 设置要素值
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
}
