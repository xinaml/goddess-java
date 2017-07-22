package com.bjike.goddess.staffwelfaremanage.service;

import com.bjike.goddess.common.api.exception.SerException;
import com.bjike.goddess.common.jpa.service.ServiceImpl;
import com.bjike.goddess.common.provider.utils.RpcTransmit;
import com.bjike.goddess.common.utils.bean.BeanTransform;
import com.bjike.goddess.staffwelfaremanage.bo.PersonalFestivalBO;
import com.bjike.goddess.staffwelfaremanage.dto.PersonalFestivalDTO;
import com.bjike.goddess.staffwelfaremanage.entity.PersonalFestival;
import com.bjike.goddess.staffwelfaremanage.entity.PersonalFestivalWish;
import com.bjike.goddess.staffwelfaremanage.enums.GuideAddrStatus;
import com.bjike.goddess.staffwelfaremanage.to.GuidePermissionTO;
import com.bjike.goddess.staffwelfaremanage.to.PersonalFestivalTO;
import com.bjike.goddess.user.api.UserAPI;
import com.bjike.goddess.user.bo.UserBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 个人节日业务实现
 *
 * @Author: [ Jason ]
 * @Date: [ 2017-04-07 01:56 ]
 * @Description: [ 个人节日业务实现 ]
 * @Version: [ v1.0.0 ]
 * @Copy: [ com.bjike ]
 */
@CacheConfig(cacheNames = "staffwelfaremanageSerCache")
@Service
public class PersonalFestivalSerImpl extends ServiceImpl<PersonalFestival, PersonalFestivalDTO> implements PersonalFestivalSer {

    @Autowired
    private UserAPI userAPI;
    @Autowired
    private PersonalFestivalWishSer personalFestivalWishSer;

    @Autowired
    private CusPermissionSer cusPermissionSer;

    /**
     * 核对查看权限（部门级别）
     */
    private void checkSeeIdentity() throws SerException {
        Boolean flag = false;
        String userToken = RpcTransmit.getUserToken();
        UserBO userBO = userAPI.currentUser();
        RpcTransmit.transmitUserToken(userToken);
        String userName = userBO.getUsername();
        if (!"admin".equals(userName.toLowerCase())) {
            flag = cusPermissionSer.getCusPermission("1");
            if (!flag) {
                throw new SerException("您不是相应部门的人员，不可以操作");
            }
        }
        RpcTransmit.transmitUserToken(userToken);
    }

    /**
     * 核对添加修改删除审核权限（岗位级别）
     */
    private void checkAddIdentity() throws SerException {
        Boolean flag = false;
        String userToken = RpcTransmit.getUserToken();
        UserBO userBO = userAPI.currentUser();
        RpcTransmit.transmitUserToken(userToken);
        String userName = userBO.getUsername();
        if (!"admin".equals(userName.toLowerCase())) {
            flag = cusPermissionSer.busCusPermission("2");
            if (!flag) {
                throw new SerException("您不是相应部门的人员，不可以操作");
            }
        }
        RpcTransmit.transmitUserToken(userToken);
    }

