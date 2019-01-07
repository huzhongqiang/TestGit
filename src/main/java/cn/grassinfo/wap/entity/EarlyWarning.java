package cn.grassinfo.wap.entity;

//预警信息
public class EarlyWarning {
	private String code;
	private String content;
	private String img;
	private String name;
	
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
