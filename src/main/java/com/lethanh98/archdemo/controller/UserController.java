package com.lethanh98.archdemo.controller;

import com.google.common.util.concurrent.ListenableFuture;
import com.lethanh98.archdemo.entity.User;
import com.lethanh98.archdemo.repo.UserRepo;
import com.lethanh98.archdemo.reponse.UsersRP;
import com.lethanh98.archdemo.reponse.dto.UsersDtoRp;
import com.lethanh98.archdemo.request.PostUserRQ;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/api/users")
public class UserController extends BaseResource {

    @Autowired
    UserRepo userRepo;

    @GetMapping()
    public DeferredResult<?> get() {
        ListenableFuture<?> future = asyncExecute(() -> {
            log.info("test");
            List<User> users = (List<User>) userRepo.findAll();
            List<UsersDtoRp> usersDTOPR = new ArrayList<>();
            users.stream().forEach(user -> {
                UsersDtoRp usersDtoRp = new UsersDtoRp();
                BeanUtils.copyProperties(user, usersDtoRp);
                usersDTOPR.add(usersDtoRp);
            });
            UsersRP usersRP = new UsersRP();
            usersRP.setData(usersDTOPR);
            usersRP.setStatus(200);
            return usersRP;
        });
        return callBackResponse(future);

    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public DeferredResult<?> post(@Valid @RequestBody() PostUserRQ userRQ,
                                  @PathVariable(value = "id", required = false) Integer id,
                                  @RequestHeader(value = "authen") String authen) {
        ListenableFuture<?> future = asyncExecute(() -> {
            log.info("test");
            User user = new User();
            user.setFirstName(userRQ.getFirstName());
            user.setLastName(userRQ.getFirstName());
            return userRepo.save(user);
        });
        return callBackResponse(future);

    }

}
