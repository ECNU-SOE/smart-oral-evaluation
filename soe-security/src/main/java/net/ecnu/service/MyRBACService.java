package net.ecnu.service;

import net.ecnu.mapper.MyUserDetailsServiceMapper;
import net.ecnu.model.JwtProperties;
import net.ecnu.model.MyUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Component("rbacService")
public class MyRBACService {

    @Resource
    private JwtProperties jwtProperties;

    @Resource
    private MyUserDetailsServiceMapper myUserDetailsServiceMapper;

    /**
     * 判断某用户是否具有该request资源的访问权限
     */
    public boolean hasPermission(HttpServletRequest request, Authentication authentication){

        Object principal = authentication.getPrincipal();

        if(principal instanceof UserDetails){

            UserDetails userDetails = ((UserDetails)principal);
            List<GrantedAuthority> authorityList =
                    AuthorityUtils.commaSeparatedStringToAuthorityList(request.getRequestURI());
            return userDetails.getAuthorities().contains(authorityList.get(0))
                    || jwtProperties.getDevOpeningURI().contains(request.getRequestURI());
        }
        return false;
    }

    public MyUserDetails findByUserName(String username) {
        return myUserDetailsServiceMapper.findByUserName(username);
    }

    public List<String> findRoleByUserName(String username) {
        return myUserDetailsServiceMapper.findRoleByUserName(username);
    }

    public List<String> findApiByRoleCode(String roleCode) {
        return myUserDetailsServiceMapper.findApiByRoleCode(roleCode);
    }

}
