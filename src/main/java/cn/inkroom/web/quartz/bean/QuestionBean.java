package cn.inkroom.web.quartz.bean;

/**
 * @author 墨盒
 * @version 1.0
 * @Date 2017/8/21
 * @Time 17:23
 * @Descorption
 */
public class QuestionBean {
    private long id;
    private String question;
    private String answer;
    private long owner;

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "QuestionBean{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                '}';
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
