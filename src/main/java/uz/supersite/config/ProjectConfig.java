package uz.supersite.config;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ProjectConfig {
    @Bean
    public Cloudinary getCloudinary(){
        Map config = new HashMap();
        config.put("cloud_name","dgl2xfnnf");
        config.put("api_key","838396344358946");
        config.put("api_secret","derE9wI5wcNu2YBbzBOLF_qBqL4");
        config.put("secure",true);

        return new Cloudinary(config);
    }
}

