(function(){ 
 
    b='code'; 
    CKEDITOR.plugins.add(b,{ 
    	 requires: ["dialog"],  
        init:function(editor){ 
            editor.addCommand(b,new CKEDITOR.dialogCommand("code")); 
            editor.ui.addButton('code',{ 
                label:'code', 
                icon: this.path + 'add.png', 
                command:b 
            }); 
            CKEDITOR.dialog.add("code", this.path + "dialogs/code.js")  
        } 
    }); 
})();
