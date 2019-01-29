package com.sdp.servflow.serlayout.process.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.sdp.common.CurrentUserUtils;
import com.sdp.frame.util.IdUtil;
import com.sdp.servflow.pubandorder.node.analyze.abstractfactory.NodeFactory;
import com.sdp.servflow.pubandorder.node.analyze.factory.NodeFactoryImp;
import com.sdp.servflow.pubandorder.node.model.node.EndNode;
import com.sdp.servflow.pubandorder.node.model.node.Node;
import com.sdp.servflow.pubandorder.node.model.node.StartNode;
import com.sdp.servflow.pubandorder.node.xmlpackage.XmlBuilderUtil;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceMainBean;
import com.sdp.servflow.pubandorder.servicebasic.model.ServiceVersionBean;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceMainService;
import com.sdp.servflow.pubandorder.servicebasic.service.ServiceVersionService;
import com.sdp.servflow.serlayout.datahandler.SerFlowDataConstant;
import com.sdp.servflow.serlayout.datahandler.SerNormalData;
import com.sdp.servflow.serlayout.process.model.ServiceInfoPo;
import com.sdp.servflow.serlayout.process.service.SerProcessXmlSysService;
import com.sdp.servflow.serlayout.process.service.ServiceInfoPoService;
import com.sdp.servflow.serlayout.sso.model.SerspLoginBean;
import com.sdp.servflow.serlayout.sso.service.SerspLoginBeanService;

@Service
@ConfigurationProperties(prefix = "system.conf", ignoreUnknownFields = true)
public class SerProcessXmlSysServiceImpl implements SerProcessXmlSysService {

    @Autowired
    private ServiceInfoPoService serviceInfoPoService;

    @Autowired
    private ServiceMainService serviceMainService;

    @Autowired
    private ServiceVersionService serviceVersionService;

    @Autowired
    private SerspLoginBeanService serspLoginBeanService;

    @Autowired
    private SerNormalData serNormalData;
    
    //导出文件的临时文件夹（定时任务定时清理）
    private String flowchartfiles;

    public String getFlowchartfiles() {
        return flowchartfiles;
    }

    public void setFlowchartfiles(String flowchartfiles) {
        this.flowchartfiles = flowchartfiles;
    }
    
    //导入文件临时文件夹（定时任务定时清理）
    private String filesavepath;

    public String getFilesavepath() {
        return filesavepath;
    }

    public void setFilesavepath(String filesavepath) {
        this.filesavepath = filesavepath;
    }

    @Override
    public Map<String, Object> exportfile(String serVerId, String serId, String serName) throws Exception {
        Map<String, Object> result = new HashMap<String, Object>();
        String rs = "";
        String serFlow = "";

        Map<String, String> parammap = new HashMap<String, String>();
        parammap.put("serVerId", serVerId);
        parammap.put("serId", serId);
        parammap.put("delFlag", "0");
        parammap.put("tenantId", CurrentUserUtils.getInstance().getUser().getTenantId());
        parammap.put("loginId", CurrentUserUtils.getInstance().getUser().getLoginId());

        List<ServiceInfoPo> serviceInfoPos = serviceInfoPoService.getAllByCondition(parammap);
        if (null != serviceInfoPos && serviceInfoPos.size() > 0) {
            serFlow = serviceInfoPos.get(0).getSerFlow();
        }

        System.out.println(serFlow);
        Map<Object, Object> map = createFile(serId);

        if ((boolean) map.get("createflag")) {
            FileOutputStream fos = new FileOutputStream((String) map.get("file"), true);
            //true表示在文件末尾追加
            fos.write(serFlow.getBytes());
            fos.close();

            rs = "success";
        } else {
            rs = "failed";
        }

        result.put("result", rs);
        result.put("fileName", map.get("fileName"));
        return result;
    }

    /**
     * Description:创建文件
     *
     * @param fileName
     * @return Map<Object,Object>
     * @see
     */
    private Map<Object, Object> createFile(String fileName) {
        Map<Object, Object> map = new HashMap();
        Boolean bool = false;

        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("-yyyyMMddhhmmss");
        String sdate = sdf.format(now);

        fileName = fileName + sdate + ".txt";//文件路径+文件名称+文件类型
        if(!isValidFileName(fileName)) {
	    	    String regEx = "[`~!@#$%^&*()\\-+={}':;,\\[\\].<>/?￥%…（）_+|【】‘；：”“’。，、？\\s]";
	    	    Pattern p = Pattern.compile(regEx);
	    	    Matcher m = p.matcher(fileName);
	    	    fileName = m.replaceAll("").trim();
		}
        System.out.println(getFlowchartfiles() + fileName);
        File file = new File(getFlowchartfiles() + fileName);
        File filedir = new File(getFlowchartfiles());
        try {
        		if(!filedir.exists()) {
        			filedir.mkdir();  
        		}
            if (!file.exists()) {
                file.createNewFile();
            }
            bool = true;
        } catch (Exception e) {
            bool = false;
            e.printStackTrace();
        }

        map.put("fileName", fileName);
        map.put("file", getFlowchartfiles() + fileName);
        map.put("createflag", bool);

        return map;
    }


