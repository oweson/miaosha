package com.imooc.miaosha.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
@Setter
@Getter
@ToString
public class OrderInfo {
	private Long id;
	private Long userId;
	private Long goodsId;
	private Long  deliveryAddrId;
	private String goodsName;
	private Integer goodsCount;
	private Double goodsPrice;
	private Integer orderChannel;
	private Integer status;
	private Date createDate;
	private Date payDate;

}
