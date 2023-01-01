$(function() {
  	// 엔터키로 검색
    $("input[name='searchKeyword']").on("keyup",function(key){
        if(key.keyCode==13) {
			//alert("안녕");
            $("form[name='searchForm']").submit();
	            
        }
    });
	    
	// a태그 사용하여 form submit시 무조건 e.preventDefault()하기.
    $("#searchA").click(function(e) {
		e.preventDefault();
		
		//alert("안녕");
		$("form[name='searchForm']").submit();
	});
});