package net.ecnu.controller.request;

import lombok.Data;
import net.ecnu.controller.group.Create;
import net.ecnu.controller.group.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class TagFilterReq {

    private List<Integer> ids;

    private String name;

    private Double weight;

    private Integer category;


}
