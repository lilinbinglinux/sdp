<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/font-awesome-4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/css/bootstrap.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrap/bootstrap-select/dist/css/bootstrap-select.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/bootstrapvalidator/dist/css/bootstrapValidator.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/zTree_v3/css/zTreeStyle/zTreeStyle.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/message/message.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/index.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/accordion.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/myLayerSkin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/dataCanvas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/dataModel/loading.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/dataModel/summerTab/css/summerTab.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/js/dataModel/summerBox/css/summerBox.css">
    <script src="${pageContext.request.contextPath}/resources/plugin/jquery/jquery-1.9.1.js"></script>
    <script>
      document.onreadystatechange = function() {
        if( document.readyState == "complete" ){
          $("#loading").fadeOut();
        }
      }
    </script>
</head>
<body>
<div class="container-power">
    <div class="power-menu-list">
        <span class="menu-left">
            <span class="fa fa-list-ul menu-item menu-show" aria-hidden="true"></span>
        </span>
        <span class="menu-right">
            <span class="crow-line"></span>
            <span class="fa fa-table power-tool-item tool-class-box" data-toggle="tooltip" data-placement="bottom" title="建表" aria-hidden="true"></span>
            <%--<span class="fa fa-hand-pointer-o power-tool-item tool-hand" aria-hidden="true"></span>--%>
            <span class="fa fa-mouse-pointer power-tool-item tool-mouse" aria-hidden="true"></span>
            <%--<span class="fa fa-long-arrow-right power-tool-item tool-line tool-straight-line" aria-hidden="true"></span>--%>
            <span class="fa fa-times power-tool-item tool-delete" data-toggle="tooltip" data-placement="bottom" title="删除" aria-hidden="true"></span>
            <span class="crow-line"></span>
        </span>
        <span class="power-tableType-name"></span>
        <input type="text" hidden="hidden" id="dataResId" />
        <input type="text" hidden="hidden" id="tableTypeId" />
        <input type="text" hidden="hidden" id="resType" />
        <div style="width: 200px;height: 40px;display: inline-block;position: absolute;right: 15px;">
          <span class="menu-save-btn">保存</span>
        </div>
    </div>
    <div class="power-main">
        <div class="power-left-nav">
            <div class="stepTwo-module">
              <ul id="curTree" class="ztree">

              </ul>
            </div>

        </div>
        <div class="power-container" id="power-container">
            <div class="power-canvas-container" id="container" >
                <canvas class="power-canvas" id="power-canvas"></canvas>
                <!-- <%--<div class="summer-box">--%>
                  <%--<div class="summer-box-title">表名</div>--%>
                  <%--<ul class="summer-column">--%>
                    <%--<li class="column-item">--%>
                      <%--<span class="item-content"><img src="${pageContext.request.contextPath}/resources/img/dataModel/key.png" /></span>--%>
                      <%--<sapn class="item-content"><nobr>createDate<sapn class="column-type">:</sapn><sapn class="column-type">varchar(1024)</sapn></nobr></sapn>--%>
                    <%--</li>--%>
                  <%--</ul>--%>
                <%--</div>--%>
                <%--<div id="bo1" class="summer-box" style="left: 300px;" data-toggle="summer-box">--%>
                  <%--<div class="summer-box-title">表名</div>--%>
                  <%--<ul class="summer-column">--%>
                    <%--<li class="column-item">--%>
                      <%--<i class="fa fa-arrow-left" aria-hidden="true"></i>--%>
                      <%--<span class="item-content"><img src="${pageContext.request.contextPath}/resources/img/dataModel/key.png" /></span>--%>
                      <%--<sapn class="item-content"><nobr>createDate7<sapn class="column-type">:</sapn><sapn class="column-type">varchar(1024)</sapn></nobr></sapn>--%>
                      <%--<i class="fa fa-arrow-right" aria-hidden="true"></i>--%>
                    <%--</li>--%>
                    <%--<li class="column-item">--%>
                      <%--<i class="fa fa-arrow-left" aria-hidden="true"></i>--%>
                      <%--<span class="item-content"></span>--%>
                      <%--<sapn class="item-content"><nobr>createDate7<sapn class="column-type">:</sapn><sapn class="column-type">varchar(1024)</sapn></nobr></sapn>--%>
                      <%--<i class="fa fa-arrow-right" aria-hidden="true"></i>--%>
                    <%--</li>--%>
                    <%--<li class="column-item">--%>
                      <%--<i class="fa fa-arrow-left" aria-hidden="true"></i>--%>
                      <%--<span class="item-content"></span>--%>
                      <%--<sapn class="item-content"><nobr>createDate7<sapn class="column-type">:</sapn><sapn class="column-type">varchar(1024)</sapn></nobr></sapn>--%>
                      <%--<i class="fa fa-arrow-right" aria-hidden="true"></i>--%>
                    <%--</li>--%>
                    <%--<li class="column-item">--%>
                      <%--<i class="fa fa-arrow-left" aria-hidden="true"></i>--%>
                      <%--<span class="item-content"></span>--%>
                      <%--<sapn class="item-content"><nobr>createDate7<sapn class="column-type">:</sapn><sapn class="column-type">varchar(1024)</sapn></nobr></sapn>--%>
                      <%--<i class="fa fa-arrow-right" aria-hidden="true"></i>--%>
                    <%--</li>--%>
                    <%--<li class="column-item">--%>
                      <%--<i class="fa fa-arrow-left" aria-hidden="true"></i>--%>
                      <%--<span class="item-content"></span>--%>
                      <%--<sapn class="item-content"><nobr>createDate7<sapn class="column-type">:</sapn><sapn class="column-type">varchar(1024)</sapn></nobr></sapn>--%>
                      <%--<i class="fa fa-arrow-right" aria-hidden="true"></i>--%>
                    <%--</li>--%>
                  <%--</ul>--%>
                  <%--<div class="drop-arrow-container"><div class="drop-arrow"><i class="fa fa-angle-double-down" aria-hidden="true"></i></div></div>--%>
                <%--</div>--%> -->

                <div class="position-circle  circle-top"></div>
                <div class="position-circle  circle-right"></div>
                <div class="position-circle  circle-bottom"></div>
                <div class="position-circle  circle-left"></div>
                <div class="position-circle  circle-top-right"></div>
                <div class="position-circle  circle-top-left"></div>
                <div class="position-circle  circle-bottom-right"></div>
                <div class="position-circle  circle-bottom-left"></div>
            </div>
        </div>
        <ul class="rMenu">
          <li>新增</li>
          <li>编辑</li>
          <li>删除</li>
        </ul>
    </div>
