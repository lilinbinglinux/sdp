(function(){
	setTimeout(() => {
	function changeTable(){
		   var data_table = $('#operateLog_table');
		   var data_table_tr = $('#operateLog_table thead tr');
		   var fix_table = $('#tabletr');
		   var fix_table_thead = $('#tabletr thead');
		   
		   var table_width = data_table.width();
		   fix_table.width(table_width);
		   fix_table_thead.width(table_width);
		   var th1_width = data_table_tr.children('th').eq(0).outerWidth();
		   console.log(data_table_tr.children('th').eq(0).width());
		   console.log(th1_width);
		   var th2_width = data_table_tr.children('th').eq(1).outerWidth();
		   var th3_width = data_table_tr.children('th').eq(2).outerWidth();
		   var th4_width = data_table_tr.children('th').eq(3).outerWidth();
		   var th5_width = data_table_tr.children('th').eq(4).outerWidth();
		   var th6_width = data_table_tr.children('th').eq(5).outerWidth();
		   var th7_width = data_table_tr.children('th').eq(6).outerWidth();
		   var th8_width = data_table_tr.children('th').eq(7).outerWidth();
		   
		   $("#th1").css("width",th1_width);
		   $("#th2").css("width",th2_width);
		   $("#th3").css("width",th3_width);
		   $("#th4").css("width",th4_width);
		   $("#th5").css("width",th5_width);
		   $("#th6").css("width",th6_width);
		   $("#th7").css("width",th7_width);
		   $("#th8").css("width",th8_width);
	}
	changeTable();
	window.resize = function(){
		changeTable();
	};   
	window.onscroll = function(){
			changeTable();
	    	var yheight=window.pageYOffset; //滚动条距顶端的距离 
	        if(yheight >= 1){
	            if (100 >= yheight) {
	               $("#tabletr").css("display","block");
	            }
	        }else{
	             $("#tabletr").css("display","none");
	        }
	 }    
	}, 100);
		   
}());