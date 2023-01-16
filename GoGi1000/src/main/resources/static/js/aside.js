$(function() {
    $("#side").children().children().first().attr('class','isSelect');
    $("#side").children().children().first().css("background-color", "white");
    $("#side").children().children().first().css("color", "#ee2d7a");
    $("#side").children().children().first().css("border-top-left-radius", "40%")
    $("#side").children().children().first().css("border-bottom-left-radius", "40%");
	
	$(".sideList a").on({
       click: function() { 
            // 전체 데이트 코스 분류 전체 설정
            $(".sideList a").css("background-color", "#ee2d7a");
            $(".sideList a").css("color", "white");
            $(".sideList a").removeClass("isSelect");

            // 선택된 데이트 코스 분류에 대해서만 설정
            $(this).attr('class','isSelect');
		    $(this).css("background-color", "white");
		    $(this).css("color", "#ee2d7a");
		    $(this).css("border-top-left-radius", "40%")
		    $(this).css("border-bottom-left-radius", "40%");
       }
	})
})