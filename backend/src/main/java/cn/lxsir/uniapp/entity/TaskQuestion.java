package cn.lxsir.uniapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("TASK_QUESTION")
public class TaskQuestion {
    /** 问题id */
    @TableField("QUESTION_ID")
    private Integer questionId ;
    /** 任务id */
    @TableField("TASK_ID")
    private String taskId ;

    @TableField("garbage_type")
    private Integer garbageType;

    @TableField("garbage_name")
    private String garbageName;
    /** ID */
    @TableId(value = "ID",type = IdType.INPUT)
    private String id ;

    /** 问题id */
    public Integer getQuestionId(){
        return this.questionId;
    }
    /** 问题id */
    public void setQuestionId(Integer questionId){
        this.questionId=questionId;
    }
    /** 任务id */
    public String getTaskId(){
        return this.taskId;
    }
    /** 任务id */
    public void setTaskId(String taskId){
        this.taskId=taskId;
    }
    /** ID */
    public String getId(){
        return this.id;
    }
    /** ID */
    public void setId(String id){
        this.id=id;
    }
}
