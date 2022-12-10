package net.ecnu.service.authentication.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.SysOrgMapper;
import net.ecnu.mapper.SystemMapper;
import net.ecnu.model.authentication.SysOrg;
import net.ecnu.model.authentication.SysOrgNode;
import net.ecnu.model.authentication.tree.DataTreeUtil;
import net.ecnu.service.authentication.SysOrgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysOrgServiceImpl implements SysOrgService {

    @Resource
    private SystemMapper systemMapper;

    @Resource
    private SysOrgMapper sysOrgMapper;

    /**
     * 根据当前登录用户所属组织，查询组织树
     * @param rootOrgId 当前登录用户的组织id
     * @param orgNameLike 组织名称参数
     * @param orgStatus 组织状态参数
     * @return 组织列表
     */
    @Override
    public List<SysOrgNode> getOrgTreeById(Integer rootOrgId, String orgNameLike, Boolean orgStatus) {
        if (rootOrgId != null) {
            List<SysOrg> sysOrgs = systemMapper.selectOrgTree(rootOrgId, orgNameLike, orgStatus);

            List<SysOrgNode> sysOrgNodes = sysOrgs.stream().map(item -> {
                SysOrgNode bean = new SysOrgNode();
                BeanUtils.copyProperties(item, bean);
                return bean;
            }).collect(Collectors.toList());

            if (StringUtils.isNotEmpty(orgNameLike)) {
                return sysOrgNodes;//根据组织名称查询，返回平面列表
            } else {//否则返回树型结构列表
                return DataTreeUtil.buildTree(sysOrgNodes, rootOrgId);
            }

        } else {
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "查询参数用户名组织id不能为空");
        }
    }

    @Override
    public void updateOrg(SysOrg sysorg) {
        if(sysorg.getId() == null){
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "修改操作必须带主键");
        }else{
            sysOrgMapper.updateById(sysorg);
        }
    }

    @Override
    public void addOrg(SysOrg sysorg) {
        setOrgIdsAndLevel(sysorg);

        sysorg.setIsLeaf(true); //新增的组织节点都是子节点，没有下级
        SysOrg parent = new SysOrg();
        parent.setId(sysorg.getOrgPid());
        parent.setIsLeaf(false);//更新父节点为非子节点。
        sysOrgMapper.updateById(parent);

        sysorg.setStatus(false);//设置是否禁用，新增节点默认可用
        sysOrgMapper.insert(sysorg);
    }

    //设置某子节点的所有祖辈id
    private void setOrgIdsAndLevel(SysOrg child){
        List<SysOrg> allOrgs = sysOrgMapper.selectList(null);
        for(SysOrg sysOrg: allOrgs){
            //从组织列表中找到自己的直接父亲
            if(sysOrg.getId().equals(child.getOrgPid())){
                //直接父亲的所有祖辈id + 直接父id = 当前子节点的所有祖辈id
                //爸爸的所有祖辈 + 爸爸 = 孩子的所有祖辈
                child.setOrgPids(sysOrg.getOrgPids() + ",[" + child.getOrgPid() + "]" );
                child.setLevel(sysOrg.getLevel() + 1);
            }
        }
    }

    @Override
    public void deleteOrg(SysOrg sysOrg) {
        List<SysOrg> myChilds = sysOrgMapper.selectList(new QueryWrapper<SysOrg>()
                .like("org_pids","["+ sysOrg.getId() +"]"));

        if(myChilds.size() > 0){
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "不能删除有下级组织的组织机构");
        }

        List<SysOrg> myFatherChilds = sysOrgMapper.selectList(new QueryWrapper<SysOrg>()
                .like("org_pids","["+ sysOrg.getOrgPid() +"]"));

        //我的父节点只有我这一个子节点，而我还要被删除，更新父节点为叶子节点。
        if(myFatherChilds.size() == 1){
            SysOrg parent = new SysOrg();
            parent.setId(sysOrg.getOrgPid());
            parent.setIsLeaf(true);//更新父节点为叶子节点。
            sysOrgMapper.updateById(parent);
        }
        //删除节点
        sysOrgMapper.deleteById(sysOrg.getId());
    }

    @Override
    public void updateStatus(Integer id, Boolean status) {
        if(Objects.isNull(id)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"修改操作必须带主键");
        }
        SysOrg sysOrg = new SysOrg();
        sysOrg.setId(id);
        sysOrg.setStatus(status);
        sysOrgMapper.updateById(sysOrg);
    }
}
