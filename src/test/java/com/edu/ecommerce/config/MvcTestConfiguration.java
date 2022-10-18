package com.edu.ecommerce.config;

import com.edu.ecommerce.mapper.CategoryMapper;
import com.edu.ecommerce.mapper.SignUpUserMapper;
import com.edu.ecommerce.repository.*;
import com.edu.ecommerce.security.AuthenticatedUser;
import com.edu.ecommerce.service.impl.CategoryServiceImpl;
import com.edu.ecommerce.service.impl.RoleServiceImpl;
import com.edu.ecommerce.service.impl.UserServiceImpl;
import com.edu.ecommerce.service.interfaces.*;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class MvcTestConfiguration {

    @MockBean
    CategoryRepository categoryRepositoryMock;
    @MockBean
    TokenRepository tokenRepositoryMock;
    @MockBean
    CartRepository cartRepositoryMock;
    @MockBean
    FileRepository fileRepositoryMock;
    @MockBean
    OrderItemsRepository orderItemsRepositoryMock;
    @MockBean
    OrderRepository orderRepositoryMock;
    @MockBean
    ProductRepository productRepositoryMock;
    @MockBean
    RoleRepository roleRepositoryMock;
    @MockBean
    UserRepository userRepositoryMock;
    @MockBean
    WishListRepository wishListRepositoryMock;
    @MockBean
    AuthenticatedUser authenticatedUserMock;
    @MockBean
    DataService dataServiceMock;
    @MockBean
    AuthenticationService authenticationServiceMock;
    @MockBean
    SignUpUserMapper signUpUserMapperMock;
    @MockBean
    RoleService roleServiceMock;



   @Bean
    public MapperFacade mapperFacade() {
        return new DefaultMapperFactory.Builder().build().getMapperFacade();
    }

    @Bean
    public CategoryService categoryService() {
        return new CategoryServiceImpl(categoryRepositoryMock);
    }

    @Bean
    public CategoryMapper categoryMapper() {
        return new CategoryMapper();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepositoryMock, authenticatedUserMock, roleRepositoryMock, roleServiceMock, dataServiceMock, authenticationServiceMock, signUpUserMapperMock);    }

    @Bean
    public RoleService roleService() {
        return new RoleServiceImpl(roleRepositoryMock, userService());
    }


}
