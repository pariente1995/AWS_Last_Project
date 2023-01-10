$(function() {
	// 메인 페이지에서 엔터 검색 시, 검색창에 내용이 없을 때 alert 나오게 하고 페이지 이동 막기.
	let headerSearchForm = document.getElementById("header-searchForm");
	headerSearchForm.addEventListener("submit", function(e) {
		let headerSearchInput = document.getElementById("header-search-input");
		if (headerSearchInput.value.length == 0) {
			alert("입력창에 입력해 주세요.");
			e.preventDefault();
		}
	}) 
	
	// 메인 페이지에서 검색 버튼 클릭 시, 검색창에 내용이 없을 때 alert 나오게 하고 페이지 이동 막기.
	let searchA = document.getElementById("searchA");
	searchA.addEventListener("click", function(e) {
		let headerSearchInput = document.getElementById("header-search-input");
		if (headerSearchInput.value.length == 0) {
			alert("입력창에 입력해 주세요.");
			e.preventDefault();
		}
	})
})