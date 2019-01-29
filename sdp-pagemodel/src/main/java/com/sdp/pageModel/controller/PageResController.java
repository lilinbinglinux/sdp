package com.sdp.pageModel.controller;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sdp.common.entity.BasePageForm;
import com.sdp.common.entity.Status;
import com.sdp.pageModel.entity.PageRes;
import com.sdp.pageModel.service.PageResService;

/**
 * @description:
 * 组件资源控制层
 * @author: zhoutao
 * @version: 15:30 2018/4/16
 * @see:
 * @since:
 * @modified by:
 */
@Controller
@RequestMapping("/v1/pageRes")
public class PageResController {

    /**
     * pageResService
     */
    @Autowired
    private PageResService pageResService;

    /**
     * 组件中添加资源，查询资源树结构
     * @param resTypeId resTypeId
     * @return map
     */
    @RequestMapping(value = {"/res/tree/{resTypeId}"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Map<String, Object>> findPageResTreeInfo(@PathVariable("resTypeId") String resTypeId){
        Map<String, Object> pageResult = pageResService.findPageResTreeInfo(resTypeId);
        return new ResponseEntity<>(pageResult, HttpStatus.OK);
    }


    /**
     * 分页查询当前资源类型下所属的资源
     * @param baseForm baseForm
     * @param resTypeId resTypeId
     * @return PagingResult
     */
    @RequestMapping(value = {"/restype/resinfo/{resTypeId}"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Map<String, Object>> findPageResInfoing(BasePageForm baseForm, @PathVariable("resTypeId") String resTypeId, String selectKey){
    	Map<String, Object> map = pageResService.findPageResInfoing(baseForm, resTypeId, selectKey);
        return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
    }

    /**
     * 新增资源数据
     * @param resTypeId resTypeId
     */
    @RequestMapping(value = {"/res/{resTypeId}"}, method = RequestMethod.POST)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Status> savePageResInfo(@PathVariable("resTypeId") String resTypeId, PageRes pageRes, @RequestParam(value = "uploadFile") MultipartFile uploadFile){
        Status status = pageResService.savePageResInfo(resTypeId, pageRes,  uploadFile);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

    /**
     * 删除资源数据
     * @param resId resId
     */
    @RequestMapping(value = {"/res/delete/{resId}"}, method = RequestMethod.DELETE)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Status> deletePageResInfo(@PathVariable("resId") String resId){
        Status status = pageResService.deletePageResInfo(resId);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

    /**
     * 修改资源数据
     * @param resId resId
     * @param pageRes PageRes
     */
    @RequestMapping(value = {"/res/update/{resId}"}, method = RequestMethod.PUT)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<Status> updatePageResInfo(@PathVariable("resId") String resId, PageRes pageRes, @RequestParam(value = "uploadFile") MultipartFile uploadFile){
        Status status = pageResService.updatePageResInfo(resId, pageRes, uploadFile);
        return new ResponseEntity<Status>(status, HttpStatus.OK);
    }

    /**
     * 查询资源数据
     * @param resId resId
     */
    @RequestMapping(value = {"/res/{resId}"}, method = RequestMethod.GET)
    @ResponseBody
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseEntity<PageRes> findPageResInfo(@PathVariable("resId") String resId){
        PageRes pageResult = pageResService.findByPrimaryKey(resId);
        return new ResponseEntity<PageRes>(pageResult, HttpStatus.OK);
    }

    /**
     * Description:
     * 查询资源文件数据
     * @param response
     * @param resId resId
     * @see
     */
    @RequestMapping(value = "/res/file/{resId}")
    @ResponseBody
    public void findPageResFileInfo(HttpServletResponse response, @PathVariable("resId") String resId) {
        pageResService.getResFileInfo(response, resId);
    }

}
