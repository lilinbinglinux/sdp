package com.sdp.sqlModel.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sdp.common.CurrentUserUtils;
import com.sdp.common.entity.Status;
import com.sdp.frame.web.entity.user.User;
import com.sdp.sqlModel.entity.SqlField;
import com.sdp.sqlModel.entity.SqlShape;
import com.sdp.sqlModel.entity.SqlSubTable;
import com.sdp.sqlModel.entity.SqlSubTreasury;
import com.sdp.sqlModel.entity.SqlSubTreasuryDivision;
import com.sdp.sqlModel.entity.SqlTable;
import com.sdp.sqlModel.entity.SqlTableForeignkey;
import com.sdp.sqlModel.mapper.SqlFieldMapper;
import com.sdp.sqlModel.mapper.SqlShapeMapper;
import com.sdp.sqlModel.mapper.SqlSubTableMapper;
import com.sdp.sqlModel.mapper.SqlSubTreasuryDivisionMapper;
import com.sdp.sqlModel.mapper.SqlSubTreasuryMapper;
import com.sdp.sqlModel.mapper.SqlTableForeignkeyMapper;
import com.sdp.sqlModel.mapper.SqlTableMapper;

@Service
public class SqlTableService {
	@Autowired SqlTableMapper sqlTableDao;
	@Autowired SqlFieldMapper sqlFieldDao;
	@Autowired SqlTableForeignkeyMapper sqlTableForeignkeyMapper;
	@Autowired SqlShapeMapper sqlShapeMapper;
//	@Autowired SqlPublicPartitionMapper sqlPublicPartitionMapper;
//	@Autowired SqlPartitionMapper sqlPartitionMapper;
	@Autowired SqlSubTableMapper sqlSubTableMapper;
	@Autowired SqlSubTreasuryMapper sqlSubTreasuryMapper;
//	@Autowired SqlSubdivisionMapper sqlSubdivisionMapper;
	@Autowired SqlSubTreasuryDivisionMapper sqlSubTreasuryDivisionMapper;
	
	private static final Logger log = LoggerFactory.getLogger(SqlTableService.class);

	public List<SqlTable> findByTenantId(String dataResId){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		return sqlTableDao.findByDataResIdAndTenantId(dataResId);
	}
	public List<SqlTable> findByTenantIdAndTableTypeId(String tableTypeId){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		return sqlTableDao.findByTenantIdAndTableTypeId(userInfo.getTenantId(),tableTypeId);
	}
	
