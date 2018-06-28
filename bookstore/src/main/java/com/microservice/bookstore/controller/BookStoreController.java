package com.microservice.bookstore.controller;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api("书本相关api")
@RestController
@RequestMapping("/book")
public class BookStoreController {
	@ApiOperation("根据ID获得书本信息")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="id",dataType="int",required=true,value="书本id",defaultValue="1")
	})
	@ApiResponses({
		@ApiResponse(code=400,message="请求参数没填好"),
		@ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
	})
	@RequestMapping(value="/getBookInfo",method=RequestMethod.GET)
	public String getBookInfo(@RequestParam("id")int id){
		return "Book No."+id;
	}
}
