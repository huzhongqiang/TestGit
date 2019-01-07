//详情页面脚本
$(function() {
	var url = window.location.href;
	var parameter = url.split("?")[1];
	if (parameter) {
		var type = parameter.split("&")[1];
		var id = parameter.split("%")[1];
		// 数据绑定例子
		$.ajax({
			url: "static/Testdata/test.json",
			type: "get",
			dataType: "json",
			success: function(data) {
				var detailData="";
				if(type=="notice"){
					 detailData= data["公告通知"]; //获得公告通知数据
				}
				analysisData(detailData,id);
			},
			error: function() {
				console.log("error");
			}
		});
	}
})
function analysisData(data,id){
	$.each(data,function(i,item){
		if(item.ID==id){
			$("#detailTit").html(item.Title);
			$("#releaseTime").html(item.PubTime);
			var detailHtml="";
			var textD=item.Content.split("||");
			for(var n=0;n<textD.length;n++){
				detailHtml+="<p class='detail_text'>"+textD[n]+"</p>";
			}
			$("#detailPart").html(detailHtml);
		}

	})
}