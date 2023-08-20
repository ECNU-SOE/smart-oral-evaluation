package net.ecnu.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import net.ecnu.mapper.ClassCpsgrpMapper;
import net.ecnu.model.dto.StatisticsDto;
import net.ecnu.model.vo.ClassCpsgrpInfoVo;
import net.ecnu.model.vo.StatisticsVo;
import net.ecnu.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @description: 数据统计接口实现类
 * @Author lsy
 * @Date 2023/8/20 10:37
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private ClassCpsgrpMapper classCpsgrpMapper;

    @Override
    public Page<StatisticsVo> getStatisticsInfo(StatisticsDto statisticsDto) {
        Page<StatisticsVo> resultPage = new Page<>();
        List<StatisticsVo> statisticsVos = classCpsgrpMapper.getStatisticsInfo(statisticsDto);
        //完整度排序
        if (Objects.nonNull(statisticsDto.getPronCompletionKey())) {
            if (statisticsDto.getPronCompletionKey() == 0) {
                statisticsVos = statisticsVos.stream().sorted((o1, o2) -> (int) (o1.getPronCompletionAverage() - o2.getPronCompletionAverage())).collect(Collectors.toList());
            } else if (statisticsDto.getPronCompletionKey() == 1) {
                statisticsVos = statisticsVos.stream().sorted((o1, o2) -> (int) (o2.getPronCompletionAverage() - o1.getPronCompletionAverage())).collect(Collectors.toList());
            }
        }
        //准确度排序
        if (Objects.nonNull(statisticsDto.getPronAccuracyKey())) {
            if (statisticsDto.getPronAccuracyKey() == 0) {
                statisticsVos = statisticsVos.stream().sorted((o1, o2) -> (int) (o1.getPronAccuracyAverage() - o2.getPronAccuracyAverage())).collect(Collectors.toList());
            } else if (statisticsDto.getPronAccuracyKey() == 1) {
                statisticsVos = statisticsVos.stream().sorted((o1, o2) -> (int) (o2.getPronAccuracyAverage() - o1.getPronAccuracyAverage())).collect(Collectors.toList());
            }
        }
        //流畅度排序
        if (Objects.nonNull(statisticsDto.getPronFluencyKey())) {
            if (statisticsDto.getPronFluencyKey() == 0) {
                statisticsVos = statisticsVos.stream().sorted((o1, o2) -> (int) (o1.getPronFluencyAverage() - o2.getPronFluencyAverage())).collect(Collectors.toList());
            } else if (statisticsDto.getPronFluencyKey() == 1) {
                statisticsVos = statisticsVos.stream().sorted((o1, o2) -> (int) (o2.getPronFluencyAverage() - o1.getPronFluencyAverage())).collect(Collectors.toList());
            }
        }
        //综合总分排序
        if (Objects.nonNull(statisticsDto.getSuggestedScoreKey())) {
            if (statisticsDto.getSuggestedScoreKey() == 0) {
                statisticsVos = statisticsVos.stream().sorted((o1,o2) -> (int) (o1.getSuggestedScoreAverage() - o2.getSuggestedScoreAverage())).collect(Collectors.toList());
            } else if (statisticsDto.getSuggestedScoreKey() == 1) {
                statisticsVos = statisticsVos.stream().sorted((o1,o2) -> (int) (o2.getSuggestedScoreAverage() - o1.getSuggestedScoreAverage())).collect(Collectors.toList());
            }
        }
        int total = statisticsVos.size();
        int ceil = (int) Math.ceil((double) total / statisticsDto.getPageSize());
        resultPage.setPages(ceil);
        List<StatisticsVo> list = new ArrayList<>();
        if (total >= statisticsDto.getPageNum() * statisticsDto.getPageSize()) {
            list = statisticsVos.subList((statisticsDto.getPageNum() - 1) * statisticsDto.getPageSize(), statisticsDto.getPageNum() * statisticsDto.getPageSize());
        } else {
            list = statisticsVos.subList((statisticsDto.getPageNum() - 1) * statisticsDto.getPageSize(), total);
        }
        resultPage.setRecords(list);
        resultPage.setTotal(total);
        resultPage.setCurrent(statisticsDto.getPageNum());
        resultPage.setSize(statisticsDto.getPageSize());
        return resultPage;
    }

    /**
     * 获取指定课程下的试题集数据
     * @param courseId      课程id
     * @param currentTypeId 测验/考试flag
     **/
    @Override
    public List<ClassCpsgrpInfoVo> getCpsgrpInfoByCourseId(String courseId, Integer currentTypeId) {
        List<ClassCpsgrpInfoVo> classCpsgrpInfoVos = classCpsgrpMapper.getCpsgrpInfoByCourseId(courseId, currentTypeId);
        return classCpsgrpInfoVos;
    }
}