    @Override
    public void downloadFile(HttpServletResponse response, String fileName){
    		if(!isValidFileName(fileName)) {
        	    String regEx = "[`~!@#$%^&*()\\-+={}':;,\\[\\].<>/?￥%…（）_+|【】‘；：”“’。，、？\\s]";
        	    Pattern p = Pattern.compile(regEx);
        	    Matcher m = p.matcher(fileName);
        	    fileName = m.replaceAll("").trim();
    		}
    			 String path = flowchartfiles + fileName;
    		        InputStream fis = null;
    		        try {

    		            // path是指欲下载的文件的路径。
    		            File file = new File(path.trim());
    		            // 取得文件名。
    		            String filename = file.getName();

    		            // 以流的形式下载文件。
    		            fis = new BufferedInputStream(new FileInputStream(path));
    		            byte[] buffer = new byte[fis.available()];
    		            fis.read(buffer);
    		            fis.close();
    		            // 清空response
    		            response.reset();
    		            // 设置response的Header
    		            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
    		            response.addHeader("Content-Length", "" + file.length());
    		            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
    		            response.setContentType("application/octet-stream");
    		            toClient.write(buffer);
    		            toClient.flush();
    		            toClient.close();
    		            
    		        } catch (IOException e) {
    		            e.printStackTrace();
    		        } finally {
    		            if (fis != null) {
    		                try {
    		                    fis.close();
    		                } catch (IOException e) {
    		                    e.printStackTrace();
    		                }
    		            }
    		        }
       
    }


    @Override
    public Object uploadFile(HttpServletRequest request, HttpServletResponse response, String fileName) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        File filepath = new File(filesavepath);
        if (!filepath.exists()) {
            filepath.mkdirs();
        }

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        // 获得文件
        MultipartFile multipartFile = multipartRequest.getFile("file");
        OutputStream os = null;
        InputStream is = null;
        File uploadFile = null;
        String resultStr = "";
        try {
            is = multipartFile.getInputStream();
            if (is != null) {
                uploadFile = new File(filesavepath, System.currentTimeMillis() + ".xml");
                os = new FileOutputStream(uploadFile);
                IOUtils.copy(is, os);
                os.flush();
            }

            resultStr = this.dbHandler(uploadFile, fileName);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        } finally {
            IOUtils.closeQuietly(os);
            IOUtils.closeQuietly(is);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", resultStr);
        return jsonObject;
    }


