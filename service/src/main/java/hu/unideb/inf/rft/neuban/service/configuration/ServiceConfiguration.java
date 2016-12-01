package hu.unideb.inf.rft.neuban.service.configuration;

import hu.unideb.inf.rft.neuban.persistence.configuration.PersistenceConfiguration;
import hu.unideb.inf.rft.neuban.persistence.entities.UserEntity;
import hu.unideb.inf.rft.neuban.service.converter.impl.ListDataConverterServiceImpl;
import hu.unideb.inf.rft.neuban.service.converter.impl.SingleDataConverterServiceImpl;
import hu.unideb.inf.rft.neuban.service.domain.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@ComponentScan(basePackages = "hu.unideb.inf.rft.neuban.service")
@Import(PersistenceConfiguration.class)
public class ServiceConfiguration {

    @Autowired
    private WebApplicationContext context;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SingleDataConverterServiceImpl singleUserDataConverterService() {
        return new SingleDataConverterServiceImpl(UserEntity.class, UserDto.class, this.context.getBean(ModelMapper.class));
    }

    @Bean
    public ListDataConverterServiceImpl listUserDataConverterService() {
        return new ListDataConverterServiceImpl(UserEntity.class, UserDto.class, this.context.getBean(ModelMapper.class));
    }
}
