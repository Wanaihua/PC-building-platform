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
  @TableName("bm_mainboard")
@ApiModel(value = "Mainboard对象", description = "")
public class Mainboard implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String mainboardName;

    private String mainboardFactory;

    private String mainboardPrice;

    private String mainboardType;

    private String mainboardImg;

    private String mainboardMemorySticksSlot;


}
