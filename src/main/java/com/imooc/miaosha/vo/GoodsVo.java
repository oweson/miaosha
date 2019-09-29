package com.imooc.miaosha.vo;

import java.util.Date;

import com.imooc.miaosha.domain.Goods;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 商品表和秒杀表公用的Vo
 */
@Setter
@Getter
@ToString
public class GoodsVo extends Goods {
    private Double miaoshaPrice;
    private Integer stockCount;
    private Date startDate;
    private Date endDate;
}
