
<div class="top_nav08_s"><!DOCTYPE html>
<html>
<body>
<div class="g-in clearfix mt-mb">
	<div class="qxmt_main">
		<div class="main_left">
		<div class="part_tit"><span>电视气象节目</span></div>
		<div class="nav_block">
		<span>频道</span>
		<ul class="qxmt_navl">
		<#if list?? && list[0]??>
			<#list list as ls>
				<li onclick="changeVideo('${ls.url!'无'}');">${ls.name!'无'}</li>
			</#list>
		</#if>
		</ul>
		</div>
           <div class="video">
           	<#if list?? && list[0]??>
                <iframe src="/xstq/info.html?url='${list[0].url!'无'}'" id="Ivideo" width="625px" height="470px" scrolling="no" marginheight="0" marginwidth="0" allowFullScreen=""></iframe>
           	</#if>
           </div>
		</div>
	</div>
</div>
<@extends name="../base.ftl"/>
<script type="text/javascript">
function changeVideo(url) {
	if(isIE()){
		$("#Ivideo").attr("src", "/xstq/info.html?url="+url);
	}else{
		$("#Ivideo").attr("src", "/xstq/info.html?url="+url);
	}
	function isIE() { //ie?  
	    if (!!window.ActiveXObject || "ActiveXObject" in window)  
	        return true;  
	    else  
	        return false;  
	} 
}
     

</script>

</body>
</html>
