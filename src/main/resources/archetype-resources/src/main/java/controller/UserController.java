package ${package}.controller;

import ${package}.annotation.ApiResponsesBase;
import ${package}.entity.User;
import ${package}.repo.UserRepo;
import ${package}.reponse.ResponseBase;
import ${package}.reponse.UsersRP;
import ${package}.reponse.dto.UsersDtoRp;
import ${package}.request.PostUserRQ;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/users")
@ApiResponsesBase()
public class UserController {

  @Autowired
  UserRepo userRepo;

  @GetMapping()
  @ApiOperation(value = "Lấy tất cả users", response = UsersRP.class, authorizations = @Authorization("Authen_web_admin"))
  public ResponseBase get() {
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
  }

  @PostMapping()
  @Transactional
  public User post(@Valid @RequestBody() PostUserRQ userRQ,
      @PathVariable(value = "id", required = false) Integer id,
      @RequestHeader(value = "authen") String authen
  ) {
    User user = new User();
    user.setFirstName(userRQ.getFirstName());
    user.setLastName(userRQ.getFirstName());
    return userRepo.save(user);
  }

}
