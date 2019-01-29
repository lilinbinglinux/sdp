package com.sdp.common.constant;

public class Dictionary {

    /**
     * HttpStatus码
     * 200 OK - [GET/DELETE]：请求已成功,服务器成功返回用户请求的数据，该操作是幂等的（Idempotent)
     * 201 CREATED - [POST/PUT/PATCH]：用户新建或修改数据成功。
     * 202 Accepted - [*]：表示一个请求已经进入后台排队（异步任务）
     * 204 NO CONTENT - NO CONTENT - [*]：服务器成功处理了请求，但不需要返回任何实体内容
     *
     * 400 INVALID REQUEST - [POST/PUT/PATCH]：用户发出的请求有错误，服务器没有进行新建或修改数据的操作，该操作是幂等的。
     * 401 Unauthorized - [*]：表示用户没有权限（令牌、用户名、密码错误）。
     * 403 Forbidden - [*] 表示用户得到授权（与401错误相对），但是访问是被禁止的。
     * 404 NOT FOUND - [*]：用户发出的请求针对的是不存在的记录，服务器没有进行操作，该操作是幂等的。
     * 406 Not Acceptable - [GET]：用户请求的格式不可得（比如用户请求JSON格式，但是只有XML格式）。
     * 410 Gone -[GET]：用户请求的资源被永久删除，且不会再得到的。
     * 422 Unprocesable entity - [POST/PUT/PATCH] 当创建一个对象时，发生一个验证错误。
     * 500 INTERNAL SERVER ERROR - [*]：服务器发生错误，用户将无法判断发出的请求是否成功。
     *
     */
    public enum HttpStatus {
        OK(200), CREATED(201),Accepted(202), NO_CONTENT(204), INVALID_REQUEST(400), UNAUTHORIZED(401),FORBIDDEN(403),
        NOT_FOUND(404), NOT_ACCEPTABLE(406), GONE(410), UNPROCESABLE(422), SERVER_INTERVAL_ERROR(500);

        public final Integer value;

        HttpStatus(Integer value) {
            this.value = value;
        }
    }

    /**
     * 通用状态
     */
    public enum CommonStatus{
        YES(1), NO(0);

        public final Integer value;

        CommonStatus(Integer value) {
            this.value = value;
        }
    }

    /**
     * 选项是否
     * 是，否
     */
    public enum Option {
        YES(true), NO(false);

        public final boolean value;

        Option(boolean value){
            this.value = value;
        }
    }

    /**
     * 属性类型
     * 表扩展属性、自定义属性、配额属性
     */
    public enum Attr {
        TABLE_EXTEND(10), CUSTOM(20), QUOTA(40);

        public final int value;

        Attr(int value){
            this.value = value;
        }
    }

    /**
     * 自定义属性类型
     * 申请属性、资源属性、访问属性
     */
    public enum AttrCustom {
        APPLY(10), RESOURCE(20), VISIT(30);

        public final int value;

        AttrCustom(int value){
            this.value = value;
        }
    }

    /**
     * 扩展属性类型
     * 基本属性、其他属性
     */
    public enum AttrExt {
        BASE(10), OTHER(20);

        public final int value;

        AttrExt(int value){
            this.value = value;
        }
    }

    /**
     * 属性值初始化类型
     * 本地、远程
     */
    public enum AttrInitType {
        LOCAL(10), REMOTE(20);

        public final int value;

        AttrInitType(int value){
            this.value = value;
        }
    }

    /**
     * 属性数据类型
     */
    public enum MetadataDataType {
        BYTE(10), SHORT(20), INT(30),LONG(40), FLORT(50), DOUBLE(60), CHAR(70), BOOLEAN(80);

        public final int value;

        MetadataDataType(int value){
            this.value = value;
        }
    }

    /**
     * 属性组件
     * 文本框、下拉框、单选框、复选框、slider滑块、按钮组
     */
    public enum MetadataModule {
        TEXTBOX(10), SELECTED(20), RADIO(30), CKECKED(40), SILDER(50), BUTTONGROUP(60);

        public final int value;

        MetadataModule(int value){
            this.value = value;
        }
    }

    /**
     * 订单类型
     * 新增、扩容、回收
     */
    public enum OrderType {
        INCREASE(10), EXPAND(20), recycle(30);

        public final int value;

        OrderType(int value){
            this.value = value;
        }
    }

    /**
     * 服务状态
     * 暂存、已注册、上线、下线
     */
    public enum SvcState {
        TEMP_STORAGE(10), REGISTERED(20), ONLINE(30), OFFLINE(40);

        public final int value;

        SvcState(int value){
            this.value = value;
        }
    }

    /**
     * 用户数据权限：
     *
     * 平台管理员 admin_p
     * 运营管理员 admin_yy
     * 用户组管理员 admin_group
     * 普通用户 user
     */
    public enum UserAuthority {
        ADMIN_P("admin_p"), ADMIN_YY("admin_yy"), ADMIN_GROUP("admin_group"), USER("user");

        public final String value;

        UserAuthority(String value){
            this.value = value;
        }
    }

