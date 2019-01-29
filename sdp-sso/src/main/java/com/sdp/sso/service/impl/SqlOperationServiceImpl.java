package com.sdp.sso.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.sdp.sso.service.ISqlOperationService;

@Service("sqlOperationService")
public class SqlOperationServiceImpl implements ISqlOperationService {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    // @Value("${datasource.name.epm}")
    // private String datasourceNameEpm ;
    //
    // @Value("${datasource.name.dm.oms}")
    // private String datasourceNameDMOms ;
    
    public Map<String, Object> getProjectLifeCycle(String projectNo) {
        
        String sql = "SELECT  P.STARTDATE, P.ENDDATE FROM EPM.PROJECT_UNI_TEST P WHERE P.PROJECTNO='" + projectNo + "'";
        
        List<Object[]> List = (List<Object[]>) entityManager.createNativeQuery(sql).getResultList();
        if (!CollectionUtils.isEmpty(List)) {
            Object[] obj = List.get(0);
            if (obj != null && obj.length > 0) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("startData", isEmpty(obj[0]));
                map.put("endData", isEmpty(obj[1]));
                
                return map;
            }
        }
        return new HashMap<String, Object>();
    }
    
    /**
     * 判空
     * 
     * @param obj
     * @return
     */
    private Object isEmpty(Object obj) {
        
        return obj == null || "".equals(String.valueOf(obj)) ? null : String.valueOf(obj);
    }
    
    /**
     * 获取市场人力成本或者获取实施自有人力成本
     * 
     * @param projectNo
     * @return
     */
    public List<Object[]> getLaborCosts(String projectNo) {
        
        String sql3 = "SELECT ROUND(SUM(B5.WORKLOAD * B5.SALARY)*1.3/10000,2) AS PEOPLEMONEY "
            + "FROM (SELECT SUBSTR(B2.WORK_DATE_TIME,1,6) AS ACCTMONTH,B1.PROJECT_ID AS PROJECTNO,B1.PROJECT_NAME AS PROJECTNAME,"
            + "B2.EMPLOYEE_NO AS EMPLOYEENO,B3.EMPLOYEE_NAME AS EMPLOYEENAME,SUM(B1.WORK_HOURS),CAST(SUM(B1.WORK_HOURS)/8/21 AS DECIMAL (10,2)) AS WORKLOAD,B4.TLEVEL_NAME AS TLEVELNAME,"
            + "(CASE WHEN B4.TLEVEL_NAME='实习生' THEN 3000 WHEN B4.TLEVEL_NAME='初级工程师' THEN 7000 WHEN B4.TLEVEL_NAME='中级工程师' "
            + "THEN 11000 WHEN B4.TLEVEL_NAME='高级工程师' THEN 17500 WHEN B4.TLEVEL_NAME='架构师' THEN 27500 WHEN B4.TLEVEL_NAME='专家' "
            + "THEN 55000 WHEN B4.TLEVEL_NAME='资深专家' THEN 75000 END) AS SALARY FROM BOM.WORK_ATTENCE_RECORD_PROJECT B1 "
            + "LEFT JOIN BOM.WORK_ATTENCE_RECORD B2 ON B1.RECORD_ID=B2.RECORD_ID LEFT JOIN DW.DW_PEOPLE_INFO_CUR B3 ON B2.EMPLOYEE_NO=B3.EMPLOYEE_ID "
            + "LEFT JOIN DW.DW_PEOPLE_T B4 ON B3.EMPLOYEE_ID=B4.EMPLOYEE_ID WHERE B1.PROJECT_ID='" + projectNo
            + "' GROUP BY SUBSTR(B2.WORK_DATE_TIME,1,6),B1.PROJECT_ID,B2.EMPLOYEE_NO) B5 GROUP BY B5.PROJECTNO";
        List<Object[]> list3 = (List<Object[]>) entityManager.createNativeQuery(sql3).getResultList();
        return list3;
    }
    
    public List<Object[]> getLaborCostsOfEmployeeId(String projectNo, String employeeId) {
        
        String sql3 = "SELECT ROUND(SUM(B5.WORKLOAD * B5.SALARY)*1.3/10000,2) AS PEOPLEMONEY "
            + "FROM (SELECT SUBSTR(B2.WORK_DATE_TIME,1,6) AS ACCTMONTH,B1.PROJECT_ID AS PROJECTNO,B1.PROJECT_NAME AS PROJECTNAME,"
            + "B2.EMPLOYEE_NO AS EMPLOYEENO,B3.EMPLOYEE_NAME AS EMPLOYEENAME,SUM(B1.WORK_HOURS),CAST(SUM(B1.WORK_HOURS)/8/21 AS DECIMAL (10,2)) AS WORKLOAD,B4.TLEVEL_NAME AS TLEVELNAME,"
            + "(CASE WHEN B4.TLEVEL_NAME='实习生' THEN 3000 WHEN B4.TLEVEL_NAME='初级工程师' THEN 7000 WHEN B4.TLEVEL_NAME='中级工程师' "
            + "THEN 11000 WHEN B4.TLEVEL_NAME='高级工程师' THEN 17500 WHEN B4.TLEVEL_NAME='架构师' THEN 27500 WHEN B4.TLEVEL_NAME='专家' "
            + "THEN 55000 WHEN B4.TLEVEL_NAME='资深专家' THEN 75000 END) AS SALARY FROM BOM.WORK_ATTENCE_RECORD_PROJECT B1 "
            + "LEFT JOIN BOM.WORK_ATTENCE_RECORD B2 ON B1.RECORD_ID=B2.RECORD_ID LEFT JOIN DW.DW_PEOPLE_INFO_CUR B3 ON B2.EMPLOYEE_NO=B3.EMPLOYEE_ID "
            + "LEFT JOIN DW.DW_PEOPLE_T B4 ON B3.EMPLOYEE_ID=B4.EMPLOYEE_ID AND b4.employee_id = '" + employeeId + "' WHERE B1.PROJECT_ID='"
            + projectNo + "' GROUP BY SUBSTR(B2.WORK_DATE_TIME,1,6),B1.PROJECT_ID,B2.EMPLOYEE_NO) B5 GROUP BY B5.PROJECTNO";
        List<Object[]> list3 = (List<Object[]>) entityManager.createNativeQuery(sql3).getResultList();
        return list3;
    }
    
    public String getProjectTypeByProjectNo(String projectNo) {
        
        String sql = "SELECT TYPE FROM EPM.PROJECT_UNI_TEST WHERE PROJECTNO='" + projectNo + "'";
        List<Object[]> List = (List<Object[]>) entityManager.createNativeQuery(sql).getResultList();
        String result = "";
        if (!CollectionUtils.isEmpty(List)) {
            String obj = String.valueOf(List.get(0));
            if (obj != null && !"".equals(obj)) {
                result = obj;
            }
        }
        return result;
    }
    
    @Override
    public List<Object[]> getAvgInputPeopleWeek(String projectNo) {
        
        List<Object[]> list = null;
        
        if (!StringUtils.isEmpty(projectNo)) {
            
            StringBuffer sql = new StringBuffer("SELECT");
            
            sql.append(" ROUND(COUNT(*)/5) AS '上周平均人数'");
            sql.append(" FROM ");
            sql.append(" bom.work_attence_record_project w ");
            sql.append(" JOIN epm.project_uni_test p ON w.project_id = p.projectNo ");
            sql.append(" WHERE ");
            sql.append(" p.projectNo =  ");
            sql.append("\'");
            sql.append(projectNo);
            sql.append("\'");
            sql.append(" AND YEARWEEK(DATE_FORMAT(SUBSTR(w.create_time, 1, 8),'%Y-%m-%d'))=YEARWEEK(NOW())-1");
            
            list = (List<Object[]>) entityManager.createNativeQuery(sql.toString()).getResultList();
            
        }
        return list;
    }
    
    public List<Object[]> getWorkHoursList(String projectNo) {
        
        List<Object[]> workHoursList = null;
        if (!StringUtils.isEmpty(projectNo)) {
            
            String sql = "SELECT a.acctDay,a. NAME,a.project_id,count(DISTINCT a.employee_no) AS peopleNumber,"
                + "SUM(a.work_hours) AS workHours, CAST(SUM(a.work_hours)/8/21 AS DECIMAL (10,2)) AS workLoad, "
                + "count(DISTINCT CASE WHEN a.isOrg=1 THEN a.employee_no ELSE NULL END) AS navOrgPeopleNumber, "
                + "sum(CASE WHEN a.isOrg=1 THEN a.work_hours ELSE 0 END) AS navOrgWorkHours, "
                + "CAST(sum(CASE WHEN a.isOrg=1 THEN a.work_hours ELSE 0 END)/8/21 AS DECIMAL (10,2)) AS navOrgWorkLoad, "
                + "count(DISTINCT CASE WHEN a.isOrg=0 THEN a.employee_no ELSE NULL END) AS outOrgPeopleNumber, "
                + "sum(CASE WHEN a.isOrg=0 THEN a.work_hours ELSE 0 END) AS outOrgWorkHours2, "
                + "CAST(SUM(CASE WHEN a.isOrg=0 THEN a.work_hours ELSE 0 END)/8/21 AS DECIMAL (10,2)) AS outOrgWorkLoad "
                + "FROM (SELECT SUBSTR(b2.work_date_time,1,8) AS acctDay,b1.project_id,b3.`name`,b1.employee_no AS employee_no,b1.work_hours AS work_hours,"
                + "(CASE WHEN b3.administrativeOrganizationId=b4.ORG_id THEN 1 ELSE 0 END) AS isOrg "
                + "FROM bom.work_attence_record_project b1 JOIN bom.work_attence_record b2 ON b1.record_id=b2.record_id "
                + "JOIN epm.project_uni_test b3 ON b1.project_id=b3.projectNo JOIN dw.dw_people_info_cur b4 ON b1.employee_no=b4.Employee_ID "
                + "WHERE b1.project_id='" + projectNo + "'" + ") a GROUP BY a.acctDay,a.project_id";
            
            workHoursList = (List<Object[]>) entityManager.createNativeQuery(sql.toString()).getResultList();
        }
        
        return workHoursList;
    }
    
    @Override
    public List<Object[]> getProOtherIndicators(String projectNo) {
        
        List<Object[]> list = null;
        
        if (!StringUtils.isEmpty(projectNo)) {
            String sql = " SELECT t.*,t.sumHk-t.sumCg AS jhk from (select h.NEWNO AS projectNo,h.NEWNAME AS projectName,"
                + "(SELECT ROUND(sum(IFNULL(h2.INCOMEMONEY,0)/h8.projNum)/10000,2) AS hk FROM dm_oms.DW_HT_RETURN_INCOME h2 "
                + "WHERE h2.HTNO=h1.HT_NO) AS sumHk,(SELECT ROUND(sum(IFNULL(h4.INCOMEMONEY_YS,0)/ h8.projNum)/10000,2) AS ys "
                + "FROM dm_oms.DM_RETURN_CHARGE_YS_ORD h4 WHERE h4.HT_NO=h.HTNO) AS sumYs,(SELECT ROUND(sum(IFNULL(h6.CHANGEDMONEY,0)/ h8.projNum)/10000,2) AS cg "
                + "FROM dm_oms.DW_CG_MANUAL h6 WHERE h6.HTNO=h1.HT_NO)AS sumCg FROM dm_oms.bo_ht_pnew_poa h JOIN dm_oms.dm_ht_sell_ord h1 ON h.HTNO=h1.HT_NO "
                + "JOIN (SELECT count(NEWNO) AS projNum,HTNO FROM dm_oms.bo_ht_pnew_poa GROUP BY HTNO) h8 ON h.HTNO=h8.HTNO WHERE h.HTNO!='' "
                + "AND h.OANO!='' AND h.NEWNO='" + projectNo + "') t";
            
            list = (List<Object[]>) entityManager.createNativeQuery(sql).getResultList();
            
        }
        
        return list;
    }
    
    @Override
    public List<Object[]> getPrePeopleMothList(String projectNo) {
        
        List<Object[]> prePeopleMothList = null;
        if (!StringUtils.isEmpty(projectNo)) {
            StringBuffer prePeopleMonthSql = new StringBuffer(" SELECT P.projectNo,");
            prePeopleMonthSql.append(" P.workLoad ");
            prePeopleMonthSql.append(" FROM ");
            prePeopleMonthSql.append(" PROJECT_UNI_TEST P ");
            prePeopleMonthSql.append(" where P.PROJECTNO = '");
            prePeopleMonthSql.append(projectNo);
            prePeopleMonthSql.append("\'");
            
            // 预估人月
            prePeopleMothList = (List<Object[]>) entityManager.createNativeQuery(prePeopleMonthSql.toString()).getResultList();
        }
        
        return prePeopleMothList;
    }
    
    @Override
    public List<Object[]> getActPeopleMothList(String projectNo) {
        
        List<Object[]> actPeopleMothList = null;
        if (!StringUtils.isEmpty(projectNo)) {
            
            StringBuffer actPeopleMonthSql = new StringBuffer(" SELECT b.project_id,");
            actPeopleMonthSql.append(" cast( ");
            actPeopleMonthSql.append(" sum(b.work_hours) / 8 / 21 AS DECIMAL (10, 2) ");
            actPeopleMonthSql.append(" ) AS workload ");
            actPeopleMonthSql.append(" FROM ");
            actPeopleMonthSql.append(" bom.work_attence_record_project b ");
            actPeopleMonthSql.append(" where b.project_id =");
            actPeopleMonthSql.append("\'");
            actPeopleMonthSql.append(projectNo);
            actPeopleMonthSql.append("\'");
            actPeopleMonthSql.append(" GROUP BY ");
            actPeopleMonthSql.append(" b.project_id ");
            
            // 实际人月
            actPeopleMothList = (List<Object[]>) entityManager.createNativeQuery(actPeopleMonthSql.toString()).getResultList();
        }
        return actPeopleMothList;
    }
    
    @Override
    public List<Object[]> getExecuteOtherIndicators(String projectNo) {
        
        List<Object[]> list = null;
        
        if (!StringUtils.isEmpty(projectNo)) {
            
            String sql = " SELECT t.*,t.sumHk-t.sumCg AS jhk from (select h.NEWNO AS projectNo,h.NEWNAME AS projectName,"
                + "(SELECT ROUND(sum(IFNULL(h2.INCOMEMONEY,0)/h8.projNum)/10000,2) AS hk FROM dm_oms.DW_HT_RETURN_INCOME h2 "
                + "WHERE h2.HTNO=h1.HT_NO) AS sumHk,(SELECT ROUND(sum(IFNULL(h4.INCOMEMONEY_YS,0)/ h8.projNum)/10000,2) AS ys "
                + "FROM dm_oms.DM_RETURN_CHARGE_YS_ORD h4 WHERE h4.HT_NO=h.HTNO) AS sumYs,(SELECT ROUND(sum(IFNULL(h6.CHANGEDMONEY,0)/ h8.projNum)/10000,2) AS cg "
                + "FROM dm_oms.DW_CG_MANUAL h6 WHERE h6.HTNO=h1.HT_NO)AS sumCg FROM dm_oms.bo_ht_pnew_poa h JOIN dm_oms.dm_ht_sell_ord h1 ON h.HTNO=h1.HT_NO "
                + "JOIN (SELECT count(NEWNO) AS projNum,HTNO FROM dm_oms.bo_ht_pnew_poa GROUP BY HTNO) h8 ON h.HTNO=h8.HTNO WHERE h.HTNO!='' "
                + "AND h.OANO!='' AND h.NEWNO='" + projectNo + "') t";
            list = (List<Object[]>) entityManager.createNativeQuery(sql).getResultList();
        }
        return list;
    }
    
    /**
     * 根据项目id获取关联商机项目编码
     * 
     * @param projectNo
     */
    private Object getBusinessCodeByProjectNo(String projectNo) {
        
        String sql = "SELECT P.LINKBUSINESSPROJECTID FROM EPM.PROJECT_UNI_TEST P WHERE P.PROJECTNO='" + projectNo + "'";
        List<Object[]> list = (List<Object[]>) entityManager.createNativeQuery(sql).getResultList();
        if (!CollectionUtils.isEmpty(list)) {
            String obj = String.valueOf(list.get(0));
            if (obj != null && !"".equals(obj) && !"null".equals(obj)) {
                return isEmpty(obj);
            }
        }
        return null;
    }
    
    public List<Object[]> getProjectInfo(String projectNo) {
        
        List<Object[]> list = null;
        
        if (!StringUtils.isEmpty(projectNo)) {
            String sql = "SELECT T1.PROJECTNO,T1.PROJECTDESCRIPTION,T1.CONTRACTMONEY,T1.ENDDATE,"
                + "DATEDIFF(T1.ENDDATE,T1.STARTDATE) AS DAYS,T1.WORKLOAD,T1.PURCHASE,T1.SPECIALREQUIRE,T1.PEOPLECOSTDESCRIPTION,"
                + "T1.PURCHASEDESCRIPTION,T1.PRETOTALCOST AS IMPCOSTYG,T2.PRETOTALCOST AS PREMARKETCOST,T1.`NAME`,T.ECODE_TEXT AS TYPE,"
                + "ROUND((CAST(T1.PRETOTALCOST AS DECIMAL (38,6))+CAST(IFNULL(T2.PRETOTALCOST,0) AS DECIMAL (38,6))),2) AS TOTALCOST,"
                + "ROUND((CAST(T1.CONTRACTMONEY AS DECIMAL (38,6))-CAST(T1.PURCHASE AS DECIMAL(38,6))),2) AS PRENETSALES,"
                + "ROUND((CAST(T1.CONTRACTMONEY AS DECIMAL (38,6))-CAST(T1.PURCHASE AS DECIMAL(38,6))-CAST(T1.PRETOTALCOST AS DECIMAL (38,6)))*100/(CAST(T1.CONTRACTMONEY AS DECIMAL (38,6))-CAST(T1.PURCHASE AS DECIMAL(38,6))),2) AS PREIMPRATE "
                + ",T1.PEOPLENUMBER,DP.ECODE_TEXT AS currentProcess,T1.customInvestment,T1.PRESALECOST,T1.MARKETOTHERCOST,RL.ecode_text AS devGrade,"
                + "o.org_name AS organizationName,T1.EXPECTTENDERDATE,T1.SIGNPOSSIBILITY,T1.otherExpensesDescription,T1.CUSTOMERMANAGERID "
                + "FROM EPM.PROJECT_UNI_TEST T1 LEFT JOIN EPM.PROJECT_UNI_TEST T2 ON T1.LINKBUSINESSPROJECTID=T2.PROJECTNO "
                + "LEFT JOIN EPM.DICTIONARY_TYPE_UNI_TEST T ON T1.TYPE=T.ECODE_ID LEFT JOIN EPM.dictionary_phase_uni_test DP ON T1.projectPhase=DP.ECODE_ID "
                + " LEFT JOIN EPM.dictionary_researchlevel_uni_test RL ON T1.researchLevel=RL.ecode_id LEFT JOIN `security`.orginfo o ON o.ID=T1.administrativeOrganizationId  WHERE T1.PROJECTNO='"
                + projectNo + "'";
            
            list = (List<Object[]>) entityManager.createNativeQuery(sql).getResultList();
        }
        return list;
    }
    
    public List<Object[]> getLastWeekAvg(String projectNo) {
        
        List<Object[]> list = null;
        if (!StringUtils.isEmpty(projectNo)) {
            String lastWeekAvgSql = "SELECT ROUND(COUNT(*)/5,2) FROM BOM.WORK_ATTENCE_RECORD_PROJECT W JOIN EPM.PROJECT_UNI_TEST P ON W.PROJECT_ID = P.PROJECTNO WHERE P.PROJECTNO = '"
                + projectNo + "' AND YEARWEEK(DATE_FORMAT(SUBSTR(W.CREATE_TIME, 1, 8),'%Y%m%d'))=YEARWEEK(NOW())-1";
            list = (List<Object[]>) entityManager.createNativeQuery(lastWeekAvgSql).getResultList();
        }
        return list;
    }
    
    public List<Object[]> getActHtMoneyAndPurchase(String projectNo) {
        
        List<Object[]> list = null;
        if (!StringUtils.isEmpty(projectNo)) {
            String actSql = "SELECT H.NEWNO AS PROJECTNO,H.NEWNAME AS PROJECTNAME,ROUND(SUM((H1.HT_MONEY/H7.PROJCOUNT))/10000,2) AS SUMHTMONEY,ROUND(SUM((H1.CG_MONEY/H7.PROJCOUNT))/10000,2) AS SUMCGMONEY "
                + "FROM DM_OMS.BO_HT_PNEW_POA H JOIN DM_OMS.DM_HT_SELL_ORD H1 ON H.HTNO=H1.HT_NO AND H.NEWNO='" + projectNo + "' "
                + " JOIN (SELECT COUNT(NEWNO) AS PROJCOUNT,HTNO FROM DM_OMS.BO_HT_PNEW_POA GROUP BY HTNO) H7 ON H7.HTNO=H.HTNO WHERE H.HTNO!='' AND H.OANO!='' GROUP BY PROJECTNO";
            list = (List<Object[]>) entityManager.createNativeQuery(actSql).getResultList();
        }
        return list;
    }
    
    public List<Object[]> getActManMonth(String projectNo) {
        
        List<Object[]> list = null;
        if (!StringUtils.isEmpty(projectNo)) {
            String actPeopleMothSql = "SELECT ROUND((SUM(w.work_hours))/8/21,2) FROM bom.work_attence_record_project w "
                + "JOIN epm.project_uni_test p ON w.project_id = p.projectNo WHERE p.projectNo = '" + projectNo
                + "' AND SUBSTR(w.create_time,1,4) = SUBSTR(DATE_FORMAT(NOW(),'%Y-%m'),1,4)";
            list = (List<Object[]>) entityManager.createNativeQuery(actPeopleMothSql).getResultList();
        }
        return list;
    }
    
    public List<Object[]> getProjectDetail(String projectNo) {
        
        List<Object[]> list = null;
        if (!StringUtils.isEmpty(projectNo)) {
                String sql = "SELECT t1.projectNo,o.org_name,t1.PROJECTMANAGERID,t1.projectManagerName,t1.CUSTOMERMANAGERID,d.Employee_NAME AS cManagerName"
                    + ",t1.customName,t1.CUSTOMERLEVEL,t1.PRODUCTMANAGERID,d2.EMPLOYEE_NAME AS productManagerName,t1.linkBusinessProjectId AS '关联商机项目编码',"
                    + "t2.`name` AS '关联商机项目名称',(SELECT ECODE_TEXT FROM epm.dictionary_stragtegytype_uni_test WHERE ECODE_ID=t1.strategyType) AS '战略性质',"
                    + "(SELECT GROUP_CONCAT(d.ECODE_TEXT) FROM ditionary_innovatetype_uni_test d WHERE find_in_set(d.ECODE_ID,t1.innovateType)) AS '创新类型',"
                    + "(SELECT ECODE_TEXT FROM epm.ditionary_continuitytype_uni_test WHERE ECODE_ID=t1.continuity) AS '项目持续性', "
                    + "(SELECT GROUP_CONCAT(HTNO) FROM dm_oms.bo_ht_pnew_poa WHERE NEWNO=t1.projectNo) AS '合同编号',org.ORG_NAME AS administrativeOrganizationId "
                    + "FROM epm.project_uni_test t1 LEFT JOIN epm.project_uni_test t2 ON t1.linkBusinessProjectId=t2.projectNo "
                    + "LEFT JOIN `security`.orginfo o ON o.ID=t1.manageOrganizationId LEFT JOIN `security`.orginfo org ON org.ID = t1.administrativeOrganizationId LEFT JOIN dw.dw_people_info_cur d "
                    + "ON d.Employee_ID=t1.customerManagerId LEFT JOIN DW.DW_PEOPLE_INFO_CUR d2 ON d2.EMPLOYEE_ID=t1.PRODUCTMANAGERID "
                    + "WHERE t1.projectNo='" + projectNo + "'";
            list = (List<Object[]>) entityManager.createNativeQuery(sql).getResultList();
        }
        return list;
    }
    
    @Override
    public List<Object[]> getPreValueOfOperatingIndicators(String projectNo) {
        
        List<Object[]> list = new ArrayList<Object[]>();
        
        if (!StringUtils.isEmpty(projectNo)) {
            String sum = "SELECT P1.PROJECTNO,P1.PEOPLECOST,P1.PURCHASE,P1.TRAVELEXPENSES,P1.CLAIMINGEXPENSES,P1.RENTCOST AS '项目房租（万）',P1.PRETOTALCOST "
                + ",P1.CUSTOMINVESTMENT,P1.MARKETSALECOST AS '市场销售费用（万）',P1.MARKETOTHERCOST AS '市场其他费用（万）', P1.CONTRACTMONEY,P1.PRESALECOST,"
                + "ROUND((CAST(P1.CONTRACTMONEY as DECIMAL(38,6))-CAST(P1.PURCHASE as DECIMAL(38,6))),2) AS NETSALES,"
                + "ROUND((CAST(P1.CONTRACTMONEY as DECIMAL(38,6))-CAST(P1.PURCHASE as DECIMAL(38,6))-CAST(P1.PRETOTALCOST as DECIMAL(38,6))),2) AS GROSSPROFIT, "
                + "ROUND((P1.CONTRACTMONEY - P1.PURCHASE - P1.PRETOTALCOST) * 100 / P1.PRETOTALCOST,2) AS GROSSPROFITRATE ,"
                + "P1.OTHEREXPENSES AS '其他费用（万）',P1.PRETOTALCOST AS '成本合计(万)',P1.SOURCECOST ,P2.MARKETSALECOST"
                + " FROM EPM.PROJECT_UNI_TEST P1  LEFT JOIN EPM.PROJECT_UNI_TEST P2 ON P1.linkBusinessProjectId = P2.projectNo WHERE P1.PROJECTNO ='"
                + projectNo + "'";
            list = (List<Object[]>) entityManager.createNativeQuery(sum).getResultList();
            if (list == null) {
                return new ArrayList<Object[]>();
            }
        }
        
        return list;
    }
    
    @Override
    public List<Object[]> getActOperatingIndicatorData(String projectNo) {
        
        List<Object[]> list = new ArrayList<Object[]>();
        
        if (!StringUtils.isEmpty(projectNo)) {
            // 市场侧
            String sql2 = "SELECT ROUND(SUM(CASE WHEN d.feetype='市场' AND substr(d.updatelitemcode,1,3)='201' AND d.updatelitemcode <> '20104' AND d.PROJECTNO!='' THEN IFNULL(d.changedmoney,0) ELSE 0 END)/10000,2) AS marketSaleMoney,"
                + "ROUND(SUM(CASE WHEN d.feetype='售前' AND substr(d.updatelitemcode,1,3)<>'201' AND d.PROJECTNO!='' THEN IFNULL(d.changedmoney,0) ELSE 0 END)/10000,2) AS preSaleMoney,"
                + "ROUND(SUM(CASE WHEN d.feetype='市场' AND substr(d.updatelitemcode,1,3) <> '201' AND d.PROJECTNO!='' THEN IFNULL(d.changedmoney,0) ELSE 0 END)/10000,2) AS otherMarketMoney,"
                + "ROUND(SUM(CASE WHEN d.feetype LIKE '日常%' AND substr(d.updatelitemcode,1,3)='203' AND d.PROJECTNO!='' THEN IFNULL(d.changedmoney,0) ELSE 0 END)/10000,2) AS travel,"
                + "ROUND(SUM(CASE WHEN d.feetype LIKE '日常%' AND substr(d.updatelitemcode,1,3)='204' AND d.PROJECTNO!='' THEN IFNULL(d.changedmoney,0) ELSE 0 END)/10000,2) AS rent,"
                + "ROUND(SUM(CASE WHEN ((d.feetype LIKE '日常%' AND substr(d.updatelitemcode,1,3) NOT IN ('203','204')) OR d.updatelitemcode='20104') AND d.PROJECTNO!='' THEN IFNULL(d.changedmoney,0) ELSE 0 END)/10000,2) AS other,"
                + "ROUND(SUM(CASE WHEN d.updatelitemcode='24108' AND d.PROJECTNO!='' THEN IFNULL(d.changedmoney,0) ELSE 0 END)/10000,2) AS peoplecost ,p.LINKBUSINESSPROJECTID "
                + "FROM epm.project_uni_test p LEFT JOIN dm_oms.bo_ht_pnew_poa b ON p.projectNo=b.NEWNO "
                + "LEFT JOIN dm_oms.dm_all_fee_detail_mon d ON d.PROJECTNO=b.OANO WHERE p.projectNo='" + projectNo + "' ";
            
            list = (List<Object[]>) entityManager.createNativeQuery(sql2).getResultList();
            if (list == null) {
                return new ArrayList<Object[]>();
            }
        }
        
        return list;
    }
    
}
