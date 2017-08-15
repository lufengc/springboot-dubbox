package com.dsjk.boot.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.base.Result;
import com.dsjk.boot.common.base.ResultCode;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
import com.dsjk.boot.common.utils.CaptchaUtils;
import com.dsjk.boot.web.security.JwtTokenUtil;
import com.dsjk.boot.web.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author fengcheng
 * @version 2017/8/8
 */
@RestController
public class LoginController {

    @Reference(group = Global.DUBBO_GROUP)
    private UserService userService;

    private final RedisTemplate<String, String> redisTemplate;
    private AuthenticationManager authenticationManager;
    private UserDetailsService userDetailsService;
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public LoginController(RedisTemplate<String, String> redisTemplate,
                           AuthenticationManager authenticationManager,
                           UserDetailsService userDetailsService,
                           JwtTokenUtil jwtTokenUtil) {
        this.redisTemplate = redisTemplate;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @RequestMapping(value = "getCaptcha", method = RequestMethod.GET, produces = MediaType.IMAGE_PNG_VALUE)
    public void getCaptcha(HttpServletResponse response, HttpServletRequest request) throws IOException {
        String width = request.getParameter("width");
        String height = request.getParameter("height");

        //生成验证码
        String uuid = UUID.randomUUID().toString();

        Map<String, Object> image = CaptchaUtils.createImage(width, height);

        //将验证码以<key,value>形式缓存到redis
        redisTemplate.opsForValue().set(uuid, (String) image.get("code"), 3 * 60, TimeUnit.SECONDS);

        //将验证码key，及验证码的图片返回
        Cookie cookie = new Cookie("CaptchaCode", uuid);
        response.addCookie(cookie);
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        OutputStream out = response.getOutputStream();
        ImageIO.write((BufferedImage) image.get("image"), "JPEG", out);
        out.close();

    }

    @RequestMapping("doLogin")
    public Result doLogin(String loginName, String password, String captchaCode, String captchaValue) {

        //验证码校验
        /*String captchaCode = loginParam.getCaptchaCode();
        if (captchaCode == null) {
            return Result.of(ResultCode.INVALID_CAPTCHA);
        }
        String captchaValue = redisTemplate.opsForValue().get(captchaCode);
        if (captchaValue == null) {
            return Result.of(ResultCode.INVALID_CAPTCHA);
        }
        redisTemplate.delete(captchaCode);

        if (Objects.equals(captchaValue, loginParam.getCaptchaValue())) {
            return Result.of(ResultCode.INVALID_CAPTCHA);
        }*/


        // Perform the security
        try {
            final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginName, password));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                return Result.of(ResultCode.INVALID_PASSWORD);
            }
        }

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginName);
        final String token = jwtTokenUtil.generateToken(userDetails);

        Map<String, Object> map = new HashMap<>();
        map.put("token", token);

        return Result.of(map);
    }

    @RequestMapping("register")
    public Result register(User user) {
        return userService.save(user);
    }

    @RequestMapping("refreshToken")
    public Result refreshToken(HttpServletRequest request) {
        String oldToken = request.getHeader(JwtTokenUtil.HEADER);
        final String token = oldToken.substring(JwtTokenUtil.BEARER.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            String newToken = jwtTokenUtil.refreshToken(token);
            return Result.of(newToken);
        }
        return Result.of(ResultCode.FAILD);
    }

}
