//需共用的脚本
$(function(){
	//背景图自适应start
	var BgW=$(".mainbg").width();
	var WscaleLeft=330/1920;
	var WscaleRight=300/1920;
	var WLeft=BgW*WscaleLeft;
	var WRight=BgW*WscaleRight;
	$(".leftCloud").show();
	$(".rightCloud").show();
	$(".leftCloud").css({"width":WLeft+"px","margin-left":-WLeft+"px"});
	$(".rightCloud").css({"width":WRight+"px","margin-right":-WRight+"px"});
	//背景图自适应end
	//左导航背景自适应高度计算start
	leftNavAdapt();
	//当大屏时背页面不能铺满整个屏幕时改变高度
    var windowH=getWindowH();
    var containerH=$(".container").height();
    if(windowH>containerH){
    	$(".container").height(windowH+"px");
    }

	//上导航公告通知切换
	$(".keywordList li").click(function(){
		$(this).addClass("current").siblings().removeClass("current");
		$(".m_nav li").removeClass("current");
	})
	// 导航样式切换
	$(".m_nav li").click(function(){
		$(this).addClass("current").siblings().removeClass("current");
		$(".keywordList li").removeClass("current");
	})
	//左导航样式切换
	$(".navList li.firstClass").click(function(){
		$(this).addClass("current").siblings().removeClass("current");
		leftNavAdapt();//重新计算高度
	})
	//左导航子菜单样式切换
	$(".subnav li").click(function(){
		$(this).addClass("current").siblings().removeClass("current");
	})
})
//左导航高度自适应
function leftNavAdapt(){
	var realH=$(".leftNav").height();
	var centBgH=realH-$(".bgTop").height()-$(".bgBtm").height()+2+"px";
	$(".bgcenter").height(centBgH);
}
//获取屏幕高度
function getWindowH(){  
    if(window.innerHeight!= undefined){  
        return window.innerHeight;  
    }  
    else{ //IE8不支持window.innerHeight的情况下 
        var B= document.body, D= document.documentElement;  
        return Math.max(D.clientHeight, B.clientHeight);  
    }  
} 