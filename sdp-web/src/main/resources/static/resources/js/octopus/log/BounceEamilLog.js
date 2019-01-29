function comExtraInfo(obj){
    var comExtraInfo = $(obj).attr('comExtraInfo');
    layer.tips('<div class="more">'+comExtraInfo+'</div>', $(obj) ,{
          tips: [2, 'rgba(220,0,0,.7)'],area: ['800px', 'auto'],time:60000}
    );
}

function receiverInfo(obj){
    var comExtraInfo = $(obj).attr('comExtraInfo');
    layer.tips('<div class="receiverInfo">'+comExtraInfo+'</div>', $(obj) ,{
          tips: [3, 'rgba(220,0,0,.7)'],area: ['200px', 'auto'],time:60000}
    );

}
function contentInfo(obj){
    var comExtraInfo = $(obj).attr('comExtraInfo');
    layer.tips('<div class="contentInfo">'+comExtraInfo+'</div>', $(obj) ,{
          tips: [3, 'rgba(220,0,0,.7)'],area: ['400px', 'auto'],time:60000}
    );
}
function ExtraInfo(obj){
    layer.closeAll('tips');
}
$(document).ready(function () {
    //搜索
    $("#searchBtn").click(function(e) {
        table.api().ajax.reload(); 
    });
    //清空
    $("#resetBtn").click(function(e) {
        $("#oriEmaSubject").val("");
        $("#oriEmaTo").val("");
        $("#startTime").val("");
        $("#endTime").val("");
    }); 
    

    var table = $('#operateLog_table').dataTable({
        /*"aoColumnDefs": [ { "bSortable": false, "aTargets": [ 0 ,3] }],*/
        fixedHeader: {
            header: true
        },
        "dom": '<<t>ilp>',
        "pagingType": "simple_numbers",//设置分页控件的模式
        "processing": true,
        "serverSide": true,
        "ordering":false,
/*         分页设置 
        "bPaginate": true,
        "bLengthChange": true,*/
        /* 搜索设置 */
        "bFilter": false,
        "bSort": false,
        /* 显示总条数 */
        "bInfo": true,
        "bAutoWidth": false,
        "language": {
            "paginate": {
                "previous": "首页",
                "next": "下一页"
            },
            "info": "显示_START_到_END_, 共计_TOTAL_条数据",
            "emptyTable": "无记录",
            "infoEmpty": "共计0",
             "lengthMenu": "每页显示 _MENU_ 记录",
            // "infoFiltered": "",
            // "zeroRecords": "没有找到相关内容",
            // "search": "搜索 : "
        },
        "ajax":{
            "url": ctx+"rest/api/log/bounceEmail/pager",
            "data":function(d){
         		var oriEmaSubject = $("#oriEmaSubject").val();
         		var startTime = $("#startTime").val();
         		var endTime = $("#endTime").val();
         		var oriEmaTo = $("#oriEmaTo").val();
         		d.oriEmaSubject=oriEmaSubject;
         		d.startTime=startTime,
         		d.endTime=endTime,
         		d.oriEmaTo=oriEmaTo
         	}
         },
        //序号
        "fnDrawCallback" : function(){
                                        this.api().column(0).nodes().each(function(cell, i) {
                                            cell.innerHTML =  i + 1;
                                        });
                                },
        "columns": [
                    {   
                        data : null
                    },
                    {   
                        data : "bounceSubject",
                        render : function ( data, type, row ) {
                            if (data == null || data == "") {
                                return "";
                            }
                            return data;
                        }   
                    },
                    {   
                        data : "oriEmaSubject",
                        render : function ( data, type, row ) {
                            if (data == null || data == "") {
                                return "";
                            }
                            return data;
                        }   
                    },
                    {   
                        data : "oriEmaTo",
                        render : function ( data, type, row ) {
                            if (data == null || data == "") {
                                return "";
                            }
                            return data;
                        }   
                    },
                    {   
                        data : "oriEmaDate",
                        render : function ( data, type, row ) {
                            if (data == null || data == "") {
                                return "";
                            }
                            return data;
                        }   
                    },
                    {   
                        data : "bounceReason",
                        render : function ( data, type, row ) {
                            if (data == null || data == "") {
                                return "";
                            }
                            return "<a onmouseover='comExtraInfo(this)' onmouseout='ExtraInfo(this)' comExtraInfo='"+data+"'>"+data.substring(0,20)+"..</a>";
                        }   
                    },
                    {   
                        data : "proposedSolution",
                        render : function ( data, type, row ) {
                            if (data == null || data == "") {
                                return "";
                            }
                            return "<a onmouseover='comExtraInfo(this)' onmouseout='ExtraInfo(this)' comExtraInfo='"+data+"'>"+data.substring(0,10)+"..</a>";
                        }   
                    }
                   ]
    });
});
