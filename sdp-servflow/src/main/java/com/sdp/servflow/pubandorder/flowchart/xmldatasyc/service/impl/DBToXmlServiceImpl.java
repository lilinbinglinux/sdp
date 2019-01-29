package com.sdp.servflow.pubandorder.flowchart.xmldatasyc.service.impl;
//package com.bonc.servflow.pubandorder.flowchart.xmldatasyc.service.impl;
//
//import java.io.BufferedInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.sql.Connection;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import javax.servlet.http.HttpServletResponse;
//
//import org.jdom.Document;
//import org.jdom.Element;
//import org.jdom.output.Format;
//import org.jdom.output.XMLOutputter;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Service;
//
//import com.bonc.servflow.pubandorder.flowchart.xmldatasyc.dao.Connecter;
//import com.bonc.servflow.pubandorder.flowchart.xmldatasyc.dao.TableInfoConfig;
//import com.bonc.servflow.pubandorder.flowchart.xmldatasyc.parser.DBToXML;
//import com.bonc.servflow.pubandorder.flowchart.xmldatasyc.service.DBToXmlService;
//
//@Service
//@ConfigurationProperties(prefix = "system.conf", ignoreUnknownFields = true)
//public class DBToXmlServiceImpl implements DBToXmlService {
//    
//    private String flowchartfiles;
//    
//    public String getFlowchartfiles() {
//        return flowchartfiles;
//    }
//
//    public void setFlowchartfiles(String flowchartfiles) {
//        this.flowchartfiles = flowchartfiles;
//    }
//
//    @Override
//    public Map<String,Object> exportfile(String flowchartId,String flowchartName) throws Exception {
//        Map<String,Object> result = new HashMap<String,Object>();
//        String rs = "";
//        Connection conn = null;
//        Connecter con = new Connecter();
//        DBToXML dtx = new DBToXML();
//        conn = con.connMySql();
//        Document doc = new Document(new Element(TableInfoConfig.ROOTNAME));
//        
//        //layout_node
//        Map<String,String> layout_node_map = 
//            SqlConstruction(TableInfoConfig.TABLE_LAYOUTNODE,flowchartId,TableInfoConfig.ElEMENT_LAYOUTNODE);
//        doc = dtx.saveXmlFile(conn,layout_node_map,doc);
//        
//        //layout_node_join
//        Map<String,String> layout_node_join_map = 
//            SqlConstruction(TableInfoConfig.TABLE_LAYOUTNODEJOIN,flowchartId,TableInfoConfig.ElEMENT_LAYOUTNODEJOIN);
//        doc = dtx.saveXmlFile(conn,layout_node_join_map,doc);
//        
//        //layout_param
//        Map<String,String> layout_param_map = 
//            SqlConstruction(TableInfoConfig.TABLE_LAYOUTPARAM,flowchartId,TableInfoConfig.ElEMENT_LAYOUTPARAM);
//        doc = dtx.saveXmlFile(conn,layout_param_map,doc);
//        
//        //layout_pub
//        Map<String,String> layout_pub_map = 
//            SqlConstruction(TableInfoConfig.TABLE_LAYOUTPUB,flowchartId,TableInfoConfig.ElEMENT_LAYOUTPUB);
//        doc = dtx.saveXmlFile(conn,layout_pub_map,doc);
//        
//        //pub_interfaceres
//        Map<String,String> pub_interfaceres_map = 
//            SqlConstruction(TableInfoConfig.TABLE_PUBINTERFACERES,flowchartId,TableInfoConfig.ElEMENT_PUBINTERFACERES);
//        doc = dtx.saveXmlFile(conn,pub_interfaceres_map,doc);
//        
//        //pub_reqparam
//        Map<String,String> pub_reqparam_map = 
//            SqlConstruction(TableInfoConfig.TABLE_PUBREQPARAM,flowchartId,TableInfoConfig.ElEMENT_PUBREQPARAM);
//        doc = dtx.saveXmlFile(conn,pub_reqparam_map,doc);
//        
//        //输出xml文档
//        XMLOutputter xout = new XMLOutputter(Format.getPrettyFormat());
//        FileOutputStream fos = null;
//        
//        Map<Object,Object> map= createFile(flowchartName);
//        
//        if((boolean)map.get("createflag")){
//            fos = new FileOutputStream((String)map.get("file"),true); 
//            xout.output(doc, fos);
//            rs = "success";
//        }else{
//            rs = "failed";
//        }
//        
//        if(conn != null) {
//            conn.close();
//            conn = null;
//        }
//        result.put("result", rs);
//        result.put("fileName", map.get("fileName"));
//        return result;
//    }
//    
//    /**
//     * 
//     * Description:sql构造器 
//     * 
//     *@param tablename
//     *@param flowChartId
//     *@param elementName
//     *@return Map<String,String>
//     *
//     * @see
//     */
//    private static Map<String,String> SqlConstruction (String tablename,String flowChartId,String elementName){
//        Map<String,String> map = new HashMap<String,String>();
//        String sql = "";
//        
//        if(tablename.contains("layout_")){
//            sql = "select * from "+tablename+" where flowChartId = '"+flowChartId+"'";
//        }else if(tablename.contains("pub_")){
//            sql = "select * from "+tablename+" where pubid = '"+flowChartId+"'";
//        }
//        
//        map.put("elementName", elementName);
//        map.put("sql", sql);
//        return map;
//    }
//    
//    /**
//     * 
//     * Description:创建文件 
//     * 
//     *@param fileName
//     *@return Map<Object,Object>
//     *
//     * @see
//     */
//   private Map<Object,Object> createFile(String fileName){
//       Map<Object,Object> map = new HashMap();
//       Boolean bool = false;
//       
//       Date now = new Date(); 
//       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
//       String sdate = sdf.format(now);
//        
//       fileName = fileName+sdate+".xml";//文件路径+文件名称+文件类型
//       System.out.println(getFlowchartfiles()+fileName);
//       File file = new File(getFlowchartfiles()+fileName);
//       try{
//           if(!file.exists()){
//               file.createNewFile();
//               bool = true;
//           }
//       }catch(Exception e){
//           bool = false;
//           e.printStackTrace();
//       }
//       
//       map.put("fileName", fileName);
//       map.put("file", getFlowchartfiles()+fileName);
//       map.put("createflag", bool);
//       
//       return map;
//   }
//   
//   
//   
//   
//   @Override
//   public void downloadFile(HttpServletResponse res,String fileName) {
//       res.setHeader("content-type", "application/octet-stream");
//       res.setContentType("application/octet-stream");
//       res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//       byte[] buff = new byte[1024];
//       BufferedInputStream bis = null;
//       OutputStream os = null;
//       try {
//         os = res.getOutputStream();
//         bis = new BufferedInputStream(new FileInputStream(new File(flowchartfiles
//             + fileName)));
//         int i = bis.read(buff);
//         System.out.println(i);
//         while (i != -1) {
//           os.write(buff, 0, buff.length);
//           os.flush();
//           i = bis.read(buff);
//           System.out.println(i);
//         }
//       } catch (IOException e) {
//         e.printStackTrace();
//       } finally {
//         if (bis != null) {
//           try {
//             bis.close();
//           } catch (IOException e) {
//             e.printStackTrace();
//           }
//         }
//       }
//     }
//}
