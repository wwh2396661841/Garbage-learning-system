package cn.lxsir.uniapp.dto;

import cn.lxsir.uniapp.entity.QuestionBank;
import lombok.Data;

import java.util.List;

@Data
public class TaskAddDto {

    private String classId;

    private List<QuestionBank> questions;
}
