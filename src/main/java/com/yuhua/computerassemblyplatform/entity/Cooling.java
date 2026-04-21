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
 * @since 2024-01-03
 */
@Getter
@Setter
  @TableName("bm_cooling")
@ApiModel(value = "Cooling对象", description = "")
public class Cooling implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String coolingName;

    private String coolingWay;

    private String coolingHeatPipes;

    private String coolingPrice;

    private String coolingFactory;

    private String coolingImg;

    private Date coolingTime;


}
