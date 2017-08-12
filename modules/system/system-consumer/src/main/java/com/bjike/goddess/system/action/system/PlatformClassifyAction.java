package com.bjike.goddess.system.action.system;

import com.bjike.goddess.common.api.dto.Restrict;
import com.bjike.goddess.common.api.entity.ADD;
import com.bjike.goddess.common.api.entity.EDIT;
import com.bjike.goddess.common.api.exception.ActException;
import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.api.restful.Result;
import com.bjike.goddess.common.consumer.action.BaseFileAction;
import com.bjike.goddess.common.consumer.interceptor.login.LoginAuth;
import com.bjike.goddess.common.consumer.restful.ActResult;
import com.bjike.goddess.common.utils.bean.BeanTransform;
import com.bjike.goddess.common.utils.excel.Excel;
import com.bjike.goddess.common.utils.excel.ExcelUtil;
import com.bjike.goddess.storage.api.FileAPI;
import com.bjike.goddess.system.api.PlatformClassifyAPI;
import com.bjike.goddess.system.bo.PlatformClassifyBO;
import com.bjike.goddess.system.dto.PlatformClassifyDTO;
import com.bjike.goddess.system.entity.PlatformClassify;
import com.bjike.goddess.system.excel.PlatformClassifyExcel;
import com.bjike.goddess.system.to.PlatformClassifyTO;
import com.bjike.goddess.system.vo.PlatformClassifyVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * 平台分类
 *
 * @Author: [ xiazhili ]
 * @Date: [ 2017-08-10 01:44 ]
 * @Description: [ 平台分类 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@RestController
@RequestMapping("platformclassify")
public class PlatformClassifyAction extends BaseFileAction{
    @Autowired
    private PlatformClassifyAPI platformClassifyAPI;
    /**
     * 平台分类列表总条数
     *
     * @param dto 平台分类记录dto
     * @des 获取所有平台分类
     * @version v1
     */
    @GetMapping("v1/count")
    public Result count(PlatformClassifyDTO dto) throws ActException {
        try {
            Long count = platformClassifyAPI.count(dto);
            return ActResult.initialize(count);
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }

    /**
     * 一个平台分类
     *
     * @param id
     * @return class PlatformClassifyVO
     * @des 获取一个平台分类
     * @version v1
     */
    @GetMapping("v1/wait/{id}")
    public Result wait(@PathVariable String id) throws ActException {
        try {
            PlatformClassifyBO platformClassifyBO = platformClassifyAPI.getOne(id);
            return ActResult.initialize(BeanTransform.copyProperties(platformClassifyBO, PlatformClassifyVO.class));
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }


    /**
     * 平台分类列表
     *
     * @param dto 平台分类记录dto
     * @return class PlatformClassifyVO
     * @des 获取所有平台分类
     * @version v1
     */
    @GetMapping("v1/list")
    public Result list(PlatformClassifyDTO dto, HttpServletRequest request) throws ActException {
        try {
            List<PlatformClassifyVO> platformClassifyVOS = BeanTransform.copyProperties(
                    platformClassifyAPI.list(dto), PlatformClassifyVO.class, request);
            return ActResult.initialize(platformClassifyVOS);
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }

    /**
     * 添加平台分类
     *
     * @param to 平台分类to
     * @return class PlatformClassifyVO
     * @des 添加平台分类
     * @version v1
     */
    @LoginAuth
    @PostMapping("v1/add")
    public Result add(PlatformClassifyTO to, BindingResult bindingResult) throws ActException {
        try {
            PlatformClassifyBO platformClassifyBO = platformClassifyAPI.insert(to);
            return ActResult.initialize(BeanTransform.copyProperties(platformClassifyBO,PlatformClassifyVO.class));
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }

    /**
     * 编辑平台分类
     *
     * @param to 平台分类数据to
     * @return class PlatformClassifyVO
     * @des 编辑平台分类
     * @version v1
     */
    @LoginAuth
    @PostMapping("v1/edit")
    public Result edit(PlatformClassifyTO to, BindingResult bindingResult) throws ActException {
        try {
            PlatformClassifyBO platformClassifyBO = platformClassifyAPI.edit(to);
            return ActResult.initialize(BeanTransform.copyProperties(platformClassifyBO,PlatformClassifyVO.class));
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }

    /**
     * 删除平台分类
     *
     * @param id 用户id
     * @des 根据用户id删除平台分类
     * @version v1
     */
    @LoginAuth
    @DeleteMapping("v1/delete/{id}")
    public Result delete(@PathVariable String id) throws ActException {
        try {
            platformClassifyAPI.remove(id);
            return new ActResult("delete success!");
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }
    /**
     * 导入Excel
     *
     * @param request 注入HttpServletRequest对象
     * @version v1
     */
    @LoginAuth
    @PostMapping("v1/importExcel")
    public Result importExcel(HttpServletRequest request) throws ActException {
        try {
            List<InputStream> inputStreams = super.getInputStreams(request);
            InputStream is = inputStreams.get(1);
            Excel excel = new Excel(0, 1);
            List<PlatformClassifyExcel> tos = ExcelUtil.excelToClazz(is, PlatformClassifyExcel.class, excel);
            List<PlatformClassifyTO> tocs = new ArrayList<>();
            for (PlatformClassifyExcel str : tos) {
                PlatformClassifyTO platformClassifyTO = BeanTransform.copyProperties(str, PlatformClassifyTO.class,"updateTime","fieldUpdateTime","newNodeUpdateTime");
                platformClassifyTO.setUpdateTime(String.valueOf(str.getUpdateTime()));
                platformClassifyTO.setFieldUpdateTime(String.valueOf(str.getFieldUpdateTime()));
                platformClassifyTO.setNewNodeUpdateTime(String.valueOf(str.getNewNodeUpdateTime()));
                tocs.add(platformClassifyTO);
            }
            //注意序列化
            platformClassifyAPI.importExcel(tocs);
            return new ActResult("导入成功");
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        }
    }

    /**
     * 导出excel
     *
     * @param dto 平台分类
     * @des 导出平台分类
     * @version v1
     */
    @LoginAuth
    @GetMapping("v1/export")
    public Result exportReport(PlatformClassifyDTO dto, HttpServletResponse response) throws ActException {
        try {
            String fileName = "平台分类.xlsx";
            super.writeOutFile(response, platformClassifyAPI.exportExcel(dto), fileName);
            return new ActResult("导出成功");
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        } catch (IOException e1) {
            throw new ActException(e1.getMessage());
        }
    }

    /**
     * excel模板下载
     *
     * @des 下载模板平台分类
     * @version v1
     */
    @GetMapping("v1/templateExport")
    public Result templateExport(HttpServletResponse response) throws ActException {
        try {
            String fileName = "平台分类导入模板.xlsx";
            super.writeOutFile(response, platformClassifyAPI.templateExport(), fileName);
            return new ActResult("导出成功");
        } catch (SerException e) {
            throw new ActException(e.getMessage());
        } catch (IOException e1) {
            throw new ActException(e1.getMessage());
        }
    }

}