package com.sdp.servflow.pubandorder.flowchart.service;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.pubandorder.flowchart.model.ProcessNodeJoin;

public interface ProcessNodeJoinService {
	void addJoin(ProcessNodeJoin processNodeJoin);

	List<ProcessNodeJoin> findAllByFlowId(Map<String, String> map);

	void deleteAll(Map<String, String> map);
}
