<%--
  Created by IntelliJ IDEA.
  User: wangxiang
  Date: 2018/4/20
  Time: 15:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String pageTypeId = (String) request.getAttribute("pageTypeId");
%>
<html>
<head>
    <title>页面分类</title>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="<%=path%>/resources/plugin/zTree_v3/css/demo.css" type="text/css">
    <link rel="stylesheet" href="<%=path%>/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" href="<%=path%>/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="<%=path%>/resources/plugin/DataTables-1.10.15/media/css/dataTables.bootstrap.css">
    <link rel="stylesheet" href="<%=path%>/resources/css/pageModel/baseclassify.css">
    <script>
      var path = "<%=path%>";
      var pageTypeId = "<%=pageTypeId%>" ;
    </script>
</head>
<body>
<div class="container" style="height: 100%; min-width: 1163px;">
    <div class="row" style="height: 100%; padding-top: 20px;">
        <div class="col-md-3 col-xs-3 zTreeDemoBackground left" style="height: 100%;margin-top: 50px;">
            <ul id="treeDemo" class="ztree"></ul>
        </div>
        <div class="col-md-9 col-xs-9 right" style="height: 100%">
            <div class="row text-right" style="margin: 0;">
                <button id="addPageModelBtn" type="button" class="btn btn-primary" data-toggle="modal">新增</button>
            </div>
            <br />
            <table id="example" class="table table-striped table-bordered">
                <thead>
                <tr>
                    <th>序号</th>
                    <th>页面名称</th>
                    <th>页面类型</th>
                    <th>创建时间</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody></tbody>
            </table>
        </div>
    </div>
</div>

<!--增加资源分类 模态框（Modal） -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    新增页面分类
                </h4>
            </div>
            <div class="modal-body">
                <form id="addNodeForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="pageTypeName" class="col-sm-2 control-label">页面类型名称</label>
                        <div class="col-sm-10">
                            <input type="text" id="pageTypeName" name="pageTypeName" class="form-control">
                            <div style="text-align: right;"><span id="pageTypeNameError" class="span-error"></span></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="pageSortId" class="col-sm-2 control-label">排序</label>
                        <div class="col-sm-10">
                            <input type="text" id="pageSortId" name="pageSortId" class="form-control" placeholder="请输入数字序号">
                            <div style="text-align: right;"><span id="pageSortIdError" class="span-error"></span></div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" id="addNodeBtn" onclick="addNodeFormBtn()" class="btn btn-primary">
                    保存
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!--修改资源分类 模态框（Modal） -->
<div class="modal fade" id="myEditModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel1">
                    修改页面分类
                </h4>
            </div>
            <div class="modal-body">
                <form id="editNodeForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="pageTypeName1" class="col-sm-2 control-label">页面类型名称</label>
                        <div class="col-sm-10">
                            <input type="text" id="pageTypeName1" name="pageTypeName" class="form-control">
                            <div style="text-align: right;"><span id="pageTypeName1Error" class="span-error"></span></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="pageSortId1" class="col-sm-2 control-label">排序</label>
                        <div class="col-sm-10">
                            <input type="text" id="pageSortId1" name="pageSortId" class="form-control" placeholder="请输入数字序号">
                            <div style="text-align: right;"><span id="pageSortId1Error" class="span-error"></span></div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" id="addNodeBtn1" onclick="editNodeFormBtn()" class="btn btn-primary">
                    提交更改
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!--新增页面 模态框（Modal） -->
<div class="modal fade" id="addPageModelModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="addPageModelLabel1">
                    添加页面
                </h4>
            </div>
            <div class="modal-body">
                <form id="addPageModelModalForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="pageName" class="col-sm-2 control-label">页面名称</label>
                        <div class="col-sm-10">
                            <input type="text" id="pageName" name="pageName" class="form-control">
                            <div style="text-align: right;"><span id="pageNameError" class="span-error"></span></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="pageSortId" class="col-sm-2 control-label">排序</label>
                        <div class="col-sm-10">
                            <input type="text" id="pageSortId2" name="pageSortId" class="form-control" placeholder="请输入数字序号">
                            <div style="text-align: right;"><span id="pageSortId2Error" class="span-error"></span></div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" id="addPageModelFormBtn" onclick="addPageModelForm()" class="btn btn-primary">
                    提交更改
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<!--更新页面 模态框（Modal） -->
<div class="modal fade" id="updatePageModelModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="updatePageModelLabel1">
                    更新页面
                </h4>
            </div>
            <div class="modal-body">
                <form id="updatePageModelModalForm" class="form-horizontal">
                    <div class="form-group">
                        <label for="pageName" class="col-sm-2 control-label">页面名称</label>
                        <div class="col-sm-10">
                            <input type="hidden" id="pageId" name="pageId">
                            <input type="text" id="pageName3" name="pageName" class="form-control">
                            <div style="text-align: right;"><span id="pageNameError3" class="span-error"></span></div>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="pageSortId" class="col-sm-2 control-label">排序</label>
                        <div class="col-sm-10">
                            <input type="text" id="pageSortId3" name="pageSortId" class="form-control" placeholder="请输入数字序号">
                            <div style="text-align: right;"><span id="pageSortId3Error" class="span-error"></span></div>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <button type="button" id="updatePageModelFormBtn" onclick="updatePageModelForm()" class="btn btn-primary">
                    提交更改
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
<script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=path%>/resources/plugin/zTree_v3/js/jquery.ztree.exedit.js"></script>
<script type="text/javascript" src="<%=path%>/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/plugin/DataTables-1.10.15/media/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=path%>/resources/plugin/DataTables-1.10.15/media/js/dataTables.bootstrap.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/util_common.js"></script>
<script type="text/javascript" src="<%=path%>/resources/js/pageModel/pageclassify.js"></script>
<script>
  var table = $('#example').DataTable({
    language: language,
    processing: true,
    serverSide : true,
    paging : true,//是否分页
    pagingType : "full_numbers",//除首页、上一页、下一页、末页四个按钮还有页数按钮
    ajax: function (data, callback, settings) {
        //封装请求参数
        var param = {};
        param.length = data.length;//页面显示记录条数，在页面显示每页显示多少项的时候
        param.start = data.start;//开始的记录序号
        //param.pageNo = (data.start / data.length)+1;//当前页码
        param.selectKey = data.search.value;
        //ajax请求数据
        $.ajax({
          type: "GET",
          url: "<%=path%>/v1/pageModel/childDetail/"+ pageTypeId,
          cache: false,  //禁用缓存
          data: param,  //传入组装的参数
          dataType: "json",
          success: function (data1) {
            if(data1) {
              var result = data1;
              if(result) {
                //封装返回数据
                var returnData = {};
                returnData.draw = data.draw;//这里直接自行返回了draw计数器,应该由后台返回
                returnData.recordsTotal = result.total;//返回数据全部记录
                returnData.recordsFiltered = result.total;//后台不实现过滤功能，每次查询均视作全部结果
                returnData.data = result.data;//返回的数据列表
                //调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
                //此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕
                callback(returnData);
              } else {
                alert(data1.message);
              }
            }
          }
        });
      },
    //每页显示三条数据
    pageLength: 10,
    columns: columns,
    columnDefs: columnDefs,
    drawCallback: function () {
      appendSkipPage();

      //自增序号
      let api = this.api();
      let startIndex = api.context[0]._iDisplayStart;//获取本页开始的条数
      api.column(0).nodes().each(function(cell, i) {
        cell.innerHTML = startIndex + i + 1;
      });
    }

  });
</script>
</body>
</html>
