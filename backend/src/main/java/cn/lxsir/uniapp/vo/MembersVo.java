package cn.lxsir.uniapp.vo;

import lombok.Data;

import java.util.List;

@Data
public class MembersVo {

    private List<String> keyList;

    private List<List> memberList;
}
