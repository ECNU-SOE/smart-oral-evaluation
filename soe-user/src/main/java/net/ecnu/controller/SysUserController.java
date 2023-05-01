package net.ecnu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.ecnu.constant.RolesConst;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.UserMapper;
import net.ecnu.model.LoginProperties;
import net.ecnu.model.UserDO;
import net.ecnu.model.authentication.SysUserOrg;
import net.ecnu.model.dto.UserDTO;
import net.ecnu.model.vo.UserVO;
import net.ecnu.service.UserService;
import net.ecnu.service.authentication.SysUserService;
import net.ecnu.util.IDUtil;
import net.ecnu.util.JsonData;
import net.ecnu.util.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 权限管理系统-用户管理模块
 */
@Api(value = "用户管理模块")
@RestController
@RequestMapping("/system/sysuser")
public class SysUserController {

    @Resource
    private SysUserService sysuserService;

    @Resource
    private UserService userService;

    @Autowired
    UserMapper sysUserMapper;

    //注册用户
    /*@PostMapping("register")
    public JsonData register(@RequestBody RegisterDto registerDto){
        if(Objects.isNull(registerDto)|| StringUtils.isBlank(registerDto.getEmail())
                ||StringUtils.isBlank(registerDto.getPassword())|| StringUtils.isBlank(registerDto.getUsername())){
            JsonData.buildError("参数不能空");
        }
        //注册用户，此时还未分配权限
        //sysUserMapper.insert();
        return JsonData.buildSuccess();
    }*/

    //根据登录用户名查询用户信息
    @ApiOperation("根据登录用户名查询用户信息")
    @GetMapping(value = "/info")
    public JsonData info() {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        UserDO userByUserName = sysuserService.getUserByUserName(currentAccountNo);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userByUserName,userVO);
        return JsonData.buildSuccess(userVO);
    }

    //用户管理：查询，username替换成user表中realName，页面展示时隐藏掉用户的accountNo
    @ApiOperation("用户管理：查询")
    @PostMapping("/query")
    public JsonData query(@RequestParam(value = "orgId",required = false) Integer orgId ,
                                   @RequestParam(value = "realName",required = false) String realName ,
                                   @RequestParam(value = "phone",required = false) String phone,
                                   @RequestParam(value = "email",required = false) String email,
                                   @RequestParam(value = "enabled",required = false) Boolean enabled,
                                   @RequestParam(value = "createStartTime",required = false) Date createStartTime,
                                   @RequestParam(value = "createEndTime",required = false) Date createEndTime,
                                   @RequestParam(value = "pageNum",required = true) Integer pageNum,
                                   @RequestParam(value = "pageSize",required = true) Integer pageSize) {

        IPage<SysUserOrg> sysUserOrgIPage = sysuserService.queryUser(orgId, realName, phone, email, enabled,
                createStartTime, createEndTime,
                pageNum, pageSize);
        return JsonData.buildSuccess(sysUserOrgIPage);
    }

    //用户管理：更新
    @ApiOperation("用户管理：更新")
    @PostMapping(value = "/update")
    public JsonData update(@RequestBody UserDTO sysUser) {
        sysuserService.updateUser(sysUser);
        return JsonData.buildSuccess("更新用户成功！");
    }

    //用户管理：新增
    @ApiOperation("用户管理：新增")
    @PostMapping(value = "/add")
    public JsonData add(@RequestBody UserDTO sysUser) {
        //雪花算法生成accountNo
        sysUser.setAccountNo("user_" + IDUtil.getSnowflakeId());
        sysuserService.addUser(sysUser);
        return JsonData.buildSuccess("新增用户成功！");
    }

    //用户管理：删除，删除指定用户时，将对应用户的accountNo发送过来
    @ApiOperation("用户管理：删除")
    @PostMapping(value = "/delete")
    public JsonData delete(@RequestParam String accountNo) {
        if(StringUtils.isBlank(accountNo)){
            return JsonData.buildCodeAndMsg(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(), BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getMessage());
        }
        sysuserService.deleteUser(accountNo);
        return JsonData.buildSuccess("删除用户成功!");
    }

    //用户管理：重置密码
    @ApiOperation("用户管理：重置密码")
    @PostMapping(value = "/pwd/reset")
    public JsonData pwdreset(@RequestParam String username) {
        if(StringUtils.isBlank(username)){
            return JsonData.buildCodeAndMsg(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(), BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getMessage());
        }
        //用户不能重置同等级及以上用户的密码,不过用户可以重置自己的密码
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        Integer topRoleAsUser = userService.getTopRole(currentAccountNo);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(LoginProperties.username,username);
        UserDO userDO = sysUserMapper.selectOne(queryWrapper);
        Integer topRoleAsOperated = userService.getTopRole(userDO.getAccountNo());
        if (topRoleAsUser < topRoleAsOperated || currentAccountNo.equals(userDO.getAccountNo())) {
            if(topRoleAsUser > RolesConst.ROLE_ADMIN){
                return JsonData.buildError("请联系管理员进行操作");
            }
            sysuserService.pwdreset(username);
            return JsonData.buildSuccess("重置密码成功!");
        } else {
            return JsonData.buildError("用户权限不足!");
        }
    }

    //判断登录用户密码是否是默认密码
    @ApiOperation("判断登录用户密码是否是默认密码")
    @PostMapping(value = "/pwd/isdefault")
    public JsonData isdefault() {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        return sysuserService.isdefault(currentAccountNo);
    }

    //修改密码
    @ApiOperation("修改密码")
    @PostMapping(value = "/pwd/change")
    public JsonData pwdchange(@RequestParam String oldPass,
                              @RequestParam String newPass) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        sysuserService.changePwd(currentAccountNo,oldPass,newPass);
        return JsonData.buildSuccess("修改密码成功!");
    }

    //用户管理：更新用户激活状态
    @ApiOperation("用户管理：更新用户激活状态")
    @PostMapping(value = "/enabled/change")
    public JsonData update(@RequestParam Boolean enabled,@RequestParam String accountNo) {
        if(StringUtils.isEmpty(accountNo)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getMessage());
        }
        sysuserService.updateEnabled(accountNo, enabled);
        return JsonData.buildSuccess("用户状态更新成功！");
    }
}
