package net.ecnu.service.authentication.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.constant.SOEConst;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.SystemMapper;
import net.ecnu.mapper.UserMapper;
import net.ecnu.model.UserDO;
import net.ecnu.model.authentication.SysUserOrg;
import net.ecnu.model.vo.dto.UserDTO;
import net.ecnu.service.authentication.SysUserService;
import net.ecnu.utils.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SystemMapper systemMapper;

    @Resource
    private PasswordEncoder passwordEncoder;

    //用户管理：查询用户
    @Override
    public IPage<SysUserOrg> queryUser(Integer orgId, String realName, String phone, String email, Boolean enabled, Date createStartTime, Date createEndTime, Integer pageNum, Integer pageSize) {
        Page<SysUserOrg> page = new Page<>(pageNum,pageSize);   //查询第pageNum页，每页pageSize条数据
        if(orgId == null){ //获取当前登录用户的orgId
            String accountNo = RequestParamUtil.currentAccountNo();
            if(StringUtils.isBlank(accountNo)){
                UserDO sysUser = userMapper.selectOne(
                        new QueryWrapper<UserDO>()
                                .eq("account_no",accountNo)
                );
                orgId = sysUser.getOrgId();
            }else{
                throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"查询用户操作必须携带有效token");
            }
        }
        //查询orgId组织及其自组织的用户列表
        return systemMapper.selectUser(page,realName,orgId,phone,email,enabled,createStartTime,createEndTime);
    }

    @Override
    public UserDO getUserByUserName(String accountNo) {
        if(StringUtils.isBlank(accountNo)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"查询参数用户唯一id为空");
        }
        UserDO sysUser = userMapper.selectOne(
                new QueryWrapper<UserDO>().eq("account_no",accountNo));
        if(sysUser != null){
            sysUser.setPwd("");  //清空密码信息
        }
        return sysUser;
    }

    //用户管理：修改
    @Override
    public void updateUser(UserDTO sysuser) {
        if(Objects.isNull(sysuser)|| StringUtils.isBlank(sysuser.getPhone())){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(), BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getMessage());
        }
        //根据用户名修改用户信息
        LambdaQueryWrapper<UserDO> lambdaQ = Wrappers.lambdaQuery();
        lambdaQ.eq(UserDO::getPhone, sysuser.getPhone());
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(sysuser,userDO);
        userMapper.update(userDO,lambdaQ);
    }

    //用户管理：新增
    @Override
    public void addUser(UserDTO sysuser) {
        sysuser.setPwd(passwordEncoder.encode(
                SOEConst.USER_INIT_PASSWORD
        ));
        sysuser.setEnabled(true); //新增用户激活
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(sysuser,userDO);
        userMapper.insert(userDO);
    }

    //用户管理：删除
    @Override
    public void deleteUser(String accountNo) {
        if(StringUtils.isBlank(accountNo)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"删除操作必须带用户唯一id");
        }
        //根据用户名删除用户信息
        LambdaQueryWrapper<UserDO> lambdaQ = Wrappers.lambdaQuery();
        lambdaQ.eq(UserDO::getAccountNo, accountNo);
        userMapper.delete(lambdaQ);
    }

    //用户管理：重置密码
    @Override
    public void pwdreset(String phone) {
        if(StringUtils.isBlank(phone)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"重置密码操作必须带手机号");
        }
        UserDO sysUser = new UserDO();
        sysUser.setPwd(passwordEncoder.encode(
                SOEConst.USER_INIT_PASSWORD
        ));

        //根据用户名修改用户信息
        LambdaQueryWrapper<UserDO> lambdaQ = Wrappers.lambdaQuery();
        lambdaQ.eq(UserDO::getPwd, phone);
        userMapper.update(sysUser,lambdaQ);
    }

    @Override
    public void updateEnabled(String phone, Boolean enabled) {
        if(StringUtils.isBlank(phone)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"修改操作必须带账号");
        }
        UserDO sysUser = new UserDO();
        sysUser.setEnabled(enabled);

        //根据用户名修改用户信息
        LambdaQueryWrapper<UserDO> lambdaQ = Wrappers.lambdaQuery();
        lambdaQ.eq(UserDO::getPhone, phone);
        userMapper.update(sysUser,lambdaQ);
    }

    @Override
    public void changePwd(String phone, String oldPass, String newPass) {
        UserDO sysUser = userMapper.selectOne(
                new QueryWrapper<UserDO>().eq("phone",phone));
        //判断旧密码是否正确
        boolean isMatch = passwordEncoder.matches(oldPass,sysUser.getPwd());
        if(isMatch){
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),"原密码输入错误，请确认后重新输入！");
        }
        UserDO sysUserNew = new UserDO();
        sysUserNew.setAccountNo(sysUser.getAccountNo());
        sysUserNew.setPwd(passwordEncoder.encode(newPass));
        userMapper.updateById(sysUserNew);
    }

    @Override
    public Boolean isdefault(String accountNo) {
        if(StringUtils.isBlank(accountNo)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"判断是否为默认密码必须携带有效token");
        }
        UserDO sysUser = userMapper.selectOne(
                new QueryWrapper<UserDO>().eq("account_no",accountNo));
        //判断数据库密码是否是默认密码
        return passwordEncoder.matches(SOEConst.USER_INIT_PASSWORD , sysUser.getPwd());
    }


}
