package com.edu.userservice.configuration;

import com.edu.userservice.dto.role.RoleDto;
import com.edu.userservice.model.UserRole;
import ma.glasnost.orika.MapperFactory;
import net.rakugakibox.spring.boot.orika.OrikaMapperFactoryConfigurer;
import org.springframework.stereotype.Component;

@Component
public class OrikaMapperConfig implements OrikaMapperFactoryConfigurer {

    @Override
    public void configure(MapperFactory mapperFactory) {
        mapperFactory.classMap(UserRole.class, RoleDto.class).byDefault().register();
        mapperFactory.classMap(RoleDto.class, UserRole.class).byDefault().register();
    }
}
