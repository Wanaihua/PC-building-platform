package com.yuhua.computerassemblyplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author YuHua
 * @since 2024-01-04
 */
@Getter
@Setter
  @TableName("bm_keyboard")
@ApiModel(value = "Keyboard对象", description = "")
public class Keyboard implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String keyboardName;

    private String keyboardType;

    private String keyboardFactory;

    private String keyboardPrice;

    private String keyboardImg;

    private Date keyboardTime;

    private String keyboardColor;


}
