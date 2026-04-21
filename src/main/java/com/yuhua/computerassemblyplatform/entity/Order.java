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
 * @since 2024-01-14
 */
@Getter
@Setter
  @TableName("bm_order")
@ApiModel(value = "Order对象", description = "")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private Integer userId;

    private Integer chassisId;

    private Integer coolingId;

    private Integer cpuId;

    private Integer graphicsId;

    private Integer hardDiskId;

    private Integer keyboardId;

    private Integer mainboardId;

    private Integer memorySticksId;

    private Integer mouseId;

    private Integer platformId;

    private Integer powerId;

    private Integer audioId;

    private String createTime;

    private String state;

    private Double TotalPrice;

}
