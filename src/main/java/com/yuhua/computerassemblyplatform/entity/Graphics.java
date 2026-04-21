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
  @TableName("bm_graphics")
@ApiModel(value = "Graphics对象", description = "")
public class Graphics implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String graphicsName;

    private String graphicsPrice;

    private String graphicsFactory;

    private String graphicsMemory;

    private String graphicsBitWidth;

    private String graphicsFan;

    private String graphicsLight;

    private String graphicsImg;


}
