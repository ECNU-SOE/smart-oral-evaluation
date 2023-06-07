package net.ecnu.mapper;

import net.ecnu.model.SignLogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author LYW
 * @since 2023-05-24
 */
public interface SignLogMapper extends BaseMapper<SignLogDO> {

    @Select("select sign_date from sign_log where user_id = #{accountNo} order by sign_date desc")
    List<LocalDate> getSignDatesDescByAccountNo(String accountNo);
    @Select("select sign_date from sign_log where year(sign_date) = #{arg0} and month(sign_date) = #{arg1}")
    List<LocalDate> getSignDatesByYearAndMonth(Integer year,Integer month);
}