</div>
<div id="loading">
  <div class="lds-css ng-scope"><div style="width:100%;height:100%;position: absolute;left: 0;top: 0;bottom: 0;right: 0;margin: auto;" class="lds-ripple"><div></div><div></div></div>

  </div>
</div>

<%--新增表模态框--%>
<div class="modal fade" id="newTableModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" data-backdrop="static">
  <div class="modal-dialog modal-lg" role="document" style="width: 1000px;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title">添加表</h4>
      </div>
      <div class="modal-body" style="height: 500px;overflow: auto;">

        <div class="summer-tab" id="canvasTab">
          <div class="summer-tabs">
            <div class="tab-item active" id="basic">基本</div>
            <div class="tab-item" id="column">列</div>
            <div class="tab-item" id="option">设置</div>
            <div class="summer-line"></div>
          </div>
          <div class="summer-tab-content">
            <div class="tab-content" style="padding-right:100px;"> 
              <form class="form-horizontal" id="table-info-form">
                <div class="form-group">
                  <label class="col-sm-2 control-label">表名<span style="color:red;">*</span></label>
                  <div class="col-sm-10">
                    <input type="text" name="tableName" class="form-control" placeholder="表名" autocomplete="false">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">表英文名<span style="color:red;">*</span></label>
                  <div class="col-sm-10">
                    <input type="text" name="tableSqlName" class="form-control" placeholder="表英文名">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">注释</label>
                  <div class="col-sm-10">
                    <input type="text" name="tableResume" class="form-control" placeholder="注释">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">序号</label>
                  <div class="col-sm-10">
                    <input type="text" name="sortId" class="form-control" placeholder="序号" autocomplete="false">
                  </div>
                </div>
              </form>
            </div>
            <div class="tab-content">
              <div class="new-line-container" style="margin-bottom: 15px;">
                <span id="column-add" class="fa fa-plus-square line-tool-add" aria-hidden="true"></span>
                <span id="column-delete" class="fa fa-minus-square line-tool-add" aria-hidden="true"></span>
              </div>
              <div class="table-responsive">
                <table class="table table-bordered table-condensed" id="inner-table">
                  <thead>
                  <tr style="background-color: #eee;">
                    <th><input type="checkbox" id="title-checkbox"></th>
                    <th style="width: 120px;">名称</th>
                    <th style="width: 120px;">英文名</th>
                    <th style="width: 100px;">类型</th>
                    <th style="width: 100px;">长度</th>
                    <th style="width: 100px;">精度</th>
                    <th style="width: 50px;">主键</th>
                    <th style="width: 50px;">索引</th>
                    <th style="width: 50px;">非空</th>
                    <th>说明</th>
                  </tr>
                  </thead>
                  <tbody>
                  <%--body--%>
                  </tbody>
                </table>
              </div>
            </div>
            <div class="tab-content" style="position:relative;padding-right:100px;">
              <form class="form-horizontal" id="sub_form">
                <div class="form-group">
                  <label class="col-sm-2 control-label">操作类型</label>
                  <div class="col-sm-10">
                    <select name="shapeType" class="form-control" id="shapeType-option">
                      <option value=""></option>
                      <option value="1">表分区</option>
                      <option value="2">分表</option>
                      <option value="3">分库</option>
                    </select>
                  </div>
                </div>
                <div class="option-three">
                  <%--分区--%>
                  <div class="option-sub sub-area">
                    <div class="form-group">
                      <label class="col-sm-2 control-label">分区信息<span style="color:red;">*</span></label>
                      <div class="col-sm-10">
                        <textarea name="partitionValue" class="form-control" rows="6"></textarea>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 control-label">模式</label>
                      <div class="col-sm-10">
                        <label class="checkbox-inline">
                          <input type="radio" name="state" value="0" checked /> 自动
                        </label>
                        <label class="checkbox-inline">
                          <input type="radio" name="state"  value="1" /> 手动
                        </label>
                      </div>
                    </div>
                    <div style="padding-left: 60px; margin-top: 35px;">
                      <span style="color: red;font-size: 16px;font-weight: 700;">注：</span>
                      <span style="font-size: 12px;color: #666;">分区信息一旦变更，很可能会产生不可逆转的后果，请先备份数据！</span>
                    </div>
                  </div>

                  <%--分表--%>
                  <div class="option-sub sub-meter">
                    <div class="form-group">
                      <label class="col-sm-2 control-label">字段<span style="color:red;">*</span></label>
                      <div class="col-sm-10">
                        <div class="input-group">
                          <select name="fields" class="form-control"></select>
                          <span class="input-group-btn">
                          <button class="btn btn-warning refresh-btn" style="height: 34px;" type="button"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span></button>
                        </span>
                        </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 control-label">类型<span style="color:red;">*</span></label>
                      <div class="col-sm-10">
                        <select name="types" class="form-control">
                          <option></option>
                          <option value="1">date</option>
                          <option value="2">string(截取长度)</option>
                          <option value="3">数值(如：100-1，200-2)</option>
                        </select>
                      </div>
                    </div>
                    <div class="typeChange-content">
                      
                    </div>
                  </div>

                  <%--分库--%>
                  <div class="option-sub sub-treasury">
                    <div class="form-group">
                      <label class="col-sm-2 control-label">字段<span style="color:red;">*</span></label>
                      <div class="col-sm-10">
                        <div class="input-group">
                          <select name="fields" class="form-control"></select>
                          <span class="input-group-btn">
                          <button class="btn btn-warning refresh-btn" style="height: 34px;" type="button"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span></button>
                        </span>
                        </div>
                      </div>
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 control-label">类型<span style="color:red;">*</span></label>
                      <div class="col-sm-10">
                        <select name="types" class="form-control">
                          <option></option>
                          <option value="1">string(截取长度)</option>
                          <option value="2">数值(如：100-1，200-2)</option>
                          <option value="3">date</option>
                        </select>
                      </div>
                    </div>
                    <div class="typeChange-content">
                    </div>
                    <div class="form-group">
                      <label class="col-sm-2 control-label">库<span style="color:red;">*</span></label>
                      <div class="col-sm-10">
                        <div class="input-group">
                          <select name="sqlSubTreasuryDivision" class="form-control selectpicker" id="sqlSubTreasury" multiple title="请选择数据库" data-actions-box="true">

                          </select>
                          <span class="input-group-btn">
                          <button class="btn btn-warning refresh-source-btn" style="height: 34px;" type="button"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span></button>
                        </span>
                        </div>
                      </div>
                    </div>
                    <div id="dataInputs">
                    </div>
                  </div>
                </div>
              </form>
              <div class="no-allow-touch"></div>
            </div>
          </div>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary table-submit">提交</button>
      </div>
    </div>
  </div>
