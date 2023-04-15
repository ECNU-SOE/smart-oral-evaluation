package net.ecnu.service.authentication.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.SysApiMapper;
import net.ecnu.mapper.SysRoleApiMapper;
import net.ecnu.mapper.SystemMapper;
import net.ecnu.model.authentication.SysApi;
import net.ecnu.model.authentication.SysApiNode;
import net.ecnu.model.authentication.SysRoleApi;
import net.ecnu.model.authentication.tree.DataTreeUtil;
import net.ecnu.service.authentication.SysApiService;
import net.ecnu.util.JsonData;
import net.ecnu.util.RequestParamUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysApiServiceImpl implements SysApiService {

    @Resource
    private SystemMapper systemMapper;

    @Resource
    private SysApiMapper sysApiMapper;

    @Resource
    private SysRoleApiMapper sysRoleApiMapper;

    @Override
    public List<SysApiNode> getApiTree(String apiNameLike, Boolean apiStatus) {
        //查找level=1的API节点，即：根节点
        SysApi rootSysApi = sysApiMapper.selectOne(
                new QueryWrapper<SysApi>().eq("level",1));

        if (rootSysApi != null) {
            Long rootApiId = rootSysApi.getId();

            List<SysApi> sysApis = systemMapper.selectApiTree(rootApiId, apiNameLike, apiStatus);

            List<SysApiNode> sysApiNodes = sysApis.stream().map(item -> {
                SysApiNode bean = new SysApiNode();
                BeanUtils.copyProperties(item, bean);
                return bean;
            }).collect(Collectors.toList());
            if (StringUtils.isNotEmpty(apiNameLike) && apiStatus != null) {
                return sysApiNodes;//根据api名称等查询会破坏树形结构，返回平面列表
            } else {//否则返回树型结构列表
                return DataTreeUtil.buildTree(sysApiNodes, rootApiId);
            }
        } else {
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "请先在数据库内为API配置一个分类的根节点，level=1");
        }
    }

    @Override
    @Transactional
    public void addApi(SysApi sysapi) {
        String currentAccountNo = RequestParamUtil.currentAccountNo();
        if(StringUtils.isBlank(currentAccountNo)){
            throw new BizException(BizCodeEnum.TOKEN_EXCEPTION.getCode(),"添加接口操作必须携带有效token");
        }
        setApiIdsAndLevel(sysapi);

        sysapi.setIsLeaf(true); //新增的菜单节点都是子节点，没有下级
        SysApi parent = new SysApi();
        parent.setId(sysapi.getApiPid());
        parent.setIsLeaf(false);//更新父节点为非子节点。
        parent.setUpdateBy(currentAccountNo);
        parent.setUpdateTime(LocalDateTime.now());
        sysApiMapper.updateById(parent);

        sysapi.setStatus(false);//设置是否禁用，新增节点默认可用
        //添加操作记录
        sysapi.setCreateBy(currentAccountNo);
        sysapi.setCreateTime(LocalDateTime.now());
        sysApiMapper.insert(sysapi);
    }

    //设置某子节点的所有祖辈id
    private void setApiIdsAndLevel(SysApi child){
        List<SysApi> allApis = sysApiMapper.selectList(null);
        for(SysApi sysApi: allApis){
            //从组织列表中找到自己的直接父亲
            if(sysApi.getId().equals(child.getApiPid())){
                //直接父亲的所有祖辈id + 直接父id = 当前子节点的所有祖辈id
                //爸爸的所有祖辈 + 爸爸 = 孩子的所有祖辈
                child.setApiPids(sysApi.getApiPids() + ",[" + child.getApiPid() + "]" );
                child.setLevel(sysApi.getLevel() + 1);
            }
        }
    }

    @Override
    @Transactional
    public JsonData updateApi(SysApi sysapi) {
        if(sysapi.getId() == null){
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "修改操作必须带主键");
        }else{
            String currentAccountNo = RequestParamUtil.currentAccountNo();
            if(StringUtils.isBlank(currentAccountNo)){
                throw new BizException(BizCodeEnum.TOKEN_EXCEPTION.getCode(),"修改接口信息必须携带有效token");
            }
            sysapi.setUpdateTime(LocalDateTime.now());
            sysapi.setUpdateBy(currentAccountNo);
            int updateStatus = sysApiMapper.updateById(sysapi);
            if(updateStatus> 0){
                return JsonData.buildSuccess("更新接口配置成功！");
            }else {
                return JsonData.buildError("更新接口失败");
            }
        }
    }

    @Override
    @Transactional
    public void deleteApi(SysApi sysApi) {
        //查找被删除节点的子节点
        List<SysApi> myChild = sysApiMapper.selectList(new QueryWrapper<SysApi>()
                .like("api_pids","["+ sysApi.getId() +"]"));

        if(myChild.size() > 0){
            throw new BizException(BizCodeEnum.USER_INPUT_ERROR.getCode(),
                    "不能删除含有下级API接口的节点");
        }

        //查找被删除节点的父节点
        List<SysApi> myFatherChild = sysApiMapper.selectList(new QueryWrapper<SysApi>()
                .like("api_pids","["+ sysApi.getApiPid() +"]"));
        //我的父节点只有我这一个子节点，而我还要被删除，更新父节点为叶子节点。
        if(myFatherChild.size() == 1){
            SysApi parent = new SysApi();
            parent.setId(sysApi.getApiPid());
            parent.setIsLeaf(true);//更新父节点为叶子节点。
            sysApiMapper.updateById(parent);
        }
        //删除节点
        sysApiMapper.deleteById(sysApi.getId());
    }


    @Override
    public List<String> getCheckedKeys(Integer roleId) {
        return systemMapper.selectApiCheckedKeys(roleId);
    }

    @Override
    public List<String> getExpandedKeys() {
        return systemMapper.selectApiExpandedKeys();
    }

    @Override
    @Transactional
    public void saveCheckedKeys(String roleCode, Integer roleId, List<Integer> checkedIds) {
        //保存之前先删除
        sysRoleApiMapper.delete(new UpdateWrapper<SysRoleApi>().eq("role_id",roleId));
        systemMapper.insertRoleApiIds(roleId,checkedIds);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, Boolean status) {
        if(Objects.isNull(id)){
            throw new BizException(BizCodeEnum.PARAM_CANNOT_BE_EMPTY.getCode(),"修改接口信息操作必须带主键");
        }
        SysApi sysApi = new SysApi();
        sysApi.setId(id);
        sysApi.setStatus(status);
        sysApiMapper.updateById(sysApi);
    }
}
