package ${package}.reponse;

import ${package}.reponse.dto.UsersDtoRp;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class UsersRP extends ResponseBase<List<UsersDtoRp>> {

  @ApiModelProperty(notes = ApiModelPropertyBase
  )
  @Override
  public Integer getStatus() {
    return super.getStatus();

  }
}