    /**
     * 订购方式
     * 审批、付费、自动开通
     */
    public enum Ways {
        ORDER_APPROVE(10), PAY(20), PROBATION(30);
        public final int value;

        Ways(int value){
            this.value = value;
        }
    }

    /**
     * 数据库记录保存基本信息指令
     * 1.新增
     * 2.更新
     */
    public enum Directive {
        SAVE(1), UPDATE(2);

        public final int value;

        Directive(int value){this.value = value;}
    }

    /**
     * 套餐状态：10 暂存，20发布
     */
    public enum PackageState{
        SCRATCH(10), PUBLISH(20);

        public final int value;

        PackageState(int value){this.value = value;}
    }

    /**
     * 控制模式类型
     * 10 按时间
     * 20 按资源
     */
    public enum ModelType{
        TIME(10), RESOURCE(20);

        public final int value;

        ModelType(int value){this.value = value;}
    }

    /**
     * 订单状态
     * 10：待支付
     * 20：支付成功
     * 30：待审批
     * 40：审批中
     * 50：通过
     * 60：驳回
     * 100：被占用，扩展时请不要用此值（这个数值在查询的时代表有效订单即20与50的并集）
     */
    public enum OrderState{
        NO_PAYMENT(10), PAYMENT_SUCCESS(20), NO_APPROVAL(30), APPROVAL_PROCESS(40), APPROVAL_SUCCESS(50),FAIL(60);

        public final int value;

        OrderState(int value){this.value=value;}
    }

    /**
     * 支付状态
     * 10：支付成功
     * 20：支付失败
     */
    public enum PaymentState{
        PAYMENT_SUCCESS(10), PAYMENT_FAIL(20);
        public final int value;
        PaymentState(int value){this.value=value;}
    }

    /**
     * 实例运行状态
     * 60 未创建
     * 10：未启动
     * 1010: 启动中
     * 20：运行
     * 30：停止
     * 3010：停止中
     * 40：失败
     * 50：异常
     */
    public enum InstanceWorkState{
        UNSTART(10),STARTING(1010), RUNNING(20),STOP(30),STOPING(3010), FAIL(40), EXCEPTION(50),UNCREATE(60);

        public final int value;

        InstanceWorkState(int value){this.value=value;}
    }

    /**
     * 服务订购状态
     * 10:创建中
     * 20:正常
     * 30:已到期
     */
    public enum InstanceOrderState{
        CREATION(10),NORMAL(20),EXPIRE(30);
        public final int value;

        InstanceOrderState(int value){this.value=value;}
    }

    /**
     * 节点状态
     * 10：未启动
     * 1010: 启动中
     * 20：运行中
     * 30：停止
     * 3010：停止中
     * 40:失败
     * 50：异常
     */
    public enum NodeState{
        UNSTART(10),STARTING(1010),RUNNING(20),STOP(30),STOPING(3010),FAIL(40), EXCEPTION(50);

        public final int value;

        NodeState(int value){this.value=value;}
    }
    
    /**
     * 节点状态
     * 10：未启动
     * 1010: 启动中
     * 20：运行中
     * 30：停止
     * 3010：停止中
     * 40:失败
     * 50：异常
     */
    public enum NodeStateStr{
        UNSTART("unstart"),STARTING("starting"),RUNNING("running"),STOP("stop"),STOPING("stoping"),FAIL("fail"), EXCEPTION("exception");

        public final String value;

        NodeStateStr(String value){this.value=value;}
    }

    /**
     * 实例操作（动词）
     * 10：创建
     * 20：启动
     * 30：停止
     * 40：逻辑删除
     * 50：彻底删除
     * 60：恢复
     */
    public enum InstanceOperation{
        CREATING(10), STARTING(20), STOPOING(30), LOGICAL_DELETING(40), COMPLETE_DELETING(50), RECOVERING(60);
        public final int value;
        InstanceOperation(int value){this.value=value;}
    }

    /**
     * 10  待处理
     * 20  已处理
     * 30  已受理
     */
    public enum FeedBackState{
        PENDING(10), PROCESSED(20), ACCEPTED(30);

        public final int value;

        FeedBackState(int value) {
            this.value = value;
        }
    }
    
    /**
     * 资源类型
     * 0 文本 
     * 1 js 
     * 2 css 
     * 3 图片 
     * 4 flash 
     * 5 视频
     */
    public enum ResType{
        TEXT(0), JS(1), CSS(2), IMAGE(3), FLASH(4), VIDEO(5);
        public final int value;
        ResType(int value){this.value=value;}
    }

