package net.ecnu.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import net.ecnu.model.authentication.SysApi;
import net.ecnu.model.authentication.SysApiExample;
import org.apache.ibatis.annotations.Param;

/**
 * @author Joshua
 * @description
 * @date 2023/12/24 16:45
 */
public interface SysApiMapper extends BaseMapper<SysApi> {
    long countByExample(SysApiExample example);

    int deleteByExample(SysApiExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SysApi record);

    int insertSelective(SysApi record);

    List<SysApi> selectByExample(SysApiExample example);

    SysApi selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SysApi record, @Param("example") SysApiExample example);

    int updateByExample(@Param("record") SysApi record, @Param("example") SysApiExample example);

    int updateByPrimaryKeySelective(SysApi record);

    int updateByPrimaryKey(SysApi record);

    int updateBatch(List<SysApi> list);

    int batchInsert(@Param("list") List<SysApi> list);

    List<SysApi> selectSonApiNode(@Param("parentId") Long id);
}