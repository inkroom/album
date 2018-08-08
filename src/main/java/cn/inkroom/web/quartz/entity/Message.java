package cn.inkroom.web.quartz.entity;

import java.util.Date;

public class Message
{
  private Long id;
  private String sender;
  private Date sendTime;
  private String recever;
  private String content;
  
  public Long getId()
  {
    return this.id;
  }
  
  public void setId(Long id)
  {
    this.id = id;
  }
  
  public String getSender()
  {
    return this.sender;
  }
  
  public void setSender(String sender)
  {
    this.sender = sender;
  }
  
  public Date getSendTime()
  {
    return this.sendTime;
  }
  
  public void setSendTime(Date sendTime)
  {
    this.sendTime = sendTime;
  }
  
  public String getRecever()
  {
    return this.recever;
  }
  
  public void setRecever(String recever)
  {
    this.recever = recever;
  }
  
  public String getContent()
  {
    return this.content;
  }
  
  public void setContent(String content)
  {
    this.content = content;
  }
}
