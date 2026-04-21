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
 * @since 2024-01-04
 */
@Getter
@Setter
  @TableName("bm_platform")
@ApiModel(value = "Platform对象", description = "")
public class Platform implements Serializable {

    private static final long serialVersionUID = 1L;

      @TableId(value = "id", type = IdType.AUTO)
      private Integer id;

    private String platformName;

    private String platformPrice;

    private String platformSize;

    private String platformResolution;

    private String platformImg;

    private String platformPort;

    private String platformRefreshRate;

    private String platformFactory;


}
