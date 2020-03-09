package life.guohui.community.dto;

import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long gmtCreate;
    private Integer status;
    private Long notifier;//评论者
    private Long outerid;//问题Id
    private String notifierName;//评论者的名字
    private String outerTitle;//问题提出者的问题标题
    private String typeName;
    private Integer type;
}
