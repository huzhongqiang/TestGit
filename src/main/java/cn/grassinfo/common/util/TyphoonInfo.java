package cn.grassinfo.common.util;

public class TyphoonInfo {

	private String bianhao;//编号
	private String fabuzhe;//发布者
	private String zhongwenbianhao;//中文编号
	private String guojibianhao;//国际编号
	private String xianzaishijian;//现在时间
	private String yubaoshixiao;//预报时效
	private String xianzaijindu;//现在经度
	private String xianzaiweidu;//现在纬度
	private String yubaojindu;//预报经度
	private String yubaoweidu;//预报纬度
	private String xianzaiqiya;//现在气压
	private String xianzaifengli;//现在风力
	private String fenglifor7;//7级风力
	private String fenglifor10;//10级
	private String yidongsudu;//移动速度
	private String yidongfangxiang;//移动方向
	private String yubaofengli;//预报风力
	private String yubaoqiya;//预报气压
	private String taifengjibie;//台风级别
	private String timeString;//时次
	
	
	public String getTimeString() {
		return timeString;
	}
	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
	public String getGuojibianhao() {
		return guojibianhao;
	}
	public void setGuojibianhao(String guojibianhao) {
		this.guojibianhao = guojibianhao;
	}
	public String getTaifengjibie() {
		return taifengjibie;
	}
	public void setTaifengjibie(String taifengjibie) {
		this.taifengjibie = taifengjibie;
	}
	public String getBianhao() {
		return bianhao;
	}
	public void setBianhao(String bianhao) {
		this.bianhao = bianhao;
	}
	public String getFabuzhe() {
		return fabuzhe;
	}
	public void setFabuzhe(String fabuzhe) {
		this.fabuzhe = fabuzhe;
	}
	public String getZhongwenbianhao() {
		return zhongwenbianhao;
	}
	public void setZhongwenbianhao(String zhongwenbianhao) {
		this.zhongwenbianhao = zhongwenbianhao;
	}
	public String getXianzaishijian() {
		return xianzaishijian;
	}
	public void setXianzaishijian(String xianzaishijian) {
		this.xianzaishijian = xianzaishijian;
		this.timeString = xianzaishijian.substring(0, 2)+"月"+xianzaishijian.substring(2, 4)+"日"+xianzaishijian.substring(4, 6)+"时";
	}
	public String getYubaoshixiao() {
		return yubaoshixiao;
	}
	public void setYubaoshixiao(String yubaoshixiao) {
		this.yubaoshixiao = yubaoshixiao;
	}
	public String getXianzaijindu() {
		return xianzaijindu;
	}
	public void setXianzaijindu(String xianzaijindu) {
		this.xianzaijindu = xianzaijindu;
	}
	public String getXianzaiweidu() {
		return xianzaiweidu;
	}
	public void setXianzaiweidu(String xianzaiweidu) {
		this.xianzaiweidu = xianzaiweidu;
	}
	public String getYubaojindu() {
		return yubaojindu;
	}
	public void setYubaojindu(String yubaojindu) {
		this.yubaojindu = yubaojindu;
	}
	public String getYubaoweidu() {
		return yubaoweidu;
	}
	public void setYubaoweidu(String yubaoweidu) {
		this.yubaoweidu = yubaoweidu;
	}
	public String getXianzaiqiya() {
		return xianzaiqiya;
	}
	public void setXianzaiqiya(String xianzaiqiya) {
		this.xianzaiqiya = xianzaiqiya;
	}
	public String getXianzaifengli() {
		return xianzaifengli;
	}
	public void setXianzaifengli(String xianzaifengli) {
		this.xianzaifengli = xianzaifengli;
	}
	public String getFenglifor7() {
		return fenglifor7;
	}
	public void setFenglifor7(String fenglifor7) {
		if(!"".equals(fenglifor7)&&fenglifor7!=null){
			this.fenglifor7 = fenglifor7;
		}else{
			this.fenglifor7 = "0";
		}
	}
	public String getFenglifor10() {
		return fenglifor10;
	}
	public void setFenglifor10(String fenglifor10) {
		if(!"".equals(fenglifor10)&&fenglifor10!=null){
			this.fenglifor10 = fenglifor10;
		}else{
			this.fenglifor10 = "0";
		}
	}
	public String getYidongsudu() {
		return yidongsudu;
	}
	public void setYidongsudu(String yidongsudu) {
		this.yidongsudu = yidongsudu;
	}
	public String getYidongfangxiang() {
		return yidongfangxiang;
	}
	public void setYidongfangxiang(String yidongfangxiang) {
		this.yidongfangxiang = yidongfangxiang;
	}
	public String getYubaofengli() {
		return yubaofengli;
	}
	public void setYubaofengli(String yubaofengli) {
		this.yubaofengli = yubaofengli;
	}
	public String getYubaoqiya() {
		return yubaoqiya;
	}
	public void setYubaoqiya(String yubaoqiya) {
		this.yubaoqiya = yubaoqiya;
	}
	
	
	
}