    /**
     * 核对查看权限（部门级别）
     */
    private Boolean guideSeeIdentity() throws SerException {
        Boolean flag = false;
        String userToken = RpcTransmit.getUserToken();
        UserBO userBO = userAPI.currentUser();
        RpcTransmit.transmitUserToken(userToken);
        String userName = userBO.getUsername();
        if (!"admin".equals(userName.toLowerCase())) {
            flag = cusPermissionSer.getCusPermission("1");
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * 核对添加修改删除审核权限（岗位级别）
     */
    private Boolean guideAddIdentity() throws SerException {
        Boolean flag = false;
        String userToken = RpcTransmit.getUserToken();
        UserBO userBO = userAPI.currentUser();
        RpcTransmit.transmitUserToken(userToken);
        String userName = userBO.getUsername();
        if (!"admin".equals(userName.toLowerCase())) {
            flag = cusPermissionSer.busCusPermission("2");
        } else {
            flag = true;
        }
        return flag;
    }

    @Override
    public Boolean sonPermission() throws SerException {
        String userToken = RpcTransmit.getUserToken();
        Boolean flagSee = guideSeeIdentity();
        RpcTransmit.transmitUserToken(userToken);
        Boolean flagAdd = guideAddIdentity();
        if (flagSee || flagAdd) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean guidePermission(GuidePermissionTO guidePermissionTO) throws SerException {
        String userToken = RpcTransmit.getUserToken();
        GuideAddrStatus guideAddrStatus = guidePermissionTO.getGuideAddrStatus();
        Boolean flag = true;
        switch (guideAddrStatus) {
            case LIST:
                flag = guideSeeIdentity();
                break;
            case ADD:
                flag = guideAddIdentity();
                break;
            case EDIT:
                flag = guideAddIdentity();
                break;
            case AUDIT:
                flag = guideAddIdentity();
                break;
            case DELETE:
                flag = guideAddIdentity();
                break;
            case CONGEL:
                flag = guideAddIdentity();
                break;
            case THAW:
                flag = guideAddIdentity();
                break;
            case COLLECT:
                flag = guideAddIdentity();
                break;
            case IMPORT:
                flag = guideAddIdentity();
                break;
            case EXPORT:
                flag = guideAddIdentity();
                break;
            case UPLOAD:
                flag = guideAddIdentity();
                break;
            case DOWNLOAD:
                flag = guideAddIdentity();
                break;
            case SEE:
                flag = guideSeeIdentity();
                break;
            case SEEFILE:
                flag = guideSeeIdentity();
                break;
            default:
                flag = true;
                break;
        }

        RpcTransmit.transmitUserToken(userToken);
        return flag;
    }

    @Override
    @Transactional(rollbackFor = SerException.class)
    public PersonalFestivalBO insertModel(PersonalFestivalTO to) throws SerException {
        checkSeeIdentity();
        PersonalFestival model = BeanTransform.copyProperties(to, PersonalFestival.class, true);
        UserBO userBO = userAPI.currentUser();
        if (userBO != null) {
            model.setUserName(userBO.getUsername());
            model.setUserId(userBO.getId());
        }
        super.save(model);
        to.setId(model.getId());
        return BeanTransform.copyProperties(to, PersonalFestivalBO.class);
    }

    @Override
    @Transactional(rollbackFor = SerException.class)
    public PersonalFestivalBO updateModel(PersonalFestivalTO to) throws SerException {
        checkSeeIdentity();
        if (!StringUtils.isEmpty(to.getId())) {
            PersonalFestival model = super.findById(to.getId());
            if (model != null) {
                BeanTransform.copyProperties(to, model, true);
                model.setModifyTime(LocalDateTime.now());
                super.update(model);
            } else {
                throw new SerException("更新对象不能为空");
            }
        } else {
            throw new SerException("更新ID不能为空!");
        }
        return BeanTransform.copyProperties(to, PersonalFestivalBO.class);
    }

    @Override
    @Transactional(rollbackFor = SerException.class)
    public List<PersonalFestivalBO> pageList(PersonalFestivalDTO dto) throws SerException {
        checkSeeIdentity();
        dto.getSorts().add("createTime=desc");
        List<PersonalFestival> list = super.findByPage(dto);
        return BeanTransform.copyProperties(list, PersonalFestivalBO.class);
    }

    @Override
    @Transactional(rollbackFor = SerException.class)
    public void wish(String id, String wishStatement) throws SerException {
        checkSeeIdentity();
        if (!StringUtils.isEmpty(id)) {
            PersonalFestival model = super.findById(id);
            if (model != null) {
                UserBO userBO = userAPI.currentUser();
                if (userBO != null) {
                    if (userBO.getUsername().equals(model.getUserName())) {
                        throw new SerException("亲，本人的祝福不如藏心里吧!");
                    } else {
                        PersonalFestivalWish personalFestivalWish = new PersonalFestivalWish();
                        personalFestivalWish.setFestivalId(id);
                        personalFestivalWish.setWishStatement(wishStatement);
                        personalFestivalWish.setSendUser(userBO.getUsername());
                        personalFestivalWish.setSendUserId(userBO.getId());
                        personalFestivalWish.setReceiveUser(model.getUserName());
                        personalFestivalWish.setReceiveUserId(model.getUserId());
                        personalFestivalWishSer.save(personalFestivalWish);
                    }
                } else {
                    throw new SerException("用户未登录或登录已失效!");
                }
            } else {
                throw new SerException("节日对象不能为空!");
            }
        } else {
            throw new SerException("节日Id不能为空!");
        }

    }
}