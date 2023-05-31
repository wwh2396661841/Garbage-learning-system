package cn.lxsir.uniapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("TASK_QUESTION_RESULT")
public class TaskResult {

    @TableId(value = "ID",type = IdType.INPUT)
    private String id;

    @TableField("question_id")
    private String questionId;

    @TableField("whether")
    private String whether;

    @TableField("garbage_name")
    private String garbageName;

    @TableField("garbage_type")
    private String garbageType;

    @TableField("selected_type")
    private String selectedType;

    @TableField("user_id")
    private String userId;

    @TableField("task_id")
    private String taskId;

    @TableField("class_id")
    private String classId;
}
