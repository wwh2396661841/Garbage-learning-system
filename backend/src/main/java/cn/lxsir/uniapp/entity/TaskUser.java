package cn.lxsir.uniapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("TASK_USER")
public class TaskUser {
    /** 任务id */
   @TableField("TASK_ID")
    private String taskId ;
    /** 用户id */
    @TableField("USER_ID")
    private String userId ;
    /** ID */
    @TableId(value = "ID",type = IdType.INPUT)
    private String id ;
    /** 是否完成 */
    @TableField("FINISH_FLAG")
    private String finishFlag ;

    /** 任务id */
    public String getTaskId(){
        return this.taskId;
    }
    /** 任务id */
    public void setTaskId(String taskId){
        this.taskId=taskId;
    }
    /** 用户id */
    public String getUserId(){
        return this.userId;
    }
    /** 用户id */
    public void setUserId(String userId){
        this.userId=userId;
    }
    /** ID */
    public String getId(){
        return this.id;
    }
    /** ID */
    public void setId(String id){
        this.id=id;
    }
    /** 是否完成 */
    public String getFinishFlag(){
        return this.finishFlag;
    }
    /** 是否完成 */
    public void setFinishFlag(String finishFlag){
        this.finishFlag=finishFlag;
    }
}
