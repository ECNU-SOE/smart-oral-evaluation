package net.ecnu.domain;

import lombok.Data;

import java.util.Date;

@Data
public class Cpsgrp {
    private String id;
    private int type;
    private int level;
    private String name;
    private String description;
    private Date startTime;
    private Date endTime;
    private String creator;
    private Date gmtCreate;
    private Date gmtModified;
}
