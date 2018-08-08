package cn.inkroom.web.quartz.config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PathConfig
{
  @Value("${image_base_path}")
  private String imageBasePath;
  @Value("${image_base_cover}")
  private String imageBaseCover;
  
  public String getImageBaseCover()
  {
    return this.imageBaseCover;
  }
  
  public String getImageBasePath()
  {
    return this.imageBasePath;
  }
}