</div>

<%--确认模态框--%>
<div id="confirmCloseModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-body">
          表分区一经修改，表中数据可能会丢失，确认修改分区信息吗？
      </div>
      <div class="modal-footer">
        <button id="cancel-modal" type="button" class="btn btn-default" >取消</button>
        <button type="button" class="btn btn-primary confirm-submit">确认</button>
      </div>
    </div>
  </div>
</div>

<!-- 删除确认框 -->
<div id="confirmDeleteModal" class="modal fade bs-example-modal-sm" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel">
  <div class="modal-dialog modal-sm" role="document">
    <div class="modal-content">
      <div class="modal-body">
          
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
        <button type="button" class="btn btn-primary delete-confirm">确认</button>
      </div>
    </div>
  </div>
</div>
<script>
	var curContext = '<%=path%>';
  var dataResId = '${dataResId}';
  var isHaveOtherUser = true;
  var statusInterval = null;

  function getCanvasContext(){
    var powerCanvas = document.getElementById("power-canvas");
    var ctx1 = powerCanvas.getContext("2d");
    return ctx1;
  }
</script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-3.3.6/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrap-select-1.13.2/dist/js/bootstrap-select.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/bootstrapvalidator/dist/js/bootstrapValidator.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/zTree_v3/js/jquery.ztree.all.js"></script>
<script src="${pageContext.request.contextPath}/resources/plugin/message/message.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/summerTab/js/summerTab.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/uuid.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/keywords.js"></script>
<script>
	var canvasTab = $("#canvasTab").tab();
