package com.yuhua.computerassemblyplatform.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
  @TableName("bm_harddisk")
@ApiModel(value = "Harddisk对象", description = "")
public class Harddisk implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String harddiskName;

    private String harddiskType;

    private String harddiskVolume;

    private String harddiskReadVelocity;

    private String harddiskMoveVelocity;

    private String harddiskFactory;

    private String harddiskPrice;

    private String harddiskImg;


}
