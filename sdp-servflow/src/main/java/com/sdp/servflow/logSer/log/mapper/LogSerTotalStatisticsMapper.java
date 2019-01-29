package com.sdp.servflow.logSer.log.mapper;

import java.util.List;
import java.util.Map;

import com.sdp.servflow.logSer.log.model.Ser_Id_Version_Statistics;

public interface LogSerTotalStatisticsMapper {

    int selectCountSer(Map map);

    List<Ser_Id_Version_Statistics> selectserVersion(Map map);
    
    Ser_Id_Version_Statistics selectSyncSeridVerIdName(Map<String, String> map);

	Ser_Id_Version_Statistics selectAsynSeridName(Map<String, String> map);

	Ser_Id_Version_Statistics selectCASSeridName(Map<String, String> map);

	Ser_Id_Version_Statistics selectSer_id_version_nameSendCount(Ser_Id_Version_Statistics sivs);

	Ser_Id_Version_Statistics selectSer_id_Version_Name(Ser_Id_Version_Statistics sivs);

	void ser_id_Version_NameUpdate(Ser_Id_Version_Statistics sivsTotal);

	void ser_id_Version_NameSave(Ser_Id_Version_Statistics sivsTotal);
}
