package com.sdp.code.service;

import java.util.List;

import com.sdp.code.entity.PCodeItem;
import com.sdp.code.entity.PCodeSet;

public interface IPCodeInfoService {

	List<PCodeSet> getCodeSet();

	List<PCodeItem> getCodeItem();

	void insertCodeSet(PCodeSet codeSet);

	void insertCodeItem(PCodeItem codeItem);

	PCodeItem singleCodeItem(String id);

	List<PCodeItem> initSetTable(String parentId);

	List<PCodeItem> initDetailTable(String parentId);

	void deleteCodeItem(PCodeItem codeItem);

	void deleteCodeItemByTypePath(PCodeItem codeItem);

	void deleteCodeItemBySetId(String setId);

	void deleteCodeSet(PCodeSet codeSet);

	void updateCodeItem(PCodeItem codeItem);

	void updateCodeSet(PCodeSet codeSet);

}
