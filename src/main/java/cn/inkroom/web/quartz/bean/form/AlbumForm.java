package cn.inkroom.web.quartz.bean.form;

import cn.inkroom.web.quartz.annotions.Valid;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/21
 * @Time 14:47
 * @Descorption
 */
public class AlbumForm {
    @Valid(minLength = 1, maxLength = 30, name = "相册名称", message = "相册权限格式不对")
    private String name;
    @Valid(regex = "[012]", minLength = 1, maxLength = 1, name = "相册权限", message = "权限格式不对")
    private String authority;
    @Valid(maxLength = 200, name = "相册说明", message = "相册说明格式不对")
    private String content;
    @Valid(regex = "[012]", minLength = 1, maxLength = 1, name = "相册说明", message = "相册类型不对")
    private String type;
    @Valid(minLength = 1, maxLength = 20, name = "问题", emptyAbled = true, message = "问题格式不对")
    private String question;
    @Valid(minLength = 1, maxLength = 30, name = "答案", emptyAbled = true, message = "答案格式不对")
    private String answer;

    private long owner;
    private long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