</script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/canvasArrow.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/summerBox/js/drawArrow.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/accordion.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/dataCanvas.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/dataModel/summerBox/js/summerBox.js"></script>
<script>


	  //统一重新保存所有坐标
	  $(".menu-save-btn").click(function (e) {
		  saveXY();
	  });

	  $(document).mousemove(function (e) {
		  containerMousemove(e);
	  });
	  $(document).click(function (e) {
      documentMouseDown(e);
    });
    //检查状态
    var statusUUID = getuuid(32,32);
    function loadStatus(){
      $.ajax({
        url: curContext + "/v1/sqlModel/checkStatus",
        type: "POST",
        data: { "dataResId": dataResId, "uuid": statusUUID },
        success: function(result){
          isHaveOtherUser = result;
          if( result ){
            //true 表视没人用
        
            //改变状态
            $.ajax({
              url: curContext + "/v1/sqlModel/changeStatus",
              type: "post",
              data: JSON.stringify({ "dataResId": dataResId, "dataStatus": "1", "uuid": statusUUID }),
              dataType : "json",
							contentType:"application/json",
              success: function( status ){
                if( status.code === 200 ){
                  
                  
                }
              }
            });
          }else{
            //fale 表视有人用
            
            //改变状态
            $.ajax({
              url: curContext + "/v1/sqlModel/changeStatus",
              type: "post",
              data: JSON.stringify({ "dataResId": dataResId, "dataStatus": "0", "uuid": statusUUID }),
              dataType : "json",
							contentType:"application/json",
              success: function( status ){
                console.log(status);
              }
            });
          }
        }
      });
    }
    
    loadStatus();
    
    //定时请求接口检查状态
  /*   statusInterval = setInterval(function(){
      loadStatus();
    }, 10000); */

    //浏览器关闭窗口事件
    window.onbeforeunload = function(){
      if( isHaveOtherUser ){
        //改变状态
        $.ajax({
          url: curContext + "/v1/sqlModel/changeStatus",
          type: "post",
          data: JSON.stringify({ "dataResId": dataResId, "dataStatus": "1", "uuid": statusUUID }),
          dataType : "json",
					contentType:"application/json",
          success: function( status ){
            console.log(status);
          }
        });
      }else{
        $.ajax({
          url: curContext + "/v1/sqlModel/changeStatus",
          type: "post",
          data: JSON.stringify({ "dataResId": dataResId, "dataStatus": "0", "uuid": statusUUID }),
          dataType : "json",
					contentType:"application/json",
          success: function( status ){
            console.log(status);
          }
        });
      }
    }
</script>
</body>
</html>