    /**
     * 1   待处理
     * 2   处理成功
     * 3   SP调用异常
     * 4   SP调用响应异常
     * 5   SP调用响应失败
     * 6   SP调用响应成功
     * 7   SP回调超时
     * 8   SP回调响应异常
     * 9   SP回调响应失败
     * 10  SP回调响应成功
     * 11  handle调用异常
     * 12  handle调用响应异常
     * 13  handle调用响应失败
     * 14  handle调用响应成功
     */
    public enum OperState{
        PENDING(1), SUCCESS(2),
        SP_CALL_EXCEPTION(3),SP_CALL_RSPONSE_EXCEPTION(4), SP_CALL_RESPONSE_FAIL(5), SP_CALL_RESPONSE_SUCCESS(6),
        SP_CALLBACK_OVERTIME(7), SP_CALLBACK_RESPONSE_EXCEPTION(8), SP_CALLBACK_RESPONSE_FAIL(9),
        SP_CALLBACK_RESPONSE_SUCCESS(10),
        HANDLE_EXCEPTION(11), HANDLE_RESPONSE_EXCEPTION(12), HANDLE_RESPONSE_FAIL(13), HANDLE_RESPONSE_SUCCESS(14);
        public final int value;
        OperState(int value) {
            this.value = value;
        }
    }

    /**
     * 0    成功
     * -1   失败
     */
    public enum ResultCode{
        SUCCESS(0), FAIL(-1),SUCCESS_1(1);
        public final int value;
        ResultCode(int value) {
            this.value = value;
        }
    }

    /**
     * 业务数据权限枚举类型
     * root : 系统管理员
     * yunwei_admin : 运维管理员
     * tenant_root : 租户管理员
     * svc_provision : 服务提供者
     * svc_use : 服务使用者
     * svc_use_scan : 服务使用浏览者
     * normal : 普通用户
     */
    public enum DataRoleCode{
        ROOT("root"), YUNWEI_ADMIN("yunwei_admin"),TENANT_ROOT("tenant_root"), SVC_PROVISION("svc_provision"), SVC_USE("svc_use"),SVC_USE_SCAN("svc_use_scan"),NORMAL("normal"), LOGINID_ADMIN("admin");
        public final String value;
        DataRoleCode(String value) {
            this.value = value;
        }
    }

    /**
     * 调用安全接口使用的枚举参数
     * 主体类型(USER,ROLE,ORG)
     */
    public enum MasterType{
        USER("USER"),ROLE("ROLE"),ORG("ORG");
        public final String value;
        MasterType(String value) {
            this.value = value;
        }
    }
    
    /**
     * 支付类型
     * 10  按时间
     * 20  按资源
     * 30  按时间和资源同时计算
     */
    public enum ChargeType{
    		TIME(10), RESOURCE(20),ALLTIMERES(30);

        public final int value;

        ChargeType(int value){this.value = value;}
    }
    
    /**
     * 按时间支付的时间单位
     * 10  日
     * 20  月
     * 30  年
     */
    public enum ChargeTimeType{
    		DAY(10), MONTH(20), YEAR(30);

        public final int value;

        ChargeTimeType(int value){this.value = value;}
    }
    
    /**
     * 是否签收
     * 10  已签收
     * 20  未签收
     * 30  默认签收
     */
    public enum SignToken{
    		SIGNIN(10),NOTSIGNIN(20),DEFALUTSIGN(30);

    		public final int value;

    	    SignToken(int value){this.value = value;}
    }
    
    /**
     * 查询数据所有
     * 00  查询所有（不限制tenantid和loginid）
     * 10  查询该租户下所有（只限制tenantid）
     * 20  查询该用户下所有（限制tenantid和loginid）
     * 30  服务商,暂时只做标记，不做处理
     */
    public enum AuthToken{
    		All("00"),TenantId("10"),LoginId("20"),Provider("30");

    		public final String value;

    		AuthToken(String value){this.value = value;}
    }
    

    public enum ErrorInfo{
        REQUEST_EXCEPTION("101","请求参数异常"),
        SP_CALL_EXCEPTION("201","服务提供方服务异常"),
        SP_CALL_RESPONSE_EXCEPTION("202","服务提供方响应参数异常"),
        SP_CALL_RESPONSE_FAIL("203","服务提供方返回失败"),
        HANDLE_EXCEPTION("301","handle方法异常"),
        HANDLE_RESPONSE_EXCEPTION("302","handle方法响应参数异常"),
        HANDLE_RESPONSE_FAIL("303", "handle方法返回失败"),
        SERVER_INTERNAL_EXCEPTION("999", "服务内部异常");
        public final String errorCode;
        public final String errorDesc;
        ErrorInfo(String errorCode, String errorDesc) {
            this.errorCode = errorCode;
            this.errorDesc = errorDesc;
        }
        public static String getErrorDesc(String errorCode) {
        	ErrorInfo infor[] = ErrorInfo.values();
        	for(int i=0;i<infor.length;i++){
        		if(infor[i].errorCode.equals(errorCode)){
        			return infor[i].errorDesc;
        		}
        	}
        	return "未找到对应失败编码描述";
        }
        public static boolean isNeedCallHandle(String errorCode) {
            String startChar = errorCode.substring(0, 1);
            if("3".equals(startChar) || "9".equals(startChar)) {
                return false;
            }
            return true;
        }
    }
    
    /**
     * bcm 常用枚举
     * CIFILEPRE：Ftp路径前缀
     */
    public enum BCMConstant {
    	CIFILEPRE("/ci"),IMAGEFILEPRE("/image");
    	public final String value;
    	BCMConstant(String value) {
    		this.value = value;
    	}
    }
}