	public Map<String,Object> findSqlTableByTableTypeId(String tableTypeId){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		List<SqlTable> sqlTables = new ArrayList<SqlTable>();
		List<String> sqlTableIdes = new ArrayList<String>();
		List<SqlTable> sqlTableIdNew = new ArrayList<SqlTable>();
		StringBuilder sBuilder = new StringBuilder();
		List<SqlTableForeignkey> sqlTableForeignkeyes = null;
		List<SqlTable> sqlTableResult = findByTenantIdAndTableTypeId(tableTypeId);
		if(sqlTableResult != null && !sqlTableResult.isEmpty()){
			for (int i = 0; i < sqlTableResult.size(); i++) {
				if(i != sqlTableResult.size()-1){
					sBuilder.append("'"+sqlTableResult.get(i).getTableId()+"',");
				}else{
					sBuilder.append("'"+sqlTableResult.get(i).getTableId()+"'");
				}
				sqlTableIdes.add(sqlTableResult.get(i).getTableId());
				sqlTables.add(findByTableId(sqlTableResult.get(i).getTableId()));
			}
			if(!StringUtils.isBlank(sBuilder.toString())){
				sqlTableForeignkeyes = sqlTableForeignkeyMapper.foreignkeyInfo(sBuilder.toString(), userInfo.getTenantId());
				if(sqlTableForeignkeyes != null && !sqlTableForeignkeyes.isEmpty()){
					for (SqlTableForeignkey sqlTableForeignkey2 : sqlTableForeignkeyes) {
						if(!sqlTableIdes.contains(sqlTableForeignkey2.getMainTable())){
							sqlTableIdNew.add(findByTableId(sqlTableForeignkey2.getMainTable()));
							sqlTableIdes.add(sqlTableForeignkey2.getMainTable());
						}else if(!sqlTableIdes.contains(sqlTableForeignkey2.getJoinTable())){
							sqlTableIdNew.add(findByTableId(sqlTableForeignkey2.getJoinTable()));
							sqlTableIdes.add(sqlTableForeignkey2.getJoinTable());
						}
					}
				}
			}
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sqlTables", sqlTables);
		map.put("sqlTableIdNew", sqlTableIdNew);
		map.put("sqlTableForeignkeyes", sqlTableForeignkeyes);
		return map;
	}
	/**
	 * 获取表信息
	 * @param tableId
	 * @return
	 */
	public SqlTable findByTableId(String tableId){
		User userInfo = CurrentUserUtils.getInstance().getUser();
		SqlTable sqlTable = sqlTableDao.findByTableId(tableId);
		if(sqlTable != null){
			List<SqlField> sqlFieldes = sqlFieldDao.findByTableIdTenantId(tableId, userInfo.getTenantId());
			if(sqlFieldes != null && !sqlFieldes.isEmpty()){
				sqlTable.setSqlFieldes(sqlFieldes);
			}
			List<SqlShape> sqlShapes = sqlShapes( sqlTable, userInfo);
			if(sqlShapes!=null && !sqlShapes.isEmpty()){
				sqlTable.setSqlShape(sqlShapes);
			}
		}
		return sqlTable;
	}
	/**
	 * 获取表形态信息
	 * @param sqlTable
	 * @param userInfo
	 * @return
	 */
	public List<SqlShape> sqlShapes(SqlTable sqlTable,User userInfo){
		List<SqlShape> sqlShapes = sqlShapeMapper.findByTableId(sqlTable.getTableId(), userInfo.getTenantId());
		if(sqlShapes != null && !sqlShapes.isEmpty()){
			for (SqlShape sqlShape : sqlShapes) {
				if(sqlShape.getShapeType().equals("1")){//1 分区 2 分表 3 分库
				}else if(sqlShape.getShapeType().equals("2")){//
					SqlSubTable sqlSubTable = sqlSubTableMapper.findSqlSubTable(sqlShape.getShapeId(),  userInfo.getTenantId());
					if(sqlSubTable != null){
						sqlShape.setSqlSubTable(sqlSubTable);
					}
				}else if(sqlShape.getShapeType().equals("3")){//
					SqlSubTreasury sqlSubTreasuryes = sqlSubTreasuryMapper.findSqlSubTreasury(sqlShape.getShapeId(),  userInfo.getTenantId());
					if(sqlSubTreasuryes != null){
						//--查询子库信息
						List<SqlSubTreasuryDivision> sqlSubTreasuryDivisiones = sqlSubTreasuryDivisionMapper.findDivision(sqlSubTreasuryes.getSubTreasuryId(), userInfo.getTenantId());
						if(sqlSubTreasuryDivisiones != null && !sqlSubTreasuryDivisiones.isEmpty()){
							sqlSubTreasuryes.setSqlSubTreasuryDivision(sqlSubTreasuryDivisiones);
						}
						sqlShape.setSqlSubTreasury(sqlSubTreasuryes);
					}
				}
			}
		}
		return sqlShapes;
	}
	
	/**
	 * 坐标保存
	 */
	@Transactional(rollbackFor = Exception.class)
	public Status saveCoordinate(List<Map<String, Object>> tableIdes) {
		Status status = null;
		User userInfo = CurrentUserUtils.getInstance().getUser();
        if(tableIdes != null && !tableIdes.isEmpty()){
        	for (Map<String, Object> table : tableIdes) {
        		if(table != null ){
        			sqlTableDao.updateCoordinate(table);
        			Map<String, Object> map = new HashMap<String, Object>();
        			map.put("foreignY", table.get("tableX"));
        			map.put("foreignX", table.get("tableY"));
        			map.put("mainTable", table.get("tableId"));
        			map.put("tenantId", userInfo.getTenantId());
        			sqlTableForeignkeyMapper.updateCode(map);
        		}
			}
        }
        return status;
	}
	
	/**
	 * 移表接口
	 */
	@Transactional(rollbackFor = Exception.class)
	public Status changeType(String tableId,String tableTypeId) {
		Status status = null;
		User userInfo = CurrentUserUtils.getInstance().getUser();
		sqlTableDao.updateTableTypeId(tableId, tableTypeId);
		return status;
	}
	
}
