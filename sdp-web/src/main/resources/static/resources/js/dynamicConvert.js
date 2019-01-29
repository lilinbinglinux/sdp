function formItemConvert( posy ) {
  posy = Number( posy );

  switch ( posy ) {
    case 10 :
      return '文本框';
    case 20 :
      return '下拉框';
    case 30 :
      return '单选框';
    case 40 :
      return '复选框';
    case 50 :
      return 'Slider滑块';
    default :
      return '-';
  }

}

function basicTypeConvert( posy ) {
  posy = Number( posy );

  switch ( posy ) {
    case 10 :
      return 'byte';
    case 20 :
      return 'short';
    case 30 :
      return 'int';
    case 40 :
      return 'long';
    case 50 :
      return 'float';
    case 60 :
      return 'double';
    case 70 :
      return 'char';
    case 80 :
      return 'boolean';
    default :
      return '-';
  }
}

function proLabelConvert( posy ) {
    posy = Number( posy );

    switch ( posy ) {
        case 10 :
            return '申请';
        case 20 :
            return '访问';
        case 30 :
            return '资源';
        default :
            return '-';
    }
}

function svcStateConvert( posy ) {
  posy = Number( posy );

  switch ( posy ) {
    case 10 :
      return '暂存';
    case 20 :
      return '已注册';
    case 30 :
      return '上线';
    case 40 :
      return '下线';
    default :
      return '-';
  }
}

function svcCategoryStateConvert( posy ) {
    posy = Number( posy );

    switch ( posy ) {
        case 0 :
            return '启用';
        case 1 :
            return '停用';
        default :
            return '-';
    }
}

/*function orderTypeConvert( posy ) {
  posy = Number( posy );

  switch ( posy ) {
    case 10 :
      return '新增';
    case 20 :
      return '扩容';
    case 30 :
      return '回收';
    default :
      return '-';
  }
}*/

/*function orderStateConvert( posy ) {
  posy = Number( posy );

  switch ( posy ) {
    case 10 :
      return '待支付';
    case 20 :
      return '支付成功';
    case 30 :
      return '待审批';
    case 40 :
      return '审批中';
    case 50 :
      return '通过';
    case 60 :
      return '失败';
    default :
      return '-';
  }
}*/

function controlTypeConvert( posy ) {
    posy = Number( posy );

    switch ( posy ) {
        case 10 :
            return '按时间';
        case 20 :
            return '按次数';
        default :
            return '-';
    }
}

function packageStatusConvert(posy) {
    posy = Number( posy );

    switch ( posy ) {
        case 10 :
            return '暂存';
        case 20 :
            return '发布';
        case 30 :
            return '上架';
        case 40 :
            return '下架';
        default :
            return '-';
    }
}

// 实例运行状态
function caseStatusConvert( posy ) {
  posy = Number( posy );

  switch ( posy ) {
    case 10 :
      return '未启动';
    case 1010 :
      return '启动中';
    case 20 :
      return '运行中';
    case 30 :
      return '停止';
    case 3010 :
      return '停止中';
    case 40 :
      return '失败';
    case 50 :
      return '异常';
    case 60 :
      return '未创建';
    default :
      return '-';
  }
}

/*function instanceOrderStateConvert( posy ) {
  posy = Number( posy );

  switch ( posy ) {
    case 10 :
      return '创建中';
    case 20 :
      return '使用中';
    case 30 :
      return '已到期';
    default :
      return '-';
  }
};



function chargeTimeTypeConvert( posy ) {
  posy = Number( posy );

  switch ( posy ) {
    case 10 :
      return '日';
    case 20 :
      return '月';
    case 30 :
      return '年';
    default :
      return '-';
  }
};*/

/**
 * 下一步：服务依赖
 */
// 是否展示实例
function isShowRelyOnProsConvert( posy ) {
    posy = Number( posy );

    switch ( posy ) {
        case 10 :
            return '是';
        case 20 :
            return '否';
        default :
            return '-';
    }
}

// 是否默认配置
function isConfRelyOnProsConvert( posy ) {
    posy = Number( posy );

    switch ( posy ) {
        case 10 :
            return '否';
        case 20 :
            return '是';
        default :
            return '-';
    }
}

/**
 * bcm-构建
 */
// 构建状态
function constructionStatusConvert( posy ) {
    posy = Number( posy );

    switch ( posy ) {
        case 1 :
            return '未构建';
        case 2 :
            return '构建中';
        case 3 :
            return '构建完成';
        case 4 :
            return '构建失败';
        default :
            return '-';
    }
}

// 构建结果
function constructionResultConvert( posy ) {
    posy = Number( posy );

    switch ( posy ) {
        case 2 :
            return '构建中';
        case 3 :
            return '构建完成';
        case 4 :
            return '构建失败';
        default :
            return '-';
    }
}

// 代码托管工具(代码来源)
function codeControlTypeConvert( posy ) {
    posy = Number( posy );

    switch ( posy ) {
        case 0 :
            return 'none';
        case 1 :
            return 'gitlab';
        case 2 :
            return 'svn';
        case 3 :
            return 'github';
        default :
            return '-';
    }
}


/**
 * bcm-镜像
 */

// 镜像生成方式
function imageCiTypeConvert( posy ) {
    posy = Number( posy );

    switch ( posy ) {
        case 0 :
            return '镜像上传';
        case 1 :
            return '代码构建';
        case 2 :
            return 'DockerFile构建';
        default :
            return '-';
    }
}