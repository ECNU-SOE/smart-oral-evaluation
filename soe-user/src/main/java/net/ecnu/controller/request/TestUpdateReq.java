package net.ecnu.controller.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
public class TestUpdateReq {

    @NotBlank(message = "语料组id不能为空",groups = {Update.class})
    private String id;
    private String courseId;
    private String title;
    private String description;
    private Integer type;
    private Date startTime;
    private Date endTime;
}
