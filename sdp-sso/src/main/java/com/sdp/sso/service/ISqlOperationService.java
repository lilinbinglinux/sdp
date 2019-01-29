package com.sdp.sso.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

public interface ISqlOperationService {
    
    /**
     *
     * @Description 根据项目Id查询项目概要信息
     * @param projectNo
     * @return
     */
    List<Object[]> getProjectInfo(String projectNo);
    
    /**
     *
     * @Description 根据项目Id查询项目详细信息
     * @param projectNo
     * @return
     */
    List<Object[]> getProjectDetail(String projectNo);
    
    /**
     * @Description 根据项目Id查询项目里程碑信息
     * @param projectNo
     * @return
     */
    Map<String, Object> getProjectLifeCycle(String projectNo);
    
    
    /**
     *
     * @Description 根据项目Id查询其他费用
     * @param projectNo
     * @return
     */
    List<Object[]> getProOtherIndicators(String projectNo);
    
        /**
     * 根据项目编号查询：项目编号、人力成本、purchase、差旅费、CLAIMINGEXPENSES、项目租房、预估总成本、CUSTOMINVESTMENT、市场销售费用、
     *市场其他费用、合同额、售前费用其他费用、成本合计、外包成本
     * @param projectNo
     * @return
     */
    List<Object[]> getPreValueOfOperatingIndicators(String projectNo);
    
    /**
     * @Description 根据项目Id查询项目经营指标构成中实际值
     * @param projectNo
     * @return
     */
    List<Object[]> getActOperatingIndicatorData(String projectNo);
    
    /**
     * @Description 根据项目Id查询项目经营指标构成中实际值
     * @param projectNo
     * @return
     */
    String getProjectTypeByProjectNo(String projectNo);
    
    /**
     * @Description 根据项目ID查询预估人月
     * @param projectNo
     * @return
     */
    List<Object[]> getPrePeopleMothList(String projectNo);
    
    /**
     * @Description 根据项目ID查询实际人月
     * @param projectNo
     * @return
     */
    List<Object[]> getActPeopleMothList(String projectNo);
    
    /**
     * @Description 根据项目ID查询实施类项目其他指标
     * @param projectNo
     * @return
     */
    List<Object[]> getExecuteOtherIndicators(@RequestParam(required = true) String projectNo);
    
    /**
     * @Description 根据项目ID查询实施类项目其他指标
     * @param projectNo
     * @return
     */
    List<Object[]> getAvgInputPeopleWeek(@RequestParam(required = true) String projectNo);
    
    /**
     *
     * @Description 根据项目Id查询人员投入信息
     * @param projectNo
     * @return
     */
    List<Object[]> getWorkHoursList(String projectNo);

    /**
     * 获取实际人月
     * @param projectNo
     * @return
     */
    List<Object[]> getActManMonth(String projectNo);

    /**
     * 获取已签合同额和采购发生
     * @param projectNo
     * @return
     */
    List<Object[]> getActHtMoneyAndPurchase(String projectNo);

    /**
     * 获取上周平均人数
     * @param projectNo
     * @return
     */
    List<Object[]> getLastWeekAvg(String projectNo);

    /**
     * 根据项目id及职员id获取该职员人力成本
     * @param projectNo
     * @param employeeId
     * @return
     */
    List<Object[]> getLaborCostsOfEmployeeId(String projectNo, String employeeId);

    /**
     * 根据项目id获取人力成本
     * @param projectNo
     * @return
     */
    List<Object[]> getLaborCosts(String projectNo);
}
