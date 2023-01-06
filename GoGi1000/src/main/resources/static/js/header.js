$(function() {
  	// 엔터키로 검색
    $("input[name='searchKeyword']").on("keyup",function(key){
        if(key.keyCode==13) {
			//alert("안녕");			
            $("form[name='header-searchForm']").submit();
            
            
	            
        }
    });
	    
	// a태그 사용하여 form submit시 무조건 e.preventDefault()하기.
	// 왜냐하면 form 안에 submit 역할을 하는 버튼을 눌렀어도 새로 실행하지 되서 정보가 날라감으로 막아줘야됌.
    $("#searchA").click(function(e) {
		e.preventDefault();
		
		//alert("안녕");
		$("form[name='header-searchForm']").submit();
	});
});