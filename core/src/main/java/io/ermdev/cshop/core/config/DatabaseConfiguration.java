package io.ermdev.cshop.core.config;

import io.ermdev.cshop.data.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseConfiguration implements CommandLineRunner {


    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;
    private TokenRepository tokenRepository;
    private TokenUserRepository tokenUserRepository;

    @Override
    public void run(String... strings) throws Exception {
        userRepository.createTable();
        roleRepository.createTable();
        userRoleRepository.createTable();
        tokenRepository.createTable();
        tokenUserRepository.createTable();
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setUserRoleRepository(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Autowired
    public void setTokenRepository(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Autowired
    public void setTokenUserRepository(TokenUserRepository tokenUserRepository) {
        this.tokenUserRepository = tokenUserRepository;
    }
}
