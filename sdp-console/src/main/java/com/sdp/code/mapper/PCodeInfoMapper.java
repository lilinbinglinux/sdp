package com.sdp.code.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.sdp.code.entity.PCodeItem;
import com.sdp.code.entity.PCodeSet;



@Repository
public interface PCodeInfoMapper {

	List<PCodeSet> getCodeSet();

	List<PCodeItem> getCodeItem();

	List<PCodeSet> getCodeSetByParentId(String parentId);

	List<PCodeItem> getCodeItemByParentId(String parentId);

	void insertCodeSet(PCodeSet codeSet);

	void insertCodeItem(PCodeItem codeItem);

	PCodeItem findItemById(String itemId);

	void deleteCodeItem(PCodeItem codeItem);

	void deleteCodeItemByTypePath(PCodeItem codeItem);

	void deleteCodeItemBySetId(String setId, String delFlag);

//	void deleteItemByParentId(String parentId);

//	void deleteSetByParentId(String parentId);

	void deleteCodeSet(PCodeSet codeSet);

	void updateCodeItem(PCodeItem codeItem);

	void updateCodeSet(PCodeSet codeSet);

	Integer maxSort();
	
}
