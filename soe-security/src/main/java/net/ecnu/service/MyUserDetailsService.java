package net.ecnu.service;

import net.ecnu.model.MyUserDetails;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MyUserDetailsService implements UserDetailsService {

    @Resource
    MyRBACService myRBACService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //加载基础用户信息
        MyUserDetails myUserDetails = myRBACService.findByUserName(username);

        //加载用户角色列表
        List<String> roleCodes = myRBACService.findRoleByUserName(username);

        List<String> authorities = new ArrayList<>();
        for(String roleCode : roleCodes){
            //通过用户角色列表加载用户的资源权限列表
            authorities.addAll(myRBACService.findApiByRoleCode(roleCode));
        }

        //角色是一个特殊的权限，ROLE_前缀
        roleCodes = roleCodes.stream()
                .map(rc -> "ROLE_" +rc)
                .collect(Collectors.toList());

        authorities.addAll(roleCodes);

        myUserDetails.setAuthorities(
                AuthorityUtils.commaSeparatedStringToAuthorityList(
                        String.join(",",authorities)
                )
        );
        return myUserDetails;
    }
}
