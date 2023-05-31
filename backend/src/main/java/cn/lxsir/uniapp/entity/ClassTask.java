package cn.lxsir.uniapp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("CLASS_TASK")
public class ClassTask {
    /** 创建人 */
    @TableField("CREATE_BY")
    private String createdBy ;
    /** 创建时间 */
    @TableField("CREATE_TIME")
    private Date createdTime ;
    /** 更新人 */
    @TableField("UPDATE_BY")
    private String updatedBy ;
    /** 更新时间 */
    @TableField("UPDATE_TIME")
    private Date updatedTime ;
    /** 截止天数 */
    @TableField("LIMIT_DAY")
    private Integer limitDay ;
    /** 班级id */
    @TableField("CLASS_ID")
    private String classId ;
    /** 任务id */
    @TableField("TASK_ID")
    private String taskId ;
    /** 问题数量 */
    @TableField("QUESTION_NUM")
    private Integer questionNum ;

}
