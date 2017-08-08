package com.dsjk.boot.web.user;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dsjk.boot.common.base.Global;
import com.dsjk.boot.common.base.Result;
import com.dsjk.boot.common.base.ResultCode;
import com.dsjk.boot.common.bean.user.LoginParam;
import com.dsjk.boot.common.bean.user.User;
import com.dsjk.boot.common.service.user.UserService;
import com.dsjk.boot.common.utils.CaptchaUtils;
import com.dsjk.boot.common.utils.Encodes;
import com.dsjk.boot.web.config.Audience;
import com.dsjk.boot.web.config.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
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

    private final Audience audience;
    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public LoginController(Audience audience, RedisTemplate<String, String> redisTemplate) {
        this.audience = audience;
        this.redisTemplate = redisTemplate;
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
    public Object getAccessToken(LoginParam loginParam) {

        //验证码校验
//        String captchaCode = loginParam.getCaptchaCode();
//        if (captchaCode == null) {
//            return Result.of(ResultCode.INVALID_CAPTCHA);
//        }
//        String captchaValue = redisTemplate.opsForValue().get(captchaCode);
//        if (captchaValue == null) {
//            return Result.of(ResultCode.INVALID_CAPTCHA);
//        }
//        redisTemplate.delete(captchaCode);
//
//        if (Objects.equals(captchaValue, loginParam.getCaptchaValue())) {
//            return Result.of(ResultCode.INVALID_CAPTCHA);
//        }


        if (loginParam.getClientId() == null
                || (loginParam.getClientId().compareTo(audience.getClientId()) != 0)) {
            return Result.of(ResultCode.INVALID_CLIENTID);
        }

        //验证用户名密码
        User user = userService.getUserByLoginName(loginParam.getUserName());
        if (user == null) {
            return Result.of(ResultCode.INVALID_PASSWORD);
        } else {
            if (!Encodes.validatePassword(loginParam.getPassword(), user.getPassword())) {
                return Result.of(ResultCode.INVALID_PASSWORD);
            }
        }

        //拼装accessToken
        String token = JwtHelper.createJWT(loginParam.getUserName(), user.getId(),
                "admin", audience.getClientId(), audience.getName(),
                audience.getExpiresSecond() * 1000, audience.getBase64Secret());

        //返回accessToken
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("tokenType", "bearer");
        map.put("expiresIn", audience.getExpiresSecond());
        return Result.of(map);
    }

}
