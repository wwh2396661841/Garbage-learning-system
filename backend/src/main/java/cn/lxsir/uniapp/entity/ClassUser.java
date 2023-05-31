package cn.lxsir.uniapp.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import net.sourceforge.pinyin4j.PinyinHelper;
import org.apache.commons.lang.StringUtils;

@Data
@TableName("CLASS_USER")
public class ClassUser {

    @TableId(value = "ID",type= IdType.INPUT)
    private String id;

    @TableField("USER_NAME")
    private String userName;

    @TableField("USER_TYPE")
    private String userType;

    @TableField("NICK_NAME")
    private String nickName;

    @TableField("USER_ID")
    private String userId;

    @TableField("CLASS_NAME")
    private String className;

    @TableField("CLASS_ID")
    private String classId;

    public  String getFirstName(){
        String firstWord = null;

        if(StringUtils.isNotEmpty(nickName)){
            char c = nickName.charAt(0);
            //字母
            if(c >= 65 && c<=90){
                firstWord = String.valueOf(c);
            }
            else if( c >= 97 && c <= 122 ){
                firstWord = String.valueOf(c);
            }
            //汉字
            else if( c >= 19968 && c <= 40869){
                firstWord = String.valueOf(PinyinHelper.toHanyuPinyinStringArray(c)[0].charAt(0));
            }
            else {
                firstWord = "未知";
            }

        }
        else {
            firstWord = "未知";

        }
        return firstWord;
    }

}
