$(function() {
	$(".sideList a").on({
		mouseover: function() {
	    	$(this).css("background-color", "white");
	    	$(this).css("color", "#ee2d7a");
	   		$(this).css("border-top-left-radius", "40%")
	      	$(this).css("border-bottom-left-radius", "40%");
	    },
	   	mouseleave: function() {
			if($(this).attr('class') !='isSelect') {
	        	$(this).css("background-color", "#ee2d7a");
	        	$(this).css("color", "white");
	        	$(this).css("border-top-left-radius", "40%")
		    	$(this).css("border-bottom-left-radius", "40%");
	        }
	   	}
	});
});