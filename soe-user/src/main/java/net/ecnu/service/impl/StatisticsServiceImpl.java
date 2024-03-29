package net.ecnu.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import net.ecnu.enums.BizCodeEnum;
import net.ecnu.enums.CpsgrpTypeEnum;
import net.ecnu.exception.BizException;
import net.ecnu.mapper.ClassCpsgrpMapper;
import net.ecnu.mapper.ClassMapper;
import net.ecnu.mapper.StatisticsMapper;
import net.ecnu.mapper.UserClassMapper;
import net.ecnu.model.ClassDO;
import net.ecnu.model.UserClassDO;
import net.ecnu.model.dto.*;
import net.ecnu.model.vo.*;
import net.ecnu.model.vo.dto.ClassOptions;
import net.ecnu.model.vo.dto.ClassScoreAnalysis;
import net.ecnu.model.vo.dto.CpsgrpOptions;
import net.ecnu.service.StatisticsService;
import net.ecnu.util.ExcelUtil;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 数据统计接口实现类
 * @Author lsy
 * @Date 2023/8/20 10:37
 */
@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private ClassCpsgrpMapper classCpsgrpMapper;

    @Resource
    private ClassMapper classMapper;

    @Resource
    private UserClassMapper userClassMapper;

    @Resource
    private StatisticsMapper statisticsMapper;

    @Value("${class.score.info.template}")
    private String classScoreInfoTemplate;

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
                statisticsVos = statisticsVos.stream().sorted((o1, o2) -> (int) (o1.getSuggestedScoreAverage() - o2.getSuggestedScoreAverage())).collect(Collectors.toList());
            } else if (statisticsDto.getSuggestedScoreKey() == 1) {
                statisticsVos = statisticsVos.stream().sorted((o1, o2) -> (int) (o2.getSuggestedScoreAverage() - o1.getSuggestedScoreAverage())).collect(Collectors.toList());
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
     *
     * @param courseId      课程id
     * @param currentTypeId 测验/考试flag
     **/
    @Override
    public List<ClassCpsgrpInfoVo> getCpsgrpInfoByCourseId(String courseId, Integer currentTypeId) {
        List<ClassCpsgrpInfoVo> classCpsgrpInfoVos = classCpsgrpMapper.getCpsgrpInfoByCourseId(courseId, currentTypeId);
        return classCpsgrpInfoVos;
    }

    @Override
    public void exportExcel(String classId, String cpsgrpId, HttpServletResponse response) throws IOException {
        log.info("开始导出班级成绩Excel，入参classId:{}", JSON.toJSON(classId));
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        org.springframework.core.io.Resource[] resources = resolver.getResources("/template/class_score_info_template.xlsx");
        org.springframework.core.io.Resource resource = resources[0];
        InputStream inputStream = resource.getInputStream();
        try {
            //获取班级下学生指定作业的成绩
            CourseClassCpsgrpInfoDto courseClassCpsgrpInfoDto = classCpsgrpMapper.getClassCpsgrpInfo(classId, cpsgrpId);
            List<ClassScoreInfoDto> classScoreInfoDtos = classCpsgrpMapper.getClassScoreInfo(classId, cpsgrpId);
            //Workbook workbook = ExcelUtil.getWorkbook(classScoreInfoTemplate);
            Workbook workbook = new XSSFWorkbook(inputStream);
            //Workbook workbook = ExcelUtil.getWorkbook("E:\\汉语正音平台\\class_score_info_template.xlsx");
            CellStyle promptStyle = workbook.createCellStyle();
            //设置字体
            Font promptFont = workbook.createFont();
            promptFont.setFontName("微软雅黑");
            promptStyle.setFont(promptFont);
            promptStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
            promptStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
            promptStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
            promptStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
            promptStyle.setWrapText(true);//自动换行
            promptStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);//水平居中
            promptStyle.setVerticalAlignment((short) 1);//垂直居中
            Sheet sheet = workbook.getSheetAt(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(1, true);
            //课程名称
            sheet.createRow(3);
            sheet.getRow(3).createCell(0).setCellStyle(promptStyle);
            sheet.getRow(3).createCell(1).setCellStyle(promptStyle);
            sheet.getRow(3).getCell(0).setCellValue("课程名称");
            CellRangeAddress courseName = new CellRangeAddress(3, 3, 2, 4);
            sheet.addMergedRegion(courseName);
            sheet.getRow(3).createCell(2).setCellStyle(promptStyle);
            sheet.getRow(3).createCell(3).setCellStyle(promptStyle);
            sheet.getRow(3).createCell(4).setCellStyle(promptStyle);
            sheet.getRow(3).getCell(2).setCellValue(courseClassCpsgrpInfoDto.getCourseName());
            //课程开始、结束时间
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String startTime = courseClassCpsgrpInfoDto.getCourseStartTime().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
            String endTime = courseClassCpsgrpInfoDto.getCourseEndTime().toInstant()
                    .atZone(ZoneId.systemDefault()).toLocalDateTime().format(formatter);
            sheet.getRow(3).createCell(5).setCellStyle(promptStyle);
            sheet.getRow(3).createCell(6).setCellStyle(promptStyle);
            sheet.getRow(3).getCell(5).setCellValue("课程开始时间");
            sheet.getRow(3).createCell(7).setCellStyle(promptStyle);
            sheet.getRow(3).getCell(7).setCellValue(startTime);

            sheet.getRow(3).createCell(8).setCellStyle(promptStyle);
            sheet.getRow(3).createCell(9).setCellStyle(promptStyle);
            sheet.getRow(3).getCell(8).setCellValue("课程结束时间");
            sheet.getRow(3).createCell(10).setCellStyle(promptStyle);
            sheet.getRow(3).getCell(10).setCellValue(endTime);
            //班级名称
            sheet.createRow(4);
            sheet.getRow(4).createCell(0).setCellStyle(promptStyle);
            sheet.getRow(4).createCell(1).setCellStyle(promptStyle);
            sheet.getRow(4).getCell(0).setCellValue("班级名称");
            CellRangeAddress classNameCellRange = new CellRangeAddress(4, 4, 2, 4);
            sheet.addMergedRegion(classNameCellRange);
            sheet.getRow(4).createCell(2).setCellStyle(promptStyle);
            sheet.getRow(4).createCell(3).setCellStyle(promptStyle);
            sheet.getRow(4).createCell(4).setCellStyle(promptStyle);
            sheet.getRow(4).getCell(2).setCellValue(courseClassCpsgrpInfoDto.getClassName());
            //授课老师,未找到对应字段，暂时为空
            sheet.getRow(4).createCell(5).setCellStyle(promptStyle);
            sheet.getRow(4).createCell(6).setCellStyle(promptStyle);
            sheet.getRow(4).getCell(5).setCellValue("授课老师");

            sheet.getRow(4).createCell(7).setCellStyle(promptStyle);
            sheet.getRow(4).getCell(7).setCellValue("");

            //学生人数
            sheet.getRow(4).createCell(8).setCellStyle(promptStyle);
            sheet.getRow(4).createCell(9).setCellStyle(promptStyle);
            sheet.getRow(4).getCell(8).setCellValue("学生人数");

            sheet.getRow(4).createCell(10).setCellStyle(promptStyle);
            sheet.getRow(4).getCell(10).setCellValue(String.valueOf(courseClassCpsgrpInfoDto.getStudentNums()));
            //作业名称
            sheet.createRow(5);
            sheet.getRow(5).createCell(0).setCellStyle(promptStyle);
            sheet.getRow(5).createCell(1).setCellStyle(promptStyle);
            sheet.getRow(5).getCell(0).setCellValue("作业名称");

            CellRangeAddress homeworkCellRange = new CellRangeAddress(5, 5, 2, 11);
            sheet.addMergedRegion(homeworkCellRange);
            for (int index = 2 ; index <= 11 ; index++){
                sheet.getRow(5).createCell(index).setCellStyle(promptStyle);
            }
            sheet.getRow(5).getCell(2).setCellValue(courseClassCpsgrpInfoDto.getCpsgrpName());
            //填充表格字段
            fillStudentScoreInfo(7, sheet, classScoreInfoDtos,promptStyle);
            String fileName = courseClassCpsgrpInfoDto.getClassName() + "-" + courseClassCpsgrpInfoDto.getCpsgrpName() + ".xlsx";
            String gFileName = URLEncoder.encode(fileName, "UTF-8");
            // 定义输出类型
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment;filename=" + gFileName);
            //定义为默认展示第一个sheet
            workbook.setActiveSheet(0);
            // 获取输出流
            OutputStream out = response.getOutputStream();
            workbook.write(out);
            out.close();
        } catch (Exception e) {
            log.info("导出Excel失败，异常信息：{}", JSON.toJSON(e.getMessage()));
            throw new BizException(BizCodeEnum.EXCEL_ERROR.getCode(), "导出Excel失败,请联系管理员");
        }
    }

    @Override
    public Map<String, Object> getOptionsInfo(String courseId) {
        Map<String, Object> resultMap = new HashMap<>();
        //查询课程下的有效班级
        List<ClassOptions> classOptions = classMapper.getOptionsInfo(courseId);
        //查询课程下老师发布的所有语料组
        List<String> classIdList = classOptions.stream().map(ClassOptions::getClassId).collect(Collectors.toList());
        List<CpsgrpOptions> cpsgrpOptionsList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(classIdList)) {
            cpsgrpOptionsList = classCpsgrpMapper.getCpsgrpInfo(classIdList);
        }
        resultMap.put("cpsgrpList", cpsgrpOptionsList);
        resultMap.put("classList", classOptions);
        //获取语料组类型枚举数据
        resultMap.put("cpsgrpType",CpsgrpTypeEnum.getAllTypeInfo());
        return resultMap;
    }

    @Override
    public ScoreStatisticsVo scoreStatistics(ScoreStatisticsReq scoreStatisticsReq) {
        ScoreStatisticsVo scoreStatisticsVo = new ScoreStatisticsVo();
        scoreStatisticsVo.setCpsgrpId(scoreStatisticsReq.getCpsgrpId());
        //查询指定课程下，发布该语料组的班级
        List<ClassScoreAnalysis> classScoreAnalyses = classCpsgrpMapper.getClassIds(scoreStatisticsReq);
        for (ClassScoreAnalysis classScoreAnalysis : classScoreAnalyses) {
            //根据班级id获取学生id
            List<String> studentIdList = classCpsgrpMapper.getStudentIdByClassId(classScoreAnalysis.getClassId());
            //获取此次测评/考试，该班级下的学生答题情况
            List<StudentTranscriptDto> studentTranscriptDtoList = classCpsgrpMapper.getTranscriptInfo(studentIdList,scoreStatisticsReq.getCpsgrpId());
            classScoreAnalysis.setClassSize(studentIdList.size());
            if (CollectionUtils.isEmpty(studentTranscriptDtoList)) {
                classScoreAnalysis.setHighestScore(0.0);
                classScoreAnalysis.setLowestScore(0.0);
                classScoreAnalysis.setAverageScore(0.0);
                classScoreAnalysis.setFailNums(0);
                classScoreAnalysis.setPassNums(0);
                classScoreAnalysis.setCurrentRespondentNums(0);
            } else {
                classScoreAnalysis.setCurrentRespondentNums(studentTranscriptDtoList.size());
                BigDecimal averageDeci = new BigDecimal(0);
                BigDecimal minDeci = new BigDecimal(999L);
                BigDecimal maxDeci = new BigDecimal(0);
                Integer failNums = 0;
                Integer passNums = 0;
                BigDecimal line = new BigDecimal(60);
                for (StudentTranscriptDto studentTranscriptDto : studentTranscriptDtoList) {
                    if (studentTranscriptDto.getSuggestedScore().compareTo(maxDeci) > 0) {
                        maxDeci = studentTranscriptDto.getSuggestedScore();
                    }
                    if (minDeci.compareTo(studentTranscriptDto.getSuggestedScore()) > 0) {
                        minDeci = studentTranscriptDto.getSuggestedScore();
                    }
                    averageDeci = averageDeci.add(studentTranscriptDto.getSuggestedScore());
                    if (studentTranscriptDto.getSuggestedScore().compareTo(line) >= 0) {
                        passNums++;
                    } else {
                        failNums++;
                    }
                }
                averageDeci = averageDeci.divide(new BigDecimal(studentIdList.size()));
                classScoreAnalysis.setLowestScore(minDeci.doubleValue());
                classScoreAnalysis.setHighestScore(maxDeci.doubleValue());
                classScoreAnalysis.setAverageScore(averageDeci.doubleValue());
                classScoreAnalysis.setPassNums(passNums);
                classScoreAnalysis.setFailNums(failNums);
            }
        }
        scoreStatisticsVo.setClassScoreAnalysisList(classScoreAnalyses);
        return scoreStatisticsVo;
    }

    @Override
    public CompletionStatisticsVo completionStatistics(CompletionStatisticsReq completionStatisticsReq) {
        CompletionStatisticsVo completionStatisticsVo = new CompletionStatisticsVo();
        //1、查询班级的信息
        QueryWrapper<ClassDO> classDOQueryWrapper = new QueryWrapper<>();
        classDOQueryWrapper.eq("id",completionStatisticsReq.getClassId()).eq("course_id",completionStatisticsReq.getCourseId())
                .eq("del",0);
        ClassDO classDO = classMapper.selectOne(classDOQueryWrapper);
        completionStatisticsVo.setClassId(classDO.getId());
        //班级名称
        completionStatisticsVo.setClassName(classDO.getName());
        //2、获取班级内的学生人数
        QueryWrapper<UserClassDO> userClassDOQueryWrapper = new QueryWrapper<>();
        userClassDOQueryWrapper.eq("class_id",classDO.getId()).eq("r_type",1);
        List<UserClassDO> userClassDOList = userClassMapper.selectList(userClassDOQueryWrapper);
        if (!CollectionUtils.isEmpty(userClassDOList)) {
            completionStatisticsVo.setStudentNums(userClassDOList.size());
        } else {
            throw new BizException(BizCodeEnum.STUDENT_IS_ZERO);
        }
        //3、获取班级下布置的作业
        List<CpsgrpOptions> cpsgrpInfo = classCpsgrpMapper.getCpsgrpInfo(Arrays.asList(completionStatisticsReq.getClassId()));
        //4、获取班级内所有测评、考试的完成情况
        List<String> studentList = userClassDOList.stream().map(UserClassDO::getAccountNo).collect(Collectors.toList());
        int evaluationNums = 0;
        int examNums = 0;
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度2即保留两位小数
        nt.setMinimumFractionDigits(2);
        //测评完成度
        List<CompleteClassStatistics> evaCompletionStatistics = new ArrayList<>();
        //考试完成度
        List<CompleteClassStatistics> examCompletionStatistics = new ArrayList<>();
        for (CpsgrpOptions cpsgrpOptions : cpsgrpInfo) {
            Integer counts = statisticsMapper.getCountByStuIdsAndCpsgrpId(studentList, cpsgrpOptions.getCpsgrpId());
            //计算测评、考试的完成率
            double completeRate = (double)counts/completionStatisticsVo.getStudentNums();
            CompleteClassStatistics completeClassStatistics = new CompleteClassStatistics();
            completeClassStatistics.setCpsgrpId(cpsgrpOptions.getCpsgrpId());
            completeClassStatistics.setCpsgrpName(cpsgrpOptions.getCpsgrpName());
            completeClassStatistics.setCpsgrpType(cpsgrpOptions.getCpsgrpType());
            completeClassStatistics.setCompleteRate(nt.format(completeRate));
            if (cpsgrpOptions.getCpsgrpType().intValue() == CpsgrpTypeEnum.TEXT.getCode().intValue()) {
                evaluationNums++;
                evaCompletionStatistics.add(completeClassStatistics);
            } else if (cpsgrpOptions.getCpsgrpType().intValue() == CpsgrpTypeEnum.EXAM.getCode().intValue()) {
                examNums++;
                examCompletionStatistics.add(completeClassStatistics);
            }
        }
        completionStatisticsVo.setEvaluationNums(evaluationNums);
        completionStatisticsVo.setExamNums(examNums);
        completionStatisticsVo.setEvaCompletionStatistics(evaCompletionStatistics);
        completionStatisticsVo.setExamCompletionStatistics(examCompletionStatistics);
        return completionStatisticsVo;
    }

    /**
     * 填充学生成绩数据
     *
     * @param rowLine            开始行数
     * @param sheet              sheet页对象
     * @param classScoreInfoDtos 需要填充的表格内容
     **/
    public void fillStudentScoreInfo(Integer rowLine, Sheet sheet, List<ClassScoreInfoDto> classScoreInfoDtos,CellStyle promptStyle) {
        int index = 0;
        for (ClassScoreInfoDto classScoreInfoDto : classScoreInfoDtos) {
            CellRangeAddress studentNameRange = new CellRangeAddress(rowLine + index, rowLine + index, 0, 1);
            sheet.addMergedRegion(studentNameRange);
            //学生姓名
            sheet.createRow(rowLine + index);
            sheet.getRow(rowLine + index).createCell(0).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).createCell(1).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).getCell(0).setCellValue(classScoreInfoDto.getStudentName());
            //性别
            sheet.getRow(rowLine + index).createCell(2).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).getCell(2).setCellValue(classScoreInfoDto.getSex());
            //完整度得分
            CellRangeAddress completeRange = new CellRangeAddress(rowLine + index, rowLine + index, 3, 4);
            sheet.addMergedRegion(completeRange);
            sheet.getRow(rowLine + index).createCell(3).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).createCell(4).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).getCell(3).setCellValue(Objects.isNull(classScoreInfoDto.getPronCompletionScore()) ? "" : String.valueOf(classScoreInfoDto.getPronCompletionScore()));
            //准确度得分
            CellRangeAddress pronAccuracyRange = new CellRangeAddress(rowLine + index, rowLine + index, 5, 6);
            sheet.addMergedRegion(pronAccuracyRange);
            sheet.getRow(rowLine + index).createCell(5).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).createCell(6).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).getCell(5).setCellValue(Objects.isNull(classScoreInfoDto.getPronAccuracyScore()) ? "" : String.valueOf(classScoreInfoDto.getPronAccuracyScore()));
            //流畅度得分
            CellRangeAddress pronFluencyRange = new CellRangeAddress(rowLine + index, rowLine + index, 7, 8);
            sheet.addMergedRegion(pronFluencyRange);
            sheet.getRow(rowLine + index).createCell(7).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).createCell(8).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).getCell(7).setCellValue(Objects.isNull(classScoreInfoDto.getPronFluencyScore()) ? "" : String.valueOf(classScoreInfoDto.getPronFluencyScore()));
            //综合得分
            CellRangeAddress suggestedScoreRange = new CellRangeAddress(rowLine + index, rowLine + index, 9, 10);
            sheet.addMergedRegion(suggestedScoreRange);
            sheet.getRow(rowLine + index).createCell(9).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).createCell(10).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).getCell(9).setCellValue(Objects.isNull(classScoreInfoDto.getSuggestedScoreScore()) ? "" : String.valueOf(classScoreInfoDto.getSuggestedScoreScore()));
            //排名
            sheet.getRow(rowLine + index).createCell(11).setCellStyle(promptStyle);
            sheet.getRow(rowLine + index).getCell(11).setCellValue(index + 1);
            index++;
        }
    }

}