    /**
     * 数据库数据处理
     */
    public String dbHandler(File file, String fileName) throws Exception {

        String serflowStr = fileString(file).trim();
        System.out.println(serflowStr);
        InputStream is = new ByteArrayInputStream(serflowStr.getBytes("utf-8"));
        SAXBuilder sb = new SAXBuilder();
        Document doc = sb.build(is);
        Element sourceRoot = doc.getRootElement();
        List<Element> list = sourceRoot.getChildren();
        List<Node> nodelist = new ArrayList<Node>();

        NodeFactory factory = NodeFactoryImp.getOneNodeFactory();
        StartNode startnode = null;
        EndNode endnode = null;
        for (Element element : list) {
            Node node = factory.createNode(element);
            
            //开始节点上的名称不能重复（文件上传加别名来进行区分）
            if (node.getNodeStyle().equals(Node.startNodeStyle)) {
                startnode = (StartNode) node;
                if(startnode.getNodeName().contains("fileup")) {
                		String name = startnode.getNodeName();
                		String initname = name.substring(0,name.indexOf("(fileup"));
                		name = name.substring(name.indexOf("(fileup"), name.indexOf(")")+1);
                		List<Long> digitList = new ArrayList<Long>();
                	    Pattern p = Pattern.compile("(\\d+)");
                	    Matcher m = p.matcher(name);
                	    while (m.find()) {
                	        String find = m.group(1).toString();
                	        digitList.add(Long.valueOf(find));
                	    }
                	    long cout = digitList.get(0)+1;
                	    initname = initname + "(fileup"+ cout + ")";
                	    startnode.setNodeName(initname);
                }else {
                	 	startnode.setNodeName(startnode.getNodeName()+"(fileup1)");
                }
               
            }
            if (node.getNodeStyle().equals(Node.endNodeStyle)) {
                endnode = (EndNode) node;
            }
            nodelist.add(node);
        }
        String typeId = startnode.getSerType();
        
        serflowStr = XmlBuilderUtil.getSingleStion().build(nodelist);

        //如果为空则默认为同步服务
        typeId = typeId == "" ? SerFlowDataConstant.synchronously_id : typeId;
        typeId = typeId == 	null ? SerFlowDataConstant.synchronously_id : typeId;
        String typeStr = serNormalData.serTypeHandler(typeId);

        try {
            if (typeStr.equals(SerFlowDataConstant.synchronously_id)) {
                ServiceMainBean bean = this.serviceMainBeanHandler(startnode);
                this.serviceVersionBeanHandler(startnode, endnode, serflowStr, bean);
            } else if (typeStr.equals(SerFlowDataConstant.spcas_id)) {
                this.serspLoginBeanHandler(startnode, serflowStr);
            } else {
                return "fail";
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return "success";
    }


    /**
     * 读取txt文件的内容
     *
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    private static String fileString(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    private ServiceMainBean serviceMainBeanHandler(Node node) {
        //serviceMainBean 插入
//        ServiceMainBean serviceMainBean = serviceMainService.getByPrimaryKey(node.getFlowChartId());
//        if(serviceMainBean != null) {
//        		throw new RuntimeException();
//        }else {
//    			
//        }

        ServiceMainBean bean = new ServiceMainBean();
        StartNode startnode = (StartNode) node;
        bean.setSerId(IdUtil.createId());
        bean.setSerName(startnode.getNodeName());
        bean.setSerType(startnode.getSerType());
        String sercode = bean.getSerId();
        bean.setSerCode(sercode.substring(sercode.length()-6, sercode.length()));
        bean.setSerCode(bean.getSerId().substring(0, 6));
        bean.setSerVersion("1.0.0");
        bean.setSerResume("");
        bean.setCreatTime(new Date());
        bean.setSynchFlag("0");
        bean.setDelFlag("0");
        bean.setStopFlag("0");
        bean.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
        bean.setLoginId(CurrentUserUtils.getInstance().getUser().getLoginId());
        try {
            serviceMainService.insert(bean);
        } catch (Exception e) {
            new RuntimeException();
            e.printStackTrace();
        }

        return bean;
    }

    private void serviceVersionBeanHandler(StartNode startnode, EndNode endnode, String serflowStr, ServiceMainBean bean) {
        //serviceVersionBean 插入
        ServiceVersionBean serviceVersionBean = new ServiceVersionBean();
        serviceVersionBean.setSerVerId(IdUtil.createId());
        serviceVersionBean.setSerVersion("1.0.0");
        serviceVersionBean.setSerId(bean.getSerId());
        serviceVersionBean.setSerAgreement(startnode.getAgreement());
        serviceVersionBean.setSerRequest(startnode.getParam().getFormat());
        serviceVersionBean.setSerResponse(endnode.getParam().getFormat());
        /*
		 * TODO 
		 * 1.对外暴露接口请求方式
		 * 2.对外暴漏接口端口
		 */
        serviceVersionBean.setSerRestType("1");
        //serviceVersionBean.setSerPoint("8080");
        serviceVersionBean.setSerFlow(serflowStr);
        serviceVersionBean.setCreatTime(new Date());
        serviceVersionBean.setDelFlag("0");
        serviceVersionBean.setStopFlag("0");
        serviceVersionBean.setLoginId(CurrentUserUtils.getInstance().getUser().getLoginId());
        serviceVersionBean.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());

        serviceVersionService.insert(serviceVersionBean);

    }

    private void serspLoginBeanHandler(StartNode startnode, String serflowStr) {
        SerspLoginBean serspLoginBean = new SerspLoginBean();
        serspLoginBean.setSploginid(IdUtil.createId());
        serspLoginBean.setSpname(startnode.getNodeName());
        serspLoginBean.setSpcode(serspLoginBean.getSploginid().substring(0, 6));
        serspLoginBean.setSptype(startnode.getSerType());
        //serspLoginBean.setSppath();
        serspLoginBean.setSpresume("");
        serspLoginBean.setSpagreement(startnode.getAgreement());
        serspLoginBean.setSprestype(startnode.getParam().getFormat());
        serspLoginBean.setSpflow(serflowStr);
        serspLoginBean.setCreatime(new Date());
        serspLoginBean.setDelflag("0");
        serspLoginBean.setStopflag("0");
        serspLoginBean.setLoginId(CurrentUserUtils.getInstance().getUser().getLoginId());
        serspLoginBean.setTenantId(CurrentUserUtils.getInstance().getUser().getTenantId());
        serspLoginBeanService.insert(serspLoginBean);
    }
    
    /**
     * 文件名称校验
     * @param fileName
     * @return
     */
    public static boolean isValidFileName(String fileName) { 
    		if (fileName == null || fileName.length() > 255) 
    			return false; 
    		else return fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$"); 
    	}
    
}
