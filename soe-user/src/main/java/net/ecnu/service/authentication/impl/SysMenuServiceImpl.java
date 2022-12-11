package net.ecnu.service.authentication.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.SysMenuMapper;
import net.ecnu.mapper.SysRoleMenuMapper;
import net.ecnu.mapper.SystemMapper;
import net.ecnu.model.authentication.SysMenu;
import net.ecnu.model.authentication.SysMenuNode;
import net.ecnu.model.authentication.SysRoleMenu;
import net.ecnu.model.authentication.tree.DataTreeUtil;
import net.ecnu.service.authentication.SysMenuService;
import net.ecnu.utils.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Resource
    private SystemMapper systemMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;


    @Override
    public List<SysMenuNode> getMenuTree(String menuNameLike, Boolean menuStatus) {
        //保证数据库里面level=1的节点只有一个，根节点
        SysMenu rootSysMenu = sysMenuMapper.selectOne(
                new QueryWrapper<SysMenu>().eq("level",1));

        if (rootSysMenu != null) {
            Integer rootMenuId = rootSysMenu.getId();

            List<SysMenu> sysMenus
                    = systemMapper.selectMenuTree(rootMenuId, menuNameLike, menuStatus);

            List<SysMenuNode> sysMenuNodes = sysMenus.stream().map(item -> {
                SysMenuNode bean = new SysMenuNode();
                BeanUtils.copyProperties(item, bean);
                return bean;
            }).collect(Collectors.toList());

            if (StringUtils.isNotEmpty(menuNameLike) || menuStatus != null) {
                return sysMenuNodes;//根据菜单名称查询，返回平面列表
            } else {//否则返回菜单的树型结构列表
                return DataTreeUtil.buildTree(sysMenuNodes, rootMenuId);
            }

        } else {
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "请先在数据库内为菜单配置一个分类的根节点，level=1");
        }
    }

    @Override
    public void updateMenu(SysMenu sysmenu) {
        if(sysmenu.getId() == null){
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "修改操作必须带主键");
        }else{
            String currentAccountNo = RequestParamUtil.currentAccountNo();
            if(StringUtils.isBlank(currentAccountNo)){
                throw new BizException(BizCodeEnum.TOKEN_EXCEPTION.getCode(),"更新菜单信息必须携带有效token");
            }
            sysmenu.setUpdateBy(currentAccountNo);
            sysmenu.setUpdateTime(LocalDateTime.now());
            sysMenuMapper.updateById(sysmenu);
        }
    }

    @Override
    public void addMenu(SysMenu sysmenu) {
        setMenuIdsAndLevel(sysmenu);

        sysmenu.setIsLeaf(true); //新增的菜单节点都是子节点，没有下级
        SysMenu parent = new SysMenu();
        parent.setId(sysmenu.getMenuPid());
        parent.setIsLeaf(false);//更新父节点为非子节点。
        sysMenuMapper.updateById(parent);

        sysmenu.setStatus(false);//设置是否禁用，新增节点默认可用
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if(StringUtils.isBlank(currentAccountNo)){
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION.getCode(),"添加菜单必须携带有效token");
        }
        sysmenu.setCreateBy(currentAccountNo);
        sysmenu.setCreateTime(LocalDateTime.now());
        sysMenuMapper.insert(sysmenu);
    }

    //设置某子节点的所有祖辈id
    private void setMenuIdsAndLevel(SysMenu child){
        List<SysMenu> allMenus = sysMenuMapper.selectList(null);
        for(SysMenu sysMenu: allMenus){
            //从组织列表中找到自己的直接父亲
            if(sysMenu.getId().equals(child.getMenuPid())){
                //直接父亲的所有祖辈id + 直接父id = 当前子节点的所有祖辈id
                //爸爸的所有祖辈 + 爸爸 = 孩子的所有祖辈
                child.setMenuPids(sysMenu.getMenuPids() + ",[" + child.getMenuPid() + "]" );
                child.setLevel(sysMenu.getLevel() + 1);
            }
        }
    }

    @Override
    public void deleteMenu(SysMenu sysMenu) {
        //查找被删除节点的子节点
        List<SysMenu> myChilds = sysMenuMapper.selectList(new QueryWrapper<SysMenu>()
                .like("menu_pids","["+ sysMenu.getId() +"]"));

        if(myChilds.size() > 0){
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "不能删除含有下级菜单的菜单");
        }

        //查找被删除节点的父节点
        List<SysMenu> myFatherChilds = sysMenuMapper.selectList(new QueryWrapper<SysMenu>()
                .like("menu_pids","["+ sysMenu.getMenuPid() +"]"));

        //我的父节点只有我这一个子节点，而我还要被删除，更新父节点为叶子节点。
        if(myFatherChilds.size() == 1){
            SysMenu parent = new SysMenu();
            parent.setId(sysMenu.getMenuPid());
            parent.setIsLeaf(true);//更新父节点为叶子节点。
            sysMenuMapper.updateById(parent);
        }
        //删除节点
        sysMenuMapper.deleteById(sysMenu.getId());
    }

    @Override
    public List<String> getCheckedKeys(Integer roleId) {
        return systemMapper.selectMenuCheckedKeys(roleId);
    }

    @Override
    public List<String> getExpandedKeys() {
        return systemMapper.selectMenuExpandedKeys();
    }

    @Override
    public void saveCheckedKeys(Integer roleId, List<Integer> checkedIds) {
        //保存之前先删除
        sysRoleMenuMapper.delete(new UpdateWrapper<SysRoleMenu>()
                .eq("role_id",roleId));
        systemMapper.insertRoleMenuIds(roleId,checkedIds);
    }

    @Override
    public List<SysMenuNode> getMenuTreeByUsername(String phone) {
        List<SysMenu> sysMenus = systemMapper.selectMenuByUsername(phone);

        if (sysMenus.size() > 0) {
            Integer rootMenuId = sysMenus.get(0).getId();

            List<SysMenuNode> sysMenuNodes =
                    sysMenus.stream().map(item -> {
                        SysMenuNode bean = new SysMenuNode();
                        BeanUtils.copyProperties(item, bean);
                        return bean;
                    }).collect(Collectors.toList());
            return DataTreeUtil.buildTreeWithoutRoot(sysMenuNodes, rootMenuId);
        }
        return new ArrayList<>();
    }

    @Override
    public void updateStatus(Integer id, Boolean status) {
        if(Objects.isNull(id)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"更改菜单状态信息操作必须带主键");
        }
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        sysMenu.setStatus(status);
        sysMenuMapper.updateById(sysMenu);
    }

    @Override
    public void updateHidden(Integer id, Boolean hidden) {
        if(Objects.isNull(id)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"修改菜单的隐藏状态操作必须带主键");
        }
        SysMenu sysMenu = new SysMenu();
        sysMenu.setId(id);
        sysMenu.setHidden(hidden);
        sysMenuMapper.updateById(sysMenu);
    }
}
