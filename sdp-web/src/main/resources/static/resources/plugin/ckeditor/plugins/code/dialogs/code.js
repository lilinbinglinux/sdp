var temp =[];
(function() { 
	getData();
    CKEDITOR.dialog.add("code",   
    function(a) { 
        return {  
            title: "添加字段",  
            minWidth: "500px",  
            minHeight:"500px",  
            contents: [
                       {  
                id: "tab1",  
                label: "",  
                title: "",  
                expand: true,  
                width: "500px",  
                height: "500px",  
                padding: 0,  
                elements: [
	                {
	                    id: "enctype",
	                    type: "select",
//	                    label: "添加字段",
	                    style: "width:100%",
	                    accessKey: "E",
	                    "default": "",
	                    width: "500px",  
	                    height: "500px", 
	                    items: temp,
	                    setup: function (a) {
	                        this.setValue(a.data("cke-saved-name") || a.getAttribute("name") || "")
	                    },
	                    commit: function (a) {
	                        a = a.element;
	                        this.getValue() ? a.data("cke-saved-name", this.getValue()) : (a.data("cke-saved-name", !1), a.removeAttribute("name"))
	                    }
	                },
	               
                
                ]  
            }],  
            onOk: function () {
             
            	var a = this.getParentEditor(), b = this.textField, c = !b;
                c && (b = a.document.createElement("input"), b.setAttribute("type", "text"));
                b = {element: b};
                
                c && (d = a.document.createElement("span"), d.html="123");
                d = {element: d};
                
                console.log(d)
                c && a.insertElement(b.element);
                c && a.insertElement(d.element);
                this.commitContent(b);
                this.commitContent(d);
                c || a.getSelection().selectElement(b.element)
            },
           
        }  
    })  
})(); 
function getData(){
	 $.ajax({
      type: "POST",
      async:false,
      url: "../form/selectField",
      data : function(d) {// 查询参数
  		filetabledata = new Object();
  		filetabledata.start = 0;
  		filetabledata.length = 10;
  		return $.extend({}, filetabledata, {
  			"jsonStr" : "zlglyqyb001"
  		});
  	},
      dataType: "json",
      success: function(d){
    	  d = d.data;
    	  var formdata =new Array();
	  	  for(var i = 0 ;i<d.length;i++){
	  		  var a =new Array();
	  		  a.push(d[i].fieldcname);
	  		  a.push(d[i].fieldename);
	  		formdata.push(a);
	  	  }
	  	 temp = formdata;
      }
  });
	 
	
}

	
