package net.ecnu.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @description:
 * @Author lsy
 * @Date 2023/9/16 15:12
 */
@Mapper
public interface StatisticsMapper {

    Integer getCountByStuIdsAndCpsgrpId(@Param("studentList") List<String> studentList,@Param("cpsgrpId") String cpsgrpId);
}
